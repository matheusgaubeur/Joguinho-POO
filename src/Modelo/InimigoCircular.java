package Modelo;

import Modelo.Comportamentos.Movimento.MovimentoCircular; // <<-- MUDANÇA
import java.io.Serializable;

/**
 * Novo personagem (requisito do PDF) com movimento circular (horário).
 * Refatorado para o Padrão Strategy.
 */
// <<-- MUDANÇA: 'extends InimigoPatrulha' se torna 'extends Personagem implements Mortal'
public class InimigoCircular extends Personagem implements Serializable, Mortal {

    // <<-- MUDANÇA: Atributo 'direcaoAtual' removido.
    
    // (A velocidade padrão que InimigoPatrulha usava era 5)
    private static final int VELOCIDADE_PADRAO = 5;

    public InimigoCircular(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        // Inimigos de patrulha não devem ser obstáculos
        this.setbTransponivel(false); 
        
        // <<-- MUDANÇA: A MÁGICA
        // Dizemos a este Personagem qual "cérebro" de movimento ele deve usar.
        setComportamentoMovimento(
            new MovimentoCircular(VELOCIDADE_PADRAO)
        );
        // setComportamentoAtaque(new AtaqueNulo()); // Já é o padrão
    }

    /**
     * Implementa a lógica de movimento circular.
     * Tenta mover na direção atual. Se bater, tenta a próxima direção.
     */
    // <<-- MUDANÇA: O método 'proximoMovimento()' foi MOVIDO para a Estratégia.

    // (O 'aoColidirComHeroi()' da Onda 2 permanece)
    @Override
    public String aoColidirComHeroi(Hero hero) {
        return "HERO_DIED";
    }
}