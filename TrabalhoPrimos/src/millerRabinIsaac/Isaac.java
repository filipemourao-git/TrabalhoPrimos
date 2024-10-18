package millerRabinIsaac;

import java.math.BigInteger;

public class Isaac {

    // Constantes usadas no algoritmo ISAAC
    final static int SIZEL = 8;                   // Logaritmo do tamanho de rsl[] e mem[] (Um valor de 8 significa que o tamanho será 2 elevado a oitava potência).
    final static int SIZE = 1 << SIZEL;            // Tamanho de rsl[] e mem[](tamanho real do array que será 256).
    final static int MASK = (SIZE - 1) << 2;       //uso de uma máscara de bits(1020) para realizar uma operação de busca no array(mem), que contém números pseudoaleatórios.

    // Variáveis internas do gerador ISAAC
    private int count;                             // Contador que mantém a contagem de quantos resultados já foram gerados e armazenados em rsl[].
    private int rsl[];                             // Um array que armazena os resultados gerados pelo gerador.
    private int mem[];                             // Um array que contém o estado interno do gerador.
    
    //Variáveis internas auxiliares utilizadas no algoritmo para acumulação e controle.
    
    private int a;                                 // Acumulador.
    private int b;                                 // Último resultado.
    private int c;                                 // Contador, garante ciclo de pelo menos 2^40.

   
    // Construtor com semente, inicializa o gerador com a semente fornecida.
    public Isaac(int seed[]) { //Recebe um array de sementes (seed[]) que é copiado para rsl[].(nesse caso utilizei como semente a hora atual, aplicada na classe main).
    	
    	//mem e rsl são inicializados com o tamanho definido por SIZE.
        mem = new int[SIZE];
        rsl = new int[SIZE];
        
        // Copia a semente fornecida para o array de resultados rsl[].
        for (int i = 0; i < seed.length; ++i) {
            rsl[i] = seed[i];
        }
        
        Init(true);  // Inicializa o gerador com a semente.
    }

    // Método gerarNumeros: Gera 256 resultados aleatórios com base no estado atual do gerador.
    public final void gerarNumeros() {
        int i, j, x, y;

        b += ++c;  // Incrementa o contador c e ajusta o valor de b.
        
        
        for (i = 0, j = SIZE / 2; i < SIZE / 2; ) {   //dois loops aninhados que geram os números aleatórios(i e j controlam os índices usados para acessar os arrays mem[] e rsl[]).
         
        	
    //Dentro do loop, o código realiza várias operações em a, b, e mem[] para gerar números aleatórios:


        	x = mem[i];                  // Obtém o valor de mem[i]
            a ^= a << 13;                // Realiza uma operação de deslocamento e XOR em a (neste caso desloca a 13 bits para a esquerda e aplica uma operação XOR entre a e o resultado.)
            a += mem[j++];               // Adiciona o valor de mem[j] ao a
            
    /**O operador x &(AND bit a bit) MASK aplica uma máscara para limitar o índice dentro do array mem[], e o deslocamento de 2 bits (>> 2) ajusta o valor para um índice válido. 
       O resultado obtido é somado a a e b, gerando o valor y que será armazenado no array mem[].*/
            
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;  
            
    /**atualiza b com um novo valor gerado a partir de um lookup em mem[] baseado no valor de y (deslocado e mascarado), somado a x. 
       Esse novo valor de b é então armazenado no array rsl[] como parte dos números pseudoaleatórios gerados.*/
             
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;  
            
    //prosegue diversos rounds

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;

            x = mem[i];
            a ^= a << 2;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;
        }

        for (j = 0; j < SIZE / 2; ) {
            x = mem[i];
            a ^= a << 13;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 6;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;

            x = mem[i];
            a ^= a << 2;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;

            x = mem[i];
            a ^= a >>> 16;
            a += mem[j++];
            mem[i] = y = mem[(x & MASK) >> 2] + a + b;
            rsl[i++] = b = mem[((y >> SIZEL) & MASK) >> 2] + x;
        }
    }

