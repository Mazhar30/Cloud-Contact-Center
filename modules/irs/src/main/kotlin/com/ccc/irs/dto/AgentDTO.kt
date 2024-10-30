package com.ccc.irs.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AgentDTO @JsonCreator constructor(
    @JsonProperty("id") val id: String,
    @JsonProperty("available") val available: Boolean,
    @JsonProperty("skills") val skills: List<SkillDTO>
)