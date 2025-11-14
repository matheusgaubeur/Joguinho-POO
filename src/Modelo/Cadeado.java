package Modelo;

import Auxiliar.Desenho; // Import necessário

public class Cadeado extends Personagem {

    public Cadeado(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // <<-- CORREÇÃO: Deve ser true
    }

    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático
    }

    @Override
    public String aoColidirComHeroi(Hero h) { // <<-- Assinatura corrigida
        if (h.temChave()) {
            h.usarChave();
            return "CADEADO_ABERTO";
        }

        // <<-- LÓGICA NOVA: Empurra o Herói
        return "REJEITADO"; 
    }
}