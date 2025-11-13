package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface (Padrão Strategy) que define um comportamento de ataque.
 */
public interface ComportamentoAtaque extends Serializable {
    /**
     * Executa a lógica de ataque do personagem.
     * @param p O Personagem que possui este comportamento.
     * @param faseAtual A lista de todos os personagens (para adicionar projéteis).
     * @param hero O Herói (para mirar).
     */
    void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero);
}