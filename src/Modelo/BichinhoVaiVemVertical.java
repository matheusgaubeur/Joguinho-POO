package Modelo;

import java.io.Serializable;

/**
 * Refatorado para herdar de InimigoPatrulha.
 * Não controla mais o timer, apenas a lógica de direção.
 */
public class BichinhoVaiVemVertical extends InimigoPatrulha implements Serializable {
    
    boolean bUp;
    
    public BichinhoVaiVemVertical(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        // this.bTransponivel = false; // Original era false, vamos manter.
        this.setbTransponivel(false);      
        bUp = true;        
    }

    /**
     * Implementa o "buraco" da classe pai.
     */
    @Override
    public void proximoMovimento(){
        if(bUp)
            this.moveUp();
        else
            this.moveDown();
        bUp = !bUp; // Inverte a direção
    }  
}