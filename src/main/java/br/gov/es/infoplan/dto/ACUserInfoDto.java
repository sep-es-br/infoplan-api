package br.gov.es.infoplan.dto;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ACUserInfoDto(
        String apelido,
        Boolean cpfValidado,
        Boolean verificada,
        String verificacaoTipo,
        String subNovo,
        Boolean agentepublico,
        String email,
        String emailCorporativo,
        String sub,
        Set<String> role
) {

    public ACUserInfoDto(ACUserInfoDtoStringRole userInfo) {
        this(userInfo.apelido(), userInfo.cpfValidado(), userInfo.verificada(), userInfo.verificacaoTipo(), userInfo.subNovo(),
                userInfo.agentepublico(), userInfo.email(), userInfo.emailCorporativo(), userInfo.sub(),
                new HashSet<>(List.of(userInfo.role())));
    }

    public ACUserInfoDto(LinkedHashMap<String, Object> userInfoHashMap) {
        this(
                (String) userInfoHashMap.get("apelido"),
                Boolean.valueOf((String) userInfoHashMap.get("cpfValidado")),
                Boolean.valueOf((String) userInfoHashMap.get("verificada")),
                (String) userInfoHashMap.get("verificacaoTipo"),
                (String) userInfoHashMap.get("subNovo"),
                Boolean.valueOf((String) userInfoHashMap.get("agentepublico")),
                (String) userInfoHashMap.get("email"),
                (String) userInfoHashMap.get("emailCorporativo"),
                (String) userInfoHashMap.get("sub"),
                converterRole(userInfoHashMap.get("role"))
        );
    }

    private static Set<String> converterRole(Object role) {
        if (role == null) {
            return new HashSet<>();
        } else if (role instanceof String) {
            return new HashSet<>(List.of((String) role));
        } else if (role instanceof ArrayList<?>) {
            return new HashSet<>((ArrayList<String>) role);
        } else {
            throw new IllegalArgumentException("Tipo invalido para parametro 'role': " + role.getClass().getName());
        }
    }


}
