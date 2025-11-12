package Modelo;


import java.io.Serializable;

/**
 * Inimigo clássico. Refatorado para herdar de InimigoAtirador.
 * A única responsabilidade é dizer QUE projétil ela cria.
 */
public class Caveira extends InimigoAtirador implements Serializable {
    
    public Caveira(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public Personagem criarProjetil() {
        // A Caveira cria Fogo que sai de sua posição.
        return new Fogo("fire.png", pPosicao.getLinha(), pPosicao.getColuna() + 1);
    }
}