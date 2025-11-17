package Modelo;
import java.io.Serializable;

public class Bau extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    public Bau(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // DEVE ser transponível para a colisão ser processada
    }

    @Override public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {}

    @Override
    public String aoColidirComHeroi(Hero h) {
        if (h.temChave()) {
            h.usarChave(); 
            return "BAU_ABERTO"; // Chama o sorteio
        }
        
        // Não tem chave
        return "REJEITADO";
    }
}