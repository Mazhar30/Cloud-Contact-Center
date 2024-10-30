package com.ccc.irs.repository

import com.ccc.irs.model.Agent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AgentRepository : JpaRepository<Agent, String> {

    @Query("SELECT a FROM Agent a JOIN a.skills s WHERE a.available = true AND s.name = :skillName")
    fun findFirstByAvailableTrueAndSkillName(@Param("skillName") skillName: String): Optional<Agent>

    fun findAllByAvailable(state: Boolean): List<Agent>
}
