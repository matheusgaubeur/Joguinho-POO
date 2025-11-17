package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

// Vai e Vem Vertical (cima-baixo)
public class MovimentoVaiVemVertical implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    private boolean bUp;
    private int contadorDeFrames;
    private final int velocidade;

    public MovimentoVaiVemVertical(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.bUp = true; // Começa indo para cima
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
        if (bUp) {
            p.moveUp();
        } else {
            p.moveDown();
        }
        bUp = !bUp; // Inverte a direção para o próximo loop
    }
}