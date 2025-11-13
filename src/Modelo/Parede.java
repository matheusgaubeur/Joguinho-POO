package Modelo;

import java.util.ArrayList; // <<-- MUDANÇA: Import necessário
import Modelo.Hero;       // <<-- MUDANÇA: Import necessário

/**
 * Um obstáculo simples e intransponível.
 * Usado para construir as paredes do Lobby e as barreiras de gelo da Fase 1.
 */
public class Parede extends Personagem {
    
    public Parede(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false; // NÃO PODE PASSAR POR CIMA
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
}