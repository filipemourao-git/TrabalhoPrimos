package isaacRandom;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class IsaacOutput {

    public void printAndMeasure(Isaac isaac, int count) {
        List<BigInteger> numbers = new ArrayList<>();
        List<Long> times = new ArrayList<>();

        // Cabeçalho da tabela
        System.out.printf("%-5s | %-40s | %-15s%n", "Index", "Número Aleatório", "Tempo (ns)");
        System.out.println("=".repeat(70));

        long totalTime = 0; // Variável para acumular o tempo total

        // Loop para gerar e exibir os números aleatórios
        for (int i = 0; i < count; i++) {
            // Marca o início do tempo em nanossegundos
            long startTime = System.nanoTime();

            // Gera um número aleatório grande de até 4096 bits
            BigInteger bigRandomNumber = isaac.val4096();

            // Marca o fim do tempo em nanossegundos
            long endTime = System.nanoTime();

            // Calcula o tempo gasto
            long timeTaken = endTime - startTime;
            times.add(timeTaken);
            numbers.add(bigRandomNumber);

            // Acumula o tempo total
            totalTime += timeTaken;

            // Exibe o índice, o número aleatório gerado e o tempo gasto em nanossegundos
            System.out.printf("%-5d | %-40s | %-15d%n", i + 1, bigRandomNumber.toString(10), timeTaken);
        }

        // Calcular a média aritmética do tempo
        long averageTime = totalTime / count;

        // Exibe a média
        System.out.println("=".repeat(70));
        System.out.printf("Média de tempo para gerar %d números: %d ns%n", count, averageTime);
    }
}
