package com.ccc.irs.model

import jakarta.persistence.*
import lombok.*

@Getter
@Setter
@Entity
data class Agent(

    @Id
    var id: String = "",
    var available: Boolean = false,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "agent_skill",
        joinColumns = [JoinColumn(name = "agent_id")],
        inverseJoinColumns = [JoinColumn(name = "skill_id")]
    )
    var skills: List<Skill> = listOf()
)
