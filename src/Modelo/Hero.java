package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hero extends Personagem implements Serializable{
    public Hero(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG,linha, coluna);
    }

    public void voltaAUltimaPosicao(){
        this.pPosicao.volta();
    }
    
    
    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }

    /*TO-DO: este metodo pode ser interessante a todos os personagens que se movem*/
    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    
    public boolean moveUp() {
        if(super.moveUp())
            return validaPosicao();
        return false;
    }

    public boolean moveDown() {
        if(super.moveDown())
            return validaPosicao();
        return false;
    }

    public boolean moveRight() {
        if(super.moveRight())
            return validaPosicao();
        return false;
    }

    public boolean moveLeft() {
        if(super.moveLeft())
            return validaPosicao();
        return false;
    }    
    
    public void setSkin(String sNomeImagePNG) {
        try {
            // Esse código é copiado do construtor de Personagem.java
            iImage = new javax.swing.ImageIcon(new java.io.File(".").getCanonicalPath() + Auxiliar.Consts.PATH + sNomeImagePNG);
            java.awt.Image img = iImage.getImage();
            java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(Auxiliar.Consts.CELL_SIDE, Auxiliar.Consts.CELL_SIDE, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Auxiliar.Consts.CELL_SIDE, Auxiliar.Consts.CELL_SIDE, null);
            iImage = new javax.swing.ImageIcon(bi);
        } catch (java.io.IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
