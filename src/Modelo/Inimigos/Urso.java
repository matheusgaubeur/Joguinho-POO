package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoVaiVemHorizontal;
import Modelo.Personagem;
import java.io.Serializable;

public class Urso extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Urso(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        setComportamentoMovimento(new MovimentoVaiVemHorizontal(10)); // Já é o padrão
        setComportamentoAtaque(new AtaqueNulo());
    }
}