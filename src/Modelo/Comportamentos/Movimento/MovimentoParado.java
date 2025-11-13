package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Estratégia de Movimento para um personagem que NUNCA se move.
 * (Substitui a necessidade da classe Estatico)
 */
public class MovimentoParado implements ComportamentoMovimento {
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // Não faz nada. Está parado.
    }
}