package Modelo.Comportamentos.Projeteis;

import java.io.Serializable;

public class ProjetilEsquerda extends Projetil implements Serializable {
    private static final long serialVersionUID = 1L;
    public ProjetilEsquerda(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    @Override
    public boolean move() {
        return this.moveLeft();
    }
}