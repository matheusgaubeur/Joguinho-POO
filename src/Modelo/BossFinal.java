package Modelo;

import Auxiliar.Desenho;
import Modelo.Comportamentos.Ataque.*; // Importa todos os nossos ataques
import Modelo.Comportamentos.Movimento.*; // Importa todos os nossos movimentos
import java.io.Serializable;
import java.util.ArrayList;

/**
 * O Chefão Final do jogo.
 * Esta classe é um exemplo de "State Machine" (Máquina de Estados)
 * que usa o Padrão Strategy para trocar seus comportamentos
 * de movimento e ataque dinamicamente.
 *
 * VERSÃO MODIFICADA: Alterna entre 2 estados (Perseguir e Atirar).
 */
public class BossFinal extends Personagem implements Serializable, Mortal {

    private int vida;
    private int faseAtualDoBoss;
    private int timerComportamento;
    
    // Define quanto tempo cada "fase" (comportamento) do boss dura
    // (100 ticks * 150ms = 15 segundos por fase)
    // <<-- MUDANÇA: Reduzido de 200 para 100 para ficar mais dinâmico
    private static final int TEMPO_POR_FASE = 100; 

    public BossFinal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        // --- Configuração Inicial ---
        this.setbTransponivel(false); // É um obstáculo sólido
        this.vida = 10; // Precisa de 10 tiros do herói para morrer
        this.faseAtualDoBoss = 1;
        this.timerComportamento = 0;

        // --- Fase 1 (Inicial) ---
        // <<-- MUDANÇA: Inicia perseguindo o herói, mas sem atacar.
        // Isso força o boss a se reposicionar antes de atirar.
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueNulo()); // Fase 1: Apenas persegue
    }

    /**
     * O "cérebro" do Boss.
     * Roda os comportamentos atuais e gerencia a troca de fases.
     */
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // 1. Executa o comportamento de movimento e ataque atuais
        // (Isso chama os métodos .executar() das nossas Estratégias)
        super.atualizar(faseAtual, hero);

        // 2. Atualiza o timer da fase do Boss
        timerComportamento++;

        // 3. Verifica se é hora de mudar de comportamento
        if (timerComportamento > TEMPO_POR_FASE) {
            timerComportamento = 0; // Reseta o timer
            faseAtualDoBoss++;
            
            // <<-- MUDANÇA: Faz um loop (1 -> 2 -> 1 -> 2 ...)
            if (faseAtualDoBoss > 2) { // Agora só temos 2 fases
                faseAtualDoBoss = 1;
            }
            
            // Chama o método que aplica a nova estratégia
            mudarComportamento(faseAtualDoBoss);
        }
    }

    /**
     * Helper que aplica as novas Estratégias (Ataque/Movimento)
     * baseado na fase atual do Boss.
     * * <<-- MUDANÇA: Lógica simplificada para apenas 2 fases.
     */
    private void mudarComportamento(int novaFase) {
        System.out.println("BOSS MUDOU PARA A FASE: " + novaFase);
        
        switch (novaFase) {
            case 1: // Fase 1: Perseguição (para reposicionamento)
                setComportamentoMovimento(new MovimentoChaser());
                setComportamentoAtaque(new AtaqueNulo()); // Só se move, não ataca
                break;
                
            case 2: // Fase 2: Torreta (para ataque focado)
                setComportamentoMovimento(new MovimentoParado());
                setComportamentoAtaque(new AtaqueMirado("fire.png")); // Atira mirado
                break;
                
            // (Fases 3 e 4 removidas para focar no ciclo de 2 estados)
        }
    }

    /**
     * Sobrescreve o 'morrer()' de Personagem para implementar a lógica de vida.
     * Este método é chamado pelo 'ProjetilHeroi' quando ele acerta o Boss.
     * * (Esta função não precisou de mudanças)
     */
    @Override
    public void morrer() {
        this.vida--; // Perde 1 de vida
        System.out.println("Boss foi atingido! Vida restante: " + this.vida);

        if (this.vida <= 0) {
            // A vida acabou! Agora sim, ele morre de verdade.
            super.morrer(); // Seta a flag bEstaVivo = false
            
            // Ao morrer, spawna o Portal de saída
            triggerFimDeFase();
        }
        
        // Se a vida > 0, ele não morre (ignora a chamada 'super.morrer()')
    }
    
    /**
     * Cria o Portal de saída no local da morte do Boss
     * e exibe uma mensagem de vitória.
     * * (Esta função não precisou de mudanças)
     */
    private void triggerFimDeFase() {
        // Pega a posição do Boss
        int linha = this.getPosicao().getLinha();
        int coluna = this.getPosicao().getColuna();
        
        // Cria o portal de saída
        Portal saida = new Portal("esfera.png", linha, coluna);
        
        // IMPORTANTE: Destino 0 (Lobby).
        saida.setDestinoFase(0); 
        
        // Adiciona o portal e a mensagem ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(saida);
        Desenho.acessoATelaDoJogo().addPersonagem(
            new Mensagem("CHEFE DERROTADO!\n\nEntre no portal...", true)
        );
    }
}