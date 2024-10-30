package com.ccc.irs.controller

import com.ccc.irs.message.AgentAvailabilityUpdateMessage
import com.ccc.irs.message.CustomerRequest
import com.ccc.irs.service.AgentService
import com.ccc.irs.message.MessageProducer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/interact")
class InteractionController @Autowired constructor(
    private val agentService: AgentService,
    private val messageProducer: MessageProducer
) {

    @PostMapping("/getAgent")
    fun getAgent(@RequestBody customerRequest: CustomerRequest): Mono<ResponseEntity<String>> {
        return agentService.findAvailableAgent(customerRequest.interactionType)
            .flatMap { agent ->
                println("Agent found: $agent")
                if (agent != null) {
                    println("Sending agent availability message to RabbitMQ")
                    messageProducer.sendAvailabilityMessage(agent.id, false)
                    Mono.just(ResponseEntity.ok("Interaction routed to ${agent.id}"))
                } else {
                    println("Sending customer request message to RabbitMQ")
                    messageProducer.sendCustomerRequest(customerRequest)
                    Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No available agents"))
                }
            }
            .switchIfEmpty(
                Mono.defer {
                    println("Sending customer request message to RabbitMQ (from switchIfEmpty)")
                    messageProducer.sendCustomerRequest(customerRequest)
                    Mono.just(ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("No available agents"))
                }
            )
            .onErrorResume { e ->
                println("Error retrieving agent: ${e.message}") // Add error logging
                Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred"))
            }
    }


    @PostMapping("/updateAgentAvailability")
    fun updateAgentAvailability(@RequestBody request: AgentAvailabilityUpdateMessage): Mono<ResponseEntity<String>> {
        return agentService.findAgent(request.agentId)
            .flatMap { agent ->
                if (agent != null) {
                    println("Sending agent availability message to RabbitMQ")
                    messageProducer.sendAvailabilityMessage(request.agentId, request.available)
                    Mono.just(ResponseEntity.ok("Availability Updated"))
                } else {
                    Mono.just(ResponseEntity.status(503).body("No agents found"))
                }
            }
            .switchIfEmpty(Mono.just(ResponseEntity.status(503).body("No agents found")))
            .onErrorResume { e ->
                println("Error updating agent availability: ${e.message}")
                Mono.just(ResponseEntity.status(500).body("An error occurred"))
            }
    }
}