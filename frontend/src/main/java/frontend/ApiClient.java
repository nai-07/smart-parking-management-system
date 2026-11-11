package frontend;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {

    private static final String BASE_URL = "http://localhost:8080";

    private final HttpClient client = HttpClient.newHttpClient();

    public String get(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        return send(request);
    }

    public String post(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return send(request);
    }

    public String postJson(String endpoint, String json)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        return send(request);
    }

    public int getStatus(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .GET()
                .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        ).statusCode();
    }

    public int postStatus(String endpoint)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        return client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        ).statusCode();
    }

    private String send(HttpRequest request)
            throws IOException, InterruptedException {

        HttpResponse<String> response =
                client.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

        return response.statusCode() + "||" + response.body();
    }

    public static int statusCode(String response) {
        int separator = response.indexOf("||");

        if (separator == -1) {
            return 500;
        }

        return Integer.parseInt(
                response.substring(0, separator)
        );
    }

    public static String body(String response) {
        int separator = response.indexOf("||");

        if (separator == -1) {
            return response;
        }

        return response.substring(separator + 2);
    }
}