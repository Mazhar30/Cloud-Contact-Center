package com.example.mhs.controller

import com.example.mhs.model.CallHistory
import com.example.mhs.model.ChatMessage
import com.example.mhs.repository.CallHistoryRepository
import com.example.mhs.repository.ChatMessageRepository
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional

@Controller
class MessageController(
    private val messagingTemplate: SimpMessagingTemplate,
    private val chatMessageRepository: ChatMessageRepository,
    private val callHistoryRepository: CallHistoryRepository
) {

    @MessageMapping("/sendMessage")
    @Transactional
    fun sendMessage(chatMessage: ChatMessage) {
        println("Received message: $chatMessage")
        chatMessageRepository.save(chatMessage)
        messagingTemplate.convertAndSend("/topic/messages", chatMessage)
    }

    @MessageMapping("/makeCall")
    @Transactional
    fun makeCall(callHistory: CallHistory) {
        callHistoryRepository.save(callHistory)
        messagingTemplate.convertAndSend("/topic/calls", callHistory)
    }
}