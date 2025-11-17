package Modelo;

import java.io.Serializable;

public class PortaFechada extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;

    public PortaFechada(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true;
    }

    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
    }

    @Override
    public String aoColidirComHeroi(Hero h) { // <<-- Assinatura corrigida
        if (h.temChave()) {
            h.usarChave();
            return "CADEADO_ABERTO";
        }

        return "REJEITADO"; 
    }
}