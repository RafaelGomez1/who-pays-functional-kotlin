package rgomez.whopayskotlin.charges.domain

import arrow.core.Validated
import java.math.BigDecimal
import java.time.ZonedDateTime

@JvmInline
value class Debtor(val value: String) {
    companion object {
        fun create(value: String) : Validated<ValueObjectError, Debtor> =
            validate(Debtor(value)) { debtor -> UuidRegex.matches(debtor.value) }
    }
}

@JvmInline
value class Creditor(val value: String) {
    companion object {
        fun empty() = Creditor(value = "")

        fun create(value: String) : Validated<ValueObjectError, Creditor> =
            validate(Creditor(value)) { creditor -> UuidRegex.matches(creditor.value) }
    }
}

data class Money(val amount: BigDecimal, val currency: Currency = Currency.EUR) {
    init {
        require(amount > 0.0.toBigDecimal())
    }

    enum class Currency {
        EUR
    }
}

@JvmInline
value class Payer(val value: String) {
    companion object {
        fun create(value: String) : Validated<ValueObjectError, Payer> =
            validate(Payer(value)) { payer -> UuidRegex.matches(payer.value) }
    }
}

@JvmInline
value class ChargeId(val value: String) {

    init {
        require(UuidRegex.matches(value))
    }

    companion object {
        fun create(value: String) : Validated<ValueObjectError, ChargeId> =
            validate(ChargeId(value)) { id -> UuidRegex.matches(id.value) }
    }
}

@JvmInline
value class Quantity(val value: Double) {
    companion object {
        fun create(value: Double) : Validated<ValueObjectError, Quantity> =
            validate(Quantity(value)) { quantity -> quantity.value > MINIMUM_ACCEPTED_AMOUNT }
    }
}

@JvmInline
value class Concept(val value: String) {
    companion object {
        fun create(value: String) : Validated<ValueObjectError, Concept> =
            validate(Concept(value)) { concept -> concept.value.isNotBlank()  }
    }
}

@JvmInline
value class Date(val value: ZonedDateTime) {
    companion object {
        fun create(value: ZonedDateTime) : Validated<ValueObjectError, Date> =
            validate(Date(value)) { date -> date.value.isBefore(ZonedDateTime.now())  }
    }
}

sealed class ValueObjectError : RuntimeException {
    constructor(message: String) : super(message)
    constructor(reason: Throwable) : super(reason)
    constructor() : super()

    data class InitializationError(val reason: String) : ValueObjectError(reason)
}