package Modelo;
import java.io.Serializable;

public class ProjetilDireita extends Projetil implements Serializable {
    public ProjetilDireita(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    @Override
    public boolean move() {
        return this.moveRight();
    }
}