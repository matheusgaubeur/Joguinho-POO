package Modelo.Comportamentos.Projeteis;

import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Personagem;
import java.util.ArrayList;

public class ProjetilHeroi extends Personagem {
    private static final long serialVersionUID = 1L;
    
    private final int direcaoMovimento; // 0=Cima, 1=Baixo, 2=Esquerda, 3=Direita
    private boolean recemNascido = true; // Sua ideia de atraso de spawn

    public ProjetilHeroi(String sNomeImagePNG, int linha, int coluna, int direcao) {
        
        super(sNomeImagePNG, linha, coluna);
        this.direcaoMovimento = direcao;
        this.bTransponivel = true; // Pode voar sobre itens
    }

    // Este método é só nosso, não é mais uma obrigação da herança
    public boolean move() {
        return switch (this.direcaoMovimento) {
            case 0 -> this.moveUp();
            case 1 -> this.moveDown();
            case 2 -> this.moveLeft();
            case 3 -> this.moveRight();
            default -> false;
        };
    }

    // Sobrescreve 'aoColidirComHeroi' para não fazer nada
    @Override
    public String aoColidirComHeroi(Hero hero) {
        return "GAME_RUNNING"; // Não mata o herói
    }
    
    // Implementa o método abstrato de Personagem
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Lógica do "delay de spawn" (para o tiro aparecer antes de mover)
        if (recemNascido) {
            recemNascido = false;
            return; 
        }

        // 1. Tenta mover. Se bater na borda (false), marca a si mesmo para morrer.
        if (!this.move()) {
            this.morrer(); // Usa a flag bEstaVivo que criamos
            return; 
        }

        // 2. Checa colisão com Inimigos (Personagens 'Mortal')
        // (Iteramos de 1 para pular o Herói, que está em 0)
        for (int i = 1; i < faseAtual.size(); i++) {
            Personagem p = faseAtual.get(i);
            
            // Se p é Mortal E está vivo E está na mesma posição do projétil
            if (p instanceof Mortal && p.isVivo() && this.getPosicao().igual(p.getPosicao())) {
                
                this.morrer();  // Marca o projétil para ser removido
                p.morrer();     // Marca o inimigo para ser removido
                
                // TODO: Adicionar pontos na Tela pela morte
                
                return; // Sai do método (projétil já atingiu algo)
            }
        }
    }
}