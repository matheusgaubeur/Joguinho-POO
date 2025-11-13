package Modelo.Comportamentos.Movimento;

import Modelo.Hero;
import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Classe Abstrata (Template Method dentro de um Strategy)
 * que controla a LÓGICA DE VELOCIDADE (timer) para todas as patrulhas.
 */
public abstract class MovimentoPatrulha implements ComportamentoMovimento {

    private int contadorDeFrames;
    private int velocidade; // Quantos ticks espera para mover

    public MovimentoPatrulha(int velocidade) {
        this.velocidade = velocidade;
        this.contadorDeFrames = 0;
    }
    
    /**
     * O "Template Method"
     * Controla o timer e chama o método de movimento da filha.
     */
    @Override
    public final void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        if (contadorDeFrames == velocidade) {
            contadorDeFrames = 0;
            // Chama o método abstrato que a classe filha implementou
            this.proximoMovimento(p); 
        }
        contadorDeFrames++;
    }
    
    /**
     * O "buraco" a ser preenchido pelas estratégias concretas
     * (ZigueZague, Peixe, etc.)
     * @param p O Personagem a ser movido.
     */
    public abstract void proximoMovimento(Personagem p);
}