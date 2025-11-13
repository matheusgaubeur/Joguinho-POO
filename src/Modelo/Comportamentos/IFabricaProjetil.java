package Modelo.Comportamentos.Ataque;

import Modelo.Personagem;
import auxiliar.Posicao;
import java.io.Serializable;

/**
 * Interface (Padrão Factory) para desacoplar o Atirador do Projétil.
 */
public interface IFabricaProjetil extends Serializable {
    /**
     * @param p A posição do atirador.
     * @return O novo projétil criado.
     */
    Personagem criarProjetil(Posicao p);
}