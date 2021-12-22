package rgomez.whopayskotlin.charges.domain

import arrow.core.Either

interface ChargeRepository {
    fun save(charge: Charge): Either<Throwable, Unit>
    fun findBy(id: ChargeId): Either<Throwable, Charge>
}