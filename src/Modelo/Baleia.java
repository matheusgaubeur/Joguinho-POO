package Modelo;

import java.io.Serializable;

/**
 * Inimigo da Fase 1. Herda de InimigoAtirador.
 * A única responsabilidade desta classe é dizer QUAL projétil ela cria.
 */
public class Baleia extends InimigoAtirador implements Serializable {
    
    public Baleia(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    @Override
    public Personagem criarProjetil() {
        // A Baleia cria uma BolhaDeAr que sai de sua posição.
        // (Usando "fire.png" como placeholder, precisamos de uma "bolha.png")
        return new BolhaDeAr("fire.png", pPosicao.getLinha() - 1, pPosicao.getColuna());
    }
}