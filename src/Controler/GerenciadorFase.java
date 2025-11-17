package Controler;

import Modelo.Fases.*;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorFase {
    
    private final Map<Integer, IFase> mapaDeConfiguracoesDeFase;
    
    public GerenciadorFase() {
        this.mapaDeConfiguracoesDeFase = new HashMap<>();
        
        // Registra todas as fases.
        this.mapaDeConfiguracoesDeFase.put(0, new Lobby());
        this.mapaDeConfiguracoesDeFase.put(1, new Fase1());
        this.mapaDeConfiguracoesDeFase.put(2, new Fase2());
        this.mapaDeConfiguracoesDeFase.put(3, new Fase3());
        this.mapaDeConfiguracoesDeFase.put(4, new Fase4());
        this.mapaDeConfiguracoesDeFase.put(5, new Fase5());
        this.mapaDeConfiguracoesDeFase.put(6, new CreditosFinais()); // Mensagem Final
    }

    public IFase getFase(int numeroFase) {
        // Pega a fase do mapa.
        // Se não encontrar (ex: nivel 7), retorna o Lobby
        return this.mapaDeConfiguracoesDeFase.getOrDefault(numeroFase, new Lobby());
    }
}