package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class MovimentoZigueZague implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;

    private final Random rand;
    private int contadorDeFrames;
    private final int velocidade;

    public MovimentoZigueZague(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.rand = new Random();
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
        int iDirecao = rand.nextInt(4); // Gera 0, 1, 2 ou 3
        switch (iDirecao) {
            case 0 -> p.moveUp();
            case 1 -> p.moveDown();
            case 2 -> p.moveRight();
            case 3 -> p.moveLeft();
            default -> {
            }
        }
    }
}