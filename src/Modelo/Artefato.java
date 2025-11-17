package Modelo;

import java.util.ArrayList;
import java.io.Serializable;

//Representa um dos 3 itens coletaveis da fase necessarios para termina-la.
public class Artefato extends Personagem implements Serializable,Coletavel {
    private static final long serialVersionUID = 1L;
    
    public Artefato(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode ser coletado
    }

    @Override
    public void desenhar() {
        super.desenhar();
    }    
    
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }
    
    @Override
    public String aoColidirComHeroi(Hero hero) {
        return "ITEM_COLETADO";
    }
}