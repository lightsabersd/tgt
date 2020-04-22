package target.dao

import java.math.BigDecimal

data class CurrentPrice(var id: Long?, var value: BigDecimal?, var currency_code: String?) {
    constructor() : this(null, null, null)
}