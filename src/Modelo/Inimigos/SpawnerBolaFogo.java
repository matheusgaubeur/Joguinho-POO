package Modelo.Inimigos;

import Auxiliar.Desenho;
import Modelo.Hero;
import Modelo.Personagem;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Este é um Personagem invisível que atua como um "Spawner".
 * Sua única função é adicionar BolasDeFogo ao jogo em intervalos
 * de tempo.
 */
public class SpawnerBolaFogo extends Personagem implements Serializable {
    
    private static final long serialVersionUID = 1L;
    private int timer;
    private final int delay; // Tempo em ticks entre spawns

    private static final int TEMPO_DE_SPAWN = 100; 

    public SpawnerBolaFogo(int linha, int coluna) {
        // Usa uma imagem placeholder (pode ser qualquer uma), pois será invisível
        super("blackTile.png", linha, coluna); 
        
        this.bTransponivel = true; // O Herói pode passar por cima
        this.delay = TEMPO_DE_SPAWN;
        this.timer = delay; // Começa o timer
    }

    @Override
    public void atualizar(ArrayList<Personagem> e, Hero h) {
        // 1. Contar o timer
        timer--;

        // 2. Se o timer zerar...
        if (timer <= 0) {
            
            // 3. Criar a BolaDeFogo na posição EXATA deste Spawner
            BolaDeFogo novaBola = new BolaDeFogo("FogoMeteoro.png", 
                                                this.pPosicao.getLinha(), 
                                                this.pPosicao.getColuna());
            
            // 4. Adicionar a bola de fogo à tela
            // (Usamos o Desenho para acessar a lista de personagens da Tela)
            Desenho.acessoATelaDoJogo().addPersonagem(novaBola);
            
            // 5. Resetar o timer
            timer = delay;
        }
    }

    @Override
    public void desenhar() {
        // Não desenha nada, é invisível
    }
}