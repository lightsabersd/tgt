package target.service

import kotlinx.coroutines.CompletableDeferredKt
import kotlinx.coroutines.Dispatchers
import target.BaseSpec
import target.client.Description
import target.client.Item
import target.client.RedSkyClient
import target.client.RedSkyResp
import target.dao.Price
import target.dao.Product

import java.util.concurrent.CompletableFuture

class ProductServiceSpec extends BaseSpec {

	RedSkyClient mockRedskyClient = Mock(RedskyClient)

	PriceService mockPriceService = Mock(PriceService)

	ProductService productService = new ProductService(mockRedskyClient, mockPriceService, Dispatchers.Default)

	String id = "123"
	def price = new Price('$2.95', CurrencyCode.USD)

	def "product service calls redsky and returns product name"() {
		when:
		Product response = sync { productService.getProduct(id) }

		then:
		1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
		1 * mockPriceService.getPrice(id) >> CompletableDeferredKt.CompletableDeferred(null)
		response.id == id
		response.name == "The Big Lebowski (Blu-ray)"
		response.current_price == null
	}

	def "product service calls redsky and returns product name and price"() {
		when:
		Product response = sync { productService.getProduct(id) }

		then:
		1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
		1 * mockPriceService.getPrice(id) >> CompletableDeferredKt.CompletableDeferred(price)
		response.id == id
		response.name == "The Big Lebowski (Blu-ray)"
		response.current_price == price
	}

	def "product service handles price errors"() {
		setup:
		def exceptionDeferred = CompletableDeferredKt.CompletableDeferred(null)
		exceptionDeferred.completeExceptionally(new RuntimeException("error"))

		when:
		Product response = sync { productService.getProduct(id) }

		then:
		1 * mockRedskyClient.getPdp(id) >> CompletableFuture.completedFuture(buildProductResponse())
		1 * mockPriceService.getPrice(id) >> { throw  new RuntimeException("error") }

		response.id == id
		response.name == "The Big Lebowski (Blu-ray)"
		response.current_price == null
	}

	RedSkyResp buildProductResponse() {
		return new RedSkyResp(new Product(new Item(new Description("The Big Lebowski (Blu-ray)"))))
	}
}
