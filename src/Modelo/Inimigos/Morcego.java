package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoVaiVemVertical;
import Modelo.Personagem;
import java.io.Serializable;

public class Morcego extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Morcego(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        setComportamentoMovimento(new MovimentoVaiVemVertical(10));
        setComportamentoAtaque(new AtaqueNulo());
    }

}