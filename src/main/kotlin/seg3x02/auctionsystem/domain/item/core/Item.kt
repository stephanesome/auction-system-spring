package seg3x02.auctionsystem.domain.item.core

import java.util.*

class Item(val id: UUID,
        val title: String,
        val description: String) {
    var image: ByteArray? = null
}