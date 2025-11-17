package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Comportamentos.Projeteis.ProjetilBaixo;
import Auxiliar.Desenho;
import Modelo.Comportamentos.Projeteis.ProjetilCima;
import Modelo.Comportamentos.Projeteis.ProjetilDireita;
import Modelo.Comportamentos.Projeteis.ProjetilEsquerda;
import java.io.Serializable;
import java.util.ArrayList;

// Atira em todas as direçoces (Cima, Baixo, Esquerda, Direita).
public class AtaqueEmCruz implements Serializable, ComportamentoAtaque {
    private static final long serialVersionUID = 1L;
    private int contador = 0;
    
    // Define o cooldown do ataque
    private static final int INTERVALO = 30; 
    
    private final String nomeImagemProjetil;

    public AtaqueEmCruz(String sNomeImagePNG) {
        this.nomeImagemProjetil = sNomeImagePNG;
    }

    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Lógica do Timer
        contador++;
        
        // 2. Se o timer não chegou, não faz nada
        if (contador < INTERVALO) {
            return;
        }

        // 3. O timer chegou
        contador = 0; // Reseta o timer

        // 4. Define o ponto de nascimento dos projéteis.
        int linha = p.getPosicao().getLinha();
        int coluna = p.getPosicao().getColuna();

        // 5. Cria os quatro projéteis
        Personagem pCima = new ProjetilCima(
            this.nomeImagemProjetil, linha - 1, coluna
        );
        
        Personagem pBaixo = new ProjetilBaixo(
            this.nomeImagemProjetil, linha + 1, coluna
        );

        Personagem pEsquerda = new ProjetilEsquerda(
            this.nomeImagemProjetil, linha, coluna - 1
        );

        Personagem pDireita = new ProjetilDireita(
            this.nomeImagemProjetil, linha, coluna + 1
        );

        // 6. Adiciona os projéteis ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(pCima);
        Desenho.acessoATelaDoJogo().addPersonagem(pBaixo);
        Desenho.acessoATelaDoJogo().addPersonagem(pEsquerda);
        Desenho.acessoATelaDoJogo().addPersonagem(pDireita);
    }
}