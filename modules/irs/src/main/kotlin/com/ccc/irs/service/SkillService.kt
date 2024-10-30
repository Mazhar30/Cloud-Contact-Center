package com.ccc.irs.service

import com.ccc.irs.model.Skill
import com.ccc.irs.repository.SkillRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SkillService @Autowired constructor(
    private val skillRepository: SkillRepository
) {

    fun getAllSkills(): List<Skill> {
        return skillRepository.findAll()
    }

    fun getSkillById(id: Long): Optional<Skill> {
        return skillRepository.findById(id)
    }

    fun getSkillByName(name: String): Optional<Skill> {
        return skillRepository.findByName(name)
    }

    fun addSkill(skill: Skill): Skill {
        return skillRepository.save(skill)
    }

    fun updateSkill(skill: Skill): Skill? {
        val existingSkill = skill.id?.let { getSkillById(it).orElse(null) } ?: return null
        existingSkill.name = skill.name
        return skillRepository.save(existingSkill)
    }

    fun deleteSkill(id: Long) {
        skillRepository.deleteById(id)
    }
}
