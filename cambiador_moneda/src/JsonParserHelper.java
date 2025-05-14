import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

public class JsonParserHelper {

    public static Map<String, Double> obtenerTasasFiltradas(String json) {
        Map<String, Double> tasasFiltradas = new HashMap<>();

        String[] monedasPermitidas = { "ARS", "BOB", "BRL", "CLP", "COP", "USD" };

        try {
            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            JsonObject conversionRates = jsonObject.getAsJsonObject("conversion_rates");

            for (String codigo : monedasPermitidas) {
                if (conversionRates.has(codigo)) {
                    tasasFiltradas.put(codigo, conversionRates.get(codigo).getAsDouble());
                }
            }

        } catch (Exception e) {
            System.out.println("Error al analizar el JSON: " + e.getMessage());
        }

        return tasasFiltradas;
    }
}
