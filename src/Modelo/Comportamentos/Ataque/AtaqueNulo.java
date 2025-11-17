package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

public class AtaqueNulo implements Serializable, ComportamentoAtaque {
    private static final long serialVersionUID = 1L;
    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // Não faz nada. Não ataca.
    }
}