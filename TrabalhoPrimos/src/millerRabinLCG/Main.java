package millerRabinLCG;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        // Cria uma instância do gerador LCG com parâmetros aleatórios
        BigInteger seed = BigInteger.valueOf(System.currentTimeMillis()); //horário atual como semente
        BigInteger a = new BigInteger(4096, new java.security.SecureRandom());
        BigInteger c = new BigInteger(4096, new java.security.SecureRandom());
        BigInteger m = new BigInteger(4096, new java.security.SecureRandom());
        LCG lcg = new LCG(seed, a, c, m);

        int k = 100; // Número de iterações para o teste de Miller-Rabin
        BigInteger n; // Variável para armazenar o número gerado

        // Loop até encontrar um número primo
        while (true) {
            // Gera um número aleatório usando o LCG
            n = lcg.nextRandom();

            // Exibe o número gerado
            System.out.println("Testando o número: " + n);

            // Captura a saída do teste de primalidade
            
         // Cria um ByteArrayOutputStream para armazenar a saída do console em memória (como um array de bytes)
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            
         // Cria um PrintStream associado ao outputStream, que será usado para capturar a saída redirecionada
            PrintStream printStream = new PrintStream(outputStream);
            
            PrintStream originalOut = System.out; // Salvar o PrintStream original
            System.setOut(printStream); // Redirecionar a saída

            // Cria uma instância do teste de Miller-Rabin
            MillerRabin tester = new MillerRabin(n, k);

            // Executa o teste de primalidade
            tester.test();

            // Restaura o PrintStream original
            System.setOut(originalOut);
            String output = outputStream.toString(); // Captura a saída

            // Verifica se o resultado indica que n é primo
            if (output.contains("é provavelmente primo")) {
                System.out.println(n + " é primo!"); // Exibir que encontramos um primo
                break; // Parar o loop
            }
        }
    }
}
