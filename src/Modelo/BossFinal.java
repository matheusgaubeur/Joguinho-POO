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
 */
public class BossFinal extends Personagem implements Serializable, Mortal {

    private int vida;
    private int faseAtualDoBoss;
    private int timerComportamento;
    
    // Define quanto tempo cada "fase" (comportamento) do boss dura
    // (200 ticks * 150ms = 30 segundos por fase)
    private static final int TEMPO_POR_FASE = 200;

    public BossFinal(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        
        // --- Configuração Inicial ---
        this.setbTransponivel(false); // É um obstáculo sólido
        this.vida = 10; // Precisa de 10 tiros do herói para morrer
        this.faseAtualDoBoss = 1;
        this.timerComportamento = 0;

        // --- Fase 1 (Inicial) ---
        // Persegue o herói atirando projéteis mirados
        setComportamentoMovimento(new MovimentoChaser());
        setComportamentoAtaque(new AtaqueMirado("fire.png"));
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
            
            // Faz um loop (1 -> 2 -> 3 -> 4 -> 1 ...)
            if (faseAtualDoBoss > 4) {
                faseAtualDoBoss = 1;
            }
            
            // Chama o método que aplica a nova estratégia
            mudarComportamento(faseAtualDoBoss);
        }
    }

    /**
     * Helper que aplica as novas Estratégias (Ataque/Movimento)
     * baseado na fase atual do Boss.
     */
    private void mudarComportamento(int novaFase) {
        System.out.println("BOSS MUDOU PARA A FASE: " + novaFase);
        
        switch (novaFase) {
            case 1: // Fase Perseguição + Tiro Mirado (Padrão)
                setComportamentoMovimento(new MovimentoChaser());
                setComportamentoAtaque(new AtaqueMirado("fire.png"));
                break;
                
            case 2: // Fase Patrulha + Ataque em Cruz
                setComportamentoMovimento(new MovimentoCircular(5)); // Velocidade 5
                setComportamentoAtaque(new AtaqueEmCruz("bomba.png"));
                break;
                
            case 3: // Fase "Torreta" + Ataque em V
                setComportamentoMovimento(new MovimentoParado());
                setComportamentoAtaque(new AtaqueEmV("fire.png"));
                break;
                
            case 4: // Fase Caótica + Tiro Mirado
                setComportamentoMovimento(new MovimentoDiagonal(3)); // Velocidade 3 (rápido)
                setComportamentoAtaque(new AtaqueMirado("bomba.png"));
                break;
        }
    }

    /**
     * Sobrescreve o 'morrer()' de Personagem para implementar a lógica de vida.
     * Este método é chamado pelo 'ProjetilHeroi' quando ele acerta o Boss.
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
     */
    private void triggerFimDeFase() {
        // Pega a posição do Boss
        int linha = this.getPosicao().getLinha();
        int coluna = this.getPosicao().getColuna();
        
        // Cria o portal de saída
        Portal saida = new Portal("esfera.png", linha, coluna);
        
        // IMPORTANTE: Destino 0 (Lobby).
        // A Tela.proximaFase() vai ver que viemos da Fase 5
        // e nos mandar para a Fase 6 (Créditos) automaticamente.
        saida.setDestinoFase(0); 
        
        // Adiciona o portal e a mensagem ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(saida);
        Desenho.acessoATelaDoJogo().addPersonagem(
            new Mensagem("CHEFE DERROTADO!\n\nEntre no portal...", true)
        );
    }
}