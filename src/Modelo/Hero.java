package Modelo;

import Modelo.Comportamentos.Projeteis.ProjetilHeroi;
import Auxiliar.Desenho;
import Auxiliar.Posicao;
import java.io.Serializable;

public class Hero extends Personagem implements Serializable{
    private static final long serialVersionUID = 1L;
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
    
    public void adicionarMunicao(int quantidade) {
        this.nMunicao += quantidade;
        System.out.println("Municaoo coletada! Total: " + this.nMunicao);
    }
    

    // Define a contagem de munição (usado ao trocar de fase).
    public void setMunicao(int nMunicao) {
        this.nMunicao = nMunicao;
    }


    //Define a contagem de chaves (usado ao trocar de fase).
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
        
        Posicao p = new Posicao(this.getPosicao().getLinha(), this.getPosicao().getColuna());
        
        // Ajusta a posição de spawn do projétil
        switch (ultimaDirecao) {
            case 0 -> p.moveUp();
            case 1 -> p.moveDown();
            case 2 -> p.moveLeft();
            default -> p.moveRight();
        }
        
        ProjetilHeroi proj = new ProjetilHeroi("HeroiProjetil.png", p.getLinha(), p.getColuna(), this.ultimaDirecao);
        
        // Adiciona o projétil ao jogo
        Desenho.acessoATelaDoJogo().addPersonagem(proj);
    }
    
    @Override
    public boolean setPosicao(int linha, int coluna){
        if(this.pPosicao.setPosicao(linha, coluna)){
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }

    private boolean validaPosicao(){
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    
    @Override
    public boolean moveUp() {
        this.ultimaDirecao = 0;
        if(super.moveUp())
            return validaPosicao();
        return false;
    }

    @Override
    public boolean moveDown() {
        this.ultimaDirecao = 1;
        if(super.moveDown())
            return validaPosicao();
        return false;
    }

    @Override
    public boolean moveLeft() {
        this.ultimaDirecao = 2;
        if(super.moveLeft())
            return validaPosicao();
        return false;
    }   

    @Override
    public boolean moveRight() {
        this.ultimaDirecao = 3;
        if(super.moveRight())
            return validaPosicao();
        return false;
    }
    
    public void setSkin(String sNomeImagePNG) {
        try {
            // Código copiado do construtor de Personagem.java
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
    
    //Permite que o HUD leia a quantidade de chaves.
    public int getNumChaves() {
        return this.numChaves;
    }
    
    //Permite que o HUD leia a quantidade de munição.
    public int getNumMunicao() {
        return this.nMunicao;
    }
}
