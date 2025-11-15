package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;
// --- NOSSOS NOVOS IMPORTS ---
import java.io.FileInputStream;
import java.io.ObjectInputStream;
// --- FIM DOS NOVOS IMPORTS ---

/**
 * Implementação da IFase para o Nível 2 (Fogo e Pedra).
 * O layout inicial é carregado de um arquivo serializado.
 * A lógica de 3 estágios permanece no código.
 */
public class Fase2 implements IFase {

    /**
     * MÉTODO REATORADO:
     * Carrega o layout inicial da fase (paredes, herói, inimigos)
     * a partir de um arquivo .dat serializado.
     */
    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        // Simplesmente chama nosso método auxiliar para carregar o arquivo
        return carregarFaseDeArquivo("fase2_layout.dat");
    }

    /**
     * NOVO MÉTODO AUXILIAR:
     * Lê um ArrayList<Personagem> de um arquivo.
     * @param nomeArquivo O nome do arquivo (ex: "fase2_layout.dat")
     * @return O ArrayList de Personagens.
     */
    private ArrayList<Personagem> carregarFaseDeArquivo(String nomeArquivo) {
        try (FileInputStream fis = new FileInputStream(nomeArquivo);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            
            ArrayList<Personagem> fase = (ArrayList<Personagem>) ois.readObject();
            
            // Ajuste CRUCIAL: A imagem (ImageIcon) não é serializada corretamente.
            // Devemos "resetar" a skin do herói (que é o índice 0)
            // usando a skin definida nesta classe de fase.
            if (fase.get(0) instanceof Hero) {
                // getHeroSkin() é um método que já existe nesta classe
                ((Hero) fase.get(0)).setSkin(getHeroSkin()); 
            }
            
            return fase;
            
        } catch (Exception e) {
            System.err.println("FALHA AO CARREGAR ARQUIVO DE FASE: " + nomeArquivo + " - " + e.getMessage());
            // Se falhar, retorna uma fase vazia para não quebrar o jogo
            return new ArrayList<>(); 
        }
    }

    // =================================================================
    // LÓGICA DE 3 ESTÁGIOS (INTOCADA)
    // O editor de fases NÃO mexe nisso. Isso é a lógica do jogo.
    // (Este código é o mesmo que já estava no seu Fase2.java)
    // =================================================================
    
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        // "barreiras físicas surgem no mapa"
        ArrayList<Personagem> barreiras = new ArrayList<>();
        
        for(int i = 1; i < 15; i++) {
             if (i != 7 && i != 8) {
                barreiras.add(new Parede("Agua.png", i, 10));
             }
        }
        
        // Adiciona o SEGUNDO ItemChave
        barreiras.add(new ItemChave("coracao.png", 14, 1)); //
        
        return barreiras;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "o inimigo que segue o herói surge"
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        chefao.add(new Chaser("chaser.png", 13, 2)); //
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 14, 14)); //
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        Portal saida = new Portal("esfera.png", 7, 13); //
        saida.setDestinoFase(0); 
        return saida;
    }

    // =================================================================
    // MÉTODOS DE CONFIGURAÇÃO (INTOCADOS)
    // Ainda precisamos deles para o getHeroSkin() e o fundo.
    // =================================================================

    @Override
    public String getBackgroundTile() {
        return "blackTile.png"; //
    }

    @Override
    public String getHeroSkin() {
        return "robo.png"; //
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