package com.ccc.irs.repository

import com.ccc.irs.model.Skill
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SkillRepository  : JpaRepository<Skill, Long> {
    fun findByName(name: String): Optional<Skill>
}