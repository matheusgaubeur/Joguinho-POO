package Modelo.Comportamentos.Projeteis;

import Auxiliar.Desenho;
import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;


public abstract class Projetil extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    public Projetil(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Projéteis podem passar por cima de itens
    }

    
    public abstract boolean move();
    
   
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {

        if(!this.move())
            Desenho.acessoATelaDoJogo().removePersonagem(this);
    }
    
    @Override
    public void desenhar() {
        super.desenhar();
    }
    
    @Override
    public String aoColidirComHeroi(Hero hero) {
        return "HERO_DIED";
    }

}
