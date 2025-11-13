package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Estratégia de Ataque para um personagem que NUNCA ataca.
 */
public class AtaqueNulo implements ComportamentoAtaque {
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // Não faz nada. Não ataca.
    }
}