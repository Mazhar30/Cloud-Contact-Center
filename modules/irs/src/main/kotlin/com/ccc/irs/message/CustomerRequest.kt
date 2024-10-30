package com.ccc.irs.message

import java.io.Serializable

data class CustomerRequest(
    var requestId: String? = null,
    var interactionType: String = "",
    var customerId: String? = null
) : Serializable
