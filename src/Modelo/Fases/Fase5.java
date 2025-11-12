package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 5 (Sobrevivência).
 * Esta classe apenas monta o cenário (arena).
 * A lógica de timer e spawn de inimigos está na Tela.java.
 */
public class Fase5 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 7, 7)); // Centro
        
        // 2. Adiciona Paredes (layout de "arena")
        // (Usando "bricks.png" como placeholder)
        
        // Bordas
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); 
            fase.add(new Parede("bricks.png", 15, i));
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0));
            fase.add(new Parede("bricks.png", i, 15));
        }
        
        // Obstáculos internos para o herói se esconder
        for (int i = 4; i < 12; i++) {
            if(i != 7) { // Deixa o centro livre
                fase.add(new Parede("bricks.png", 4, i));
                fase.add(new Parede("bricks.png", 11, i));
            }
        }

        return fase;
    }

    // Fase de sobrevivência não usa a mecânica de 3 itens
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() { return new ArrayList<>(); }
    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() { return new ArrayList<>(); }
    @Override
    public Personagem getPersonagemColeta_3() { 
        // A fase termina por tempo (lógica na Tela.java), não por coleta.
        return null; 
    }

    @Override
    public String getBackgroundTile() {
        // Um fundo escuro para a batalha final
        return "blackTile.png";
    }

    @Override
    public String getHeroSkin() {
        // Placeholder "Skin Branca/Dourada"
        return "Robbo.png";
    }
    
    // Adicione este método dentro da classe Fase3.java
    @Override
    public String getMensagemInicial() {
        return "FASE FINAL!\nHerói, você venceu os 4 desafios.\nSobreviva por 1 minuto!";
    }
}