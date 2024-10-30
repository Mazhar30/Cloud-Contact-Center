package com.example.mhs.model

import jakarta.persistence.*

@Entity
data class ChatMessage(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val senderId: String,
    val receiverId: String,
    val message: String,
    val timestamp: Long
)

