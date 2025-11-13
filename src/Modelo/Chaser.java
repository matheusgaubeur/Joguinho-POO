/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.io.Serializable;
import java.util.ArrayList;
import Modelo.Comportamentos.Movimento.MovimentoChaser; // <<-- MUDANÇA

/**
 *
 * @author 2373891
 */
public class Chaser extends Personagem implements Serializable, Mortal {

    private boolean iDirectionV;
    private boolean iDirectionH;
    private int counter;

    public Chaser(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true;
        
        // <<-- MUDANÇA: Configura a Estratégia
        setComportamentoMovimento(new MovimentoChaser());
        // setComportamentoAtaque(new AtaqueNulo()); // Já é o padrão
    }

    // <<-- MUDANÇA: Lógica movida para o método atualizar()
    public void computeDirection(Posicao heroPos) {
        if (heroPos.getColuna() < this.getPosicao().getColuna()) {
            iDirectionH = true;
        } else if (heroPos.getColuna() > this.getPosicao().getColuna()) {
            iDirectionH = false;
        }
        if (heroPos.getLinha() < this.getPosicao().getLinha()) {
            iDirectionV = true;
        } else if (heroPos.getLinha() > this.getPosicao().getLinha()) {
            iDirectionV = false;
        }
    }

    // <<-- MUDANÇA: Renomeado de autoDesenho() para atualizar()
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // <<-- MUDANÇA: Lógica do Chaser (que estava no ControleDeJogo) movida para cá
        if (hero != null) {
            this.computeDirection(hero.getPosicao());
        }
        
        if (counter == 5) {
            if (iDirectionH) {
                this.moveLeft();
            } else {
                this.moveRight();
            }
            if (iDirectionV) {
                this.moveUp();
            } else {
                this.moveDown();
            }
            counter = 0;
        }
        counter++;
        // <<-- MUDANÇA: Removido o super.autoDesenho()
    }
    
    // <<-- MUDANÇA: Adicionado método desenhar()
    @Override
    public void desenhar() {
        super.desenhar();
    }
    
    @Override
    public String aoColidirComHeroi() {
        return "HERO_DIED";
    }
    

}
