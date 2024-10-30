package com.ccc.irs.model

import jakarta.persistence.*
import lombok.*

@Getter
@Setter
@Entity
data class Skill(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    var name: String = ""
)