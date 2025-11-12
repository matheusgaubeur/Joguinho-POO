package Modelo;

import java.io.Serializable;

/**
 * Novo personagem (requisito do PDF) com movimento circular (horário).
 * Herda de InimigoPatrulha.
 */
public class InimigoCircular extends InimigoPatrulha implements Serializable {

    // 0: Direita, 1: Baixo, 2: Esquerda, 3: Cima
    private int direcaoAtual; 

    public InimigoCircular(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.direcaoAtual = 0; // Começa movendo para Direita
        this.setbTransponivel(true);
    }

    /**
     * Implementa a lógica de movimento circular.
     * Tenta mover na direção atual. Se bater, tenta a próxima direção.
     */
    @Override
    public void proximoMovimento() {
        boolean moveu = false;
        
        // Tenta mover na direção atual
        switch (direcaoAtual) {
            case 0: moveu = this.moveRight(); break;
            case 1: moveu = this.moveDown(); break;
            case 2: moveu = this.moveLeft(); break;
            case 3: moveu = this.moveUp(); break;
        }

        // Se não moveu (bateu na parede), vira 90 graus (próxima direção)
        if (!moveu) {
            direcaoAtual = (direcaoAtual + 1) % 4;
        }
    }
}