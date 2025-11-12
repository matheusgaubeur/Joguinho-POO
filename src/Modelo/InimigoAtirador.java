package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.io.Serializable;

/**
 * CLASSE ABSTRATA para inimigos estáticos que disparam projéteis
 * (Caveira, Baleia, Jacaré, Robô).
 * Contém a lógica do timer (iContaIntervalos).
 */
public abstract class InimigoAtirador extends Estatico implements Serializable {
    
    private int iContaIntervalos;

    public InimigoAtirador(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false; // Inimigos são obstáculos
        this.iContaIntervalos = 0;
    }

    /**
     * Método de Fábrica (Factory Method) Abstrato.
     * Força as classes filhas a decidirem QUAL projétil criar.
     * @return Um novo Personagem (Projetil)
     */
    public abstract Personagem criarProjetil();

    @Override
    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if(this.iContaIntervalos == Consts.TIMER) {
            this.iContaIntervalos = 0;
            
            // Lógica genérica: cria o projétil que a classe filha definir
            Personagem p = this.criarProjetil();
            
            // Adiciona o projétil ao jogo
            Desenho.acessoATelaDoJogo().addPersonagem(p);
        }
    }
}