package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueEmV;
import Modelo.Comportamentos.Movimento.MovimentoCircular;
import Modelo.Personagem;
import java.io.Serializable;

public class Penguim extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Penguim(String sNomeImagePNG, int linha, int coluna){
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoCircular(10)); // Já é o padrão
        setComportamentoAtaque(new AtaqueEmV("GeloProjetil.png"));
    }
}