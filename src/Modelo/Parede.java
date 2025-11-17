package Modelo;

import java.util.ArrayList;
import java.io.Serializable;

// obstáculo. Usado para criar paredes.
public class Parede extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public Parede(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false; // Não pode passar por cima
    }

    @Override
    public void desenhar() {
        super.desenhar();
    }    
    
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }  
}