package Modelo;

import java.io.Serializable;

// MUDANÇA: extends Projetil -> extends ProjetilDireita
public class Fogo extends ProjetilDireita implements Serializable {
            
    public Fogo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    // O MÉTODO move() FOI APAGADO, pois é herdado de ProjetilDireita
}