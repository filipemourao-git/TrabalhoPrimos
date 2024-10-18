package linearCongruentionalGenerator;

import java.math.BigInteger;
import java.security.SecureRandom;

public class LCG {
    private BigInteger seed;  // Semente
    private final BigInteger a;  // Multiplicador
    private final BigInteger c;  // Incremento
    private final BigInteger m;  // Módulo

    // Construtor que inicializa a semente, multiplicador, incremento e módulo
    public LCG(BigInteger seed, BigInteger a, BigInteger c, BigInteger m) {
        this.seed = seed;
        this.a = a;
        this.c = c;
        this.m = m;
    }

    // Gera o próximo número aleatório de x bits (conforme eu determinar nos parâmetros).
    public BigInteger nextRandom() {
        seed = (a.multiply(seed).add(c)).mod(m);
        return seed;
    }

    // Método estático para gerar uma quantidade especificada de números aleatórios
    public static BigInteger[] generateRandomNumbers(int count) {
        SecureRandom secureRandom = new SecureRandom();

        // Usar a hora atual como semente
        BigInteger seed = BigInteger.valueOf(System.currentTimeMillis()); // Semente baseada na hora atual

        // Gera parâmetros aleatórios
        BigInteger a = new BigInteger(4096, secureRandom); // Multiplicador
        BigInteger c = new BigInteger(4096, secureRandom); // Incremento
        BigInteger m = new BigInteger(4096, secureRandom); // Módulo 

        // Cria uma nova instância de LCG
        LCG lcg = new LCG(seed, a, c, m);
        BigInteger[] randomNumbers = new BigInteger[count];

        // Gera números aleatórios
        for (int i = 0; i < count; i++) {
            randomNumbers[i] = lcg.nextRandom();
        }

        return randomNumbers;
    }
}
