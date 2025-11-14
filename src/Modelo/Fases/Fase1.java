package Modelo.Fases;

import Modelo.*;
import java.util.ArrayList;

// V-V-V- IMPORTS NOVOS NECESSÁRIOS V-V-V
import Modelo.Bau;
import Modelo.Cadeado;
import Modelo.Chave;
// ^-^-^- FIM DOS IMPORTS NOVOS -^-^-^

/**
 * Implementação da IFase para o Nível 1 (Água e Gelo).
 * Implementa a mecânica de 3 itens colecionáveis.
 * * ATUALIZAÇÃO: Agora também inclui a "Sala de Bônus" (Side Quest)
 * para teste.
 */
public class Fase1 implements IFase {

    @Override
    public ArrayList<Personagem> carregarPersonagensIniciais() {
        ArrayList<Personagem> fase = new ArrayList<>();
        
        // 1. Adiciona o Herói
        fase.add(new Hero(getHeroSkin(), 1, 1));
        
        // -----------------------------------------------------------------
        // 2. MISSÃO PRINCIPAL (Lógica dos 3 Itens - Intocada)
        // -----------------------------------------------------------------
        
        // 2a. Adiciona o PRIMEIRO Item-Chave (Coração)
        fase.add(new ItemChave("coracao.png", 1, 14)); 
        
        // 2b. Adiciona Inimigos Iniciais
        // (MOVEMOS O PEIXE para 5,10 para não bater na sala nova)
        fase.add(new Peixe("skoot.png", 5, 10, 2)); // linha, coluna, tamanhoDoLado
        fase.add(new Baleia("caveira.png", 7, 7)); 
        
        
        // -----------------------------------------------------------------
        // 3. SIDE QUEST (Lógica da Sala de Bônus - NOVA)
        // -----------------------------------------------------------------

        // 3a. Adiciona as Chaves (perto do start, para teste)
        // (Usando "coracao.png" como placeholder da imagem da Chave)
        fase.add(new Chave("coracao.png", 2, 1)); // Chave para a Porta
        fase.add(new Chave("coracao.png", 3, 1)); // Chave para o Baú
        
        // 3b. Constrói a Sala de Bônus (no meio-esquerdo)
        fase.add(new Parede("bricks.png", 3, 3)); // Parede Topo
        fase.add(new Parede("bricks.png", 3, 4));
        fase.add(new Parede("bricks.png", 3, 5));
        fase.add(new Parede("bricks.png", 4, 5)); // Parede Direita
        fase.add(new Parede("bricks.png", 5, 5));
        fase.add(new Parede("bricks.png", 6, 5));
        fase.add(new Parede("bricks.png", 6, 4)); // Parede Fundo
        fase.add(new Parede("bricks.png", 6, 3));
        fase.add(new Parede("bricks.png", 5, 3)); // Parede Esquerda
        
        // 3c. Adiciona a Porta (Cadeado)
        // (Usando "bricks.png" como placeholder da imagem do Cadeado)
        fase.add(new Cadeado("esfera.png", 4, 3)); // A porta está em (4,3)
        
        // 3d. Adiciona o Baú (dentro da sala)
        // (Usando "esfera.png" como placeholder da imagem do Baú)
        fase.add(new Bau("roboPink.png", 5, 4));
        
        // Adiciona munição na frente do herói (use a imagem 'coracao.png' como placeholder)
        fase.add(new Municao("bullet.png", 1, 2));

        // Adiciona um alvo fácil (ZigueZague) à direita
        // (use "skoot.png" ou "roboPink.png" como placeholder)
        fase.add(new ZigueZague("skoot.png", 1, 4));
        
        
        // -----------------------------------------------------------------
        // 4. Adiciona Paredes do Mapa (Bordas - Intocadas)
        // -----------------------------------------------------------------
        for (int i = 0; i < 16; i++) {
            fase.add(new Parede("bricks.png", 0, i)); // Usando bricks por enquanto
            fase.add(new Parede("bricks.png", 15, i));
        }
        for (int i = 1; i < 15; i++) {
            fase.add(new Parede("bricks.png", i, 0));
            fase.add(new Parede("bricks.png", i, 15));
        }

        return fase;
    }

    // =================================================================
    // O RESTO DA LÓGICA DA FASE (MISSÃO PRINCIPAL)
    // PERMANECE EXATAMENTE IGUAL.
    // =================================================================
    
    @Override
    public ArrayList<Personagem> getPersonagensColeta_1() {
        // "barreiras físicas surgem no mapa" (Paredes de Gelo)
        ArrayList<Personagem> barreiras = new ArrayList<>();
        // (Usando "caveira.png" como placeholder de "gelo_parede.png")
        for(int i = 1; i < 10; i++) {
             barreiras.add(new Parede("caveira.png", 10, i));
        }
        
        // Adiciona o SEGUNDO ItemChave
        barreiras.add(new ItemChave("coracao.png", 14, 1)); // <<-- MUDANÇA (Item 2)
        
        return barreiras;
    }

    @Override
    public ArrayList<Personagem> getPersonagensColeta_2() {
        // "o inimigo que segue o herói surge" (Pinguim, o "chefão")
        ArrayList<Personagem> chefao = new ArrayList<>();
        
        // (Usando a classe Chaser com a imagem "robo.png" como placeholder)
        chefao.add(new Chaser("robo.png", 13, 13));
        
        // Adiciona o TERCEIRO ItemChave
        chefao.add(new ItemChave("coracao.png", 14, 14)); // <<-- MUDANÇA (Item 3)
        
        return chefao;
    }

    @Override
    public Personagem getPersonagemColeta_3() {
        // "sair por um portal"
        Portal saida = new Portal("esfera.png", 1, 1); // (Usando esfera.png como placeholder)
        saida.setDestinoFase(0); // Destino 0 = Volta para o Lobby
        return saida;
    }

    @Override
    public String getBackgroundTile() {
        // (Placeholder, precisamos de uma imagem de "chão de gelo")
        return "Agua.png"; // (você tem essa)
    }

    @Override
    public String getHeroSkin() {
        // (Placeholder, precisamos de uma imagem de "mergulhador.png")
        return "skoot.png"; // (você tem essa)
    }
    
    @Override
    public String getMensagemInicial() {
        return "ÁGUA EM ABUNDANCIA!\nHerói, tome cuidado com os Peixes,\nPinguins e a BALEIA!!!";
    }
    
    @Override
    public String getMensagemVitoria() {
        return "Você superou a inundação!\nFase da Água concluída!";
    }
}