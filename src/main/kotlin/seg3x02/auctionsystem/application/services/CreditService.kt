package seg3x02.auctionsystem.application.services

import java.math.BigDecimal
import java.time.Month
import java.time.Year

interface CreditService {
    fun processPayment(number: Number, expirationMonth: Month, expirationYear: Year, amt: BigDecimal): Boolean
}
