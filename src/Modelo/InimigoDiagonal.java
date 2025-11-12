package Modelo;

import java.io.Serializable;

/**
 * Novo personagem (requisito do PDF) com movimento diagonal.
 * Herda de InimigoPatrulha.
 */
public class InimigoDiagonal extends InimigoPatrulha implements Serializable {

    private boolean bCima;
    private boolean bDireita;

    public InimigoDiagonal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bCima = true;
        this.bDireita = true;
        
        // Inimigos de patrulha não devem ser obstáculos
        this.setbTransponivel(true); 
    }

    /**
     * Implementa a lógica de movimento diagonal (bate-volta).
     */
    @Override
    public void proximoMovimento() {
        boolean moveuVertical = false;
        boolean moveuHorizontal = false;

        // Tenta mover Vertical
        if (bCima) {
            moveuVertical = this.moveUp();
        } else {
            moveuVertical = this.moveDown();
        }

        // Tenta mover Horizontal
        if (bDireita) {
            moveuHorizontal = this.moveRight();
        } else {
            moveuHorizontal = this.moveLeft();
        }

        // Lógica de "Bater": Se não conseguiu mover para um lado,
        // inverte a direção daquele eixo.
        if (!moveuVertical) {
            bCima = !bCima;
        }
        if (!moveuHorizontal) {
            bDireita = !bDireita;
        }
    }
}