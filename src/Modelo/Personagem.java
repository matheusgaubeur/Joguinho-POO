package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Comportamentos.Ataque.AtaqueNulo; // <<-- MUDANÇA
import Modelo.Comportamentos.Movimento.MovimentoParado; // <<-- MUDANÇA
import Modelo.Comportamentos.Movimento.ComportamentoMovimento; // <<-- MUDANÇA
import Modelo.Comportamentos.Ataque.ComportamentoAtaque; // <<-- MUDANÇA
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel;
    // <<-- MUDANÇA: Atributos de Estratégia
    private ComportamentoMovimento meuMovimento;
    private ComportamentoAtaque meuAtaque;

    protected Personagem(String sNomeImagePNG, int linha, int coluna) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = false;
        
        // <<-- MUDANÇA: Define o comportamento padrão (parado e sem ataque)
        // Classes filhas vão sobrescrever isso em seus construtores
        this.meuMovimento = new MovimentoParado();
        this.meuAtaque = new AtaqueNulo();
        
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        this.pPosicao.setPosicao(linha, coluna);
    }

    public Posicao getPosicao() {
        /*TODO: Retirar este método para que objetos externos nao possam operar
         diretamente sobre a posição do Personagem*/
        return pPosicao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }
    
    // <<-- MUDANÇA: Os "setters" para o Padrão Strategy
    public void setComportamentoMovimento(ComportamentoMovimento c) {
        this.meuMovimento = c;
    }
    
    public void setComportamentoAtaque(ComportamentoAtaque c) {
        this.meuAtaque = c;
    }
    
    // <<-- MUDANÇA: Renomeado de autoDesenho()
    public void desenhar(){
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
    
    // <<-- MUDANÇA: O método atualizar() agora DELEGA para as Estratégias
    /**
     * Atualiza a lógica do personagem delegando para seus comportamentos.
     */
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Delega a responsabilidade de mover para a Estratégia de Movimento
        meuMovimento.executar(this, faseAtual, hero);
        
        // Delega a responsabilidade de atacar para a Estratégia de Ataque
        meuAtaque.executar(this, faseAtual, hero);
    }

    // <<-- MUDANÇA IMPORTANTE:
    // Os métodos de movimento (moveUp, moveDown, etc.) PERMANECEM AQUI!
    // As Estratégias vão chamar "p.moveUp()". 
    // A violação de Liskov foi corrigida porque um personagem "Estatico"
    // (agora com MovimentoParado) NUNCA vai chamar estes métodos.
    
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }

    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }
    
    /**
     * Define o que acontece quando o Herói colide com este personagem.
     * @return Uma string de status para o ControleDeJogo (ex: "HERO_DIED")
     */
    public String aoColidirComHeroi() {
        return "GAME_RUNNING"; 
    }

    
}