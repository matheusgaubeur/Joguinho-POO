package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import Auxiliar.Posicao;
import java.util.ArrayList;

public class ControleDeJogo {
    
    public void desenharTudo(ArrayList<Personagem> e) {
        for (int i = 0; i < e.size(); i++)
            e.get(i).desenhar(); //
    }
    
    public void atualizarTudo(ArrayList<Personagem> umaFase, Hero hero, boolean isGamePaused) {
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
            
            // Esta verificação é para personagens NÃO-HERÓIS (i > 0)que NÃO são transponives
            if (i > 0 && !p.isbTransponivel()) {
                // Agora, verifica se 'p' colidiu com qualquer outro objeto sólido
                for (int j = 0; j < umaFase.size(); j++) {
                    // Não precisa checar colisão consigo mesmo
                    if (i == j) {
                        continue; 
                    }
                    
                    Personagem outro = umaFase.get(j);

                    // Se o 'outro' objeto também não for transponível e as posições são iguais (colisão).
                    if (!outro.isbTransponivel() && p.getPosicao().igual(outro.getPosicao())) {
                        // Manda o personagem 'p' de volta para a última posição.
                        p.voltaAUltimaPosicao();
                        // Já colidiu e voltou, não precisa continuar a checar outros objetos
                        break; 
                    }
                }
            }
            
            // Após atualizar, checa se a flag 'bEstaVivo' foi setada para false
            if (!p.isVivo()) {
                umaFase.remove(i);
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
                    return "GAME_RUNNING"; // Jogo continua
                }
                
                // 2. Trata os casos especiais (remoção de item)
                if (resultadoColisao.equals("ITEM_COLETADO")) {
                    umaFase.remove(pIesimoPersonagem); 
                    return "ITEM_COLETADO";
                
                } else if (resultadoColisao.equals("MUNICAO_COLETADA")) {
                    umaFase.remove(pIesimoPersonagem);
                    return "MUNICAO_COLETADA";
                    
                } else if (resultadoColisao.equals("CHAVE_COLETADA")) {
                    umaFase.remove(pIesimoPersonagem);
                    return "CHAVE_COLETADA";
                    
                } else if (resultadoColisao.equals("BAU_ABERTO")) {
                    umaFase.remove(pIesimoPersonagem); // Controle remove o baú
                    return "BAU_ABERTO"; // E avisa a Tela

                } else if (resultadoColisao.equals("CADEADO_ABERTO")) {
                    umaFase.remove(pIesimoPersonagem); // Controle remove a portaFechada
                    return "GAME_RUNNING";
                    
                } else if (resultadoColisao.equals("PONTOS")) {
                    umaFase.remove(pIesimoPersonagem);
                    return "PONTOS";
                
                } else if (!resultadoColisao.equals("GAME_RUNNING")) {
                    return resultadoColisao;
                }

            }
        }
              
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