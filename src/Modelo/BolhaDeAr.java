package Modelo;

import java.io.Serializable;

/**
 * Projétil da Baleia (Fase 1).
 * Herda de ProjetilCima para obter seu movimento.
 */
// MUDANÇA: extends Projetil -> extends ProjetilCima
public class BolhaDeAr extends ProjetilCima implements Serializable { 
            
    public BolhaDeAr(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    // O MÉTODO move() FOI APAGADO, pois é herdado de ProjetilCima
}