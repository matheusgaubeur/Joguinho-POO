package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para o Nível 0 (Lobby).
 * Contém os portas para as outras fases.
 */
public class Lobby implements IFase {
    private java.util.Set<Integer> fasesConcluidas;
    
    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero("Heroi.png", 5, 2)); // Posição central
        
        // 2. Adiciona as Portas 1-4
        Portal p1 = new Portal("ZPortaLobby.png", 2, 3); 
        p1.setDestinoFase(1); 
        fase.add(p1);

        Portal p2 = new Portal("ZPortaLobby.png", 2, 8);
        p2.setDestinoFase(2); 
        fase.add(p2);
        
        Portal p3 = new Portal("ZPortaLobby.png", 9, 3);
        p3.setDestinoFase(3); 
        fase.add(p3);
        
        Portal p4 = new Portal("ZPortaLobby.png", 9, 8);
        p4.setDestinoFase(4); 
        fase.add(p4);

        // 3. LÓGICA DE UNLOCK DA FASE 5
        // Verifica se a "memória" não é nula e se contém 4 fases concluídas
        if (this.fasesConcluidas != null && this.fasesConcluidas.size() >= 4) {
            System.out.println("PARABENS! Portal para a ultima fase esta aberto!");
            // Adiciona o portal para a Fase 5 (Sobrevivência)
            Portal p5 = new Portal("ZPortaFinal.png", 5, 10); // Porta diferente
            p5.setDestinoFase(5);
            fase.add(p5);
        }

        // 4. Adiciona as Paredes
        for (int i = 0; i < 12; i++) {
            fase.add(new Parede("LobbyParede.png", 0, i));
            fase.add(new Parede("LobbyParede.png", 11, i));
        }
        for (int i = 1; i < 11; i++) {
            fase.add(new Parede("LobbyParede.png", i, 0));
            fase.add(new Parede("LobbyParede.png", i, 11));
        }
        
        // 5. Completa a área externa com tiles pretos
        for (int i = 0; i < 12; i++) {
            fase.add(new Parede("ZPisoPreto.png", 12, i));
            fase.add(new Parede("ZPisoPreto.png", 13, i));
            fase.add(new Parede("ZPisoPreto.png", 14, i));
            fase.add(new Parede("ZPisoPreto.png", 15, i));
        }
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("ZPisoPreto.png", i, 12));
            fase.add(new Parede("ZPisoPreto.png", i, 13));
            fase.add(new Parede("ZPisoPreto.png", i, 14));
            fase.add(new Parede("ZPisoPreto.png", i, 15));
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
        return "LobbyPiso.png"; // Fundo da caverna
    }

    public void atualizarFasesConcluidas(java.util.Set<Integer> fases) {
        this.fasesConcluidas = fases;
    }
    
    @Override
    public String getMensagemInicial() { // fazer um if para imprimir uma mensagem diferente a depender da fase
        return "BEM VINDO AO PIOR JOGO DO MUNDO!!!\n\n - Mover: Use as setas.\n - Atirar: Barra de ESPACO\n - Salvar: Tecla S\n - Carregar: Tecla L";
    }
    
    @Override
    public String getMensagemVitoria() {
        return null; // O Lobby não tem mensagem de vitória
    }
}