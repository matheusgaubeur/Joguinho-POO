package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 2 (Fogo e Pedra).
 * Preenche o "casca" com a lógica de jogo do brainstorm.
 */
public class Fase2 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 1, 1)); // Canto superior esquerdo
        
        // 2. Adiciona o PRIMEIRO Item-Chave (usando "coracao.png" como placeholder)
        fase.add(new ItemChave("coracao.png", 1, 14)); // Canto superior direito // <<-- MUDANÇA (Apenas o primeiro)
        //fase.add(new ItemChave("coracao.png", 14, 1)); // Canto inferior esquerdo // <<-- MUDANÇA (Movido para Coleta_1)
        //fase.add(new ItemChave("coracao.png", 14, 14)); // Canto inferior direito // <<-- MUDANÇA (Movido para Coleta_2)
        
        // 3. Adiciona Inimigos Iniciais
        fase.add(new Vulcao("caveira.png", 7, 7)); // Centro do mapa
        
        // <<-- MUDANÇA: Adiciona inimigo de patrulha da Fase 2 (InimigoDiagonal)
        // (Usando "roboPink.png" como placeholder)
        fase.add(new InimigoDiagonal("roboPink.png", 10, 3));

        // 4. Adiciona Paredes do Mapa (Bordas)
        // (Usando "bricks.png" como placeholder de "parede_pedra.png")
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); // Borda Superior
            fase.add(new Parede("bricks.png", 15, i)); // Borda Inferior
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0)); // Borda Esquerda
            fase.add(new Parede("bricks.png", i, 15)); // Borda Direita
        }
        
        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        // "barreiras físicas surgem no mapa"
        ArrayList<Personagem> barreiras = new ArrayList<>();
        
        // Cria uma barreira vertical no meio
        // (Usando "bricks.png" como placeholder)
        for(int i = 1; i < 15; i++) {
             // Deixa uma passagem no meio (onde o vulcão está)
             if (i != 7 && i != 8) {
                barreiras.add(new Parede("Agua.png", i, 10));
             }
        }
        
        // Adiciona o SEGUNDO ItemChave
        barreiras.add(new ItemChave("coracao.png", 14, 1)); // <<-- MUDANÇA (Item 2)
        
        return barreiras;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "o inimigo que segue o herói surge"
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        // (Usando Chaser com skin "chaser.png" como placeholder)
        chefao.add(new Chaser("chaser.png", 13, 2));
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 14, 14)); // <<-- MUDANÇA (Item 3)
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        // (Usando "esfera.png" como placeholder)
        Portal saida = new Portal("esfera.png", 7, 13);
        saida.setDestinoFase(0); // Destino 0 = Volta para o Lobby
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        // Placeholder, precisamos de um "chão de pedra/lava"
        return "bricks.png";
    }

    @Override
    public String getHeroSkin() {
        // Placeholder "Armadura de Ouro"
        return "robo.png";
    }
    
    @Override
    public String getMensagemInicial() {
        return "FOGO, MUITO FOGOO...!\n\nHerói, tome cuidade para não se queimar.\n\nSobreviva!";
    }
}