package target.service

import io.micronaut.http.client.exceptions.HttpClientException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.future.await
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import target.client.RedSkyClient
import target.client.RedSkyResp
import target.dao.Price
import target.dao.Product
import target.dao.ProductData
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
class ProductService(val redSkyClient: RedSkyClient, val priceService: PriceService, override val coroutineContext: CoroutineContext) : CoroutineScope {

    val log : Logger = LoggerFactory.getLogger(ProductService::class.java)

    fun getProduct(id: Long): Deferred<Product> {
        return async {
            val productData = getProductName(id)
            val price = getPrice(id)
            Product(id, productData?.product?.item?.product_description?.title, price)
        }
    }

    private suspend fun getPrice(id: Long): Price? {
        try {
            return priceService.getPrice(id).await()
        } catch (e: Exception) {
            println ("Error retrieving price")
        }
        return null
    }

    private suspend fun getProductName(id: Long): RedSkyResp? {
        try {
            return redSkyClient.getProductName(id).await()
        } catch (e: HttpClientException) {
            println ("Error retrieving product")
        }
        return null
    }
}