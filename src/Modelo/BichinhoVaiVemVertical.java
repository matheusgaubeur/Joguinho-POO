package Modelo;

import Modelo.Comportamentos.Movimento.MovimentoVaiVemVertical; // <<-- MUDANÇA
import java.io.Serializable;

/**
 * Refatorado para o Padrão Strategy.
 * Esta classe é apenas a "casca" que define a aparência.
 * A lógica de IA está em MovimentoVaiVemVertical.java.
 */
// <<-- MUDANÇA: 'extends InimigoPatrulha' se torna 'extends Personagem implements Mortal'
public class BichinhoVaiVemVertical extends Personagem implements Serializable, Mortal {
    
    // <<-- MUDANÇA: Atributos 'bUp' e 'contadorDeFrames' removidos.
    
    // (A velocidade padrão que InimigoPatrulha usava era 5)
    private static final int VELOCIDADE_PADRAO = 5;
    
    public BichinhoVaiVemVertical(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        // <<-- MUDANÇA: 'bTransponivel' era 'false' no código antigo
        this.setbTransponivel(false);      
        
        // <<-- MUDANÇA: A MÁGICA
        // Dizemos a este Personagem qual "cérebro" de movimento ele deve usar.
        setComportamentoMovimento(
            new MovimentoVaiVemVertical(VELOCIDADE_PADRAO)
        );
        // setComportamentoAtaque(new AtaqueNulo()); // Já é o padrão
    }

    /**
     * Implementa o "buraco" da classe pai.
     */
    // <<-- MUDANÇA: O método 'proximoMovimento()' foi MOVIDO para a Estratégia.
    
    // (O 'aoColidirComHeroi()' da Onda 2 permanece)
    @Override
    public String aoColidirComHeroi() {
        return "HERO_DIED";
    }
}