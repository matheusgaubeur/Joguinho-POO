package Modelo;

import java.io.Serializable;

/**
 * Projétil da Baleia (Fase 1).
 * Herda de Projetil e implementa o movimento para CIMA.
 */
public class BolhaDeAr extends Projetil implements Serializable {
            
    public BolhaDeAr(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        return this.moveUp(); // Lógica de movimento da Bolha de Ar
    }
}