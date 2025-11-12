package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

/**
 * Implementação da IFase para os Créditos Finais (Nível 6).
 */
public class CreditosFinais implements IFase {

    // Fase de créditos é totalmente vazia.
    // A Tela.java desenha a mensagem final 
    // quando o nível 6 é carregado.
    
    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        return new ArrayList<>(); // Vazio
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() { return new ArrayList<>(); }
    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() { return new ArrayList<>(); }
    @Override
    public Personagem getPersonagemColeta_3() { return null; }

    @Override
    public String getBackgroundTile() {
        return "blackTile.png"; // Fundo preto
    }

    @Override
    public String getHeroSkin() {
        return null; // Sem herói
    }
    
    @Override
    public String getMensagemInicial() {
        return "FIM DE JOGO!!!\nOBRIGADO POR JOGAR!";
    }
}