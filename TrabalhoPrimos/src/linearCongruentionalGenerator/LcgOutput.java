package linearCongruentionalGenerator;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class LcgOutput {

    public void printAndMeasure(LCG lcg, int count) { 
        List<BigInteger> numbers = new ArrayList<>();//lista chamada numbers que armazenará números do tipo BigInteger
        List<Long> times = new ArrayList<>();//lista chamada times que armazenará valores do tipo Long, representando o tempo (em nanosegundos) levado para gerar cada número.

        // Cabeçalho da tabela
        System.out.printf("%-10s %-40s %-20s%n", "Índice", "Número Gerado", "Tempo (ns)");
        System.out.println("=".repeat(70));

        // Gera os números e calcular o tempo de cada geração
        for (int i = 0; i < count; i++) {
            long startTime = System.nanoTime(); // Tempo inicial

            BigInteger number = lcg.nextRandom(); // Gera o próximo número
            long endTime = System.nanoTime(); // Tempo final

            long duration = endTime - startTime; // Duração em nanosegundos
            times.add(duration);
            numbers.add(number);

            // Exibe o índice, número gerado e tempo de geração
            System.out.printf("%-10d %-40s %-20d%n", (i + 1), number.toString(), duration);
        }

        // Calcula a média aritmética do tempo de geração
        long totalDuration = 0;
        for (long time : times) {
            totalDuration += time;
        }

        long averageTime = totalDuration / count;
        System.out.println("\nMédia de tempo para gerar os números: " + averageTime + " ns");
    }
}
