package com.pascal.ecommerce.backend.controller;

import com.pascal.ecommerce.backend.service.niubiz.NiubizService;
import com.pascal.ecommerce.backend.service.niubiz.UniversalStorageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/niubiz")
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:5173")
public class NiubizController {

    private final NiubizService niubizService;
    private final UniversalStorageService universalStorageService;

    public NiubizController(NiubizService niubizService, UniversalStorageService universalStorageService) {
        this.niubizService = niubizService;
        this.universalStorageService = universalStorageService;
    }

    @PostMapping("/generate-session-token")
    public ResponseEntity<Map<String, Object>> generateSessionToken(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        try {
            // Extraer el valor de amount del body
            if (!requestBody.containsKey("amount") || !(requestBody.get("amount") instanceof Number)) {
                throw new IllegalArgumentException("El campo 'amount' es obligatorio y debe ser un número.");
            }
            double amount = ((Number) requestBody.get("amount")).doubleValue();

            universalStorageService.storeAmount(amount);

            // Generar token de acceso
            String accessToken = niubizService.generateAccessToken();
            universalStorageService.storeAccessToken(accessToken); //se guarda el token de acceso
            response.put("accessToken", accessToken);
            System.out.println("Access Token generado: " + accessToken);

            // Generar token de sesión
            String sessionToken = niubizService.generateSessionToken(accessToken, amount);
            response.put("sessionToken", sessionToken);
            System.out.println("Session Token generado: " + sessionToken);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            // Manejo de errores en la generación de tokens
            response.put("error", "Error al generar los tokens: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping(value = "/response-form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<String> handleFormUrlEncodedResponse(

            @RequestParam Map<String, String> formData) {
        try {
            String channel = formData.get("channel");
            if (Objects.equals(channel, "web")){
                String transactionToken = formData.get("transactionToken");
                String customerEmail = formData.get("customerEmail");
                System.out.println("Transaction Token: " + transactionToken);
                System.out.println("customerEmail: " + customerEmail);

                universalStorageService.storeTransactionToken(transactionToken);
            }
            if (Objects.equals(channel, "pagoefectivo ")){
                String transactionToken = formData.get("transactionToken");
                String customerEmail = formData.get("customerEmail");
                String url = formData.get("url");
                System.out.println("Transaction Token: " + transactionToken);
                System.out.println("customerEmail: " + customerEmail);
                System.out.println("url: " + url);

                universalStorageService.storeTransactionToken(transactionToken);
            }

            return ResponseEntity.ok("Respuesta procesada correctamente");
        } catch (Exception e) {
            return ResponseEntity.ok("Error en el pago intentalo nuevamente");
        }
    }

    @PostMapping("/generate-authorization-token")
    public ResponseEntity<Map<String, Object>> generateAuthorizationToken(
            @RequestParam String purchaseNumber) {
        Map<String, Object> response = new HashMap<>();
        String accessToken = universalStorageService.getAccessToken();
        String transactionId = universalStorageService.getTransactionToken();
        System.out.println("Token de acceso auth: " + accessToken);
        System.out.println("Transaction Id auth: " + transactionId);
        try {
            // Crear la data para la autorización
            Map<String, Object> order = Map.of(
                    "tokenId", transactionId,
                    "purchaseNumber", purchaseNumber,
                    "amount", universalStorageService.getAmount(),
                    "currency", "PEN"
            );

            // Generar Authorization Token
            Map<String, Object> authorizationResponse = niubizService.generateAuthorizationToken(accessToken, order);
            response.putAll(authorizationResponse);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // Endpoint para manejar el tiempo de espera de una sesión
    @GetMapping("/timeout")
    public ResponseEntity<String> handleTimeout(@RequestParam("id") String purchaseNumber) {
        try {
            // Lógica para manejar la expiración del tiempo de espera
                        return ResponseEntity.ok("Tiempo de espera manejado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al manejar el tiempo de espera: " + e.getMessage());
        }
    }

}