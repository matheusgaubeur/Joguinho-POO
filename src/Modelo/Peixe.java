package Modelo;

import java.io.Serializable;

/**
 * Novo personagem (Fase 1).
 * Herda de InimigoPatrulha e implementa um movimento em quadrado.
 */
public class Peixe extends InimigoPatrulha implements Serializable {

    private int tamanhoDoLado;
    private int passosRestantes;
    private int direcaoAtual; // 0: Cima, 1: Direita, 2: Baixo, 3: Esquerda

    public Peixe(String sNomeImagePNG, int linha, int coluna, int tamanhoDoLado) {
        super(sNomeImagePNG, linha, coluna);
        this.tamanhoDoLado = tamanhoDoLado;
        this.passosRestantes = tamanhoDoLado;
        this.direcaoAtual = 0; // Começa movendo para Cima
    }

    /**
     * Implementa o "buraco" da classe pai.
     * Esta é a lógica do movimento em quadrado.
     */
    @Override
    public void proximoMovimento() {
        boolean moveu = false;
        
        if (passosRestantes > 0) {
            switch (direcaoAtual) {
                case 0: moveu = this.moveUp(); break;
                case 1: moveu = this.moveRight(); break;
                case 2: moveu = this.moveDown(); break;
                case 3: moveu = this.moveLeft(); break;
            }
            
            if(moveu) {
                passosRestantes--;
            } else {
                passosRestantes = 0; // Bateu na parede, força a virada
            }

        } else {
            // Acabaram os passos, vira
            direcaoAtual = (direcaoAtual + 1) % 4;
            passosRestantes = tamanhoDoLado;
        }
    }
}