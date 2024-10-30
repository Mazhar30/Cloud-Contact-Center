package com.ccc.irs.controller

import com.ccc.irs.model.Skill
import com.ccc.irs.service.SkillService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/skill")
class SkillController @Autowired constructor(
    private val skillService: SkillService
) {

    @GetMapping("/getAllSkills")
    fun getAllSkills(): ResponseEntity<List<Skill>> {
        val skills = skillService.getAllSkills()
        return ResponseEntity.ok(skills)
    }

    @GetMapping("/getSkill/{id}")
    fun getSkill(@PathVariable id: Long): ResponseEntity<Skill> {
        val skill = skillService.getSkillById(id)
        return skill.map { ResponseEntity.ok(it) }
            .orElseGet { ResponseEntity.ofNullable(null) }
    }

    @PostMapping("/addSkill")
    fun createSkill(@RequestBody skill: Skill): Skill {
        return skillService.addSkill(skill)
    }

    @DeleteMapping("/deleteSkill/{id}")
    fun deleteSkill(@PathVariable id: Long): ResponseEntity<Void> {
        skillService.deleteSkill(id)
        return ResponseEntity.ok().build()
    }
}
