package seg3x02.auctionsystem.framework.jpa.entities.user.creditCard

import java.time.Month
import java.time.Year
import java.util.*
import javax.persistence.*

@Entity()
@Table(name="CREDIT_CARD")
class CreditCardJpaEntity(
    @Id
    val number: Number,
    val expirationMonth: Month,
    val expirationYear: Year,
    val accountFirstname: String,
    val accountLastname: String,
    @Embedded
    val accountAddress: AddressJpaEntity,
) {

}