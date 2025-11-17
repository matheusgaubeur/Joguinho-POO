package Modelo;

import java.io.Serializable;

public class Chave extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Chave(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode passar por cima para coletar
    }

    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }

    @Override
    public String aoColidirComHeroi(Hero h) {
        // Avisa o ControleDeJogo que uma chave foi coletada
        return "CHAVE_COLETADA"; 
    }
}