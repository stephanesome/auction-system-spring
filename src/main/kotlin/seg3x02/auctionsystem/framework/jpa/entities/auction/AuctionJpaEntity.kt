package seg3x02.auctionsystem.framework.jpa.entities.auction

import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity()
@Table(name="AUCTIONS")
data class AuctionJpaEntity(@Id val id: UUID,
                            @Column(name = "auction_start_time", columnDefinition = "TIMESTAMP")
                            val startTime: LocalDateTime,
                            val duration: Duration,
                            val startPrice: BigDecimal,
                            val minIncrement: BigDecimal,
                            val item: UUID,
                            val seller: UUID,
                            @Embedded
                            val category: AuctionCategoryJpaEntity
) {
    val isclosed: Boolean = false
    @ElementCollection
    val bids: MutableList<UUID> = ArrayList()
}