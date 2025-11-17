package Modelo.Fases;

import Controler.Tela;
import Modelo.*;
import Modelo.Inimigos.Fantasma;
import Modelo.Inimigos.Morcego;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Fase4 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fase4_layout.dat");
             ObjectInputStream serializador = new ObjectInputStream(fis)) {

            // 1. Carrega o objeto de save do arquivo
            Tela.SaveState save = (Tela.SaveState) serializador.readObject();

            // 2. Adiciona todos os personagens do save na lista da fase
            fase.addAll(save.faseAtual);

        } catch (Exception ex) {
            System.err.println("ERRO AO CARREGAR 'fase4_layout.dat': " + ex.getMessage());
            if (fase.isEmpty()) {
                fase.add(new Hero("Heroi.png", 1, 1));
                fase.add(new Mensagem("ERRO: 'fase4_layout.dat' NAO ENCONTRADO!", true));
            }
        }
        
        // --- ADIÇÕES MANUAIS (Pós-Layout) ---
        fase.add(new Fantasma("CavernaFantasma.png",18,4));
        fase.add(new Morcego("CavernaMorcego.png",5,22));
        fase.add(new Fantasma("CavernaFantasma.png",45,3));
        fase.add(new Fantasma("CavernaFantasma.png",2,26));
        fase.add(new Artefato("CavernaArtefato.png",42,46));
        
        
        fase.add(new Chave("ZChave.png", 45,4));
        fase.add(new Chave("ZChave.png", 5,23));
        fase.add(new Chave("ZChave.png", 36,44));
        fase.add(new Chave("ZChave.png", 23,25));
        fase.add(new Chave("ZChave.png", 46,32));
        fase.add(new Bau("ZBau.png", 11, 37));
        fase.add(new Bau("ZBau.png", 33,33));
        fase.add(new Bau("ZBau.png", 13,22));
     
        fase.add(new PortaFechada("ZPortaVelha.png",31,19));
        fase.add(new PortaFechada("ZPortaVelha.png",19,38));
        fase.add(new PortaFechada("ZPortaVelha.png",37,39));
        
        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Morcego("CavernaMorcego.png", 46,13));
        mods.add(new Artefato("CavernaArtefato.png", 46,14));
        mods.add(new Fantasma("CavernaFantasma.png", 2,26));        
        mods.add(new Morcego("CavernaMorcego.png", 3,47));
        return mods;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Morcego("CavernaMorcego.png", 43,46));
        mods.add(new Morcego("CavernaMorcego.png", 26,40));
        mods.add(new Fantasma("CavernaFantasma.png", 2,29));
         mods.add(new Fantasma("CavernaFantasma.png", 43,46));
        mods.add(new Artefato("CavernaArtefato.png", 26,41));
        return mods;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        Portal saida = new Portal("ZPortaLobby.png", 5, 5);
        saida.setDestinoFase(0);
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        return "CavernaPiso.png";
    }
    
    @Override
    public String getMensagemInicial() {
        return "Quem apagou a luz? \nNão consigo ver nada nessa caverna escura!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Ufa, finalmente um pouco de luz!\nVocê escapou da caverna.";
    }
}