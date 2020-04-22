package target.controller

import io.micronaut.http.annotation.*
import kotlinx.coroutines.future.asCompletableFuture
import target.dao.Price
import target.dao.Product
import target.service.PriceService
import target.service.ProductService
import java.util.concurrent.CompletableFuture
import javax.validation.Valid

@Controller
class ProductController(val productService: ProductService, val priceService: PriceService) {

    @Get("/products/{id}")
    fun getProduct(@PathVariable("id") id: Long): CompletableFuture<Product> {
        return productService.getProduct(id).asCompletableFuture()
    }

    @Put("/products/{id}")
    fun upsertPrice(@PathVariable("id") id: Long, @Valid @Body price: Price): CompletableFuture<Price> {
        return priceService.udpate(id, price).asCompletableFuture()
    }

}