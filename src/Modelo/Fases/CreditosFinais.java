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
        fase.add(new Hero(getHeroSkin(), 7, 7)); // Posição central

        // 2. Adiciona as Mensagens (NÃO-BLOQUEANTES) - CORRIGIDO
        // O 'false' no construtor da Mensagem é a chave!
        fase.add(new Mensagem("Criado por:", false));
        fase.add(new Mensagem("Felipe Maia", false));
        fase.add(new Mensagem("E a IA Dupla :D", false));

        // 3. Adiciona o Portal de volta ao Lobby
        Portal portalLobby = new Portal("esfera.png", 14, 1); // Canto inferior esquerdo
        portalLobby.setDestinoFase(0); // 0 = Lobby
        fase.add(portalLobby);

        // 4. Adiciona as Paredes (Bordas)
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); // Usando bricks
            fase.add(new Parede("bricks.png", 15, i));
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0));
            fase.add(new Parede("bricks.png", i, 15));
        }

        return fase;
    }

    @Override
    public String getMensagemInicial() {
        // Esta mensagem SIM, vai pausar o jogo (Bug 1 corrigido)
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
        return "blackTile.png"; // Fundo preto para os créditos
    }

    @Override
    public String getHeroSkin() {
        return "Hero.png"; // Skin padrão
    }

    @Override
    public String getMensagemVitoria() {
        return null; // Não tem mensagem de vitória, a própria fase é a vitória
    }
}