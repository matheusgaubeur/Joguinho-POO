package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

// Diagonal (bate-volta).
public class MovimentoDiagonal implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    private boolean bCima;
    private boolean bDireita;
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoDiagonal(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.bCima = true;
        this.bDireita = true;
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
        boolean moveuVertical = false;
        boolean moveuHorizontal = false;

        // Tenta mover Vertical
        if (bCima) {
            moveuVertical = p.moveUp();
        } else {
            moveuVertical = p.moveDown();
        }

        // Tenta mover Horizontal
        if (bDireita) {
            moveuHorizontal = p.moveRight();
        } else {
            moveuHorizontal = p.moveLeft();
        }

        // Lógica de "Bater"
        if (!moveuVertical) {
            bCima = !bCima;
        }
        if (!moveuHorizontal) {
            bDireita = !bDireita;
        }
    }
}