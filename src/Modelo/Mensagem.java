package Modelo;

import Auxiliar.Desenho;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

//Um personagem temporário que desenha uma mensagem na tela e depois se autodestrói.
public class Mensagem extends Personagem {
    private static final long serialVersionUID = 1L;
    
    private final String texto;
    private int timer = 0;
    // Duração da mensagem (em ticks. 30 ticks * 150ms = 4.5 segundos)
    private static final int DURACAO = 30; 
    private final boolean isBlocking;

    public Mensagem(String texto, boolean pausaOJogo) {
        super("blackTile.png", 0, 0); 
        this.texto = texto;
        this.bTransponivel = true;
        this.isBlocking = pausaOJogo;
    }
    
    // Helper para o Passo 5
    public boolean isBlocking() {
        return this.isBlocking;
    }
    
    // Helper para o Passo 5
    public String getTexto() {
        return this.texto;
    }
    
    @Override
    public void desenhar() {
        if (timer > DURACAO) {
            return; // Nao desenha nada se timer acabou.
        }

        // Pega o Graphics da Tela
        java.awt.Graphics g = Desenho.getGraphicsDaTela();
        
        g.setColor(new Color(0, 0, 0, 250)); 
        g.fillRect(50, 0, 450, 400);
        
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        
        int y = 230;
        for (String linha : texto.split("\n")) {
            g.drawString(linha, 70, y);
            y += 25; 
        }
    }
    
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        timer++;
        if (timer > DURACAO) {
            // Se esta era uma mensagem bloqueante, avisa a Tela para DESPAUSAR
            if (this.isBlocking) {
                Desenho.acessoATelaDoJogo().setGamePaused(false);
            }
            Desenho.acessoATelaDoJogo().removePersonagem(this);
        }
    }
}