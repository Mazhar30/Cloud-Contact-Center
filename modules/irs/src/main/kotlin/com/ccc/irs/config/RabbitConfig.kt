package com.ccc.irs.config

import com.ccc.irs.message.AgentAvailabilityUpdateMessage
import com.ccc.irs.message.CustomerRequest
import org.springframework.amqp.core.Queue
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class RabbitConfig {

    companion object {
        const val AGENT_QUEUE_NAME = "agent-availability-queue"
        const val CUSTOMER_QUEUE_NAME = "customer-request-queue"
    }

    @Bean
    open fun agentAvailabilityQueue(): Queue {
        return Queue(AGENT_QUEUE_NAME, false)
    }

    @Bean
    open fun customerRequestQueue(): Queue {
        return Queue(CUSTOMER_QUEUE_NAME, false)
    }

    @Bean
    open fun jackson2JsonMessageConverter(): Jackson2JsonMessageConverter {
        val converter = Jackson2JsonMessageConverter()

        val typeMapper = DefaultJackson2JavaTypeMapper()
        val idClassMapping = mapOf(
            "com.ccc.irs.message.AgentAvailabilityUpdateMessage" to AgentAvailabilityUpdateMessage::class.java,
            "com.ccc.irs.message.CustomerRequest" to CustomerRequest::class.java
        )

        typeMapper.setIdClassMapping(idClassMapping)
        converter.setJavaTypeMapper(typeMapper)
        return converter
    }

    @Bean
    open fun rabbitTemplate(connectionFactory: ConnectionFactory): RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory)
        rabbitTemplate.messageConverter = jackson2JsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    open fun rabbitListenerContainerFactory(connectionFactory: ConnectionFactory): SimpleRabbitListenerContainerFactory {
        val factory = SimpleRabbitListenerContainerFactory()
        factory.setConnectionFactory(connectionFactory)
        factory.setMessageConverter(jackson2JsonMessageConverter())
        return factory
    }
}
