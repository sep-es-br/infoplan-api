package br.gov.es.infoplan.exception.mensagens;

import org.springframework.http.HttpStatus;

import java.util.List;

public record MensagemErroRest (
    HttpStatus status,
    Integer codigo,
    String mensagem,
    List<String> erros) {

    public MensagemErroRest(HttpStatus status, String mensagem, List<String> erros) {
        this(status, status.value(), mensagem, erros);
    }
}
