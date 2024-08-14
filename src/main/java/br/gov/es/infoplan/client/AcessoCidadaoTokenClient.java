package br.gov.es.infoplan.client;

import br.gov.es.infoplan.dto.acessocidadaoapi.LoginACResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

@FeignClient(name = "acessoCidadaoToken", url = "${api.acessocidadao.uri.token}")
public interface AcessoCidadaoTokenClient {

    @PostMapping
    LoginACResponseDto login(@RequestHeader Map<String, Object> headers, String form);
}
