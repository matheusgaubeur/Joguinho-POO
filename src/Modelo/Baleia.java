package Modelo;

import Modelo.Comportamentos.Ataque.ComportamentoAtaqueAtirador;
import Modelo.Comportamentos.Ataque.IFabricaProjetil;
import auxiliar.Posicao;
import java.io.Serializable;

// <<-- MUDANÇA: Implementa a fábrica para si mesma
class FabricaBolha implements IFabricaProjetil {
    public Personagem criarProjetil(Posicao p) {
        return new BolhaDeAr("fire.png", p.getLinha(), p.getColuna() + 1);
    }
}

/**
 * Inimigo da Fase 1. Herda de InimigoAtirador.
 * A única responsabilidade desta classe é dizer QUAL projétil ela cria.
 */
public class Baleia extends Personagem implements Serializable {
    
    public Baleia(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        // <<-- MUDANÇA: Configura as Estratégias
        // setComportamentoMovimento(new MovimentoParado()); // Já é o padrão
        setComportamentoAtaque(
            new ComportamentoAtaqueAtirador(new FabricaBolha())
        );
    }

}