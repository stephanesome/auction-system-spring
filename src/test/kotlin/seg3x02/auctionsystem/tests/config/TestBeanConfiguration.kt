package seg3x02.auctionsystem.tests.config

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import seg3x02.auctionsystem.application.services.DomainEventEmitter
import seg3x02.auctionsystem.domain.auction.repositories.AuctionRepository
import seg3x02.auctionsystem.domain.auction.repositories.BidRepository
import seg3x02.auctionsystem.domain.item.repositories.ItemRepository
import seg3x02.auctionsystem.domain.user.repositories.CreditCardRepository
import seg3x02.auctionsystem.domain.user.repositories.UserRepository
import seg3x02.auctionsystem.tests.fixtures.*

@TestConfiguration
class TestBeanConfiguration {
    @Bean
    fun auctionRepository(): AuctionRepository {
        return AuctionRepositoryStub()
    }

    @Bean
    fun userRepository(): UserRepository {
        return UserRepositoryStub()
    }

    @Bean
    fun creditCardRepository(): CreditCardRepository {
        return CreditCardRepositoryStub()
    }

    @Bean
    fun itemRepository(): ItemRepository {
        return ItemRepositoryStub()
    }

    @Bean
    fun bidRepository(): BidRepository {
        return BidRepositoryStub()
    }

    @Bean
    fun eventEmitter(): DomainEventEmitter {
        return EventEmitterAdapterStub()
    }

/*    @Bean
    fun auctionScheduler(): AuctionScheduler {
        return AuctionSchedulerAdapterStub()
    }*/
}