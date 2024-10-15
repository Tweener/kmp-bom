package com.tweener.common._internal.provider

/**
 * @author Vivien Mahe
 * @since 19/12/2023
 */

data class Locale(
    val language: String,
    val country: String,
    val script: String? = null,
    val variant: String? = null,
    val extensions: Map<Char, String>? = null,
) {
    override fun toString(): String = language + "_" + country
}

val DEFAULT_LOCALE = Locale(language = LOCALE_LANGUAGE_DEFAULT, country = LOCALE_COUNTRY_DEFAULT)

class JsLocaleProvider : LocaleProvider {
    private val locale: Locale = retrieveLocale()

    override fun getLanguage(): String = locale.language
    override fun getCountry(): String = locale.country
}

actual fun createLocaleProvider(): LocaleProvider = JsLocaleProvider()

private fun retrieveLocale(): Locale {
    var locale = DEFAULT_LOCALE

//    try {
//        if (jsTypeOf(kotlinx.browser.window) != "undefined")
//            locale = forLocaleTag(kotlinx.browser.window.navigator.language)
//    } catch (ignore: Throwable) {
//    }

    return locale
}

/**
 * Transforms a languageTag like "en_US_texas" to a Locale("en","US","texas")
 *
 * See [rfc5646](https://www.rfc-editor.org/rfc/rfc5646.html#section-2.1)
 */
private fun forLocaleTag(languageTag: String, separator: Char = '_', separator2: Char = '-'): Locale {
    if (languageTag.isBlank()) return DEFAULT_LOCALE

    var language = ""
    var script = ""
    var country = ""
    var variant = ""
    val extensions = mutableMapOf<Char, String>()

    val parts = languageTag.trim().split(separator, separator2)

    // tool functions....
    fun String.isAlpha() = all { it.isLetter() }
    fun String.isDigit() = all { it.isDigit() }
    fun String.isAlphaNum() = all { it.isLetterOrDigit() }

    var index = 0

    if (parts.size > index
        && parts[index].isAlpha()
        && parts[index].length in 2..8
    )
        language = parts[index++]

    // skip empty parts
    while (parts.size > index && parts[index].isEmpty())
        index++

    // script
    if (parts.size > index
        && parts[index].isAlpha()
        && parts[index].length == 4
    )
        script = parts[index++]

    // skip empty parts
    while (parts.size > index && parts[index].isEmpty())
        index++

    // country / region
    if (parts.size > index
        && (
                (parts[index].length == 2 && parts[index].isAlpha())
                        || (parts[index].length == 3 && parts[index].isDigit())
                )
    )
        country = parts[index++]

    // skip empty parts
    while (parts.size > index && parts[index].isEmpty())
        index++

    // variant
    if (parts.size > index
        && ((parts[index].length in 5..8 && parts[index].isAlphaNum())
                || (parts[index].length == 4 && parts[index][0].isDigit() && parts[index].isAlphaNum())
                )
    )
        variant = parts[index++]

    // skip empty parts
    while (parts.size > index && parts[index].isEmpty())
        index++

    // read extensions
    while (parts.size > index && parts[index].length == 1) {
        val key = parts[index++]
        val value = StringBuilder()
        while (parts.size > index && parts[index].length in 2..8) {
            if (value.isNotEmpty())
                value.append("-")
            value.append(parts[index++])
        }
        check(value.isNotEmpty()) { "Value for extension key '$key' is empty! language-tag: $languageTag" }
        extensions[key[0]] = value.toString()
    }
    check(parts.size <= index) { "Unexpected part: ${parts[index]} -  language-tag: $languageTag" }

    return Locale(language = language, country = country, script = script, variant = variant, extensions = extensions)
}
