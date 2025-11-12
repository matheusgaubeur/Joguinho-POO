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
    
    public void desenhaTudo(ArrayList<Personagem> e) {
        for (int i = 0; i < e.size(); i++)
            e.get(i).autoDesenho();
    }
    
    public String processaTudo(ArrayList<Personagem> umaFase) {
        Hero hero = (Hero) umaFase.get(0);
        Personagem pIesimoPersonagem;
        
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            
            if (hero.getPosicao().igual(pIesimoPersonagem.getPosicao())) {
                
                // 1. O personagem é Mortal?
                if (pIesimoPersonagem instanceof Mortal) {
                    return "HERO_DIED";
                
                // 2. É um Portal?
                } else if (pIesimoPersonagem instanceof Modelo.Portal) {
                    Modelo.Portal portal = (Modelo.Portal) pIesimoPersonagem;
                    int destino = portal.getDestinoFase();
                    
                    // --- AQUI ESTÁ A CORREÇÃO ---
                    if (destino == 0) {
                        // Destino 0 = Fim da Fase (volta ao Lobby)
                        return "FASE_CONCLUIDA"; // <<-- MUDANÇA CRÍTICA
                    } else {
                        // Destino 1,2,3,4,5 = Portal de Viagem
                        return "PORTAL_FASE_" + destino; 
                    }
                    // --- FIM DA CORREÇÃO ---
                    
                // 3. É um ItemChave (os 3 colecionáveis)?
                } else if (pIesimoPersonagem instanceof Modelo.ItemChave) {
                    umaFase.remove(pIesimoPersonagem); 
                    return "ITEM_COLETADO"; 
                    
                // 4. É algum outro Coletavel (ex: moeda, vida extra)?
                } else if (pIesimoPersonagem instanceof Coletavel) {
                    umaFase.remove(pIesimoPersonagem);
                    return "PONTOS";
                }
            }
        }
        
        // Atualiza a direção dos Chasers
        for (int i = 1; i < umaFase.size(); i++) {
            pIesimoPersonagem = umaFase.get(i);
            if (pIesimoPersonagem instanceof Chaser) {
                ((Chaser) pIesimoPersonagem).computeDirection(hero.getPosicao());
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
