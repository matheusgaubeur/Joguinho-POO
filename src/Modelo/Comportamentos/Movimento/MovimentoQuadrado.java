package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Estratégia (Padrão Strategy) que implementa a lógica de movimento
 * em Quadrado (para o Peixe).
 */
public class MovimentoQuadrado implements ComportamentoMovimento {

    private int tamanhoDoLado;
    private int passosRestantes;
    private int direcaoAtual; // 0: Cima, 1: Direita, 2: Baixo, 3: Esquerda
    
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoQuadrado(int velocidade, int tamanhoDoLado) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        
        this.tamanhoDoLado = tamanhoDoLado;
        this.passosRestantes = tamanhoDoLado;
        this.direcaoAtual = 0; // Começa movendo para Cima
    }

    /**
     * O "QUANDO" (a lógica do timer)
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
     * O "O QUÊ" (a lógica que estava em Peixe)
     */
    private void proximoMovimento(Personagem p) {
        boolean moveu = false;
        
        if (passosRestantes > 0) {
            switch (direcaoAtual) {
                case 0: moveu = p.moveUp(); break;
                case 1: moveu = p.moveRight(); break;
                case 2: moveu = p.moveDown(); break;
                case 3: moveu = p.moveLeft(); break;
            }
            
            if(moveu) {
                passosRestantes--;
            } else {
                passosRestantes = 0; // Bateu na parede, força a virada
            }

        } else {
            // Acabaram os passos, vira
            direcaoAtual = (direcaoAtual + 1) % 4;
            passosRestantes = tamanhoDoLado;
        }
    }
}