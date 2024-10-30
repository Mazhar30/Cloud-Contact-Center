package com.ccc.irs.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializer

@Configuration
@EnableCaching
open class RedisConfig {

    @Value("\${spring.redis.host}")
    private val host: String = ""

    @Value("\${spring.redis.port}")
    private val port: Int? = null

    @Bean
    open fun jedisConnectionFactory(): JedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = host
        config.port = port!!
        return JedisConnectionFactory(config)
    }

    @Bean
    open fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory

        // Configure serializers
        template.valueSerializer = GenericJackson2JsonRedisSerializer() // Use this for JSON serialization
        template.keySerializer = RedisSerializer.string() // Use string for keys
        template.hashValueSerializer = GenericJackson2JsonRedisSerializer()
        template.hashKeySerializer = RedisSerializer.string()

        return template
    }
}
