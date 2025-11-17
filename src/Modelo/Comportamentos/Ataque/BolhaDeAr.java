package Modelo.Comportamentos.Ataque;

import Modelo.Comportamentos.Projeteis.ProjetilCima;
import java.io.Serializable;

public class BolhaDeAr extends ProjetilCima implements Serializable { 
    private static final long serialVersionUID = 1L;
            
    public BolhaDeAr(String sNomeImagePNG, int linha, int coluna) {
        super(sNomeImagePNG, linha, coluna);
    }
}