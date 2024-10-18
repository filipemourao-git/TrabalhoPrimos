package linearCongruentionalGenerator;

import java.math.BigInteger;

public class Main {
    public static void main(String[] args) {
        // Usar o método estático da classe LCG para gerar os parâmetros aleatórios
        LCG lcg = new LCG(
            BigInteger.valueOf(System.currentTimeMillis()), // Semente baseada na hora atual
            new BigInteger(4096, new java.security.SecureRandom()), // Multiplicador
            new BigInteger(4096, new java.security.SecureRandom()), // Incremento
            new BigInteger(4096, new java.security.SecureRandom()) // Módulo
        );

        // Criar uma instância de LCGOutput e chamar o método para gerar e imprimir números
        LcgOutput output = new LcgOutput();
        output.printAndMeasure(lcg, 2000); // Gerar e imprimir 2000 números
    }
}
