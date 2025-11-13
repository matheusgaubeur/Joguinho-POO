package Modelo;

import Modelo.Comportamentos.Movimento.MovimentoVaiVemHorizontal; // <<-- MUDANÇA
import java.io.Serializable;

/**
 * Representa o inimigo "Capivara" (Fase 3).
 * ESTA CLASSE NÃO TEM MAIS LÓGICA DE IA.
 * Ela apenas estende Personagem e, em seu construtor,
 * DEFINE sua estratégia de movimento.
 */
// <<-- MUDANÇA: 'extends InimigoPatrulha' se torna 'extends Personagem'
public class BichinhoVaiVemHorizontal extends Personagem implements Serializable, Mortal {

    // <<-- MUDANÇA: Atributos 'bRight' e 'contador' removidos.
    
    // (A velocidade padrão que InimigoPatrulha usava era 5)
    private static final int VELOCIDADE_PADRAO = 5;

    public BichinhoVaiVemHorizontal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        // <<-- MUDANÇA: 'bTransponivel' era 'false' no código antigo
        this.setbTransponivel(false); 
        
        // <<-- MUDANÇA: A MÁGICA
        // Dizemos a este Personagem qual "cérebro" de movimento ele deve usar.
        setComportamentoMovimento(
            new MovimentoVaiVemHorizontal(VELOCIDADE_PADRAO)
        );
        // setComportamentoAtaque(new AtaqueNulo()); // Já é o padrão
    }

    // <<-- MUDANÇA: O método 'proximoMovimento()' foi MOVIDO para a Estratégia.
    
    // (O 'aoColidirComHeroi()' da Onda 2 permanece)
    @Override
    public String aoColidirComHeroi() {
        return "HERO_DIED";
    }
}