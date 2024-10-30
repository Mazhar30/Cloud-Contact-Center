package com.ccc.irs.service

import com.ccc.irs.dto.AgentDTO
import com.ccc.irs.dto.SkillDTO
import com.ccc.irs.model.Agent
import com.ccc.irs.repository.AgentRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AgentService @Autowired constructor(
    private val agentRepository: AgentRepository,
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private companion object {
        const val AGENT_CACHE_KEY = "available_agents"
    }

    @PostConstruct
    fun populateAvailableAgentsInCache() {
        getAllAvailableAgents().forEach { agent ->
            val agentDTO = convertToDTO(agent)
            redisTemplate.opsForHash<Any, Any>().put(AGENT_CACHE_KEY, agentDTO.id, agentDTO)
        }
    }

    fun findAvailableAgent(skillName: String): Mono<AgentDTO?> {
        val agentKeys = redisTemplate.opsForHash<Any, Any>().keys(AGENT_CACHE_KEY)
        println(agentKeys)
        return Flux.fromIterable(agentKeys)
            .flatMap { agentId ->
                val agent = redisTemplate.opsForHash<Any, AgentDTO>().get(AGENT_CACHE_KEY, agentId)
                println("Processing agent [$agent]")
                if (agent != null && agent.skills.any { it.name == skillName }) {
                    Mono.just(agent)
                } else {
                    Mono.empty()
                }
            }
            .next()
            .switchIfEmpty(Mono.empty())
    }


    fun updateAgentAvailability(agentId: String, isAvailable: Boolean): AgentDTO? {
        val agent = agentRepository.findById(agentId).orElse(null) ?: return null
        agent.available = isAvailable
        agentRepository.save(agent)

        val agentDto = convertToDTO(agent)

        // Update Redis cache
        if (isAvailable) {
            println("adding agent to cache [$agent]")
            redisTemplate.opsForHash<Any, Any>().put(AGENT_CACHE_KEY, agentId, agentDto)
        } else {
            println("removing agent from cache [$agent]")
            redisTemplate.opsForHash<Any, Any>().delete(AGENT_CACHE_KEY, agentId)
        }
        return agentDto
    }

    fun findAgent(agentId: String): Mono<AgentDTO?> {
        val agent = agentRepository.findById(agentId).orElse(null) ?: return Mono.empty()
        return Mono.just(convertToDTO(agent))
    }

    fun getAllAgents(): List<Agent> {
        return agentRepository.findAll()
    }

    private fun getAllAvailableAgents(): List<Agent> {
        return agentRepository.findAllByAvailable(true)
    }

    private fun convertToDTO(agent: Agent): AgentDTO {
        val skillDTOs = agent.skills.map { SkillDTO(it.id, it.name) }
        return AgentDTO(agent.id, agent.available, skillDTOs)
    }
}
