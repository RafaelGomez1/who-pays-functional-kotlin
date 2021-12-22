package rgomez.whopayskotlin.charges.domain

import arrow.core.Validated
import arrow.core.invalid
import arrow.core.valid

val UuidRegex = "/^\\{?[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}\u200C\u200B\\}?\$/".toRegex()

const val MINIMUM_ACCEPTED_AMOUNT = 0.0

fun <T> validate(subject: T, criteria: (T) -> Boolean ): Validated<ValueObjectError, T> =
    if (criteria(subject)) subject.valid() else ValueObjectError.InitializationError("VO validation not applied for ${subject}").invalid()