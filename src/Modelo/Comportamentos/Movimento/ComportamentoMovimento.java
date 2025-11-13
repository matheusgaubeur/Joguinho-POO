package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface (Padrão Strategy) que define um comportamento de movimento.
 */
public interface ComportamentoMovimento extends Serializable {
    /**
     * Executa a lógica de movimento do personagem.
     * @param p O Personagem que possui este comportamento.
     * @param faseAtual A lista de todos os personagens (para colisões ou IA).
     * @param hero O Herói (para IA de perseguição).
     */
    void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero);
}