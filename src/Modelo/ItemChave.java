package Modelo;

import java.util.ArrayList; // <<-- MUDANÇA: Import necessário
import Modelo.Hero;       // <<-- MUDANÇA: Import necessário

/**
 * Representa um dos 3 itens colecionáveis da fase.
 * É estático e implementa Coletavel.
 */
public class ItemChave extends Personagem implements Coletavel {
    
    public ItemChave(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode ser coletado
    }

    // <<-- MUDANÇA: Renomeado de autoDesenho() para desenhar()
    @Override
    public void desenhar() {
        super.desenhar();
    }    
    
    // <<-- MUDANÇA: Implementação obrigatória do método abstrato
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }
    
    // <<-- MUDANÇA: Adicionada anotação @Override
    @Override
    public String aoColidirComHeroi() {
        return "ITEM_COLETADO";
    }
}