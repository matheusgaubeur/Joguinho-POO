package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 4 (Chuva e Raio).
 * Implementa a lógica de itens sequenciais.
 */
public class Fase4 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 1, 7)); // Topo central
        
        // 2. Adiciona o PRIMEIRO ItemChave
        // (Usando "coracao.png" como placeholder)
        fase.add(new ItemChave("coracao.png", 14, 7)); // Embaixo
        
        // 3. Adiciona Inimigos Iniciais
        fase.add(new RoboAtirador("robo.png", 1, 1)); // Canto superior esquerdo
        fase.add(new RoboAtirador("robo.png", 1, 14)); // Canto superior direito

        // <<-- MUDANÇA: Adiciona inimigo de patrulha da Fase 4 (ZigueZague)
        // (Usando "robo.png" como placeholder)
        fase.add(new ZigueZague("robo.png", 5, 7));
        
        // 4. Adiciona Paredes/Obstáculos (Raios Estáticos)
        // (Usando "bricks.png" como placeholder de "raio_parede.png")
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); // Borda Superior
            fase.add(new Parede("bricks.png", 15, i)); // Borda Inferior
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0)); // Borda Esquerda
            fase.add(new Parede("bricks.png", i, 15)); // Borda Direita
        }
        
        // Raios estáticos (Paredes) no meio
        fase.add(new Parede("bricks.png", 7, 3));
        fase.add(new Parede("bricks.png", 7, 4));
        fase.add(new Parede("bricks.png", 7, 5));
        fase.add(new Parede("bricks.png", 7, 9));
        fase.add(new Parede("bricks.png", 7, 10));
        fase.add(new Parede("bricks.png", 7, 11));
        
        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        // "Raio (preenche 3x3 por alguns segundos)"
        // (Esta é a classe mais difícil, o Raio 3x3. Vamos pular ela por agora
        // e implementar só o item. Podemos voltar nela se der tempo)
        ArrayList<Personagem> obstaculos = new ArrayList<>();
        
        // Adiciona o SEGUNDO ItemChave
        obstaculos.add(new ItemChave("coracao.png", 5, 1));
        
        return obstaculos;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "Drone seguidor com asas afiadas" (Chefão)
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        // (Usando Chaser com skin "chaser.png" como placeholder)
        chefao.add(new Chaser("chaser.png", 14, 14));
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 6, 14));
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // Portal de saída
        // (Usando "esfera.png" como placeholder)
        Portal saida = new Portal("esfera.png", 7, 7); // Centro
        saida.setDestinoFase(0); // Volta ao Lobby
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        // Placeholder (precisamos de "nuvem_tile.png" ou "chao_metal.png")
        return "blackTile.png";
    }

    @Override
    public String getHeroSkin() {
        // Placeholder "Capa de Chuva"
        return "skoot.png";
    }
    
    // Adicione este método dentro da classe Fase3.java
    @Override
    public String getMensagemInicial() {
        return "Chuvas e Raios!\nCuidado com os Raios, drones e... Robôs?";
    }
}