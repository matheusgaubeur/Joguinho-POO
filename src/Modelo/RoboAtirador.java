package Modelo;

import java.io.Serializable;

public class RoboAtirador extends InimigoAtirador implements Serializable {
    
    public RoboAtirador(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public Personagem criarProjetil() {
        // (Usando "bomba.png" que já temos)
        // Cria a bomba uma célula abaixo dele
        return new Bomba("bomba.png", pPosicao.getLinha() + 1, pPosicao.getColuna());
    }
}