package Modelo.Fases;

import Modelo.*;
import Modelo.Inimigos.Urso;
import Modelo.Inimigos.Penguim;
import Modelo.Inimigos.Morsa;
import Controler.Tela.SaveState;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Fase1 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fase1_layout.dat");
             ObjectInputStream serializador = new ObjectInputStream(fis)) {

            // 1. Carrega o objeto de save do arquivo
            SaveState save = (SaveState) serializador.readObject();

            // 2. Adiciona todos os personagens do save na lista da fase
            fase.addAll(save.faseAtual);

        } catch (Exception ex) {
            System.err.println("ERRO AO CARREGAR 'fase1_layout.dat': " + ex.getMessage());
            if (fase.isEmpty()) {
                fase.add(new Hero("Heroi.png", 1, 1));
                fase.add(new Mensagem("ERRO: 'fase1_layout.dat' NAO ENCONTRADO!", true));
            }
        }
        
        // --- ADIÇÕES MANUAIS (Pós-Layout) ---
        fase.add(new Penguim("GeloPinguim.png",9,23));
        fase.add(new Morsa("GeloMorsa.png",13,9));
        fase.add(new Morsa("GeloMorsa.png",16,9));
        fase.add(new Urso("GeloUrso.png",1,36));
        fase.add(new Urso("GeloUrso.png",12,1));
        fase.add(new Chave("ZChave.png", 9, 6));
        fase.add(new Chave("ZChave.png", 10, 41));
        fase.add(new Chave("ZChave.png", 28, 38));
        fase.add(new Bau("ZBau.png", 12, 37));
        fase.add(new PortaFechada("ZPortaVelha.png",14,32));
        fase.add(new PortaFechada("ZPortaVelha.png",3,29));
        fase.add(new Artefato("GeloArtefato.png", 0, 6));
        
        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Penguim("GeloPinguim.png", 17, 17));
        mods.add(new Artefato("GeloArtefato.png", 13, 39));
        mods.add(new Urso("GeloUrso.png", 14, 36));        
        return mods;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Penguim("GeloPinguim.png", 16, 14));
        mods.add(new Artefato("GeloArtefato.png", 20, 10));
        return mods;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        Portal saida = new Portal("ZPortaLobby.png", 1, 25);
        saida.setDestinoFase(0);
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        return "GeloPiso2.png";
    }
    
    @Override
    public String getMensagemInicial() {
        return "QUE FRIIIIO!\nHerói, tome cuidado com os pinguins,\nmorsas e URSOS!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Você superou a neve o gelo \ne encontrou os artefatos!\nFase concluída!";
    }
}
