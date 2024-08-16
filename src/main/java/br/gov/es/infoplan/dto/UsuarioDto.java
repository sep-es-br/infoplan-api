package br.gov.es.infoplan.dto;

import java.util.Set;

public record UsuarioDto(
        String token,
        String name,
        String email,
        Set<String> role) {
}
