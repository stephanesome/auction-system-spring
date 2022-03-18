package seg3x02.auctionsystem.domain.user.events

import seg3x02.auctionsystem.domain.DomainEvent
import java.util.*

class UserAccountCreated(val id: UUID,
                         val occuredOn: Date,
                         val userId: String): DomainEvent
