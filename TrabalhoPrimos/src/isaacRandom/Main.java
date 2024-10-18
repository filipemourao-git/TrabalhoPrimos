package isaacRandom;

public class Main {
    public static void main(String[] args) {
        // Cria um array de inteiros para armazenar a semente inicial
        int[] seed = new int[256];

        // Obtém o horário atual em milissegundos para usar como semente
        long currentTimeMillis = System.currentTimeMillis();

        // Preenche a semente com o horário atual
        for (int i = 0; i < seed.length; i++) {
            seed[i] = (int) currentTimeMillis;
        }

        // Inicializa o gerador de números aleatórios Isaac com a semente gerada
        Isaac isaac = new Isaac(seed);

        // Cria uma instância de IsaacOutput e gera 2000 números
        IsaacOutput output = new IsaacOutput();
        output.printAndMeasure(isaac, 2000); // Gerar e medir 2000 números
    }
}
