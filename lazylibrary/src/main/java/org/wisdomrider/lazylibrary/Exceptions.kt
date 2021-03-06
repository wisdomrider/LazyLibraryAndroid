package org.wisdomrider.lazylibrary

import java.lang.Exception

class LazyModuleNotFoundException(message: String?) : Exception() {
    override fun getLocalizedMessage(): String {
        return "Module is not valid.\n$message"
    }

    override val message: String?
        get() = "Module is not valid.\n$message"
}

class NotFoundException(m: String?) : Exception() {
    override fun getLocalizedMessage(): String {
        return "Unable to find what you are searching.\n$message"
    }

    override val message: String?
        get() = "Unable to find what you are searching.\n$message"
}

