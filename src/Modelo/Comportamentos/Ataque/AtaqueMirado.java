package Modelo.Comportamentos.Ataque;

import Modelo.Hero;
import Modelo.Personagem;
import Modelo.Comportamentos.Projeteis.ProjetilMirado;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.io.Serializable;
import java.util.ArrayList;

//Estratégia de Ataque mirado na direção do Herói.
public class AtaqueMirado implements Serializable, ComportamentoAtaque {
    private static final long serialVersionUID = 1L;
    private int contador = 0;
    
    // Cooldown do ataque
    private static final int INTERVALO = 30;    
    private final String nomeImagemProjetil;

    public AtaqueMirado(String sNomeImagePNG) {
        this.nomeImagemProjetil = sNomeImagePNG;
    }

    @Override
    public void executar(Personagem p, ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Lógica do Timer
        contador++;
        if (contador < INTERVALO) {
            return;
        }
        contador = 0; // Reseta o timer

        // 2. Se o herói não existir (por algum motivo), não faz nada.
        if (hero == null) {
            return; 
        }

        // 3. Pega as posições
        Posicao posAtirador = p.getPosicao();
        Posicao posHeroi = hero.getPosicao();

        // 4. Cria o Projétil Mirado, passando as duas posições
        Personagem proj = new ProjetilMirado(
            this.nomeImagemProjetil, 
            posAtirador, 
            posHeroi
        );

        // 5. Adiciona o projétil ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(proj);
    }
}