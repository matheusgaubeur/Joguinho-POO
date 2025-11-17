package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoDiagonal;
import Modelo.Personagem;
import java.io.Serializable;

public class BolaDeFogo extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public BolaDeFogo(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(true);
        setComportamentoMovimento(new MovimentoDiagonal(10)); // Já é o padrão
        setComportamentoAtaque(new AtaqueNulo());
    }

}