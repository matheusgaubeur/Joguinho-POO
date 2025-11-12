package Modelo;

import java.io.Serializable;

/**
 * Projétil do Jacaré (Fase 3).
 * Herda de Projetil e implementa o movimento para ESQUERDA.
 */
public class BolaDeLama extends Projetil implements Serializable {
            
    public BolaDeLama(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        return this.moveLeft(); // Lógica de movimento da Bola de Lama
    }
}