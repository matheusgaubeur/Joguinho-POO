package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

//Interface (Padrão Strategy) que define um comportamento de movimento.

public interface ComportamentoMovimento extends Serializable {
    
    void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero);
}