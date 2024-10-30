package com.example.mhs.model

import jakarta.persistence.*

@Entity
data class CallHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val callerId: String,
    val receiverId: String,
    val callDuration: Long,
    val timestamp: Long
)
