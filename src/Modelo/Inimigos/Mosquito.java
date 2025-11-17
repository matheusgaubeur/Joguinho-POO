package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoChaser;
import Modelo.Personagem;
import java.io.Serializable;

public class Mosquito extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Mosquito(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(true);
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo());
    }
}