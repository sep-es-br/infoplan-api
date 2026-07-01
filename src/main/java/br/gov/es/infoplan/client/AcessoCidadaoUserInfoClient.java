package br.gov.es.infoplan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.LinkedHashMap;
import java.util.Map;

@FeignClient(name = "acessoCidadaoUserInfo")
public interface AcessoCidadaoUserInfoClient {
    @GetMapping
    LinkedHashMap<String, Object> buscarUserInfoAcessoCidadao(@RequestHeader Map<String, Object> headers);
}
