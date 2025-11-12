package Controler;

import Modelo.*;
import Modelo.Fases.*; // Importa nosso novo pacote!
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Esta classe é uma "Fábrica" (Factory) de Fases.
 * Ela usa Polimorfismo (POO) para obter a configuração da fase correta,
 * eliminando a necessidade de um "switch case" imperativo.
 */
public class GerenciadorFase {
    
    private Map<Integer, IFase> mapaDeConfiguracoesDeFase;
    
    public GerenciadorFase() {
        this.mapaDeConfiguracoesDeFase = new HashMap<>();
        
        // Registra todas as nossas fases do esboço.
        // Agora que os arquivos "casca" existem, isso compila!
        this.mapaDeConfiguracoesDeFase.put(0, new Lobby());
        this.mapaDeConfiguracoesDeFase.put(1, new Fase1());
        this.mapaDeConfiguracoesDeFase.put(2, new Fase2());
        this.mapaDeConfiguracoesDeFase.put(3, new Fase3());
        this.mapaDeConfiguracoesDeFase.put(4, new Fase4());
        this.mapaDeConfiguracoesDeFase.put(5, new Fase5());
        this.mapaDeConfiguracoesDeFase.put(6, new CreditosFinais()); // Mensagem Final
    }

    /**
     * Obtém o objeto de configuração para um determinado número de fase.
     * @param numeroFase O número da fase (ex: 0, 1, 2...).
     * @return O objeto IFase correspondente.
     */
    public IFase getFase(int numeroFase) {
        // Pega a fase do mapa.
        // Se não encontrar (ex: nivel 7), retorna o Lobby
        return this.mapaDeConfiguracoesDeFase.getOrDefault(numeroFase, new Lobby());
    }
}