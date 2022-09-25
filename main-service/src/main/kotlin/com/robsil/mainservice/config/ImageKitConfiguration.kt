package com.robsil.mainservice.config

import io.imagekit.sdk.ImageKit
import io.imagekit.sdk.utils.Utils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.lang.RuntimeException

@Configuration
class ImageKitConfiguration {

    @Bean
    fun imageKit(): ImageKit {
        val imageKit = ImageKit.getInstance()
        val config = Utils.getSystemConfig(ImageKitConfiguration::class.java)
        imageKit.config = config

        if (imageKit == null) {
            throw RuntimeException("Something went wrong during initialization imageKit instance.")
        }

        return imageKit
    }
//    companion object {
//    fun imageKit(): ImageKit {
//        val imageKit = ImageKit.getInstance()
//        val config = Utils.getSystemConfig(ImageKitConfiguration::class.java)
//        imageKit.config = config
//
//        if (imageKit == null) {
//            throw RuntimeException("Something went wrong during initialization imageKit instance.")
//        }
//
//        return imageKit
//    }
//    }
}