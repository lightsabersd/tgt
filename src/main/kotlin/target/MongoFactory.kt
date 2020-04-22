package target

import com.mongodb.MongoClientSettings
import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoCollection
import io.micronaut.context.annotation.Bean
import io.micronaut.context.annotation.Factory
import org.bson.codecs.configuration.CodecRegistries
import org.bson.codecs.configuration.CodecRegistry
import org.bson.codecs.pojo.PojoCodecProvider
import target.dao.CurrentPrice
import javax.inject.Singleton

@Factory
class MongoFactory(val mongoClient: MongoClient) {

    @Bean
    @Singleton
    fun priceCollection(codecRegistry: CodecRegistry): MongoCollection<CurrentPrice> {
        return mongoClient.getDatabase("myRetail").getCollection("price", CurrentPrice::class.java).withCodecRegistry(codecRegistry)
    }

    @Bean
    @Singleton
    fun codecRegistry() : CodecRegistry {
        return CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()))
    }

}