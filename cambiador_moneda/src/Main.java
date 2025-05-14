import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<Integer, String> MONEDAS = Map.of(
            1, "ARS",
            2, "BOB",
            3, "BRL",
            4, "CLP",
            5, "COP",
            6, "USD"
    );

    private static final Map<String, String> NOMBRES_MONEDAS = Map.of(
            "ARS", "Peso argentino",
            "BOB", "Boliviano",
            "BRL", "Real brasileño",
            "CLP", "Peso chileno",
            "COP", "Peso colombiano",
            "USD", "Dólar estadounidense"
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean seguirEjecutando = true;

        while (seguirEjecutando) {
            System.out.println("=== CONVERSOR DE MONEDAS ===");
            mostrarMenu();

            System.out.println("7. Salir");
            System.out.print("Seleccione una opción [1-7]: ");
            int opcion = sc.nextInt();

            if (opcion == 7) {
                System.out.println("¡Gracias por usar el conversor! Adiós.");
                seguirEjecutando = false;
            } else if (!MONEDAS.containsKey(opcion)) {
                System.out.println("Opción inválida. Intenta nuevamente.");
            } else {
                // Procedemos con la conversión
                System.out.print("Seleccione la moneda base [1-6]: ");
                int opcionBase = sc.nextInt();
                System.out.print("Seleccione la moneda destino [1-6]: ");
                int opcionDestino = sc.nextInt();

                // Validación de entrada
                if (!MONEDAS.containsKey(opcionBase) || !MONEDAS.containsKey(opcionDestino)) {
                    System.out.println("Opción inválida. Reinicie el programa.");
                    continue;
                }

                String base = MONEDAS.get(opcionBase);
                String destino = MONEDAS.get(opcionDestino);

                if (base.equals(destino)) {
                    System.out.println("La moneda base y destino no pueden ser iguales.");
                    continue;
                }

                System.out.print("Cantidad a convertir: ");
                double cantidad = sc.nextDouble();

                // Obtener datos desde la API
                String json = ApiService.getExchangeRates(base);
                Map<String, Double> tasas = JsonParserHelper.obtenerTasasFiltradas(json);

                if (!tasas.containsKey(destino)) {
                    System.out.println("No se pudo obtener la tasa de cambio para " + destino);
                    continue;
                }

                double tasa = tasas.get(destino);
                double resultado = convertirMoneda(cantidad, tasa);  // Llamada al método de conversión
                //multiplicar la cantidad por la tasa y devolver el valor convertido


                System.out.printf("%.2f %s (%s) = %.2f %s (%s)%n",
                        cantidad, base, NOMBRES_MONEDAS.get(base),
                        resultado, destino, NOMBRES_MONEDAS.get(destino));
            }
        }
    }

    // Método para convertir las monedas
    private static double convertirMoneda(double cantidad, double tasa) {
        return cantidad * tasa;
    }

    private static void mostrarMenu() {
        for (Map.Entry<Integer, String> entrada : MONEDAS.entrySet()) {
            String codigo = entrada.getValue();
            System.out.println(entrada.getKey() + ". " + codigo + " - " + NOMBRES_MONEDAS.get(codigo));
        }
    }
}
