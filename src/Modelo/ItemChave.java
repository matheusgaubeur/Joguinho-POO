package Modelo;

/**
 * Representa um dos 3 itens colecionáveis da fase.
 * É estático e implementa Coletavel.
 */
public class ItemChave extends Estatico implements Coletavel {
    
    public ItemChave(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode ser coletado
    }

    public void autoDesenho() {
        super.autoDesenho();
    }    
}