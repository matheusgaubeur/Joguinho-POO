package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Estratégia (Padrão Strategy) que implementa a lógica de movimento
 * "Vai e Vem Vertical" (cima-baixo).
 */
public class MovimentoVaiVemVertical implements ComportamentoMovimento {

    private boolean bUp;
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoVaiVemVertical(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.bUp = true; // Começa indo para cima
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
     * O "O QUÊ" (a lógica que estava em BichinhoVaiVemVertical)
     */
    private void proximoMovimento(Personagem p) {
        if (bUp) {
            p.moveUp();
        } else {
            p.moveDown();
        }
        bUp = !bUp; // Inverte a direção para o próximo loop
    }
}