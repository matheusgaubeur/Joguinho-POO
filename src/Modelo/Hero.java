package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import Controler.ControleDeJogo;
import Controler.Tela;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Hero extends Personagem implements Serializable{
    private int numChaves = 0;
    private int nMunicao = 5; // Começa com 5 balas
    private int ultimaDirecao = 3; // 0=Cima, 1=Baixo, 2=Esquerda, 3=Direita (padrão)

    public Hero(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG,linha, coluna);
    }
    
    public void adicionarChave() {
        this.numChaves++;
        System.out.println("Chave coletada! Total: " + this.numChaves);
    }
    
    public boolean temChave() {
        return this.numChaves > 0;
    }
    
    public void usarChave() {
        if (temChave()) {
            this.numChaves--;
            System.out.println("Chave usada! Restantes: " + this.numChaves);
        }
    }
    
    // ADICIONE ESTES DOIS NOVOS MÉTODOS
    public void adicionarMunicao(int quantidade) {
        this.nMunicao += quantidade;
        System.out.println("Municaoo coletada! Total: " + this.nMunicao);
    }
    
    /**
     * Define a contagem de munição (usado ao trocar de fase).
     */
    public void setMunicao(int nMunicao) {
        this.nMunicao = nMunicao;
    }

    /**
     * Define a contagem de chaves (usado ao trocar de fase).
     */
    public void setChaves(int numChaves) {
        this.numChaves = numChaves;
    }
    
    public void atacar() {
        if (nMunicao <= 0) {
            System.out.println("Sem municao!");
            return; // Não pode atirar
        }
        
        this.nMunicao--;
        System.out.println("Tiro! Municao restante: " + this.nMunicao);
        
        // ESTA É A LINHA CORRIGIDA (Erro 2)
        Posicao p = new Posicao(this.getPosicao().getLinha(), this.getPosicao().getColuna());
        
        // Ajusta a posição de spawn do projétil
        if(ultimaDirecao == 0) p.moveUp();
        else if(ultimaDirecao == 1) p.moveDown();
        else if(ultimaDirecao == 2) p.moveLeft();
        else p.moveRight();
        
        // (Usando "fire.png" como placeholder por enquanto)
        ProjetilHeroi proj = new ProjetilHeroi("fire.png", p.getLinha(), p.getColuna(), this.ultimaDirecao);
        
        // Adiciona o projétil ao jogo (pela forma que o protótipo usa)
        Desenho.acessoATelaDoJogo().addPersonagem(proj);
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
    
    // MODIFICAR MÉTODOS DE MOVIMENTO
    @Override
    public boolean moveUp() {
        this.ultimaDirecao = 0; // <<-- ADICIONAR
        if(super.moveUp())
            return validaPosicao();
        return false;
    }

    @Override
    public boolean moveDown() {
        this.ultimaDirecao = 1; // <<-- ADICIONAR
        if(super.moveDown())
            return validaPosicao();
        return false;
    }

    @Override
    public boolean moveLeft() {
        this.ultimaDirecao = 2; // <<-- ADICIONAR
        if(super.moveLeft())
            return validaPosicao();
        return false;
    }   

    @Override
    public boolean moveRight() {
        this.ultimaDirecao = 3; // <<-- ADICIONAR
        if(super.moveRight())
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
    
    /**
     * Permite que o HUD leia a quantidade de chaves.
     * @return O número atual de chaves.
     */
    public int getNumChaves() {
        return this.numChaves;
    }
    
    /**
     * Permite que o HUD leia a quantidade de munição.
     * @return O número atual de munições.
     */
    public int getNumMunicao() {
        return this.nMunicao;
    }
}
