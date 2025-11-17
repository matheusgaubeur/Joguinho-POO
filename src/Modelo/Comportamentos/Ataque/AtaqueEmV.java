package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Comportamentos.Projeteis.ProjetilDiagonalCimaDireita;
import Modelo.Comportamentos.Projeteis.ProjetilDiagonalBaixoDireita;
import Auxiliar.Desenho;
import java.io.Serializable;
import java.util.ArrayList;

// Ataque em "V" (diagonal para cima-direita e baixo-direita).
public class AtaqueEmV implements Serializable, ComportamentoAtaque {
    private static final long serialVersionUID = 1L;
    private int contador = 0;
    
    // Define o cooldown do ataque (ex: 30 ticks * 150ms = 4.5 segundos)
    private static final int INTERVALO = 30; 
    
    private final String nomeImagemProjetil;

    public AtaqueEmV(String sNomeImagePNG) {
        this.nomeImagemProjetil = sNomeImagePNG;
    }

    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Lógica do Timer (igual ao AtaqueAtirador)
        contador++;
        
        // 2. Se o timer não chegou, não faz nada
        if (contador < INTERVALO) {
            return;
        }

        // 3. O timer chegou.
        contador = 0; // Reseta o timer

        // 4. Define o ponto de "nascimento" dos projéteis.
        int linhaSpawn = p.getPosicao().getLinha();
        int colunaSpawn = p.getPosicao().getColuna() + 1; // +1 para não nascer em cima do atirador

        // 5. Cria os dois projéteis
        Personagem pCima = new ProjetilDiagonalCimaDireita(
            this.nomeImagemProjetil, linhaSpawn, colunaSpawn
        );
        
        Personagem pBaixo = new ProjetilDiagonalBaixoDireita(
            this.nomeImagemProjetil, linhaSpawn, colunaSpawn
        );

        // 6. Adiciona os projéteis ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(pCima);
        Desenho.acessoATelaDoJogo().addPersonagem(pBaixo);
    }
}