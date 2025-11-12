package Modelo;

import java.util.Random;
import java.io.Serializable;

/**
 * Refatorado para herdar de InimigoPatrulha.
 * Implementa um movimento aleatório (Norte, Sul, Leste, Oeste).
 */
public class ZigueZague extends InimigoPatrulha implements Serializable {
    
    private Random rand;
    
    public ZigueZague(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG,linha, coluna);
        this.setbTransponivel(true);
        rand = new Random();
    }

    /**
     * Implementa a lógica de movimento aleatório.
     */
    @Override
    public void proximoMovimento(){
        int iDirecao = rand.nextInt(4); // Gera 0, 1, 2 ou 3
        
        if(iDirecao == 0)
            this.moveUp();
        else if(iDirecao == 1)
            this.moveDown();
        else if(iDirecao == 2)
            this.moveRight();
        else if(iDirecao == 3)
            this.moveLeft();
        
        // (Não precisamos mais do super.autoDesenho() aqui, 
        // a classe pai cuida disso)
    }    
}