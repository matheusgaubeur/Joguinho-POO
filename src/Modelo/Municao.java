package Modelo;

import java.io.Serializable;

// Implementa a interface 'Coletavel'
public class Municao extends Personagem implements Serializable, Coletavel {
    private static final long serialVersionUID = 1L;

    public Municao(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode passar por cima para coletar
    }
    
    // Sobrescreve o método abstrato de Personagem (mesmo sendo estático)
    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        return "MUNICAO_COLETADA"; 
    }
}