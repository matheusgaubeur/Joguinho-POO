package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;
import java.io.Serializable; // <<-- IMPORTANTE (Estratégias devem ser serializáveis)

/**
 * Estratégia (Padrão Strategy) que implementa a lógica de movimento
 * Circular (Horário), que vira ao bater na parede.
 */
public class MovimentoCircular implements ComportamentoMovimento {

    // 0: Direita, 1: Baixo, 2: Esquerda, 3: Cima
    private int direcaoAtual; 
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoCircular(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.direcaoAtual = 0; // Começa movendo para a Direita
    }

    /**
     * O "QUANDO" (a lógica do timer que estava em InimigoPatrulha)
     */
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        if (contadorDeFrames == velocidade) {
            contadorDeFrames = 0;
            // Chama o "O QUÊ"
            this.proximoMovimento(p); 
        }
        contadorDeFrames++;
    }

    /**
     * O "O QUÊ" (a lógica que estava em InimigoCircular.java)
     * Tenta mover; se bater, vira 90 graus no sentido horário.
     */
    private void proximoMovimento(Personagem p) {
        boolean moveu = false;
        
        // Tenta mover na direção atual
        switch (direcaoAtual) {
            case 0: moveu = p.moveRight(); break;
            case 1: moveu = p.moveDown(); break;
            case 2: moveu = p.moveLeft(); break;
            case 3: moveu = p.moveUp(); break;
        }

        // Se não moveu (bateu na parede), vira 90 graus (próxima direção)
        if (!moveu) {
            direcaoAtual = (direcaoAtual + 1) % 4; // (0->1, 1->2, 2->3, 3->0)
        }
    }
}