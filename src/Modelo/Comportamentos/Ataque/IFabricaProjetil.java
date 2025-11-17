package Modelo.Comportamentos.Ataque;

import Modelo.Personagem;
import Auxiliar.Posicao;
import java.io.Serializable;

public interface IFabricaProjetil extends Serializable {
    Personagem criarProjetil(Posicao p);
}