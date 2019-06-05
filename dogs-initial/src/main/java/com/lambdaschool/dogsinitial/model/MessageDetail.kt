package com.lambdaschool.dogsinitial.model

data class MessageDetail(var text: String? =null ,
                         var priority: Int? = null,
                         var secret: Boolean? = null) {
    override fun toString(): String {
        return "MessageDetail(text=$text, priority=$priority, secret=$secret)"
    }
}