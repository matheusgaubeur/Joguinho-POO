package Modelo;

import java.io.Serializable;

public class Fogo extends Projetil implements Serializable {
            
    public Fogo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public boolean move() {
        return this.moveRight(); // Lógica de movimento do Fogo
    }
}