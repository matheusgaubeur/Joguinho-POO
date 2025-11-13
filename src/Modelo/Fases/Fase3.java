package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 3 (Pântano).
 * Implementa a lógica de 3 itens colecionáveis DE FORMA SEQUENCIAL.
 */
public class Fase3 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 7, 7)); // Posição central
        
        // 2. Adiciona o PRIMEIRO ItemChave (usando "coracao.png" como placeholder)
        fase.add(new ItemChave("coracao.png", 1, 1)); 
        
        // 3. Adiciona Inimigos Iniciais (Jacaré e Capivara)
        fase.add(new Jacare("caveira.png", 7, 14)); // Atira BolaDeLama para a esquerda
        fase.add(new BichinhoVaiVemHorizontal("roboPink.png", 14, 1)); // Capivara

        // <<-- MUDANÇA: Adiciona inimigo de patrulha da Fase 3 (InimigoCircular)
        // (Usando "skoot.png" como placeholder)
        fase.add(new InimigoCircular("skoot.png", 10, 10));

        // 4. Adiciona Paredes/Obstáculos do Mapa (Bordas)
        // (Usando "bricks.png" como placeholder de "tronco.png")
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
        // "barreiras físicas surgem no mapa" (Troncos)
        ArrayList<Personagem> barreiras = new ArrayList<>();
        
        // Cria uma barreira horizontal de "troncos"
        // (Usando "bricks.png" como placeholder)
        for(int i = 4; i < 12; i++) {
             barreiras.add(new Parede("blackTile.png", 5, i));
        }
        
        // Adiciona o SEGUNDO ItemChave (corrigindo a lógica)
        barreiras.add(new ItemChave("coracao.png", 14, 14));
        
        return barreiras;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "o inimigo que segue o herói surge" (Mosquitão)
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        // (Usando Chaser com skin "chaser.png" como placeholder)
        chefao.add(new Chaser("chaser.png", 1, 14));
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 6, 7)); // Perto do herói
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        // (Usando "esfera.png" como placeholder)
        Portal saida = new Portal("esfera.png", 1, 7);
        saida.setDestinoFase(0); // Destino 0 = Volta para o Lobby
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        // Placeholder, precisamos de um "chão de pântano"
        return "bricks.png";
    }

    @Override
    public String getHeroSkin() {
        // Placeholder "Galocha"
        return "roboPink.png";
    }
    
// Adicione este método dentro da classe Fase3.java
    @Override
    public String getMensagemInicial() {
        return "TERRAS PANTANOSAS!\nCuidado com os Jacarés e... Mosquitos?";
    }
}