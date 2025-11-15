package Modelo;
import java.io.Serializable;

public class ProjetilEsquerda extends Projetil implements Serializable {
    public ProjetilEsquerda(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    @Override
    public boolean move() {
        return this.moveLeft();
    }
}