package target.dao

import java.math.BigDecimal

data class Price(val id: Long, val value: BigDecimal, val currencyCode: String)