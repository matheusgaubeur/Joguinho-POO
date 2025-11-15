package Modelo;

import java.io.Serializable;

/**
 * Projétil do Jacaré (Fase 3).
 * Herda de ProjetilEsquerda para obter seu movimento.
 */
// MUDANÇA: extends Projetil -> extends ProjetilEsquerda
public class BolaDeLama extends ProjetilEsquerda implements Serializable {
            
    public BolaDeLama(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    // O MÉTODO move() FOI APAGADO, pois é herdado de ProjetilEsquerda
}