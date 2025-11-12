package Modelo;

/**
 * Um obstáculo simples e intransponível.
 * Usado para construir as paredes do Lobby e as barreiras de gelo da Fase 1.
 */
public class Parede extends Estatico {
    
    public Parede(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false; // NÃO PODE PASSAR POR CIMA
    }

    public void autoDesenho() {
        super.autoDesenho();
    }    
}