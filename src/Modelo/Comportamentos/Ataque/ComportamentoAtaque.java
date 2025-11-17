package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

public interface ComportamentoAtaque extends Serializable {
    void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero);
}