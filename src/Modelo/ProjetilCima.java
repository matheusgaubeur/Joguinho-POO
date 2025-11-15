package Modelo;
import java.io.Serializable;

public class ProjetilCima extends Projetil implements Serializable {
    public ProjetilCima(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    @Override
    public boolean move() {
        return this.moveUp();
    }
}