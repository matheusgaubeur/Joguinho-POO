package Modelo;

// Implementa a interface 'Coletavel' que já criamos
public class Municao extends Personagem implements Coletavel {

    public Municao(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = true; // Pode passar por cima para coletar
    }
    
    // Sobrescreve o método abstrato de Personagem (mesmo sendo estático)
    @Override
    public void atualizar(java.util.ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }
    
    @Override
    public String aoColidirComHeroi(Hero h) {
        // Avisa o ControleDeJogo que a munição foi coletada
        return "MUNICAO_COLETADA"; 
    }
}