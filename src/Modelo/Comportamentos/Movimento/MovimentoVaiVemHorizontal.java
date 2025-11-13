package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Estratégia (Padrão Strategy) que implementa a lógica de movimento
 * "Vai e Vem Horizontal" (esquerda-direita).
 * Esta classe agora contém a lógica de timer E a lógica de direção.
 */
public class MovimentoVaiVemHorizontal implements ComportamentoMovimento {

    private boolean bRight;
    private int contadorDeFrames;
    private int velocidade;

    public MovimentoVaiVemHorizontal(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
        this.bRight = true; // Começa indo para a direita
    }

    /**
     * Este é o "QUANDO" (a lógica do timer que estava em InimigoPatrulha)
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
     * Este é o "O QUÊ" (a lógica que estava em BichinhoVaiVemHorizontal)
     */
    private void proximoMovimento(Personagem p) {
        if (bRight) {
            p.moveRight();
        } else {
            p.moveLeft();
        }
        bRight = !bRight; // Inverte a direção para o próximo loop
    }
}