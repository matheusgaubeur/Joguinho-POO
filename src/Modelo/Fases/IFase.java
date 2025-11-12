package Modelo.Fases;

import Modelo.Personagem;
import java.util.ArrayList;

/**
 * Interface (Contrato) Polimórfica para cada Fase.
 */
public interface IFase {
    
    // Carrega os personagens iniciais (Heroi, inimigos, etc)
    public ArrayList<Personagem> carregarPersonagensIniciais();

    // Retorna as barreiras/inimigos após a 1ª coleta
    public ArrayList<Personagem> getPersonagensColeta_1();
    
    // Retorna o "chefão" após a 2ª coleta
    public ArrayList<Personagem> getPersonagensColeta_2();

    // Retorna o Portal de saída após a 3ª coleta
    public Personagem getPersonagemColeta_3();

    // Retorna o tile de fundo da fase
    public String getBackgroundTile();
    
    // Retorna a skin do herói para esta fase
    public String getHeroSkin();
    
    /**
     * @return A mensagem de imersão para o início da fase (ou null se não houver).
     */
    public String getMensagemInicial();
}