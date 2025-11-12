package Modelo;

import java.io.Serializable;

/**
 * CLASSE ABSTRATA (Padrão Template Method)
 * Controla a lógica de "velocidade" (timer) para todos os inimigos
 * que se movem em um padrão fixo (não-Chaser).
 * * Força as classes filhas a implementar apenas a lógica de
 * "para onde ir agora?".
 */
public abstract class InimigoPatrulha extends Animado implements Serializable, Mortal {

    private int contadorDeFrames;
    private int velocidade; // Quantos ticks espera para mover (5 = padrão)

    public InimigoPatrulha(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Patrulhas podem passar sobre itens
        this.contadorDeFrames = 0;
        this.velocidade = 5; // Valor padrão
    }
    
    public void setVelocidade(int novaVelocidade) {
        this.velocidade = novaVelocidade;
    }

    /**
     * O "Template Method".
     * Esta é a lógica final que controla o timer (velocidade).
     */
    @Override
    public final void autoDesenho() {
        if (contadorDeFrames == velocidade) {
            contadorDeFrames = 0;
            // Chama o método abstrato que a classe filha implementou
            this.proximoMovimento(); 
        }
        
        contadorDeFrames++;
        super.autoDesenho(); // Desenha na posição (nova ou antiga)
    }

    /**
     * Método Abstrato (o "buraco" a ser preenchido).
     * As classes filhas (Peixe, Capivara) devem implementar esta lógica.
     */
    public abstract void proximoMovimento();
}