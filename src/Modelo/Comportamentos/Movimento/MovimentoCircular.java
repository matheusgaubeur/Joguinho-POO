package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;
import java.io.Serializable;

//Circular (Horário), que vira ao bater na parede.
public class MovimentoCircular implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    // 0: Direita, 1: Baixo, 2: Esquerda, 3: Cima
    private int direcaoAtual; 
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoCircular(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.direcaoAtual = 0; // Começa movendo para a Direita
    }

    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        if (contadorDeFrames == velocidade) {
            contadorDeFrames = 0;
            // Chama o "O QUÊ"
            this.proximoMovimento(p); 
        }
        contadorDeFrames++;
    }

    private void proximoMovimento(Personagem p) {
        boolean moveu = false;
        
        // Tenta mover na direção atual
        switch (direcaoAtual) {
            case 0 -> moveu = p.moveRight();
            case 1 -> moveu = p.moveDown();
            case 2 -> moveu = p.moveLeft();
            case 3 -> moveu = p.moveUp();
        }

        // Se não moveu (bateu na parede), vira para a próxima direção
        if (!moveu) {
            direcaoAtual = (direcaoAtual + 1) % 4;
        }
    }
}