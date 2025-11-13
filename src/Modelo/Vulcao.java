package Modelo;

import Modelo.Comportamentos.Ataque.ComportamentoAtaqueAtirador;
import Modelo.Comportamentos.Ataque.IFabricaProjetil;
import auxiliar.Posicao;
import java.io.Serializable;

// <<-- MUDANÇA: Implementa a fábrica para si mesma
class FabricaFogo implements IFabricaProjetil {
    public Personagem criarProjetil(Posicao p) {
        return new Fogo("fire.png", p.getLinha(), p.getColuna() + 1);
    }
}
/**
 * Inimigo da Fase 2. Herda de InimigoAtirador.
 * Dispara Pedras de Fogo.
 */
public class Vulcao extends Personagem implements Serializable {
    
    public Vulcao(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.setbTransponivel(false);
        
        // <<-- MUDANÇA: Configura as Estratégias
        // setComportamentoMovimento(new MovimentoParado()); // Já é o padrão
        setComportamentoAtaque(
            new ComportamentoAtaqueAtirador(new FabricaBomba())
        );
    }

}