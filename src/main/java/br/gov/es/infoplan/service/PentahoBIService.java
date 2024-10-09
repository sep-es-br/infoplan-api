package br.gov.es.infoplan.service;

import br.gov.es.infoplan.dto.NomeValorObject;
import br.gov.es.infoplan.dto.NomeValorObjectArray;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.nio.charset.Charset;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class PentahoBIService {
    
    private static final String CHARSET = "UTF-8";

    @Value("${pentahoBI.baseURL}")
    private String baseURL;

    @Value("${pentahoBI.path}")
    private String path;

    @Value("${pentahoBI.targetDashAll}")
    private String targetDashAll;

    @Value("${pentahoBI.targetDashProgram}")
    private String targetDashProgram;

    @Value("${pentahoBI.targetDashProject}")
    private String targetDashProject;

    @Value("${pentahoBI.valueSet}")
    private String valueSet;

    @Value("${pentahoBI.programTotal}")
    private String programTotal;

    @Value("${pentahoBI.projectTotal}")
    private String projectTotal;

    @Value("${pentahoBI.userId}")
    private String userId;

    @Value("${pentahoBI.password}")
    private String password;

    public List<NomeValorObject> getValoresPentahoAPI(int tipo) {
        String uri = baseURL + '?' + path + targetDashAll + "&dataAccessId=" + valueSet + tipo;

        List<NomeValorObject> result = new ArrayList<NomeValorObject>();

        try {
            NomeValorObjectArray lista = new ObjectMapper().readValue(doRequest(uri), NomeValorObjectArray.class);

            result = lista.list();
        } catch (Exception e) {
            e.printStackTrace();
        }   

        return result;
    }

    public double getProgramTotal() {

        double value = -1d;

        try {
            String response = doRequest(baseURL + '?' + path + targetDashProgram + "&dataAccessId=" + programTotal);

            JsonNode root = new ObjectMapper().readTree(response);

            ArrayNode rsNode = (ArrayNode) root.get("resultset");

            value = ((ArrayNode) rsNode.get(0)).get(0).asDouble();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return value;
    }

    public double getProjectTotal() {

        double value = -1d;

        try {
            String response = doRequest(baseURL + '?' + path + targetDashProject + "&dataAccessId=" + projectTotal);

            JsonNode root = new ObjectMapper().readTree(response);

            ArrayNode rsNode = (ArrayNode) root.get("resultset");

            value = ((ArrayNode) rsNode.get(0)).get(0).asDouble();
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 

        return value;
    }

    private String doRequest(String uri) throws Exception{
        
        String notEncoded = userId + ":" + password;
        String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", encodedAuth);

        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(Charset.forName(CHARSET)));

        return restTemplate.exchange(RequestEntity.get(new URI(uri)).headers(headers).build(), String.class).getBody();
    }

    /* public String gerarToken(ACUserInfoDto userInfo) {
        try {
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withSubject(userInfo.sub())
                    .withClaim("roles", new ArrayList<>(userInfo.role()))
                    .withExpiresAt(getDataExpiracao())
                    .sign(algoritmo);
        } catch (JWTCreationException exception) {
            throw new InfoplanServiceException(List.of("Erro ao gerar o token", exception.getMessage()));
        }
    }

    public String validarToken(String token) {
        Algorithm algoritmo = Algorithm.HMAC256(secret);
        return JWT.require(algoritmo)
                .withIssuer(ISSUER)
                .build()
                .verify(token)
                .getSubject();
    }

    private Instant getDataExpiracao() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    public List<String> getRoleFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        return decodedJWT.getClaim("roles").asList(String.class);
    } */
}
