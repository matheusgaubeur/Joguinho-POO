package Modelo;

import java.io.Serializable;

/**
 * Inimigo da Fase 2. Herda de InimigoAtirador.
 * Dispara Pedras de Fogo.
 */
public class Vulcao extends InimigoAtirador implements Serializable {
    
    public Vulcao(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public Personagem criarProjetil() {
        // (Usando "fire.png" como placeholder, precisamos de "pedra_fogo.png")
        return new PedraDeFogo("fire.png", pPosicao.getLinha() + 1, pPosicao.getColuna() + 1);
    }
}