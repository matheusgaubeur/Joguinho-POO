package Modelo;

import Auxiliar.Desenho;
import java.awt.Color;
import java.awt.Font;

/**
 * Um personagem temporário que desenha uma mensagem na tela e
 * depois se autodestrói.
 */
public class Mensagem extends Estatico {
    
    private String texto;
    private int timer = 0;
    // Duração da mensagem (em ticks. 30 ticks * 150ms = 4.5 segundos)
    private static final int DURACAO = 30; 

    public Mensagem(String texto) {
        // Usa uma imagem "invisível" (blackTile) e fica em (0,0)
        super("blackTile.png", 0, 0); 
        this.texto = texto;
        this.bTransponivel = true;
    }

    @Override
    public void autoDesenho() {
        // NÃO chamamos super.autoDesenho() porque não queremos desenhar a blackTile
        
        timer++;
        if (timer > DURACAO) {
            Desenho.acessoATelaDoJogo().removePersonagem(this);
            return;
        }

        // Pega o Graphics da Tela
        java.awt.Graphics g = Desenho.getGraphicsDaTela();
        
        // Desenha um fundo semi-transparente
        g.setColor(new Color(0, 0, 0, 250)); // Preto com 200 de transparência
        g.fillRect(50, 0, 450, 400); // (x, y, largura, altura)
        
        // Desenha o texto
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.setColor(Color.WHITE);
        
        // Lógica simples para quebrar a linha (se tiver \n)
        int y = 230;
        for (String linha : texto.split("\n")) {
            g.drawString(linha, 70, y);
            y += 25; // Próxima linha
        }
    }
}