    // Inicializa ou reinicializa o gerador ISAAC 
    public final void Init(boolean flag) {
        int i;
        
    /**Variaveis usadas temporariamente para armazenar valores intermediários que serão utilizados no processo de inicialização e "embaralhamento" do gerador. 
      Serve para gerar os valores que serão armazenados no array mem[].*/
        int a, b, c, d, e, f, g, h; 
   
    //todas as variáveis são inicializadas com o valor da constante hexadecimal 0x9e3779b9, a raãzo dourada.
        a = b = c = d = e = f = g = h = 0x9e3779b9;  /** A razão dourada, baseada na proporção áurea, um valor "mágico" que ajuda a misturar os dados.
                                                         Representação aproximada hexadecimal do número decimal 2,654,435,769.).
                                                         Conhecida como a constante dourada em várias implementações de algoritmos, especialmente em geradores pseudoaleatórios.*/

   
    /** Realiza 4 rounds(xors, shifts e adições são aplicados)
        Cada iteração do loop aplica uma série de operações de deslocamento e soma nas variáveis para embaralhar os valores.*/
        
        for (i = 0; i < 4; ++i) {
            a ^= b << 11;  d += a;  b += c;
            b ^= c >>> 2;  e += b;  c += d;
            c ^= d << 8;   f += c;  d += e;
            d ^= e >>> 16; g += d;  e += f;
            e ^= f << 10;  h += e;  f += g;
            f ^= g >>> 4;  a += f;  g += h;
            g ^= h << 8;   b += g;  h += a;
            h ^= a >>> 9;  c += h;  a += b;
        }

     
        /** Bloco responsável por acumular valores do array rsl nas variáveis internas a, b, c, d, e, f, g e h.
           O loop repete de 0 até SIZE(256), processando 8 elementos por vez, incrementando i em 8 a cada iteração. A condição 'if (flag)' verifica se o bloco deve ser executado. 
           Se verdadeiro, os elementos rsl[i] a rsl[i + 7] são somados às respectivas variáveis. O objetivo é inicializar essas variáveis com valores do array rsl, que serão utilizados depois.*/
       
        for (i = 0; i < SIZE; i += 8) {
            if (flag) {
                a += rsl[i]; b += rsl[i + 1]; c += rsl[i + 2]; d += rsl[i + 3];
                e += rsl[i + 4]; f += rsl[i + 5]; g += rsl[i + 6]; h += rsl[i + 7];
            }
            a ^= b << 11;  d += a;  b += c;
            b ^= c >>> 2;  e += b;  c += d;
            c ^= d << 8;   f += c;  d += e;
            d ^= e >>> 16; g += d;  e += f;
            e ^= f << 10;  h += e;  f += g;
            f ^= g >>> 4;  a += f;  g += h;
            g ^= h << 8;   b += g;  h += a;
            h ^= a >>> 9;  c += h;  a += b;
            
    // Preenche a memória mem[] com valores embaralhados
            mem[i] = a; mem[i + 1] = b; mem[i + 2] = c; mem[i + 3] = d;
            mem[i + 4] = e; mem[i + 5] = f; mem[i + 6] = g; mem[i + 7] = h;
        }

    // Segunda passagem, faz todos os bits influenciarem mem[]
        if (flag) {
            for (i = 0; i < SIZE; i += 8) {
                a += mem[i]; b += mem[i + 1]; c += mem[i + 2]; d += mem[i + 3];
                e += mem[i + 4]; f += mem[i + 5]; g += mem[i + 6]; h += mem[i + 7];
                a ^= b << 11;  d += a;  b += c;
                b ^= c >>> 2;  e += b;  c += d;
                c ^= d << 8;   f += c;  d += e;
                d ^= e >>> 16; g += d;  e += f;
                e ^= f << 10;  h += e;  f += g;
                f ^= g >>> 4;  a += f;  g += h;
                g ^= h << 8;   b += g;  h += a;
                h ^= a >>> 9;  c += h;  a += b;
                mem[i] = a; mem[i + 1] = b; mem[i + 2] = c; mem[i + 3] = d;
                mem[i + 4] = e; mem[i + 5] = f; mem[i + 6] = g; mem[i + 7] = h;
            }
        }

    // Gera resultados iniciais
        gerarNumeros();  // Gera números aleatórios com base na nova inicialização
        count = SIZE;    // Reseta o contador
    }

    // Método que gera um número aleatório grande de até 4096 bits
    public BigInteger val4096() {
        byte[] randomBytes = new byte[512];  // 4096 bits = 512 bytes

    // Preenche o array com bytes aleatórios, O método verifica se count é 0, e se sim, chama gerarNumeros()
        for (int i = 0; i < randomBytes.length; i += 4) {
            if (count == 0) {
                gerarNumeros();  // Se todos os valores em rsl[] foram usados, gera mais
                count = SIZE;
            }

     /** Extrai quatro bytes do valor inteiro recuperado do array rsl, armazenando-os sequencialmente no array randomBytes. 
      *  Permite que os números aleatórios gerados sejam convertidos e armazenados como bytes, facilitando a manipulação e utilização de grandes números aleatórios (até 4096 bits) em outras partes do programa.
      */
            int value = rsl[--count];
            randomBytes[i] = (byte) (value);
            randomBytes[i + 1] = (byte) (value >> 8);
            randomBytes[i + 2] = (byte) (value >> 16);
            randomBytes[i + 3] = (byte) (value >> 24);
        }

        // Um novo objeto BigInteger é criado e retornado, representando o número aleatório gerado.
        return new BigInteger(1, randomBytes);
    }
}
