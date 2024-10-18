package testeSolovay;

import java.math.BigInteger;
import java.security.SecureRandom;

public class Solovay {

    // Função para calcular o símbolo de Jacobi (a/n)
    private static int calcularJacobi(BigInteger a, BigInteger n) {
        // Se a é zero, o símbolo de Jacobi é zero
        if (a.equals(BigInteger.ZERO)) {
            return 0;
        }

        int resultado = 1; // Inicializa o resultado como 1
        // Se 'a' for negativo, converte para positivo e ajusta o resultado se necessário
        if (a.compareTo(BigInteger.ZERO) < 0) {
            a = a.negate();
            // Ajusta resultado se n ≡ 3 (mod 4)
            if (n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                resultado = -resultado;
            }
        }

        // Enquanto 'a' não for zero, aplica a lógica do símbolo de Jacobi
        while (!a.equals(BigInteger.ZERO)) {
            // Divide 'a' por 2 até que seja ímpar, ajustando o resultado conforme necessário
            while (a.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
                a = a.divide(BigInteger.TWO);
                // Ajusta resultado se n ≡ 3 (mod 8) ou n ≡ 5 (mod 8)
                if (n.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(3)) || n.mod(BigInteger.valueOf(8)).equals(BigInteger.valueOf(5))) {
                    resultado = -resultado;
                }
            }

            // Troca 'a' e 'n' (n passa a ser o novo 'a')
            BigInteger temp = a;
            a = n;
            n = temp;

            // Se a e n ambos são 3 (mod 4), inverte o sinal do resultado
            if (a.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3)) && n.mod(BigInteger.valueOf(4)).equals(BigInteger.valueOf(3))) {
                resultado = -resultado;
            }

            // Atualiza 'a' para a mod n
            a = a.mod(n);
        }

        // Retorna o resultado: 0 se n não é primo, 1 ou -1 se n é primo
        return (n.equals(BigInteger.ONE)) ? resultado : 0;
    }

    // Função de exponenciação modular
    private static BigInteger expModular(BigInteger base, BigInteger expoente, BigInteger mod) {
        BigInteger resultado = BigInteger.ONE; // Inicializa o resultado como 1
        base = base.mod(mod); // Ajusta a base para estar no módulo
        // Enquanto o expoente for maior que zero, calcula a exponenciação modular
        while (expoente.compareTo(BigInteger.ZERO) > 0) {
            // Se o expoente for ímpar, multiplica o resultado pela base
            if (expoente.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                resultado = resultado.multiply(base).mod(mod);
            }
            // Eleva a base ao quadrado
            base = base.multiply(base).mod(mod);
            // Divide o expoente por 2
            expoente = expoente.divide(BigInteger.TWO);
        }
        return resultado; // Retorna o resultado da exponenciação modular
    }

    // Teste de Solovay-Strassen
    public static boolean testeSolovay(BigInteger n, int iteracoes) {
        // Checa se n é menor que 2 ou par, retorna false se for, exceto para 2
        if (n.compareTo(BigInteger.TWO) < 0 || n.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            return n.equals(BigInteger.TWO); // Caso especial: 2 é primo
        }

        SecureRandom random = new SecureRandom(); // Inicializa gerador de números aleatórios

        // Realiza várias iterações do teste
        for (int i = 0; i < iteracoes; i++) {
            // Gera um número aleatório 'a' entre 2 e n-2
            BigInteger a = BigInteger.valueOf(2 + random.nextInt(n.subtract(BigInteger.valueOf(2)).intValue()));

            // Calcula o símbolo de Jacobi (a/n)
            int simboloJacobi = calcularJacobi(a, n);

            // Se o símbolo de Jacobi for 0, n é composto
            if (simboloJacobi == 0) {
                return false;
            }

            // Calcula a^(n-1)/2 % n
            BigInteger expoente = n.subtract(BigInteger.ONE).divide(BigInteger.TWO);
            BigInteger resultadoExpModular = expModular(a, expoente, n);

            // Verifica se a condição do teste é satisfeita
            if (!resultadoExpModular.equals(BigInteger.valueOf(simboloJacobi).mod(n))) {
                return false; // Se a condição falhar, n é composto
            }
        }

        // Se passar todas as iterações, n é provavelmente primo
        return true;
    }
}
