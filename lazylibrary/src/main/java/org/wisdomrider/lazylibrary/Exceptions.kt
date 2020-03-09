package org.wisdomrider.lazylibrary

import java.lang.Exception

class LazyModuleNotFoundException : Exception() {
    override fun getLocalizedMessage(): String {
        return "Module is not valid."
    }

    override val message: String?
        get() = "Module is not valid."
}