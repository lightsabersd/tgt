package target.service

import com.mongodb.MongoClientSettings
import com.mongodb.client.model.Filters
import com.mongodb.client.model.ReplaceOptions
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import target.dao.CurrentPrice
import target.dao.Price
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class PriceService(val priceCollection: MongoCollection<CurrentPrice>, override val coroutineContext: CoroutineContext) : CoroutineScope {
    fun getPrice(id: Long): Deferred<Price?> {
        return async {
            val price = priceCollection.find(Filters.eq("_id", id)).awaitFirstOrNull()
            if (price != null) Price(id, price.value!!, price.currency_code!!) else null
        }
    }

    fun udpate(id: Long, price: Price): Deferred<Price> {
        return async {
            priceCollection.replaceOne(Filters.eq("_id", id), CurrentPrice(id, price.value, price.currencyCode), ReplaceOptions().upsert(true)).awaitFirst()
            price
        }
    }
}