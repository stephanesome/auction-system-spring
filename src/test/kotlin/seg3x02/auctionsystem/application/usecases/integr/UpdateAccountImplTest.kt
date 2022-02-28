package seg3x02.auctionsystem.application.usecases.integr

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import seg3x02.auctionsystem.adapters.dtos.AccountDto
import seg3x02.auctionsystem.adapters.dtos.AddressDto
import seg3x02.auctionsystem.adapters.dtos.CreditCardDto
import seg3x02.auctionsystem.application.services.CreditService
import seg3x02.auctionsystem.application.services.DomainEventEmitter
import seg3x02.auctionsystem.application.usecases.UpdateAccount
import seg3x02.auctionsystem.domain.user.core.account.PendingPayment
import seg3x02.auctionsystem.domain.user.core.account.UserAccount
import seg3x02.auctionsystem.domain.user.core.creditCard.Address
import seg3x02.auctionsystem.domain.user.core.creditCard.CreditCard
import seg3x02.auctionsystem.domain.user.repositories.CreditCardRepository
import seg3x02.auctionsystem.domain.user.repositories.UserRepository
import seg3x02.auctionsystem.tests.fixtures.EventEmitterAdapterStub
import java.math.BigDecimal
import java.time.Month
import java.time.Year
import java.util.*

@SpringBootTest
class UpdateAccountImplTest {
    @Autowired
    lateinit var updateAccount: UpdateAccount
    @Autowired
    lateinit var creditService: CreditService
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var creditCardRepository: CreditCardRepository
    @Autowired
    lateinit var eventEmitter: DomainEventEmitter

    @Test
    fun updateAccount_pending_payment_credit_card() {
        // create account
        val userId = UUID.randomUUID()
        val user = UserAccount(userId,
            "Toto",
            "Tata",
            "toto@somewhere.com"
        )
        val pendingAmount = BigDecimal(20)
        user.pendingPayment = PendingPayment(
            pendingAmount
        )
        val usercc = CreditCard(5555555,
            Month.JUNE,
            Year.parse("2021"),
            "Toto",
            "Tata",
            Address(
                "125 DeLa Rue",
                "Ottawa",
                "Canada",
                "K0K0K0")
        )
        creditCardRepository.save(usercc)
        user.creditCardNumber = usercc.number
        userRepository.save(user)

        // create DTO for update
        val addr = AddressDto(
            "125 DeLa Rue",
            "Ottawa",
            "Canada",
            "K0K0K0")
        val updatedCcNumber = 6666666
        val updatedCcexYear = Year.parse("2024")
        val updatedCcexMonth = Month.AUGUST
        val cc = CreditCardDto(updatedCcNumber,
            updatedCcexMonth,
            updatedCcexYear,
            "Toto",
            "Tata",
            addr
        )
        val updatedEmail = "totonew@somewhere.com"
        val userDto = AccountDto(
            "Toto",
            "Tata",
            updatedEmail)
        userDto.creditCardInfo = cc

        updateAccount.updateAccount(userId, userDto)
        // check events and updates
        val updatedUser = userRepository.find(userId)
        Assertions.assertThat(updatedUser).isNotNull
        Assertions.assertThat(updatedUser?.email).isEqualTo(updatedEmail)
        Assertions.assertThat(updatedUser?.pendingPayment).isNull()
        Assertions.assertThat(updatedUser?.creditCardNumber).isEqualTo(updatedCcNumber)
    }

    @Test
    fun updateAccount_no_pending_payment_credit_card() {
        // create account
        val userId = UUID.randomUUID()
        val user = UserAccount(userId,
            "Toto",
            "Tata",
            "toto@somewhere.com"
        )
        user.pendingPayment = null
        val usercc = CreditCard(5555555,
            Month.JUNE,
            Year.parse("2021"),
            "Toto",
            "Tata",
            Address(
                "125 DeLa Rue",
                "Ottawa",
                "Canada",
                "K0K0K0")
        )
        creditCardRepository.save(usercc)
        user.creditCardNumber = usercc.number
        userRepository.save(user)

        // create DTO for update
        val addr = AddressDto(
            "125 DeLa Rue",
            "Ottawa",
            "Canada",
            "K0K0K0")
        val updatedCcNumber = 6666666
        val updatedCcexYear = Year.parse("2024")
        val updatedCcexMonth = Month.AUGUST
        val cc = CreditCardDto(updatedCcNumber,
            updatedCcexMonth,
            updatedCcexYear,
            "Toto",
            "Tata",
            addr
        )
        val updatedEmail = "totonew@somewhere.com"
        val userDto = AccountDto(
            "Toto",
            "Tata",
            updatedEmail)
        userDto.creditCardInfo = cc

        updateAccount.updateAccount(userId, userDto)
        // check events and updates
        val updatedUser = userRepository.find(userId)
        Assertions.assertThat(updatedUser).isNotNull
        Assertions.assertThat(updatedUser?.email).isEqualTo(updatedEmail)
        Assertions.assertThat(updatedUser?.pendingPayment).isNull()
        Assertions.assertThat(updatedUser?.creditCardNumber).isEqualTo(updatedCcNumber)
    }

    @Test
    fun updateAccount_pending_payment_no_credit_card() {
        // create account
        val userId = UUID.randomUUID()
        val user = UserAccount(userId,
            "Toto",
            "Tata",
            "toto@somewhere.com"
        )
        val pendingAmount = BigDecimal(20)
        user.pendingPayment = PendingPayment(
            pendingAmount
        )
        val usercc = CreditCard(5555555,
            Month.JUNE,
            Year.parse("2021"),
            "Toto",
            "Tata",
            Address(
                "125 DeLa Rue",
                "Ottawa",
                "Canada",
                "K0K0K0")
        )
        creditCardRepository.save(usercc)
        user.creditCardNumber = usercc.number
        userRepository.save(user)

        // create DTO for update
        val updatedEmail = "totonew@somewhere.com"
        val userDto = AccountDto(
            "Toto",
            "Tata",
            updatedEmail)

        updateAccount.updateAccount(userId, userDto)
        // check events and updates
        val updatedUser = userRepository.find(userId)
        Assertions.assertThat(updatedUser).isNotNull
        Assertions.assertThat(updatedUser?.email).isEqualTo(updatedEmail)
        Assertions.assertThat(updatedUser?.pendingPayment).isNotNull
    }
}
