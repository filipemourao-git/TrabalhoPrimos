package millerRabinLCG;


import java.math.BigInteger;
import java.security.SecureRandom;

public class MillerRabin {

    public MillerRabin(BigInteger n, int k) {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
        // Número a ser testado
        BigInteger n = new BigInteger(""); //  um número real de até 4096 bits
        int k = 100; // Número de iterações para o teste.

        // Verifica se n é 2 ou 3
        if (n.equals(BigInteger.TWO) || n.equals(BigInteger.valueOf(3))) {
            System.out.println(n + " é primo.");
            return;
        }

        // Verifica se n é par ou menor que 2
        if (n.mod(BigInteger.TWO).equals(BigInteger.ZERO) || n.compareTo(BigInteger.TWO) < 0) {
            System.out.println(n + " é composto.");
            return;
        }

        // Decompor n-1 como 2^s * d
        BigInteger d = n.subtract(BigInteger.ONE);
        int s = 0;

        // Fatoração de potências de 2 de n-1
        while (d.mod(BigInteger.TWO).equals(BigInteger.ZERO)) {
            d = d.shiftRight(1); // d = d / 2
            s++;
        }

        SecureRandom rand = new SecureRandom(); // Usei SecureRandom para melhores números aleatórios

        // LOOP: repete o teste k vezes
        for (int i = 0; i < k; i++) {
            //  Gera o valor de 'a' tal que 1 < a < n - 1
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength() - 2, rand); // Número aleatório com (n.bitLength() - 2) bits
            } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(n.subtract(BigInteger.ONE)) >= 0);

            // x ← a^d mod n 
            BigInteger x = BigInteger.ONE;
            BigInteger tempD = d;
            BigInteger base = a;

            // Exponenciação modular 
            while (tempD.compareTo(BigInteger.ZERO) > 0) {
                // Se d é ímpar, multiplica o resultado pela base e tira o mod n
                if (tempD.mod(BigInteger.TWO).equals(BigInteger.ONE)) {
                    x = x.multiply(base).mod(n);
                }
                // Atualiza a base e divide d por 2
                base = base.multiply(base).mod(n);
                tempD = tempD.shiftRight(1); // d = d / 2
            }

            // Se x == 1 ou x == n-1, continua para a próxima iteração
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE))) {
                continue;
            }

            // Teste iterativo: para r = 1 .. s - 1
            boolean composite = true;
            for (int r = 0; r < s - 1; r++) {
                // x ← x^2 mod n
                x = x.multiply(x).mod(n);

                if (x.equals(BigInteger.ONE)) {
                    System.out.println(n + " é composto.");
                    return; // n é composto
                }
                if (x.equals(n.subtract(BigInteger.ONE))) {
                    composite = false;
                    break; // Salta para o próximo teste
                }
            }

            if (composite) {
                System.out.println(n + " é composto.");
                return; // n é composto
            }
        }

        // Se não foi detectado como composto após k iterações
        System.out.println(n + " é provavelmente primo.");
    }

	public void test() {
		// TODO Auto-generated method stub
		
	}
}
