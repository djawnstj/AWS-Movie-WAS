package com.awsmovie.util

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.util.UriComponentsBuilder

object ParamBuilder {

    fun createUri(uri: String, params: MultiValueMap<String, String>): String =
        UriComponentsBuilder
            .fromUriString(uri)
            .queryParams(params)
            .toUriString()

    fun createParams(objectMapper: ObjectMapper, dto: Any): MultiValueMap<String, String> {
        return try {
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            val map: Map<String, String> =
                objectMapper.convertValue(dto, object : TypeReference<Map<String, String>>() {})
            params.setAll(map)
            params
        } catch (e: Exception) {
//            log.error("Url Parameter 변환중 오류가 발생했습니다. requestDto={}", dto, e)
            throw IllegalStateException("Url Parameter 변환중 오류가 발생했습니다.")
        }
    }

}