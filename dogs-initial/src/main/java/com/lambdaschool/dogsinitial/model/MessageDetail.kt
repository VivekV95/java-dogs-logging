package com.lambdaschool.dogsinitial.model

import java.io.Serializable

data class MessageDetail(var text: String? =null ,
                         var priority: Int? = null,
                         var secret: Boolean? = null): Serializable {
    override fun toString(): String {
        return "MessageDetail(text=$text, priority=$priority, secret=$secret)"
    }
}