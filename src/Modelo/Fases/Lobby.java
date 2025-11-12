package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 0 (Lobby).
 * Contém os portais para as outras fases.
 */
public class Lobby implements IFase {
    private java.util.Set<Integer> fasesConcluidas;
    
    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 7, 7)); // Posição central
        
        // 2. Adiciona os Portais 1-4
        Portal p1 = new Portal("esfera.png", 2, 4); 
        p1.setDestinoFase(1); 
        fase.add(p1);

        Portal p2 = new Portal("esfera.png", 2, 10);
        p2.setDestinoFase(2); 
        fase.add(p2);
        
        Portal p3 = new Portal("esfera.png", 12, 4);
        p3.setDestinoFase(3); 
        fase.add(p3);
        
        Portal p4 = new Portal("esfera.png", 12, 10);
        p4.setDestinoFase(4); 
        fase.add(p4);

        // 3. LÓGICA DE UNLOCK DA FASE 5
        // (Verifica se a "memória" não é nula e se contém 4 fases concluídas)
        if (this.fasesConcluidas != null && this.fasesConcluidas.size() >= 4) {
            System.out.println("PARABENS! Portal para a Fase 5 aberto!");
            // Adiciona o portal para a Fase 5 (Sobrevivência)
            Portal p5 = new Portal("fire.png", 7, 1); // Portal diferente (fogo)
            p5.setDestinoFase(5);
            fase.add(p5);
        }

        // 4. Adiciona as Paredes
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

    // O Lobby não tem a mecânica de 3 itens, então retorna listas vazias.
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() { return new ArrayList<>(); }
    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() { return new ArrayList<>(); }
    @Override
    public Personagem getPersonagemColeta_3() { return null; }

    @Override
    public String getBackgroundTile() {
        return "blackTile.png"; // Fundo da caverna (você já tem)
    }

    public void atualizarFasesConcluidas(java.util.Set<Integer> fases) {
        this.fasesConcluidas = fases;
    }
    
    @Override
    public String getHeroSkin() {
        return "Hero.png"; // Skin padrão (você já tem)
    }
    
    @Override
    public String getMensagemInicial() { // fazer um if para imprimir uma mensagem diferente a depender da fase
        return "BEM VINDO AO PIOR JOGO DO MUNDO!!!\n";
    }
}