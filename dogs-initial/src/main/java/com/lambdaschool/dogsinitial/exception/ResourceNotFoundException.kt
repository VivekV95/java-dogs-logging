package com.lambdaschool.dogsinitial.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException: RuntimeException {
    companion object {
        @JvmStatic
        private val serialVersionUID = 1L
    }
    constructor(message: String): super(message)

    constructor(message: String, cause: Throwable): super(message, cause)
}