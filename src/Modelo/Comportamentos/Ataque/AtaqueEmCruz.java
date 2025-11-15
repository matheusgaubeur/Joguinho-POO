package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Fogo;         // Projétil que move para a DIREITA
import Modelo.BolaDeLama;   // Projétil que move para a ESQUERDA
import Modelo.BolhaDeAr;    // Projétil que move para CIMA
import Modelo.ProjetilBaixo; // Projétil que move para BAIXO
import Auxiliar.Desenho;
import Modelo.ProjetilCima;
import Modelo.ProjetilDireita;
import Modelo.ProjetilEsquerda;
import java.util.ArrayList;

/**
 * Estratégia de Ataque (Padrão Strategy) que dispara quatro projéteis
 * nas direções cardeais (Cima, Baixo, Esquerda, Direita).
 */
public class AtaqueEmCruz implements ComportamentoAtaque {

    private int contador = 0;
    
    // Define o cooldown do ataque (ex: 30 ticks * 150ms = 4.5 segundos)
    private static final int INTERVALO = 30; 
    
    private String nomeImagemProjetil;

    /**
     * Construtor.
     * @param sNomeImagePNG A imagem que os projéteis usarão (ex: "fire.png")
     */
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

        // 3. O timer chegou! Hora de atacar.
        contador = 0; // Reseta o timer

        // 4. Define o ponto de "nascimento" dos projéteis.
        // Eles vão nascer ao redor do atirador.
        int linha = p.getPosicao().getLinha();
        int coluna = p.getPosicao().getColuna();

        // 5. Cria os quatro projéteis
        // MUDANÇA NOS NOMES DAS CLASSES
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