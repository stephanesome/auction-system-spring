package seg3x02.auctionsystem.domain.auction.core

import seg3x02.auctionsystem.adapters.dtos.BidDto
import seg3x02.auctionsystem.domain.auction.factories.BidFactory
import seg3x02.auctionsystem.domain.auction.repositories.BidRepository
import java.math.BigDecimal
import java.time.Duration
import java.time.LocalDateTime
import java.util.*

class Auction(val id: UUID,
              val startTime: LocalDateTime,
              val duration: Duration,
              val startPrice: BigDecimal,
              val minIncrement: BigDecimal,
              val seller: UUID,
              val category: AuctionCategory) {
    lateinit var item: UUID
    var isclosed: Boolean = false
    val bids: MutableList<UUID> = ArrayList()

    fun createBid(bidInfo: BidDto, bidFactory: BidFactory, bidRepository: BidRepository): UUID? {
        return if (bids.isNotEmpty()) {
            val lastBidId = bids.last()
            val lastBid = bidRepository.find(lastBidId)
            if (bidInfo.amount > lastBid?.amount?.plus(this.minIncrement))  {
                newBid(bidInfo, bidFactory, bidRepository)
            }  else {
                null
            }
        } else {
            if (bidInfo.amount > this.startPrice) {
                newBid(bidInfo, bidFactory, bidRepository)
            } else {
                null
            }
        }
    }

    private fun newBid(bidInfo: BidDto, bidFactory: BidFactory, bidRepository: BidRepository): UUID {
        val newBid = bidFactory.createBid(bidInfo)
        bids.add(newBid.id)
        bidRepository.save(newBid)
        return newBid.id
    }

    fun close(): UUID? {
        isclosed = true
        return bids.lastOrNull()
    }

    fun getBidSeller(bidId: UUID, bidRepository: BidRepository): UUID? {
        return bidRepository.find(bidId)?.buyer
    }

    fun closeTime(): LocalDateTime {
        return startTime.plus(duration)
    }
}