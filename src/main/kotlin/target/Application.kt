package target

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("target")
                .mainClass(Application.javaClass)
                .start()
    }
}