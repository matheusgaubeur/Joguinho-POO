package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmCruz;
import Modelo.Comportamentos.Movimento.MovimentoQuadrado;
import Modelo.Personagem;
import java.io.Serializable;

public class Capivara extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Capivara(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoQuadrado(3, 5));
        setComportamentoAtaque(new AtaqueEmCruz("PantanoProjetil.png"));
    }
}