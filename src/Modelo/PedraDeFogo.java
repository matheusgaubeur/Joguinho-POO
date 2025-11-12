package Modelo;

import java.io.Serializable;

/**
 * Projétil do Vulcão (Fase 2).
 * Herda de Projetil e implementa o movimento na diagonal.
 */
public class PedraDeFogo extends Projetil implements Serializable {
            
    public PedraDeFogo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        // Exemplo de movimento: diagonal para baixo e direita
        if(!super.moveDown())
            return false;
        if(!super.moveRight())
            return false; // Se bater na borda (baixo ou direita), é removido
            
        return true; 
    }
}