package seg3x02.auctionsystem.application.usecases

import seg3x02.auctionsystem.adapters.dtos.queries.AuctionCreateDto
import java.util.*

interface CreateAuction {
    fun addAuction(auctionInfo: AuctionCreateDto): UUID?
}
