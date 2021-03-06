package seg3x02.auctionsystem

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableCaching
@EnableAsync
class AuctionSystemApplication

fun main(args: Array<String>) {
    runApplication<AuctionSystemApplication>(*args)
}
