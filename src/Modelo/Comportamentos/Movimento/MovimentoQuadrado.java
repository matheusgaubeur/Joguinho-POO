package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

public class MovimentoQuadrado implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    private final int tamanhoDoLado;
    private int passosRestantes;
    private int direcaoAtual; // 0: Cima, 1: Direita, 2: Baixo, 3: Esquerda
    
    private int contadorDeFrames;
    private final int velocidade;

    public MovimentoQuadrado(int velocidade, int tamanhoDoLado) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        
        this.tamanhoDoLado = tamanhoDoLado;
        this.passosRestantes = tamanhoDoLado;
        this.direcaoAtual = 0; // Começa movendo para Cima
    }

    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        if (contadorDeFrames == velocidade) {
            contadorDeFrames = 0;
            this.proximoMovimento(p); 
        }
        contadorDeFrames++;
    }

    private void proximoMovimento(Personagem p) {
        boolean moveu = false;
        
        if (passosRestantes > 0) {
            switch (direcaoAtual) {
                case 0 -> moveu = p.moveUp();
                case 1 -> moveu = p.moveRight();
                case 2 -> moveu = p.moveDown();
                case 3 -> moveu = p.moveLeft();
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