package seg3x02.auctionsystem.application.usecases.unit

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.TestPropertySource
import seg3x02.auctionsystem.adapters.dtos.queries.AccountCreateDto
import seg3x02.auctionsystem.adapters.dtos.queries.AddressCreateDto
import seg3x02.auctionsystem.adapters.dtos.queries.CreditCardCreateDto
import seg3x02.auctionsystem.application.services.DomainEventEmitter
import seg3x02.auctionsystem.application.usecases.CreateAccount
import seg3x02.auctionsystem.domain.user.repositories.CreditCardRepository
import seg3x02.auctionsystem.domain.user.repositories.AccountRepository
import seg3x02.auctionsystem.tests.config.TestBeanConfiguration
import seg3x02.auctionsystem.tests.fixtures.EventEmitterAdapterStub
import java.time.Month
import java.time.Year

@Import(TestBeanConfiguration::class)
@TestPropertySource(locations= ["classpath:application.properties"])
@SpringBootTest
class CreateAccountImplTest {
    @Autowired
    lateinit var createAccount: CreateAccount
    @Autowired
    lateinit var accountRepository: AccountRepository
    @Autowired
    lateinit var creditCardRepository: CreditCardRepository
    @Autowired
    lateinit var eventEmitter: DomainEventEmitter

    @Test
    fun createAccount_with_credit_card_information_provided() {
        // setup accountInfo
        val addr = AddressCreateDto(
            "125 DeLa Rue",
            "Ottawa",
            "Canada",
            "K0K0K0")
        val cc = CreditCardCreateDto(5555555,
            Month.JUNE,
            Year.parse("2024"),
            "Toto",
            "Tata",
            addr
        )
        val userId = "user77876"
        val userDto = AccountCreateDto(
            userId,
            "Toto",
            "Tata",
            "toto@somewhere.com")
        userDto.creditCardInfo = cc
        val userCreated = createAccount.createAccount(userDto)
        Assertions.assertThat(userCreated).isTrue
        // get emitted events (creditCardCreated, userAccountCreated, creditCardSet)
        val accCreationEvent = (eventEmitter as EventEmitterAdapterStub).retrieveUserAccountCreatedEvent()
        Assertions.assertThat(accCreationEvent).isNotNull
        val ccCreationEvent = (eventEmitter as EventEmitterAdapterStub).retrieveCreditCardCreatedEvent()
        Assertions.assertThat(ccCreationEvent).isNotNull
        val ccSetEvent = (eventEmitter as EventEmitterAdapterStub).retrieveUserCreditCardSetEvent()
        Assertions.assertThat(ccSetEvent).isNotNull
        // check created objets
        val newAccount = accountRepository.find(userId)
        Assertions.assertThat(newAccount).isNotNull
        Assertions.assertThat(newAccount?.firstname).isEqualTo("Toto")
        Assertions.assertThat(newAccount?.creditCardNumber).isNotNull
        Assertions.assertThat(newAccount?.creditCardNumber).isEqualTo(ccCreationEvent.creditCardNumber)
    }

    @Test
    fun createAccount_no_credit_card_information_provided() {
        // setup accountInfo
        val userId = "user00"
        val userDto = AccountCreateDto(
            userId,
            "Toto",
            "Tata",
            "toto@somewhere.com")
        val userCreated = createAccount.createAccount(userDto)
        Assertions.assertThat(userCreated).isTrue
        // get emitted events (creditCardCreated, userAccountCreated, creditCardSet)
        val accCreationEvent = (eventEmitter as EventEmitterAdapterStub).retrieveUserAccountCreatedEvent()
        Assertions.assertThat(accCreationEvent).isNotNull
        // check created objets
        val newAccount = accountRepository.find(userId)
        Assertions.assertThat(newAccount).isNotNull
        Assertions.assertThat(newAccount?.firstname).isEqualTo("Toto")
        Assertions.assertThat(newAccount?.creditCardNumber).isNull()
    }
}
