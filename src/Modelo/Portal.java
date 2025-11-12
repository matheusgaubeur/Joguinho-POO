package Modelo;

import Auxiliar.Desenho;

/**
 * Um Portal pode ser uma saída de fase (destino 0)
 * ou um portal para outra fase (destino 1, 2, 3...).
 */
public class Portal extends Estatico {
    
    private int destinoFase = 0; // 0 = Padrão (próxima fase/lobby)
    
    public Portal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        // Portais não podem ser atravessados, eles são a "colisão" que muda a fase.
        this.bTransponivel = true; 
    }

    public void setDestinoFase(int destino) {
        this.destinoFase = destino;
    }
    
    public int getDestinoFase() {
        return this.destinoFase;
    }

    public void autoDesenho() {
        super.autoDesenho();
    }    
}