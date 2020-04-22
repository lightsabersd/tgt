package target.client

import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client
import target.dao.ProductData
import java.util.concurrent.CompletableFuture


@Client("redsky")
interface RedSkyClient {
    @Get("\${micronaut.http.services.redsky.path}")
    fun getProductName(id: Long) : CompletableFuture<RedSkyResp?>

}