package target.service

import static com.mongodb.client.model.Filters.eq

import com.mongodb.client.model.ReplaceOptions
import com.mongodb.reactivestreams.client.*
import io.micronaut.core.async.publisher.Publishers
import kotlinx.coroutines.Dispatchers
import target.BaseSpec
import target.dao.*

class PriceServiceSpec extends BaseSpec {

	MongoCollection<CurrentPrice> mockPriceColleciton = Mock(MongoCollection)

	PriceService priceService = new PriceService(mockPriceColleciton, Dispatchers.Default)

	String id = "123"

	Price price = new Price('$2.95', CurrencyCode.USD)

	CurrentPrice currentPrice = new CurrentPrice(id: id, value: '$2.95', currency_code: "USD")

	def "get price from mongo"() {
		when:
		Price result = sync {
			priceService.getPrice(id)
		}

		then:
		result == price
		1 * mockPriceColleciton.find({ it.toString() == eq('_id', id).toString() }) >> (Publishers.just(mongoPrice) as FindPublisher)
	}

	def "save price mongo"() {
		when:
		Price result = sync {
			priceService.udpate(id, price)
		}

		then:
		result == price
		1 * mockPriceColleciton.replaceOne(
			{ it.toString() == eq('_id', id).toString() },
			mongoPrice,
			{ it.toString() == new ReplaceOptions().upsert(true).toString() } ) >> (Publishers.just(price) as FindPublisher)
	}
}
