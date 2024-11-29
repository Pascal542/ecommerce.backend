package com.pascal.ecommerce.backend.service.niubiz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class NiubizService {

    @Value("${niubiz.api.security}")
    private String securityUrl;

    @Value("${niubiz.api.session}")
    private String sessionUrl;

    @Value("${niubiz.merchant.id}")
    private String merchantId;

    @Value("${niubiz.credentials.username}")
    private String username;

    @Value("${niubiz.credentials.password}")
    private String password;

    private static final String AUTHORIZATION_URL = "https://apisandbox.vnforappstest.com/api.authorization/v3/authorization/ecommerce/{merchantId}";


    private final RestTemplate restTemplate;

    public NiubizService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generateAccessToken() {
        String url = "https://apisandbox.vnforappstest.com/api.security/v1/security";
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth("integraciones@niubiz.com.pe", "_7z3@8fF");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);

            // Aceptar tanto 200 OK como 201 Created
            if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
                String accessToken = response.getBody();
                System.out.println("Token de acceso obtenido: " + accessToken);
                return accessToken;
            } else {
                throw new RuntimeException("Respuesta inesperada al obtener el token de acceso: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el token de acceso: " + e.getMessage(), e);
        }
    }

    public String generateSessionToken(String accessToken, double amount) {
        String sessionUrl = "https://apisandbox.vnforappstest.com/api.ecommerce/v2/ecommerce/token/session/456879852";
        RestTemplate restTemplate = new RestTemplate();

        // Encabezados de la solicitud
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Cuerpo de la solicitud
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("channel", "web");
        requestBody.put("amount", amount);

        Map<String, Object> antifraud = new HashMap<>();
        antifraud.put("clientIp", "24.252.107.29");
        Map<String, Object> merchantDefineData = new HashMap<>();
        merchantDefineData.put("MDD4", "integraciones@niubiz.com.pe");
        merchantDefineData.put("MDD32", "JD1892639123");
        merchantDefineData.put("MDD75", "Registrado");
        merchantDefineData.put("MDD77", 458);
        antifraud.put("merchantDefineData", merchantDefineData);
        requestBody.put("antifraud", antifraud);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("cardholderCity", "Lima");
        dataMap.put("cardholderCountry", "PE");
        dataMap.put("cardholderAddress", "Av Jose Pardo 831");
        dataMap.put("cardholderPostalCode", "12345");
        dataMap.put("cardholderState", "LIM");
        dataMap.put("cardholderPhoneNumber", "987654321");
        requestBody.put("dataMap", dataMap);

        // Solicitud HTTP
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(sessionUrl, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Parsear la respuesta para obtener el sessionKey
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                Map<String, Object> responseBody = objectMapper.readValue(response.getBody(), new TypeReference<>() {});
                String sessionKey = (String) responseBody.get("sessionKey");
                Long expirationTime = (Long) responseBody.get("expirationTime");
                System.out.println("Token de sesión recibido: " + sessionKey + ", Vigencia: " + expirationTime);
                return sessionKey;
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error al procesar la respuesta del token de sesión", e);
            }
        } else {
            throw new RuntimeException("Error al generar el token de sesión: " + response.getBody());
        }
    }

    public Map<String, Object> generateAuthorizationToken(String accessToken, Map<String, Object> order) {
        try {
            // Merchant ID del comercio
            String merchantId = "456879852"; // Cambia según tus datos de pruebas o producción.

            // Configurar headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", accessToken); // El token se envía directamente, sin el prefijo Bearer.

            // Crear cuerpo de la solicitud
            Map<String, Object> requestBody = Map.of(
                    "channel", "web",
                    "captureType", "manual",
                    "countable", true,
                    "order", order
            );

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

            // Enviar solicitud HTTP
            ResponseEntity<Map> response = restTemplate.exchange(
                    AUTHORIZATION_URL,
                    HttpMethod.POST,
                    request,
                    Map.class,
                    merchantId
            );

            // Verificar respuesta
            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("Autorización exitosa: " + response.getBody());
                return response.getBody();
            } else {
                throw new RuntimeException("Error en la autorización: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al generar el Authorization Token: " + e.getMessage(), e);
        }
    }



}
