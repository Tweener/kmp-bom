package com.tweener.common._internal.validator

/**
 * @author Vivien Mahe
 * @since 13/07/2024
 */
class EmailAddressValidator {

    companion object {
        private const val PATTERN =
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    }

    fun isValid(email: String): Boolean = email.matches(Regex(PATTERN))

}
