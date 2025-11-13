package Modelo;

import Auxiliar.Desenho;
import java.util.ArrayList; // <<-- MUDANÇA: Import necessário
import Modelo.Hero;       // <<-- MUDANÇA: Import necessário

/**
 * Um Portal pode ser uma saída de fase (destino 0)
 * ou um portal para outra fase (destino 1, 2, 3...).
 */
public class Portal extends Personagem {
    
    private int destinoFase = 0; // 0 = Padrão (próxima fase/lobby)
    
    public Portal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        // Portais não podem ser atravessados, eles são a "colisão" que muda a fase.
        this.bTransponivel = true; 
    }

    public void setDestinoFase(int destino) {
        this.destinoFase = destino;
    }
    
    public int getDestinoFase() {
        return this.destinoFase;
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
        if (destinoFase == 0) {
            return "FASE_CONCLUIDA";
        } else {
            return "PORTAL_FASE_" + destinoFase; 
        }
    }
}