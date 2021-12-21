package rgomez.whopayskotlin.charges.domain

import arrow.core.Either
import rgomez.whopayskotlin.charges.domain.create.CreateChargeError

interface ChargeRepository {
    fun save(charge: Charge): Either<CreateChargeError, Unit>
}