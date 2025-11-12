package Modelo;

import java.io.Serializable;

/**
 * Refatorado para herdar de InimigoPatrulha.
 * Não controla mais o timer, apenas a lógica de direção.
 */
public class BichinhoVaiVemHorizontal extends InimigoPatrulha implements Serializable {

    private boolean bRight;

    public BichinhoVaiVemHorizontal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        bRight = true;
        // this.bTransponivel = false; // Original era false, vamos manter.
        this.setbTransponivel(false); 
    }

    /**
     * Implementa o "buraco" da classe pai.
     * Esta é a única lógica que esta classe precisa saber.
     */
    @Override
    public void proximoMovimento() {
        if (bRight) {
            this.moveRight();
        } else {
            this.moveLeft();
        }
        bRight = !bRight; // Inverte a direção para o próximo loop
    }
}