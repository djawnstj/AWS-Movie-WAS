package com.awsmovie.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration
import java.util.*
import java.util.concurrent.TimeUnit

@Configuration
class WebClientConfig {

    @Value("\${spring.apiServer.protocol}")
    private val protocol: String? = null

    @Value("\${spring.apiServer.host}")
    private val host: String? = null

    @Value("\${spring.apiServer.port}")
    private val port: String? = null

    private fun httpClient(): HttpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected {
            it.addHandlerLast(ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
                .addHandlerLast(WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS))
        }

    @Bean
    fun webClient(): WebClient = WebClient.builder()
            .baseUrl("$protocol://$host:$port")
//            .defaultCookie("cookieKey", "cookieValue")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .defaultUriVariables(Collections.singletonMap("url", "$protocol://$host:$port"))
            .clientConnector(ReactorClientHttpConnector(httpClient()))
            .build()


}