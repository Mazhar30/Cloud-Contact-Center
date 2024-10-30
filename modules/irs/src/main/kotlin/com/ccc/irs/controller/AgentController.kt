package com.ccc.irs.controller

import com.ccc.irs.dto.AgentDTO
import com.ccc.irs.model.Agent
import com.ccc.irs.service.AgentService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/agent")
class AgentController @Autowired constructor(
    private val agentService: AgentService
) {

    @GetMapping("/getAllSkills")
    fun getAgents(): ResponseEntity<List<Agent>> {
        val agents = agentService.getAllAgents()
        return ResponseEntity.ok(agents)
    }

    @GetMapping("/getAgentById/{id}")
    fun getAgent(@PathVariable id: String): Mono<ResponseEntity<AgentDTO>> {
        return agentService.findAgent(id).flatMap {
            Mono.just(ResponseEntity.ok(it))
        }
    }
}
