package Modelo.Fases;

import Modelo.Inimigos.SpawnerBolaFogo;
import Modelo.Inimigos.Foguinho;
import Modelo.*;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import Controler.Tela.SaveState;

public class Fase2 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fase2_layout.dat");
             ObjectInputStream serializador = new ObjectInputStream(fis)) {

            // 1. Carrega o objeto de save do arquivo
            SaveState save = (SaveState) serializador.readObject();

            // 2. Adiciona todos os personagens do save na lista da fase
            fase.addAll(save.faseAtual);

        } catch (Exception ex) {
            System.err.println("ERRO AO CARREGAR 'fase2_layout.dat': " + ex.getMessage());
            // Se falhar, adiciona pelo menos o Herói para não quebrar o jogo
            if (fase.isEmpty()) {
                fase.add(new Hero("Heroi.png", 1, 1));
                fase.add(new Mensagem("ERRO: 'fase2_layout.dat' NAO ENCONTRADO!", true));
            }
        }

        // Adicionar os elementos da fase manualmente
        fase.add(new Artefato("FogoArtefato.png", 8, 16));
        fase.add(new Chave("ZChave.png",5,10));
        fase.add(new Chave("ZChave.png",21,12));
        fase.add(new Chave("ZChave.png",21,23));
        fase.add(new Chave("ZChave.png",13,16));
        fase.add(new PortaFechada("ZPortaVelha.png",21,4));
        fase.add(new PortaFechada("ZPortaVelha.png",6,9));
        fase.add(new PortaFechada("ZPortaVelha.png",27,27));
        fase.add(new Bau("ZBau.png",27,20));

        // Bolas de fogo caindo do ceu
        fase.add(new SpawnerBolaFogo(0, 0));
        fase.add(new SpawnerBolaFogo(0, 7));
        fase.add(new SpawnerBolaFogo(0, 14));
        fase.add(new SpawnerBolaFogo(0, 21));

        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Foguinho("FogoFoguinho.png", 15, 15));
        mods.add(new Foguinho("FogoFoguinho.png", 19, 2));
        mods.add(new Artefato("FogoArtefato.png", 17, 2));
        // Bolas de fogo caindo do ceu
        mods.add(new SpawnerBolaFogo(0, 2));
        mods.add(new SpawnerBolaFogo(0, 9));
        mods.add(new SpawnerBolaFogo(0, 17));
        mods.add(new SpawnerBolaFogo(0, 24));  
        return mods;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Foguinho("FogoFoguinho.png", 15, 15));
        mods.add(new Artefato("FogoArtefato.png", 25, 35));
        // Bolas de fogo caindo do ceu
        mods.add(new SpawnerBolaFogo(0, 4));
        mods.add(new SpawnerBolaFogo(0, 10));
        mods.add(new SpawnerBolaFogo(0, 19));
        mods.add(new SpawnerBolaFogo(0, 27));          
        return mods;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        Portal saida = new Portal("ZPortaLobby.png", 4, 8);
        saida.setDestinoFase(0); 
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        return "FogoPiso2.png"; //
    }
    
    @Override
    public String getMensagemInicial() {
        return "FOGO, MUITO FOGOO...!\n\nHerói, tome cuidade para não se queimar.\n\nSobreviva!"; //
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Parabéns, héroi!\nVocê conseguiu vencer o calor!"; //
    }
}