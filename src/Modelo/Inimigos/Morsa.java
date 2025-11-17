package Modelo.Inimigos;

import Modelo.Comportamentos.Ataque.ComportamentoAtaqueAtirador;
import Modelo.Comportamentos.Ataque.IFabricaProjetil;
import Auxiliar.Posicao;
import Modelo.Comportamentos.Ataque.BolhaDeAr;
import Modelo.Personagem;
import java.io.Serializable;

class FabricaBolha implements IFabricaProjetil {
    @Override
    public Personagem criarProjetil(Posicao p) {
        return new BolhaDeAr("GeloProjetil2.png", p.getLinha(), p.getColuna());
    }
}

public class Morsa extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Morsa(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        setComportamentoAtaque(
            new ComportamentoAtaqueAtirador(new FabricaBolha())
        );
    }

}