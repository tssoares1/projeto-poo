package com.fag.infra.celcoin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpRequest.Builder;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.fag.domain.dto.BankslipDTO;
import com.fag.domain.repositories.IBassRepository;
import com.fag.infra.utils.Utils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CelcoinBassRepository implements IBassRepository {
    private final HttpClient client;
    private final String clientId;
    private final String clientSecret;
    private final Utils utils;

    public CelcoinBassRepository() {
        this.client = HttpClient.newHttpClient();
        this.clientId = "41b44ab9a56440.teste.celcoinapi.v5";
        this.clientSecret = "e9d15cde33024c1494de7480e69b7a18c09d7cd25a8446839b3be82a56a044a3";
        this.utils = new Utils();
    }

    @Override
    public String consultInvoice(String barcode) {
        try {
            String params = "{\"barCode\":{\"type\":0,\"digitable\":\"" + barcode + "\"}}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sandbox.openfinance.celcoin.dev/v5/transactions/billpayments/authorize"))
                    .header("Content-Type", "application/json")
                    .header("accept", "application/json")
                    .header("authorization", "Bearer " + getAuthorization())
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject registerData = jsonResponse.getAsJsonObject("registerData");

            String assignor = "Assignor: " + jsonResponse.get("assignor");
            String recipient = "Recipient: " + registerData.get("recipient");
            String documentRecipient = "Document Recipient: " + registerData.get("documentRecipient");
            String payer = "Payer: " + registerData.get("payer");
            String documentPayer = "Document Payer: " + registerData.get("documentPayer");
            String payDueDate = "Pay Due Date: " + registerData.get("payDueDate");
            String value = "Value: " + jsonResponse.get("value");
            String discountValue = "Discount Value: " + registerData.get("discountValue");

            String transactionId = "Transaction ID: " + jsonResponse.get("transactionId");
            String digitable = "Digitable: " + jsonResponse.get("digitable");
            String type = "Type: " + jsonResponse.get("type");

            return String.join("\n",
                    assignor, recipient, documentRecipient, payer, documentPayer,
                    payDueDate, value, discountValue, transactionId,
                    digitable, type);

        } catch (Exception e) {
            throw new RuntimeException("Failed to Consult Invoice", e);
        }
    }

    @Override
    public String payInvoice(BankslipDTO payload) {
        try {
            String params = "{\r\n" + //
                    "  \"cpfCnpj\": \"24602516025\",\r\n" + //
                    "  \"billData\": {\r\n" + //
                    "    \"value\": " + payload.getValue() + "\r\n" + //
                    "  },\r\n" + //
                    "  \"barCode\": {\r\n" + //
                    "    \"digitable\": \"" + payload.getBarcode() + "\"\r\n" + //
                    "  },\r\n" + //
                    "  \"dueDate\": \"2021-05-25T00:00:00Z\",\r\n" + //
                    "  \"transactionIdAuthorize\": " + payload.getTransactionId() + "\r\n" + //
                    "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sandbox.openfinance.celcoin.dev/v5/transactions/billpayments"))
                    .header("Content-Type", "application/json")
                    .header("authorization", "Bearer " + getAuthorization())
                    .POST(HttpRequest.BodyPublishers.ofString(params))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonObject receipt = jsonResponse.getAsJsonObject("receipt");

            String convenant = "Convenant: " + jsonResponse.get("convenant");
            String recipientFormatted = "Recipient Formatted: " +
                    receipt.get("receiptformatted");

            return String.join("\n", convenant, recipientFormatted);

        } catch (Exception e) {
            throw new RuntimeException("Failed to Consult Invoice", e);
        }
    }

    @Override
    public String generatedQRCode(Double payload) {
        try {
            String params = "{\n" + //
                    "  \"key\": \"testepix@celcoin.com.br\",\n" + //
                    "  \"amount\": " + payload + ",\n" + //
                    "  \"merchant\": {\n" + //
                    "    \"postalCode\": \"01201005\",\n" + //
                    "    \"city\": \"Barueri\",\n" + //
                    "    \"merchantCategoryCode\": 0,\n" + //
                    "    \"name\": \"Celcoin\"\n" + //
                    "  }\n" + //
                    "}";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sandbox.openfinance.celcoin.dev/pix/v1/brcode/static"))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + getAuthorization())
                    .POST(BodyPublishers.ofString(params))
                    .build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();

            String transactionId = "Transaction ID: " + jsonResponse.get("transactionId");
            String emvqrcps = "Emvqrcps: " + jsonResponse.get("emvqrcps");
            String transactionIdentification = "Transaction Identification: "
                    + jsonResponse.get("transactionIdentification");

            return String.join("\n", transactionId, emvqrcps, transactionIdentification);
        } catch (Exception e) {
            throw new RuntimeException("Falied to Generate QR Code");
        }

    }

    private String getAuthorization() {
        try {
            String urlParams = String.format("client_id=%s&grant_type=client_credentials&client_secret=%s", clientId,
                    clientSecret);

            Builder request = HttpRequest.newBuilder()
                    .uri(URI.create("https://sandbox.openfinance.celcoin.dev/v5/token"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(urlParams));

            HttpResponse<String> response = client.send(request.build(), BodyHandlers.ofString());

            return utils.toJson(response.body()).get("access_token").toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get authentication", e);
        }
    }
}
