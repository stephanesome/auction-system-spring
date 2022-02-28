package seg3x02.auctionsystem.application.usecases.integr

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import seg3x02.auctionsystem.adapters.dtos.AccountDto
import seg3x02.auctionsystem.adapters.dtos.AddressDto
import seg3x02.auctionsystem.adapters.dtos.CreditCardDto
import seg3x02.auctionsystem.application.usecases.CreateAccount
import seg3x02.auctionsystem.domain.user.repositories.UserRepository
import java.time.Month
import java.time.Year

@SpringBootTest
class CreateAccountImplTest {
    @Autowired
    lateinit var createAccount: CreateAccount

    @Autowired
    lateinit var userRepository: UserRepository

/*
    @Autowired
    lateinit var creditCardRepository: CreditCardRepository
*/

/*
    @Autowired
    lateinit var eventEmitter: DomainEventEmitter
*/

    @Test
    fun createAccount_with_credit_card_information_provided() {
        // setup accountInfo
        val addr = AddressDto(
            "125 DeLa Rue",
            "Ottawa",
            "Canada",
            "K0K0K0"
        )
        val cc = CreditCardDto(
            5555555,
            Month.JUNE,
            Year.parse("2024"),
            "Toto",
            "Tata",
            addr
        )
        val userDto = AccountDto(
            "Toto",
            "Tata",
            "toto@somewhere.com"
        )
        userDto.creditCardInfo = cc
        val userId = createAccount.createAccount(userDto)
        Assertions.assertThat(userId).isNotNull
        // get emitted events (creditCardCreated, userAccountCreated, creditCardSet)
/*        val accCreationEvent = (eventEmitter as EventEmitterAdapterStub).retrieveUserAccountCreatedEvent()
        Assertions.assertThat(accCreationEvent).isNotNull
        val ccCreationEvent = (eventEmitter as EventEmitterAdapterStub).retrieveCreditCardCreatedEvent()
        Assertions.assertThat(ccCreationEvent).isNotNull
        val ccSetEvent = (eventEmitter as EventEmitterAdapterStub).retrieveUserCreditCardSetEvent()
        Assertions.assertThat(ccSetEvent).isNotNull*/
        // check created objets
        val newAccount = userId?.let { userRepository.find(it) }
        Assertions.assertThat(newAccount).isNotNull
        Assertions.assertThat(newAccount?.firstname).isEqualTo("Toto")
        Assertions.assertThat(newAccount?.creditCardNumber).isNotNull
//        Assertions.assertThat(newAccount?.creditCardNumber).isEqualTo(ccCreationEvent.creditCardNumber)
    }
}