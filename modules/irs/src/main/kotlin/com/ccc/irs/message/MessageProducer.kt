package com.ccc.irs.message

import com.ccc.irs.config.RabbitConfig
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MessageProducer @Autowired constructor(
    private val rabbitTemplate: RabbitTemplate
) {

    fun sendAvailabilityMessage(agentId: String, available: Boolean) {
        val message = AgentAvailabilityUpdateMessage(agentId, available)
        rabbitTemplate.convertAndSend(RabbitConfig.AGENT_QUEUE_NAME, message)
    }

    fun sendCustomerRequest(request: CustomerRequest) {
        rabbitTemplate.convertAndSend(RabbitConfig.CUSTOMER_QUEUE_NAME, request)
    }
}
