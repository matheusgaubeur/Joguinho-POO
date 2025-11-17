package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.Comportamentos.Ataque.AtaqueNulo;
import Modelo.Comportamentos.Movimento.MovimentoParado;
import Modelo.Comportamentos.Movimento.ComportamentoMovimento;
import Modelo.Comportamentos.Ataque.ComportamentoAtaque;
import Auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public abstract class Personagem implements Serializable {
    private static final long serialVersionUID = 1L;
    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel;
    private ComportamentoMovimento meuMovimento;
    private ComportamentoAtaque meuAtaque;
    protected boolean bEstaVivo = true;
    protected Posicao pPosicaoAnterior;

    protected Personagem(String sNomeImagePNG, int linha, int coluna) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = false;
        this.pPosicaoAnterior = new Posicao(linha, coluna);
        
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
        return pPosicao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }
    
    public void voltaAUltimaPosicao() {
        this.pPosicao.setPosicao(pPosicaoAnterior.getLinha(), pPosicaoAnterior.getColuna());
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }
    
    public void setComportamentoMovimento(ComportamentoMovimento c) {
        this.meuMovimento = c;
    }
    
    public void setComportamentoAtaque(ComportamentoAtaque c) {
        this.meuAtaque = c;
    }
    
    public boolean isVivo() {
        return bEstaVivo;
    }

    public void morrer() {
        this.bEstaVivo = false;
    } 

    public void desenhar(){
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());
    }
    
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Delega a responsabilidade de mover para a Estratégia de Movimento
        meuMovimento.executar(this, faseAtual, hero);
        
        // Delega a responsabilidade de atacar para a Estratégia de Ataque
        meuAtaque.executar(this, faseAtual, hero);
    }
    
    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    public boolean moveUp() {
        // Salva a posição ATUAL em pPosicaoAnterior
        this.pPosicaoAnterior.setPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna());
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        // Salva a posição ATUAL em pPosicaoAnterior
        this.pPosicaoAnterior.setPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna());
        return this.pPosicao.moveDown();
    }

    public boolean moveLeft() {
        // Salva a posição ATUAL em pPosicaoAnterior
        this.pPosicaoAnterior.setPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna());
        return this.pPosicao.moveLeft();
    }

    public boolean moveRight() {
        // Salva a posição ATUAL em pPosicaoAnterior
        this.pPosicaoAnterior.setPosicao(this.pPosicao.getLinha(), this.pPosicao.getColuna());
        return this.pPosicao.moveRight();
    }
    
    //Define o que acontece quando o Herói colide com este personagem.
    public String aoColidirComHeroi(Hero h) {
        return "GAME_RUNNING"; 
    }  
}