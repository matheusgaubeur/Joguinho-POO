package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

public class Fase5 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero("Hero.png", 1, 1)); // Canto superior esquerdo
        
        // 2. Adiciona o BossFinal
        fase.add(new BossFinal("caveira.png", 7, 7)); // No centro

        // 3. Adiciona Paredes (layout de arena)
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); 
            fase.add(new Parede("bricks.png", 15, i));
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0));
            fase.add(new Parede("bricks.png", i, 15));
        }
        
        return fase;
    }

    // Fase de Boss não usa a mecânica de 3 itens
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() { return new ArrayList<>(); }
    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() { return new ArrayList<>(); }
    @Override
    public Personagem getPersonagemColeta_3() { return null; }

    @Override
    public String getBackgroundTile() {
        return "blackTile.png";
    }

    @Override
    public String getHeroSkin() {
        return "Robbo.png"; // Skin de Herói
    }
    
    @Override
    public String getMensagemInicial() {
        return "FASE FINAL!\n\nDerrote o chefe!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Parabéns, héroi!\nVocê conseguiu vencer essa jornada incrível! Você é demais!";
    }
}