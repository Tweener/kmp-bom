package com.tweener.common._internal.provider

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

const val LOCALE_LANGUAGE_DEFAULT = "en"
const val LOCALE_COUNTRY_DEFAULT = "US"

interface LocaleProvider {
    /**
     * Returns the language code of this Locale.
     */
    fun getLanguage(): String

    /**
     * Returns the country/region code for this locale, which should either be the empty string, an uppercase ISO 3166 2-letter code, or a UN M.49 3-digit code.
     */
    fun getCountry(): String
}

expect fun createLocaleProvider(): LocaleProvider
