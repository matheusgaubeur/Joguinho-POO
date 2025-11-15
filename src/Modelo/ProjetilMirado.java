package Modelo;

import auxiliar.Posicao;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Um projétil que é disparado em direção a uma posição-alvo.
 * Ele calcula sua trajetória (vetor dx, dy) uma vez e a segue.
 * Herda de Personagem (como ProjetilHeroi) pois sua lógica de 
 * 'atualizar' é customizada.
 */
public class ProjetilMirado extends Personagem implements Serializable, Mortal {
    
    private int dx; // Vetor de movimento no eixo X (-1, 0, ou 1)
    private int dy; // Vetor de movimento no eixo Y (-1, 0, ou 1)

    // Lógica de velocidade (para não se mover a cada tick)
    private int contadorMovimento = 0;
    private static final int VELOCIDADE = 5; // Move-se na mesma velocidade dos Patrulheiros

    /**
     * @param sNomeImagePNG Imagem do projétil
     * @param posAtirador Posição de onde o tiro sai
     * @param posHeroi Posição do alvo (para onde o tiro vai)
     */
    public ProjetilMirado(String sNomeImagePNG, Posicao posAtirador, Posicao posHeroi) {
        // Nasce na posição do atirador
        super(sNomeImagePNG, posAtirador.getLinha(), posAtirador.getColuna());
        this.bTransponivel = true;
        this.contadorMovimento = 0;

        // --- A MÁGICA: Cálculo do Vetor de Direção ---
        // Calcula a diferença
        int deltaLinha = posHeroi.getLinha() - posAtirador.getLinha();
        int deltaColuna = posHeroi.getColuna() - posAtirador.getColuna();

        // Converte a diferença para um vetor normalizado (-1, 0, ou 1)
        // Ex: Math.signum(10) = 1, Math.signum(-5) = -1, Math.signum(0) = 0
        this.dy = (int) Math.signum(deltaLinha);
        this.dx = (int) Math.signum(deltaColuna);
        
        // Caso de borda: Se o Herói está exatamente em cima do atirador,
        // o projétil ficaria parado (dx=0, dy=0).
        // Vamos forçá-lo a ir para a direita, como um tiro padrão.
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
        
        // NOTA: Ao contrário do ProjetilHeroi, não precisamos checar
        // colisão com inimigos. Ele só precisa se mover. A colisão
        // com o Herói é tratada pelo ControleDeJogo.
    }
    
    @Override
    public String aoColidirComHeroi(Hero hero) {
        // É um projétil inimigo, então mata o herói
        return "HERO_DIED";
    }
}