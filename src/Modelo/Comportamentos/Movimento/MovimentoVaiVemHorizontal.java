package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

//Vai e Vem Horizontal (esquerda-direita).
public class MovimentoVaiVemHorizontal implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    private boolean bRight;
    private int contadorDeFrames;
    private final int velocidade;

    public MovimentoVaiVemHorizontal(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.bRight = true; // Começa indo para a direita
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
        if (bRight) {
            p.moveRight();
        } else {
            p.moveLeft();
        }
        bRight = !bRight; // Inverte a direção para o próximo loop
    }
}