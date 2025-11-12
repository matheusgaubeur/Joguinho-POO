package Modelo;

import java.io.Serializable;

/**
 * Inimigo da Fase 3. Herda de InimigoAtirador.
 * Dispara Bolas de Lama.
 */
public class Jacare extends InimigoAtirador implements Serializable {
    
    public Jacare(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public Personagem criarProjetil() {
        // (Usando "bomba.png" como placeholder, precisamos de "bola_lama.png")
        return new BolaDeLama("bomba.png", pPosicao.getLinha(), pPosicao.getColuna() - 1);
    }
}