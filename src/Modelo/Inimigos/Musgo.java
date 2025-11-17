package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmV;
import Modelo.Comportamentos.Movimento.MovimentoParado;
import Modelo.Personagem;
import java.io.Serializable;

public class Musgo extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Musgo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(true);
        setComportamentoMovimento(new MovimentoParado());
        setComportamentoAtaque(new AtaqueEmV("PantanoProjetil.png"));
    }
}