package com.ccc.irs.message

import com.ccc.irs.config.RabbitConfig
import com.ccc.irs.service.AgentService
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import java.util.concurrent.Executors
import java.time.Duration

@Service
class MessageListener @Autowired constructor(
    private val agentService: AgentService,
    private val messageProducer: MessageProducer,
    private val rabbitTemplate: RabbitTemplate
) {

    private val scheduler = Executors.newScheduledThreadPool(1)

    @RabbitListener(queues = [RabbitConfig.AGENT_QUEUE_NAME])
    fun handleAgentAvailabilityUpdate(message: AgentAvailabilityUpdateMessage) {
        println("Received agent availability update: $message")
        val agent = agentService.updateAgentAvailability(message.agentId, message.available)
        if (agent != null && message.available) {
            processNextCustomerRequest(agent.id)
        }
    }

    @RabbitListener(queues = [RabbitConfig.CUSTOMER_QUEUE_NAME])
    fun handleCustomerRequest(customerRequest: CustomerRequest) {
        println("Received customer request: $customerRequest")
        agentService.findAvailableAgent(customerRequest.interactionType)
            .flatMap { agent ->
                if (agent != null) {
                    agentService.updateAgentAvailability(agent.id, false)
                    Mono.just(agent)
                } else {
                    Mono.delay(Duration.ofSeconds(5))
                        .doOnNext {
                            messageProducer.sendCustomerRequest(customerRequest)
                        }
                        .then() // Use .then() to complete the Mono chain after the delay
                }
            }
            .switchIfEmpty(
                Mono.delay(Duration.ofSeconds(5))
                    .doOnNext {
                        println("No agents found initially, sending customer request message to RabbitMQ after delay")
                        messageProducer.sendCustomerRequest(customerRequest)
                    }
                    .then(Mono.empty())
            )
            .subscribe()
    }

    private fun processNextCustomerRequest(agentId: String) {
        val customerRequest = rabbitTemplate.receiveAndConvert(RabbitConfig.CUSTOMER_QUEUE_NAME) as? CustomerRequest
        if (customerRequest != null) {
            agentService.updateAgentAvailability(agentId, false)
        }
    }
}
