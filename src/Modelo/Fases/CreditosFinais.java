package Modelo.Fases;

import Modelo.Hero;
import Modelo.Mensagem;
import Modelo.Parede;
import Modelo.Personagem;
import Modelo.Portal;
import java.util.ArrayList;

/**
 * Fase 6: Tela de Créditos.
 * Implementada como uma fase real, jogável, que não pausa
 * e tem um portal de volta ao Lobby.
 */
public class CreditosFinais implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();

        // 1. Adiciona o Herói (obrigatório para a fase funcionar)
        fase.add(new Hero("Heroi.png", 4, 4)); // Posição central

        // 2. Adiciona as Mensagens (NÃO-BLOQUEANTES)
        // O 'false' no construtor da Mensagem é a chave!
        fase.add(new Mensagem("Criado por:\nFelipe Machuca\nFelipe Maia\nMatheus Gaubeur\nRafael Borges Brosco", false));
        
        // 3. Adiciona o Portal de volta ao Lobby
        Portal portalLobby = new Portal("ZPortaFinal.png", 10, 10); // Canto inferior direito
        portalLobby.setDestinoFase(0); // 0 = Lobby
        fase.add(portalLobby);

        // 4. Adiciona as Paredes (Bordas)
        for (int i = 0; i < 12; i++) {
            fase.add(new Parede("ZPisoPreto.png", 0, i));
            fase.add(new Parede("ZPisoPreto.png", 11, i));
        }
        for (int i = 1; i < 11; i++) {
            fase.add(new Parede("ZPisoPreto.png", i, 0));
            fase.add(new Parede("ZPisoPreto.png", i, 11));
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

    @Override
    public String getMensagemInicial() {
        // Esta mensagem SIM, vai pausar o jogo
        return "PARABENS! VOCE ZEROU O JOGO!";
    }

    // --- Métodos da Interface que não usamos aqui ---
    
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        return new ArrayList<>(); // Vazio
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        return new ArrayList<>(); // Vazio
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        return null; // Vazio
    }

    @Override
    public String getBackgroundTile() {
        return "ZPisoCreditos.png";
    }

    @Override
    public String getMensagemVitoria() {
        return null; // Não tem mensagem de vitória, a própria fase é a vitória
    }
}