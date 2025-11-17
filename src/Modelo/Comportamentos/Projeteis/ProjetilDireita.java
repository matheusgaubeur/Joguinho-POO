package Modelo.Comportamentos.Projeteis;
import Modelo.Comportamentos.Projeteis.Projetil;
import java.io.Serializable;

public class ProjetilDireita extends Projetil implements Serializable {
    private static final long serialVersionUID = 1L;
    public ProjetilDireita(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
    @Override
    public boolean move() {
        return this.moveRight();
    }
}