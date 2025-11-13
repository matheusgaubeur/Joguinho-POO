package Modelo.Comportamentos.Ataque;

import Auxiliar.Consts;
import Modelo.Personagem;
/**
 * Esta é a lógica que estava em 'InimigoAtirador.java'.
 * Ela controla o timer e usa uma Fábrica para criar o projétil,
 * sem saber qual projétil está criando.
 */
public class ComportamentoAtaqueAtirador implements ComportamentoAtaque {
    
    private int iContaIntervalos;
    private IFabricaProjetil fabricaProjetil;

    /**
     * @param fabricaProjetil Uma instância de fábrica (ex: new FabricaFogo())
     */
    public ComportamentoAtaqueAtirador(IFabricaProjetil fabricaProjetil) {
        this.fabricaProjetil = fabricaProjetil;
        this.iContaIntervalos = 0;
    }

    @Override
    public void executar(Modelo.Personagem p, java.util.ArrayList<Modelo.Personagem> faseAtual, Modelo.Hero hero) {
        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER) {
            this.iContaIntervalos = 0;
            
            // Usa a fábrica para criar o projétil correto
            Personagem projetil = this.fabricaProjetil.criarProjetil(p.getPosicao());
            
            // Adiciona o projétil ao jogo
            // (Ainda usando o acesso global, mas agora está isolado aqui)
            Auxiliar.Desenho.acessoATelaDoJogo().addPersonagem(projetil);
        }
    }
}