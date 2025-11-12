package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * CLASSE ABSTRATA para todos os projéteis (Bolhas, Fogo, Lama, etc.)
 * Define o comportamento comum: é Mortal e se auto-remove.
 * As classes filhas só precisam implementar como se movem.
 */
public abstract class Projetil extends Personagem implements Serializable, Mortal {
    
    public Projetil(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Projéteis podem passar por cima de itens
    }

    /**
     * Método abstrato que força as classes filhas a definirem seu movimento.
     * @return true se o movimento foi válido, false se bateu na borda.
     */
    public abstract boolean move();
    
    @Override
    public void autoDesenho() {
        super.autoDesenho();
        
        // Se o movimento falhar (bater na borda), o projétil é removido.
        if(!this.move())
            Desenho.acessoATelaDoJogo().removePersonagem(this);
    }
}