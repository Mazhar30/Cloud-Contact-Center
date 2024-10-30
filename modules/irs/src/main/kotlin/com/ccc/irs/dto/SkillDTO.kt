package com.ccc.irs.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SkillDTO @JsonCreator constructor(
    @JsonProperty("id")
    val id: Long,

    @JsonProperty("name")
    val name: String
)