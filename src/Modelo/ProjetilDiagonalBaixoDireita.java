package Modelo;

import java.io.Serializable;

/**
 * Projétil que se move na diagonal, para BAIXO e DIREITA.
 * Herda a lógica de ser 'Mortal' e de se auto-remover 
 * da classe abstrata Projetil.
 */
public class ProjetilDiagonalBaixoDireita extends Projetil implements Serializable {
            
    public ProjetilDiagonalBaixoDireita(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }

    /**
     * Implementa o "buraco" da classe pai.
     * Define a lógica de movimento específica deste projétil.
     */
    @Override
    public boolean move() {
        // Tenta mover para baixo. Se falhar (bateu na borda), retorna false.
        if (!super.moveDown())
            return false;
        
        // Tenta mover para a direita. Se falhar (bateu na borda), retorna false.
        if (!super.moveRight())
            return false;
            
        return true; // Moveu com sucesso
    }
}