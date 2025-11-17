package Modelo.Fases;

import Controler.Tela;
import Modelo.*;
import Modelo.Inimigos.Mosquito;
import Modelo.Inimigos.Musgo;
import Modelo.Inimigos.Capivara;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class Fase3 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream("fase3_layout.dat");
             ObjectInputStream serializador = new ObjectInputStream(fis)) {

            // 1. Carrega o objeto de save do arquivo
            Tela.SaveState save = (Tela.SaveState) serializador.readObject();

            // 2. Adiciona todos os personagens do save na lista da fase
            fase.addAll(save.faseAtual);

        } catch (Exception ex) {
            System.err.println("ERRO AO CARREGAR 'fase3_layout.dat': " + ex.getMessage());
            if (fase.isEmpty()) {
                fase.add(new Hero("Heroi.png", 1, 1));
                fase.add(new Mensagem("ERRO: 'fase3_layout.dat' NAO ENCONTRADO!", true));
            }
        }
        
        // --- ADIÇÕES MANUAIS (Pós-Layout) ---
        fase.add(new Capivara("PantanoCapivara.png",4,6));
        fase.add(new Capivara("PantanoCapivara.png",47,2));
        fase.add(new Musgo("PantanoMusgo.png",16,12));
        fase.add(new Mosquito("PantanoMosquito.png",1,3));
        fase.add(new Artefato("PantanoArtefato.png", 30, 1));
        
        
        fase.add(new Chave("ZChave.png", 12,12));
        fase.add(new Chave("ZChave.png", 47,9));
        fase.add(new Chave("ZChave.png", 18,18));
        fase.add(new Chave("ZChave.png", 31,26));
        fase.add(new Chave("ZChave.png", 5,8));
        fase.add(new Chave("ZChave.png", 2,10));
                
        fase.add(new Bau("ZBau.png", 10,4));
        fase.add(new Bau("ZBau.png", 31,6));
        //fase.add(new Bau("ZBau.png", 11,5));
        
        fase.add(new PortaFechada("ZPortaVelha.png",9,7));
        fase.add(new PortaFechada("ZPortaVelha.png",9,14));
       // fase.add(new PortaFechada("ZPortaVelha.png",6,7));
        
        return fase;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Musgo("PantanoMusgo.png", 5,8));
        mods.add(new Artefato("PantanoArtefato.png", 10,20));
        mods.add(new Capivara("PantanoCapivara.png", 12,16));    
        mods.add(new Capivara("PantanoCapivara.png", 7,8));
        return mods;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        ArrayList<Personagem> mods = new ArrayList<>();
        mods.add(new Musgo("PantanoMusgo.png", 5,20));
        mods.add(new Artefato("PantanoArtefato.png", 6,5));
         mods.add(new Capivara("PantanoCapivara.png", 1,8));
        return mods;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        ArrayList<Personagem> mods = new ArrayList<>();
        Portal saida = new Portal("ZPortaLobby.png", 5, 5);
        mods.add(new Mosquito("PantanoMosquito.png",8,4));
        mods.add(new Mosquito("PantanoMosquito.png",4,1));
         mods.add(new Capivara("PantanoCapivara.png", 31,7));
        saida.setDestinoFase(0);
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        return "PantanoPiso2.png";
    }
    
    @Override
    public String getMensagemInicial() {
        return "QUE LUGAR BIZARRO!!\nCuidado com as criaturas do pântano!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Parabéns, héroi!\nVocê escapou do pântano!";
    }
}


 