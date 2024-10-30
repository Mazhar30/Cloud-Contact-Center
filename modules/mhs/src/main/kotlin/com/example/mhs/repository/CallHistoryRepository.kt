package com.example.mhs.repository

import com.example.mhs.model.CallHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CallHistoryRepository : JpaRepository<CallHistory, Long>
