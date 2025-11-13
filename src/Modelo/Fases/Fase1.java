package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 1 (Água e Gelo).
 * Implementa a mecânica de 3 itens colecionáveis.
 */
public class Fase1 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 1, 1));
        
        // 2. Adiciona o PRIMEIRO Item-Chave
        // (Usando "coracao.png" como placeholder, pois você o tem)
        fase.add(new ItemChave("coracao.png", 1, 14)); // <<-- MUDANÇA (Apenas o primeiro fica)
        //fase.add(new ItemChave("coracao.png", 14, 1));   // <<-- MUDANÇA (Movido para Coleta_1)
        //fase.add(new ItemChave("coracao.png", 14, 14)); // <<-- MUDANÇA (Movido para Coleta_2)
        
        // 3. Adiciona Inimigos Iniciais (Baleia)
        // (Usando a classe Caveira com skin de "baleia.png" (placeholder))
        // (Usando "skoot.png" como placeholder, patrulhando um quadrado 3x3)
        fase.add(new Peixe("skoot.png", 5, 5, 2)); // linha, coluna, tamanhoDoLado
        fase.add(new Baleia("caveira.png", 7, 7)); // (Usando caveira.png como placeholder de "baleia.png")
        
        // 4. Adiciona Paredes do Mapa (Bordas)
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); // Usando bricks por enquanto
            fase.add(new Parede("bricks.png", 15, i));
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0));
            fase.add(new Parede("bricks.png", i, 15));
        }

        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        // "barreiras físicas surgem no mapa" (Paredes de Gelo)
        ArrayList<Personagem> barreiras = new ArrayList<>();
        // (Usando "caveira.png" como placeholder de "gelo_parede.png")
        for(int i = 1; i < 10; i++) {
             barreiras.add(new Parede("caveira.png", 10, i));
        }
        
        // Adiciona o SEGUNDO ItemChave
        barreiras.add(new ItemChave("coracao.png", 14, 1)); // <<-- MUDANÇA (Item 2)
        
        return barreiras;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "o inimigo que segue o herói surge" (Pinguim, o "chefão")
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        // (Usando a classe Chaser com a imagem "robo.png" como placeholder)
        chefao.add(new Chaser("robo.png", 13, 13));
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 14, 14)); // <<-- MUDANÇA (Item 3)
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        Portal saida = new Portal("esfera.png", 3, 3); // (Usando esfera.png como placeholder)
        saida.setDestinoFase(0); // Destino 0 = Volta para o Lobby
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        // (Placeholder, precisamos de uma imagem de "chão de gelo")
        return "Agua.png"; // (você tem essa)
    }

    @Override
    public String getHeroSkin() {
        // (Placeholder, precisamos de uma imagem de "mergulhador.png")
        return "skoot.png"; // (você tem essa)
    }
    
    @Override
    public String getMensagemInicial() {
        return "ÁGUA EM ABUNDANCIA!\nHerói, tome cuidado com os Peixes,\nPinguins e a BALEIA!!!";
    }
}