package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * Projétil do Robô (Fase 4).
 * Não se move, apenas fica 'Mortal' por um tempo e depois se remove.
 */
public class Bomba extends Personagem implements Serializable, Mortal {
    
    private int iTimer = 0;
            
    public Bomba(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();
        iTimer++;
        
        // A bomba dura 10 "ticks" (10 * 150ms = 1.5s)
        if(iTimer == 10) {
            Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
    }
}