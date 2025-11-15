package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.ProjetilDiagonalCimaDireita;  // <<-- IMPORTAMOS O PROJÉTIL NOVO
import Modelo.ProjetilDiagonalBaixoDireita; // <<-- IMPORTAMOS O PROJÉTIL NOVO
import Auxiliar.Desenho;
import java.util.ArrayList;

/**
 * Estratégia de Ataque (Padrão Strategy) que dispara dois projéteis
 * em um padrão "V" (diagonal para cima-direita e baixo-direita).
 */
public class AtaqueEmV implements ComportamentoAtaque {

    private int contador = 0;
    
    // Define o cooldown do ataque (ex: 30 ticks * 150ms = 4.5 segundos)
    // Ajuste esse valor para deixar o inimigo mais ou menos rápido
    private static final int INTERVALO = 30; 
    
    private String nomeImagemProjetil;

    /**
     * Construtor.
     * @param sNomeImagePNG A imagem que os projéteis usarão (ex: "fire.png")
     */
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

        // 3. O timer chegou! Hora de atacar.
        contador = 0; // Reseta o timer

        // 4. Define o ponto de "nascimento" dos projéteis.
        // Vamos fazer nascer um passo à frente do atirador (assumindo que ele atira para a direita)
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