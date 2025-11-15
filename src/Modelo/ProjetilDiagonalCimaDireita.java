package Modelo;

import java.io.Serializable;

/**
 * Projétil que se move na diagonal, para CIMA e DIREITA.
 * Herda a lógica de ser 'Mortal' e de se auto-remover 
 * da classe abstrata Projetil.
 */
public class ProjetilDiagonalCimaDireita extends Projetil implements Serializable {
            
    public ProjetilDiagonalCimaDireita(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    /**
     * Implementa o "buraco" da classe pai.
     * Define a lógica de movimento específica deste projétil.
     */
    @Override
    public boolean move() {
        // Tenta mover para cima. Se falhar (bateu na borda), retorna false.
        if (!super.moveUp())
            return false;
        
        // Tenta mover para a direita. Se falhar (bateu na borda), retorna false.
        if (!super.moveRight())
            return false;
            
        return true; // Moveu com sucesso
    }
}