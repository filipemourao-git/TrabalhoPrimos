package millerRabinIsaac;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        // Semente baseada no currentTimeMillis (hora atual)
        long currentTime = System.currentTimeMillis();

        // Inicializa um array de inteiros diretamente no código principal
        int[] seed = new int[256]; // Tamanho da semente para o ISAAC é 256
        for (int i = 0; i < 256; i++) {
            seed[i] = (int) (currentTime ^ (currentTime >>> i)); // Gera variações da semente
        }

        // Inicializa o gerador ISAAC com a semente gerada
        Isaac isaac = new Isaac(seed);

        int k = 100; // Número de iterações para o teste de Miller-Rabin
        BigInteger n; // Variável para armazenar o número gerado

        // Loop até encontrar um número primo
        while (true) {
            // Gera um número aleatório usando o ISAAC
            n = isaac.val4096();

            // Verifica se o número gerado é ímpar (evitando números pares)
            if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                continue; // Se for par, pula para o próximo número
            }

            // Exibe o número gerado
            System.out.println("Testando o número: " + n);

            // Captura a saída do teste de primalidade
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);
            PrintStream originalOut = System.out; // Salva o PrintStream original
            System.setOut(printStream); // Redireciona a saída para capturar

            // Cria uma instância do teste de Miller-Rabin
            MillerRabinTest tester = new MillerRabinTest(n, k);

            // Executa o teste de primalidade
            tester.test();

            // Restaura o PrintStream original
            System.setOut(originalOut);
            String output = outputStream.toString(); // Captura a saída

            // Verifica se o resultado indica que n é primo
            if (output.contains("é provavelmente primo")) {
                System.out.println(n + " é possivelmente primo!"); // Exibe que encontramos um primo
                break; // Parar o loop
            }
        }
    }
}
