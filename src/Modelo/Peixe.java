package Modelo;

import Modelo.Comportamentos.Movimento.MovimentoQuadrado; // <<-- MUDANÇA
import java.io.Serializable;

/**
 * Refatorado para o Padrão Strategy.
 * A lógica de IA está em MovimentoPeixeQuadrado.java.
 */
// <<-- MUDANÇA: 'extends InimigoPatrulha' se torna 'extends Personagem implements Mortal'
public class Peixe extends Personagem implements Serializable, Mortal {

    // <<-- MUDANÇA: Atributos de lógica de movimento removidos.
    
    // (A velocidade padrão que InimigoPatrulha usava era 5)
    private static final int VELOCIDADE_PADRAO = 5;

    public Peixe(String sNomeImagePNG, int linha, int coluna, int tamanhoDoLado) {
        super(sNomeImagePNG, linha, coluna);
        
        // <<-- MUDANÇA: A MÁGICA
        setComportamentoMovimento(
            new MovimentoQuadrado(VELOCIDADE_PADRAO, tamanhoDoLado)
        );
    }

    // <<-- MUDANÇA: O método 'proximoMovimento()' foi MOVIDO para a Estratégia.
    
    // (O 'aoColidirComHeroi()' da Onda 2 permanece)
    @Override
    public String aoColidirComHeroi() {
        return "HERO_DIED";
    }
}