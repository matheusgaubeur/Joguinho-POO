package Modelo;

import java.util.ArrayList;
import java.io.Serializable;

//Uma porta pode ser uma saída de fase ou uma porta do lobby para outra fase
public class Portal extends Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int destinoFase = 0; // 0 = Padrão (próxima fase/lobby)
    
    public Portal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        // Portas não podem ser atravessadas. A colisão muda a fase.
        this.bTransponivel = true; 
    }

    public void setDestinoFase(int destino) {
        this.destinoFase = destino;
    }
    
    public int getDestinoFase() {
        return this.destinoFase;
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
        if (destinoFase == 0) {
            return "FASE_CONCLUIDA";
        } else {
            return "PORTAL_FASE_" + destinoFase; 
        }
    }
}