package Controler;

import Modelo.Chaser;
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Mortal;
import Modelo.Coletavel;
import Modelo.Esfera;
import auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    
// <<-- MUDANÇA: Renomeado para "desenharTudo"
    public void desenharTudo(ArrayList<Personagem> e) {
        for (int i = 0; i < e.size(); i++)
            e.get(i).desenhar(); // <<-- MUDANÇA: Chamando o novo método desenhar()
    }
    
    public void atualizarTudo(ArrayList<Personagem> umaFase, Hero hero, boolean isGamePaused) {
        // (Note que o Heroi não precisa de um 'atualizar' proativo, 
        // mas alguns personagens, como o Chaser, precisam saber onde ele está)
        for (int i = 0; i < umaFase.size(); i++) {
            Personagem p = umaFase.get(i);

            // SE O JOGO ESTIVER PAUSADO:
            if (isGamePaused) {
                // Só atualiza as Mensagens (para o timer delas funcionar e o jogo despausar)
                if (p instanceof Modelo.Mensagem) { 
                    p.atualizar(umaFase, hero);
                }
            } 
            // SE O JOGO ESTIVER RODANDO:
            else {
                // Atualiza TODO MUNDO (Inimigos, Projéteis, etc.)
                p.atualizar(umaFase, hero);
            }
        }
    }
    
    public String processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                
                // 1. Pergunta ao personagem o que fazer na colisão
                String resultadoColisao = pIesimoPersonagem.aoColidirComHeroi(hero);
                
                if (resultadoColisao.equals("REJEITADO")) {
                    hero.voltaAUltimaPosicao(); // Empurra o herói de volta
                    return "GAME_RUNNING"; // Mas o jogo continua
                }
                
                // 2. Trata os casos especiais (remoção de item)
                if (resultadoColisao.equals("ITEM_COLETADO")) {
                    umaFase.remove(pIesimoPersonagem); 
                    return "ITEM_COLETADO"; 
                    
                } else if (resultadoColisao.equals("CHAVE_COLETADA")) {
                    umaFase.remove(pIesimoPersonagem); // A REMOÇÃO É AQUI!
                    return "CHAVE_COLETADA"; // Retorna imediatamente (evita erro)
                    
                } else if (resultadoColisao.equals("BAU_ABERTO")) {
                    umaFase.remove(pIesimoPersonagem); // Agora o Controle remove o baú
                    return "BAU_ABERTO"; // E avisa a Tela

                } else if (resultadoColisao.equals("CADEADO_ABERTO")) {
                    umaFase.remove(pIesimoPersonagem); // Agora o Controle remove o cadeado
                    return "GAME_RUNNING"; // Abrir um cadeado não precisa de mais lógica
                    
                } else if (resultadoColisao.equals("PONTOS")) {
                    // (Lógica para coletáveis genéricos, se tivéssemos)
                    umaFase.remove(pIesimoPersonagem);
                    return "PONTOS";
                
                } else if (!resultadoColisao.equals("GAME_RUNNING")) {
                    // Retorna "HERO_DIED", "FASE_CONCLUIDA", "PORTAL_FASE_X", etc.
                    return resultadoColisao;
                }

            }
        }
        
        // Atualiza a direção dos Chasers
//        for (int i = 1; i < umaFase.size(); i++) {
//            pIesimoPersonagem = umaFase.get(i);
//            if (pIesimoPersonagem instanceof Chaser) {
//                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
//            }
//        }
        
        return "GAME_RUNNING";
    }

    /*Retorna true se a posicao p é válida para Hero com relacao a todos os personagens no array*/
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        Personagem pIesimoPersonagem;
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (!pIesimoPersonagem.isbTransponivel()) {
                if (pIesimoPersonagem.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}