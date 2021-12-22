package rgomez.whopayskotlin.charges.infrastructure.secondaryadapter

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import javax.inject.Named
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import rgomez.whopayskotlin.charges.domain.Charge
import rgomez.whopayskotlin.charges.domain.ChargeId
import rgomez.whopayskotlin.charges.domain.ChargeRepository

@Named
class MongoChargeDatabase(private val repository: JpaChargeRepository) : ChargeRepository {
    override fun save(charge: Charge): Either<Throwable, Unit> =
        Either.catch { repository.save(charge.toJpaCharge()) }
            .flatMap { Unit.right() }

    override fun findBy(id: ChargeId): Either<Throwable, Charge> =
        Either.catch { repository.findById(id.value).get() }
            .map { it.toCharge() }

}

@Repository
interface JpaChargeRepository : MongoRepository<JpaCharge, String>