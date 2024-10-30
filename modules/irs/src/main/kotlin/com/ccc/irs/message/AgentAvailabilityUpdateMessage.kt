package com.ccc.irs.message

import java.io.Serializable

data class AgentAvailabilityUpdateMessage(
    var agentId: String = "",
    var available: Boolean = false
) : Serializable
