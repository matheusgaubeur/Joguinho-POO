package Modelo;

import Modelo.Comportamentos.Movimento.MovimentoZigueZague; // <<-- MUDANÇA
import java.util.Random;
import java.io.Serializable;

/**
 * Refatorado para o Padrão Strategy.
 * A lógica de IA está em MovimentoZigueZague.java.
 */
// <<-- MUDANÇA: 'extends InimigoPatrulha' se torna 'extends Personagem implements Mortal'
public class ZigueZague extends Personagem implements Serializable, Mortal {
    
    // <<-- MUDANÇA: Atributo 'rand' removido.
    
    // (A velocidade padrão que InimigoPatrulha usava era 5)
    private static final int VELOCIDADE_PADRAO = 5;
    
    public ZigueZague(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG,linha, coluna);
        this.setbTransponivel(true);
        
        // <<-- MUDANÇA: A MÁGICA
        setComportamentoMovimento(
            new MovimentoZigueZague(VELOCIDADE_PADRAO)
        );
    }

    // <<-- MUDANÇA: O método 'proximoMovimento()' foi MOVIDO para a Estratégia.

    // (O 'aoColidirComHeroi()' da Onda 2 permanece)
    @Override
    public String aoColidirComHeroi() {
        return "HERO_DIED";
    }
}