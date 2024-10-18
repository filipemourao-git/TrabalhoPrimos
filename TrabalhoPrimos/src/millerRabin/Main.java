package millerRabin;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        // Número a ser testado (exemplo de número de 4096 bits)
        BigInteger n = new BigInteger("5210644015679228794060694325390955853335898483908056458352183851018372555735221"); // Substitua por um número real de até 4096 bits
        int k = 100; // Número de iterações para o teste.
        
        //Cria uma instância da classe MillerRabinTest chamada tester
        MillerRabinTest tester = new MillerRabinTest(n, k);
        //Chama o método test() da instância tester
        tester.test();
    }
}
