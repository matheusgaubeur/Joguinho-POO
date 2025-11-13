package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import auxiliar.Posicao;
import java.util.ArrayList;

/**
 * Estratégia de Movimento que persegue o Herói.
 */
public class MovimentoChaser implements ComportamentoMovimento {

    private int counter;
    private boolean iDirectionV;
    private boolean iDirectionH;

    public MovimentoChaser() {
        this.counter = 0;
        this.iDirectionH = true;
        this.iDirectionV = true;
    }
    
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        if (hero != null) {
            this.computeDirection(hero.getPosicao(), p.getPosicao());
        }
        
        if (counter == 5) { // (Velocidade do Chaser)
            if (iDirectionH) {
                p.moveLeft();
            } else {
                p.moveRight();
            }
            if (iDirectionV) {
                p.moveUp();
            } else {
                p.moveDown();
            }
            counter = 0;
        }
        counter++;
    }
    
    private void computeDirection(Posicao heroPos, Posicao chaserPos) {
        if (heroPos.getColuna() < chaserPos.getColuna()) {
            iDirectionH = true;
        } else if (heroPos.getColuna() > chaserPos.getColuna()) {
            iDirectionH = false;
        }
        if (heroPos.getLinha() < chaserPos.getLinha()) {
            iDirectionV = true;
        } else if (heroPos.getLinha() > chaserPos.getLinha()) {
            iDirectionV = false;
        }
    }
}