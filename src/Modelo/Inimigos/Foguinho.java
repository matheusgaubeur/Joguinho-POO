package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmCruz;
import Modelo.Comportamentos.Movimento.MovimentoZigueZague;
import Modelo.Personagem;
import java.io.Serializable;

public class Foguinho extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Foguinho(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoZigueZague(10));
        setComportamentoAtaque(new AtaqueEmCruz("FogoProjetil.png"));
    }

}