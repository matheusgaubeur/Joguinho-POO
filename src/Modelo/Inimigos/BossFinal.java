package Modelo.Inimigos;

import Auxiliar.Desenho;
import Modelo.Comportamentos.Ataque.*;
import Modelo.Comportamentos.Movimento.*;
import Modelo.Hero;
import Modelo.Mensagem;
import Modelo.Mortal;
import Modelo.Personagem;
import Modelo.Portal;
import java.io.Serializable;
import java.util.ArrayList;

// Chefão Final
// Alterna entre 2 estados (Perseguir e Atirar)
public class BossFinal extends Personagem implements Serializable, Mortal {
    private static final long serialVersionUID = 1L;

    private int vida;
    private int faseAtualDoBoss;
    private int timerComportamento;
    
    // Define quanto tempo cada "fase" (comportamento) do boss dura
    // (100 ticks * 150ms = 15 segundos por fase)
    // <<-- MUDANÇA: Reduzido de 200 para 100 para ficar mais dinâmico
    private static final int TEMPO_POR_FASE = 100; 

    public BossFinal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        this.setbTransponivel(false); // É um obstáculo sólido
        this.vida = 10; // Precisa de 10 tiros do herói para morrer
        this.faseAtualDoBoss = 1;
        this.timerComportamento = 0;

        // Isso força o boss a se reposicionar antes de atirar.
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo()); // Fase 1: Apenas persegue
    }

    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Executa o comportamento de movimento e ataque atuais
        super.atualizar(faseAtual, hero);

        // 2. Atualiza o timer da fase do Boss
        timerComportamento++;

        // 3. Verifica se é hora de mudar de comportamento
        if (timerComportamento > TEMPO_POR_FASE) {
            timerComportamento = 0; // Reseta o timer
            faseAtualDoBoss++;
            
            if (faseAtualDoBoss > 2) { // Agora só temos 2 fases
                faseAtualDoBoss = 1;
            }
            
            // Chama o método que aplica a nova estratégia
            mudarComportamento(faseAtualDoBoss);
        }
    }

    private void mudarComportamento(int novaFase) {
        System.out.println("BOSS MUDOU PARA A FASE: " + novaFase);
        
        switch (novaFase) {
            case 1 -> {
                // Perseguição (para reposicionamento)
                setComportamentoMovimento(new MovimentoChaser());
                setComportamentoAtaque(new AtaqueNulo()); // Só se move, não ataca
            }
                
            case 2 -> {
                // Torreta (para ataque focado)
                setComportamentoMovimento(new MovimentoParado());
                setComportamentoAtaque(new AtaqueMirado("BossProjetil.png")); // Atira mirado
            }
        }
    }


    @Override
    public void morrer() {
        this.vida--; // Perde 1 de vida
        System.out.println("Boss foi atingido! Vida restante: " + this.vida);

        if (this.vida <= 0) {
            // Agora sim morre
            super.morrer(); // Seta a flag bEstaVivo = false
            
            // Ao morrer, spawna o Portal de saída
            triggerFimDeFase();
        }
    }
    
    //Cria o Portal de saída no local da morte do Boss
    private void triggerFimDeFase() {
        int linha = this.getPosicao().getLinha();
        int coluna = this.getPosicao().getColuna();
        
        // Cria o portal de saída
        Portal saida = new Portal("esfera.png", linha, coluna);
        
        // Destino 0 (Lobby).
        saida.setDestinoFase(0); 
        
        // Adiciona o portal e a mensagem ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(saida);
        Desenho.acessoATelaDoJogo().addPersonagem(
            new Mensagem("CHEFE DERROTADO!\n\nEntre no portal...", true)
        );
    }
}