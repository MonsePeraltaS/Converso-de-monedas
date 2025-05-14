import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiService {
    private static final String API_KEY = "3550b28eb13ebfe93f7b40a2";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";

    public static String getExchangeRates(String baseCurrency) {
        String url = BASE_URL + API_KEY + "/latest/" + baseCurrency;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                System.out.println("Error: código de estado " + response.statusCode());
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al hacer solicitud: " + e.getMessage());
            return null;
        }
    }
}
