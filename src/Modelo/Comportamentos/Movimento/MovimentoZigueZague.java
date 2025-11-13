package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;
import java.util.Random;

/**
 * Estratégia (Padrão Strategy) que implementa a lógica de movimento
 * aleatório (ZigueZague).
 */
public class MovimentoZigueZague implements ComportamentoMovimento {

    private Random rand;
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoZigueZague(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.rand = new Random();
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
     * O "O QUÊ" (a lógica que estava em ZigueZague)
     */
    private void proximoMovimento(Personagem p) {
        int iDirecao = rand.nextInt(4); // Gera 0, 1, 2 ou 3
        
        if(iDirecao == 0)
            p.moveUp();
        else if(iDirecao == 1)
            p.moveDown();
        else if(iDirecao == 2)
            p.moveRight();
        else if(iDirecao == 3)
            p.moveLeft();
    }
}