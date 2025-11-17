package Modelo.Comportamentos.Projeteis;

import Auxiliar.Posicao;
import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;


public class ProjetilMirado extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;
    
    private int dx; // Vetor de movimento no eixo X (-1, 0, ou 1)
    private int dy; // Vetor de movimento no eixo Y (-1, 0, ou 1)

    // Lógica de velocidade (para não se mover a cada tick)
    private int contadorMovimento = 0;
    private static final int VELOCIDADE = 1;

    public ProjetilMirado(String sNomeImagePNG, Posicao posAtirador, Posicao posHeroi) {
        // Nasce na posição do atirador
        super(sNomeImagePNG, posAtirador.getLinha(), posAtirador.getColuna());
        this.bTransponivel = true;
        this.contadorMovimento = 0;

        // Calcula a diferença
        int deltaLinha = posHeroi.getLinha() - posAtirador.getLinha();
        int deltaColuna = posHeroi.getColuna() - posAtirador.getColuna();

        // Converte a diferença para um vetor normalizado (-1, 0, ou 1)
        this.dy = (int) Math.signum(deltaLinha);
        this.dx = (int) Math.signum(deltaColuna);
        
        // Caso de borda: Se o Herói está exatamente em cima do atirador,
               if (this.dx == 0 && this.dy == 0) {
            this.dx = 1; // Padrão: move para a direita
        }
    }

    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Controla a velocidade do projétil
        contadorMovimento++;
        if (contadorMovimento < VELOCIDADE) {
            return; // Espera o timer
        }
        contadorMovimento = 0; // Reseta o timer

        // 2. Tenta se mover no vetor calculado
        boolean moveuH = true; // Moveu na horizontal?
        boolean moveuV = true; // Moveu na vertical?

        if (dx > 0) {
            moveuH = this.moveRight();
        } else if (dx < 0) {
            moveuH = this.moveLeft();
        }

        if (dy > 0) {
            moveuV = this.moveDown();
        } else if (dy < 0) {
            moveuV = this.moveUp();
        }

        // 3. Se bater em uma parede (moveuH ou moveuV for falso), 
        // o projétil se marca para morrer.
        if (!moveuH || !moveuV) {
            this.morrer(); // Usa a flag bEstaVivo
        }
        
    }
    
    @Override
    public String aoColidirComHeroi(Hero hero) {
        // É um projétil inimigo, então mata o herói
        return "HERO_DIED";
    }

}
