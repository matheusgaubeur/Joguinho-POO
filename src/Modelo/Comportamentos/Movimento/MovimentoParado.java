package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

public class MovimentoParado implements Serializable, ComportamentoMovimento {
    private static final long serialVersionUID = 1L;
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // Não faz nada. Está parado.
    }
}