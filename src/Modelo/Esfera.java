package Modelo;

import java.util.ArrayList; // <<-- MUDANÇA: Import necessário
import Modelo.Hero;       // <<-- MUDANÇA: Import necessário
import Auxiliar.Desenho;

/**
 *
 * @author Jose F Rodrigues-Jr
 */
public class Esfera extends Personagem{
    public Esfera(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
        this.bTransponivel = false;
    }

    // <<-- MUDANÇA: Renomeado de autoDesenho() para desenhar()
    @Override
    public void desenhar() {
        super.desenhar();
    }    
    
    // <<-- MUDANÇA: Implementação obrigatória do método abstrato
    @Override
    public void atualizar(ArrayList<Personagem> faseAtual, Hero hero) {
        // Estático, não faz nada
    }
}
