package Controler;

// IMPORTS DE MODELO (Apenas os utilizados)
import Modelo.Personagem;
import Modelo.Hero;
import Modelo.Fases.IFase;
import Modelo.Mensagem;
import Modelo.Parede;
import Modelo.Bau;
import Modelo.Chave;
import Modelo.Portal;
import Modelo.Artefato;
import Modelo.Fases.Lobby;  

// IMPORTS DE AUXILIARES
import Auxiliar.Consts;
import Auxiliar.Posicao; 

// IMPORTS DE UTILIDADES JAVA E SWING/AWT
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.SwingUtilities;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener, DropTargetListener {
    
    private GerenciadorFase gFase;
    private IFase configFaseAtual;
    private int vidas;
    private int pontuacao;
    private int itensColetados;
    private String backgroundTile;
    private ArrayList<Personagem> faseAtual;
    private final ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private final Set<Integer> teclasTap = new HashSet<>();
    private javax.swing.ImageIcon iCoracaoHUD;
    private final Set<Integer> fasesConcluidas; // FINAL: Apenas o conteúdo pode ser alterado
    private boolean isGamePaused = false;
    private int idFaseAtual = -1;
    private boolean isControlPressed = false;
    private File arquivoPincel = null;
    private int heroMoveCooldown = 0;
    private static final int HERO_MOVE_DELAY = 1;
    
    public Tela() {
        initComponents();
        
        // As configurações que usam 'this' foram movidas para Main.java.
        
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        // Lógica de Inicialização do Jogo
        this.gFase = new GerenciadorFase();
        this.vidas = 10;
        this.pontuacao = 0;
        this.fasesConcluidas = new HashSet<>();
        
        this.faseAtual = new ArrayList<>();
        this.iniciarFase(this.idFaseAtual);
        
        // Carrega a imagem do coração para o HUD
        try {
            iCoracaoHUD = new javax.swing.ImageIcon(new File(".").getCanonicalPath() + Consts.PATH + "ZVida.png");
            int tamanhoIcone = Consts.CELL_SIDE / 2; 
            
            Image img = iCoracaoHUD.getImage();
            BufferedImage bi = new BufferedImage(tamanhoIcone, tamanhoIcone, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, tamanhoIcone, tamanhoIcone, null);
            iCoracaoHUD = new javax.swing.ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println("Erro ao carregar imagem do HUD: " + ex.getMessage());
        }

    }
    
    /** Centraliza a lógica de carregar, reiniciar ou trocar de fase. */
    private void iniciarFase(int idFase) {
        
        // 1. Cache do estado do Herói
        Hero heroiAntigo = getHero();
        int municaoCache = 5;
        int chavesCache = 0;

        if (heroiAntigo != null) {
            municaoCache = heroiAntigo.getNumMunicao();
            chavesCache = heroiAntigo.getNumChaves();
        }
        
        // Limpa a fase antiga e input
        this.faseAtual.clear();
        this.teclasPressionadas.clear();

        // Salva o histórico de IDs
        int idFaseAnterior = this.idFaseAtual;
        this.idFaseAtual = idFase;

        // Carrega a nova configuração de fase
        this.configFaseAtual = gFase.getFase(this.idFaseAtual);
        
        // Lógica de Lobby para atualizar fases concluídas
        if (this.configFaseAtual instanceof Lobby lobby) {
            lobby.atualizarFasesConcluidas(this.fasesConcluidas);
        }
        
        if (this.configFaseAtual == null) {
            System.err.println("ERRO: Tentando carregar fase inexistente ID: " + this.idFaseAtual);
            return;
        }

        // Carrega os personagens da nova fase
        this.faseAtual.addAll(this.configFaseAtual.carregarPersonagensIniciais());

        // 2. Injeta o estado salvo no novo Herói
        Hero heroiNovo = getHero();
        if (heroiNovo != null) {
            heroiNovo.setMunicao(municaoCache);
            heroiNovo.setChaves(chavesCache);
        }
        
        // Define o background
        this.backgroundTile = this.configFaseAtual.getBackgroundTile();
        
        

        // Lógica de Mensagem Inicial/Vitória
        String msgParaMostrar = null;

        // Caso 1: Voltando ao Lobby após vitória
        if (this.idFaseAtual == 0 && idFaseAnterior > 0) {
            IFase configFaseAntiga = gFase.getFase(idFaseAnterior);
            if(configFaseAntiga != null){
                msgParaMostrar = configFaseAntiga.getMensagemVitoria();
            }
        } 
        // Caso 2: Mensagem inicial da fase
        else {
            msgParaMostrar = this.configFaseAtual.getMensagemInicial();
        }

        // Reseta a pontuação de coleta
        this.itensColetados = 0;
        
        if (msgParaMostrar != null && !msgParaMostrar.isEmpty()) {
            this.addPersonagem(new Mensagem(msgParaMostrar, true));
        }
    }
    
    public Hero getHero() {
        if (this.faseAtual.isEmpty() || !(this.faseAtual.get(0) instanceof Hero)) {
            return null; 
        }
        return (Hero) this.faseAtual.get(0);
    }
    
    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }

    public boolean ehPosicaoValida(Posicao p) {
        return cj.ehPosicaoValida(this.faseAtual, p);
    }

    public void addPersonagem(Personagem umPersonagem) {
        // Lógica do Porteiro para Mensagem (Pause)
        if (umPersonagem instanceof Mensagem novaMsg) {
            if (this.isGamePaused) {
                System.out.println("WARN: Jogo pausado, mensagem nova ignorada: " + novaMsg.getTexto());
                return;
            }
            if (novaMsg.isBlocking()) {
                this.setGamePaused(true);
            }
        }
        faseAtual.add(umPersonagem);
    }

    public void removePersonagem(Personagem umPersonagem) {
        faseAtual.remove(umPersonagem);
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    @Override
    public void paint(Graphics gOld) {
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        g2 = g.create(getInsets().left, getInsets().top, getWidth() - getInsets().right, getHeight() - getInsets().top);
        
        // Desenha cenário de fundo
        if (this.backgroundTile != null) {
            try {
                Image newImage = Toolkit.getDefaultToolkit().getImage(
                        new File(".").getCanonicalPath() + Consts.PATH + this.backgroundTile);
                
                for (int i = 0; i < Consts.RES; i++) {
                    for (int j = 0; j < Consts.RES; j++) {
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                                Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, "Erro ao carregar tile de fundo.", ex);
            }
        }
        
        // --- LÓGICA DE JOGO ---
        if (!this.faseAtual.isEmpty()) {

            // PASSO 1: ATUALIZA LÓGICA
            this.cj.atualizarTudo(faseAtual, getHero(), this.isGamePaused);
            
            // PASSO 2: PROCESSA COLISÕES (Só roda se NÃO pausado)
            if (!this.isGamePaused) {
                
                processarMovimentoHeroi();
                
                String status = this.cj.processaTudo(faseAtual);
                
                // PASSO 3: AGIR DE ACORDO COM O STATUS
                switch (status) {
                    case "HERO_DIED" -> {
                        this.vidas--;
                        this.faseAtual.clear(); 
                        this.teclasPressionadas.clear();

                        if (this.vidas <= 0) {
                            // Lógica de Game Over
                            System.out.println("GAME OVER!");
                            this.vidas = 10;
                            this.pontuacao = 0;
                            this.fasesConcluidas.clear();
                            this.itensColetados = 0;
                            this.idFaseAtual = 0;
                            this.configFaseAtual = gFase.getFase(this.idFaseAtual);
                            if(this.configFaseAtual != null) { 
                                this.backgroundTile = this.configFaseAtual.getBackgroundTile();
                                this.faseAtual.addAll(this.configFaseAtual.carregarPersonagensIniciais());
                            }
                            Hero heroiNovoLobby = getHero();
                            if (heroiNovoLobby != null) {
                                heroiNovoLobby.setMunicao(5);
                                heroiNovoLobby.setChaves(0);
                            }
                            addPersonagem(new Mensagem("GAME OVER", true)); 
                        } else {
                            // Lógica de Perder Vida (Recarregar fase)
                            System.out.println("Voce morreu! Vidas restantes: " + this.vidas);
                            this.itensColetados = 0;
                            this.configFaseAtual = gFase.getFase(this.idFaseAtual);
                            if(this.configFaseAtual != null) { 
                                this.backgroundTile = this.configFaseAtual.getBackgroundTile();
                                this.faseAtual.addAll(this.configFaseAtual.carregarPersonagensIniciais());
                            }
                            Hero heroiNovoFase = getHero();
                            if (heroiNovoFase != null) {
                                heroiNovoFase.setMunicao(5);
                                heroiNovoFase.setChaves(0);
                            }
                            String msg = "Você morreu!\nVidas restantes: " + this.vidas;
                            addPersonagem(new Mensagem(msg, true)); 
                        }
                    }
                    case "ITEM_COLETADO" -> this.processarColeta();
                    case "REJEITADO" -> getHero().voltaAUltimaPosicao();
                        
                    case "CHAVE_COLETADA" -> {
                        getHero().adicionarChave();
                        this.pontuacao += 10;
                    }
                    case "BAU_ABERTO" -> processarAberturaBau();
                        
                    case "MUNICAO_COLETADA" -> {
                        getHero().adicionarMunicao(5);
                        this.pontuacao += 5;
                    }
                    case "PORTAL_FASE_0" -> this.iniciarFase(0);
                    case "PORTAL_FASE_1" -> this.iniciarFase(1);
                    case "PORTAL_FASE_2" -> this.iniciarFase(2);
                    case "PORTAL_FASE_3" -> this.iniciarFase(3);
                    case "PORTAL_FASE_4" -> this.iniciarFase(4);
                    case "PORTAL_FASE_5" -> this.iniciarFase(5);
                    case "PORTAL_FASE_6" -> this.iniciarFase(6);
                        
                    case "FASE_CONCLUIDA" -> this.proximaFase();
                    case "GAME_RUNNING" -> {}
                }
            }
            
            // PASSO 4: DESENHAR TUDO
            this.cj.desenharTudo(faseAtual);
            
            if (!this.isGamePaused) {
                // Remoção segura de personagens que não estão mais vivos
                for (int i = faseAtual.size() - 1; i >= 0; i--) {
                    Personagem p = faseAtual.get(i);
                    if (!p.isVivo()) {
                        faseAtual.remove(i);
                    }
                }
            }
        }  

        // Desenho do HUD (Vidas, Pontuação, Chaves e Munição)
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        g2.setColor(java.awt.Color.WHITE);
        
        // Linha 1: Pontuação
        g2.drawString("Pontos: " + this.pontuacao, 430, 25);

        // Linha 1: Vidas
        if (iCoracaoHUD != null) {
            int tamanhoIcone = iCoracaoHUD.getIconWidth();
            int espacamento = 5;
            for (int i = 0; i < this.vidas; i++) {
                iCoracaoHUD.paintIcon(this, g2, 10 + (i * (tamanhoIcone + espacamento)), 5);
            }
        }
        
        // Linha 2: Chaves e Munição
        Hero h = getHero();
        if (h != null) {
            int yLinha2 = 55;
            g2.drawString("Chaves: " + h.getNumChaves(), 10, yLinha2);
            g2.drawString("Munição: " + h.getNumMunicao(), 430, yLinha2);
        }
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    /** Implementa a lógica de coleta de 3 itens para progressão de fase. */
    private void processarColeta() {
        this.pontuacao += 50;
        this.itensColetados++;
        System.out.println("Item coletado! Total: " + this.itensColetados);

        switch (this.itensColetados) {
            case 1 -> this.faseAtual.addAll(this.configFaseAtual.getPersonagensColeta_1());
            case 2 -> this.faseAtual.addAll(this.configFaseAtual.getPersonagensColeta_2());
            case 3 -> this.faseAtual.add(this.configFaseAtual.getPersonagemColeta_3());
            default -> {}
        }
    }

    private void atualizaCamera() {
        int linha = getHero().getPosicao().getLinha();
        int coluna = getHero().getPosicao().getColuna();

        cameraLinha = Math.max(0, Math.min(linha - Consts.RES / 2, Consts.MUNDO_ALTURA - Consts.RES));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.RES / 2, Consts.MUNDO_LARGURA - Consts.RES));
    }

    public void go() {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if (this.isGamePaused) {
            return;
        }
        
        try {
            // É usado no MODO CONSTRUTOR para faciliar colocar o mesmo elemento repetidas vezes
            if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
                isControlPressed = true;
            }
            if (teclasPressionadas.contains(e.getKeyCode()))
                    return;

            boolean isMoveKey = (e.getKeyCode() == KeyEvent.VK_UP ||
                                 e.getKeyCode() == KeyEvent.VK_DOWN ||
                                 e.getKeyCode() == KeyEvent.VK_LEFT ||
                                 e.getKeyCode() == KeyEvent.VK_RIGHT);

            if (isMoveKey) {
                teclasPressionadas.add(e.getKeyCode());
                teclasTap.add(e.getKeyCode());
            }
            // Entrar no MODO CONSTRUTOR
            else if (e.getKeyCode() == KeyEvent.VK_T) {
                this.faseAtual.clear();
                ArrayList<Personagem> novaFase = new ArrayList<>();

                /*Cria faseAtual adiciona personagens*/
                Hero novoHeroi = new Hero("Heroi.png", 10, 10);
                novaFase.add(novoHeroi);
                this.atualizaCamera();

                faseAtual = novaFase;
            }
            else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                getHero().atacar();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                // Salva o estado do jogo
                SaveState save = new SaveState();
                save.faseAtual = this.faseAtual;
                save.idFaseAtual = this.idFaseAtual;
                save.vidas = this.vidas;
                save.pontuacao = this.pontuacao;
                save.itensColetados = this.itensColetados;
                save.fasesConcluidas = this.fasesConcluidas;
                save.backgroundTile = this.backgroundTile;
                save.municao = getHero().getNumMunicao();

                try (FileOutputStream fos = new FileOutputStream("POO.dat");
                     ObjectOutputStream serializador = new ObjectOutputStream(fos)) {
                    serializador.writeObject(save);
                    System.out.println("Jogo Salvo!");
                } catch (IOException ex) {
                    System.err.println("Erro ao salvar: " + ex.getMessage());
                }
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                // Carrega o estado do jogo
                try (FileInputStream fis = new FileInputStream("POO.dat");
                     ObjectInputStream serializador = new ObjectInputStream(fis)) {
                    
                    SaveState save = (SaveState) serializador.readObject();

                    this.faseAtual = save.faseAtual;
                    this.idFaseAtual = save.idFaseAtual;
                    this.vidas = save.vidas;
                    this.pontuacao = save.pontuacao;
                    this.itensColetados = save.itensColetados;
                    
               
                    this.fasesConcluidas.clear();
                    this.fasesConcluidas.addAll(save.fasesConcluidas);
                    
                    this.backgroundTile = save.backgroundTile;

                    this.configFaseAtual = gFase.getFase(this.idFaseAtual);
                    
                    getHero().setMunicao(save.municao);
                    
                    System.out.println("Jogo Carregado!");
                    
                } catch (Exception ex) {
                    System.err.println("Erro ao carregar: " + ex.getMessage());
                }
            // Salva elemento .gz para fazer Drag-and-Drop
            } 
            // Comandos do Editor de Mapas (Pincéis)
            else if (e.getKeyCode() == KeyEvent.VK_1) {
                System.out.println("Parede Pantano Adicionado em:");
                salvarPincelGZ(new Parede("PantanoParede2.png", 0, 0), "PantanoParede");
            }
            else if (e.getKeyCode() == KeyEvent.VK_2) {
                System.out.println("Parede Caverna Adicionado em:");
                salvarPincelGZ(new Parede("CavernaParede.png", 0, 0), "CavernaParede");
            }
            else if (e.getKeyCode() == KeyEvent.VK_3) {
                System.out.println("Personagem Foguinho Adicionado em:");
                salvarPincelGZ(new Hero("Heroi.png", 0, 0), "Heroi");
            }
            else if (e.getKeyCode() == KeyEvent.VK_4) {
                System.out.println("Personagem Bau Adicionado em:");
                salvarPincelGZ(new Bau("GeloMorsa.png", 0, 0), "pincel_caveira");
            }
            else if (e.getKeyCode() == KeyEvent.VK_5) {
                System.out.println("Personagem ArtefatoFogo Adicionado em:");
                salvarPincelGZ(new Artefato("GeloArtefato.png", 0, 0), "pincel_itemchave");
            }
            else if (e.getKeyCode() == KeyEvent.VK_6) {
                Portal p = new Portal("ZPorta.png", 0, 0);
                System.out.println("Personagem Portal Adicionado em:");
                p.setDestinoFase(1);
                salvarPincelGZ(p, "pincel_portal_fase1");
            }
            else if (e.getKeyCode() == KeyEvent.VK_7) {
                System.out.println("Personagem Chave Adicionado em:");
                salvarPincelGZ(new Chave("ZChave.png", 0, 0), "pincel_chave");
            }
            else if (e.getKeyCode() == KeyEvent.VK_8) {
                System.out.println("Personagem Bau Adicionado em:");
                salvarPincelGZ(new Bau("ZBAu.png", 0, 0), "pincel_bau");
            }
            else if (e.getKeyCode() == KeyEvent.VK_C) {
                // 'C' de Converter
                System.out.println("Convertendo 'POO.dat' para código Java...");
                this.converterSaveParaCodigo();
            }
        } catch (Exception ee) {
            // Ignorado
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            isControlPressed = false;
            arquivoPincel = null;
        }
        teclasPressionadas.remove(e.getKeyCode());        
    }    

    @Override
    public void mousePressed(MouseEvent e) {
        if (this.isGamePaused) {
            return;
        }
        
        // Se Botão Esquerdo + CTRL, usa o Pincel (Editor)
        if (SwingUtilities.isLeftMouseButton(e) && isControlPressed && arquivoPincel != null) {
            Point clickPoint = e.getPoint(); 
            adicionarPersonagemDoArquivo(this.arquivoPincel, clickPoint);
            repaint();
            return;
        }    
        
        // Calcula a posição no GRID
        int x = e.getX();
        int y = e.getY();
        int dropLinha = (y / Consts.CELL_SIDE) + getCameraLinha();
        int dropColuna = (x / Consts.CELL_SIDE) + getCameraColuna();

        // BOTÃO DIREITO: Remover Personagem (Borracha do Editor)
        if (SwingUtilities.isRightMouseButton(e)) {
            for (int i = faseAtual.size() - 1; i >= 0; i--) {
                Personagem p = faseAtual.get(i);
                if (p.getPosicao().getLinha() == dropLinha && p.getPosicao().getColuna() == dropColuna) {
                    if (i == 0) {
                        System.out.println("Editor: Nao e possivel remover o Heroi!");
                        continue;
                    }
                    faseAtual.remove(i);
                    System.out.println("Editor: Item removido em " + dropLinha + "," + dropColuna);
                    repaint();
                    return;
                }
            }
        } 
        
        // BOTÃO ESQUERDO: Teleportar Herói
        else if (SwingUtilities.isLeftMouseButton(e)) {
            this.setTitle("X: " + x + ", Y: " + y
                    + " -> Celula: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));
            
            this.getHero().getPosicao().setPosicao(dropLinha, dropColuna);
            
            repaint();
        }
    }
    
    /** Controla a lógica de "Sorte ou Azar" do baú de bônus. */
    private void processarAberturaBau() {
        Random rand = new Random();
        int sorte = rand.nextInt(100);

        if (sorte < 20) { // 20% de chance: Munição
            getHero().adicionarMunicao(10);
            addPersonagem(new Mensagem("Maravilha!\nVocê ganhou 10 munições!", true));
        } else if (sorte < 60) { // 40% de chance: Vida Extra
            this.vidas++;
            addPersonagem(new Mensagem("Uma Poção Mágica!\nVocê recuperou uma vida!", true));
        } else if (sorte < 90) { // 30% de chance: Pontos
            this.pontuacao += 200;
            addPersonagem(new Mensagem("Um Tesouro!\n+200 Pontos!", true));
        } else { // 10% de chance: Nada
            addPersonagem(new Mensagem("O baú estava vazio...\n...exceto por esta aranha\ninofensiva. Que chato.", true));
        }
    }
    
    public boolean isGamePaused() {
        return this.isGamePaused;
    }

    public void setGamePaused(boolean paused) {
        this.isGamePaused = paused;
    }
    
    public void reiniciarFase() {
        System.out.println("Voce morreu! Vidas restantes: " + this.vidas);
        this.iniciarFase(this.idFaseAtual);
    }
    
    public void proximaFase() {
        this.pontuacao += 100;
        System.out.println("Passou de fase! Pontos: " + this.pontuacao);
        
        if (this.idFaseAtual >= 1 && this.idFaseAtual <= 4) {
            this.fasesConcluidas.add(this.idFaseAtual);
            System.out.println("Fases Concluidas: " + this.fasesConcluidas.toString());
            this.iniciarFase(0);
        } else {
            this.iniciarFase(0);
        }
    }
    
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }
    
// Implementações de Interface (Deixadas vazias, mas necessárias)
    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void keyTyped(KeyEvent e) { }

    /** Classe interna para Serialização do estado do jogo (Save/Load). */
    public static class SaveState implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        public ArrayList<Personagem> faseAtual;
        public int idFaseAtual;
        public int vidas;
        public int pontuacao;
        public int itensColetados;
        public Set<Integer> fasesConcluidas;
        public String backgroundTile;
        public int municao;
    }
    
    
    // MÉTODOS DE DRAG-AND-DROP (EDITOR DE MAPAS)

    @Override
    public void drop(DropTargetDropEvent e) {
        try {
            e.acceptDrop(DnDConstants.ACTION_COPY);
            Transferable transferable = e.getTransferable();
            
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                Point dropPoint = e.getLocation();
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                for (File file : files) {
                    System.out.println("Arquivo solto: " + file.getName());
                    
                    if (isControlPressed) {
                        this.arquivoPincel = file;
                        System.out.println("Modo Pincel ATIVADO com: " + file.getName());
                    }
                    
                    if (file.getName().toLowerCase().endsWith(".gz")) {
                        adicionarPersonagemDoArquivo(file, dropPoint);
                    }
                }
            }
            e.dropComplete(true);
        } catch (UnsupportedFlavorException | IOException ex) {
            System.err.println("Erro no Drop: " + ex.getMessage());
            e.dropComplete(false);
        }
    }

    private void adicionarPersonagemDoArquivo(File file, Point dropPoint) {
        try (FileInputStream fis = new FileInputStream(file);
             GZIPInputStream gzis = new GZIPInputStream(fis);
             ObjectInputStream ois = new ObjectInputStream(gzis)) {

            Personagem pNovo = (Personagem) ois.readObject();

            int dropLinha = (dropPoint.y / Consts.CELL_SIDE) + getCameraLinha();
            int dropColuna = (dropPoint.x / Consts.CELL_SIDE) + getCameraColuna();

            pNovo.setPosicao(dropLinha, dropColuna);
            
            this.faseAtual.add(pNovo);
            System.out.println("Adicionado: " + pNovo.getClass().getSimpleName() + " em " + dropLinha + "," + dropColuna);

        } catch (Exception ex) {
            System.err.println("Falha ao ler objeto do arquivo: " + ex.getMessage());
        }
    }

    // Métodos obrigatórios de DropTargetListener
    @Override public void dragEnter(DropTargetDragEvent e) {
        if (e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            e.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            e.rejectDrag();
        }
    }
    @Override public void dragOver(DropTargetDragEvent e) { }
    @Override public void dropActionChanged(DropTargetDragEvent e) { }
    @Override public void dragExit(DropTargetEvent e) { }
    
    // FIM DOS MÉTODOS DE DRAG-AND-DROP
    
    /** Método auxiliar para criar um "pincel" serializado .gz de um personagem (Editor). */
    private void salvarPincelGZ(Personagem p, String nomeArquivo) {
        try (FileOutputStream fos = new FileOutputStream(nomeArquivo + ".gz");
             GZIPOutputStream gzos = new GZIPOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(gzos)) {
            
            oos.writeObject(p);
            System.out.println(">>> PINCEL CRIADO: " + nomeArquivo + ".gz");

        } catch (Exception ex) {
            System.err.println("Erro ao criar pincel: " + ex.getMessage());
        }
    }
    
    /** Processa o input de movimento do Herói com um cooldown (chamado a cada "tick"). */
    private void processarMovimentoHeroi() {
        boolean moveu = false;

        // 1. Prioridade Alta: Processar "Taps" (clique rápido)
        if (!teclasTap.isEmpty()) {
            if (teclasTap.contains(KeyEvent.VK_UP)) { getHero().moveUp(); moveu = true; } 
            else if (teclasTap.contains(KeyEvent.VK_DOWN)) { getHero().moveDown(); moveu = true; } 
            else if (teclasTap.contains(KeyEvent.VK_LEFT)) { getHero().moveLeft(); moveu = true; } 
            else if (teclasTap.contains(KeyEvent.VK_RIGHT)) { getHero().moveRight(); moveu = true; }
            teclasTap.clear();
        }

        // 2. Prioridade Baixa: Processar "Holds" (segurar) com cooldown
        else if (this.heroMoveCooldown == 0) {
            if (teclasPressionadas.contains(KeyEvent.VK_UP)) { getHero().moveUp(); moveu = true; } 
            else if (teclasPressionadas.contains(KeyEvent.VK_DOWN)) { getHero().moveDown(); moveu = true; } 
            else if (teclasPressionadas.contains(KeyEvent.VK_LEFT)) { getHero().moveLeft(); moveu = true; } 
            else if (teclasPressionadas.contains(KeyEvent.VK_RIGHT)) { getHero().moveRight(); moveu = true; }
        }
        
        // 3. Lógica de Cooldown
        if (moveu) {
            this.heroMoveCooldown = HERO_MOVE_DELAY;
            this.atualizaCamera();
            this.setTitle("-> Celula: " + (getHero().getPosicao().getLinha()) + ", " + (getHero().getPosicao().getColuna()));
        } 
        else if (this.heroMoveCooldown > 0) {
            this.heroMoveCooldown--;
        }
    }
    
    private void converterSaveParaCodigo() {
        // 1. Carrega o ArrayList<Personagem> do arquivo de save
        ArrayList<Personagem> faseSalva;
        try (FileInputStream fis = new FileInputStream("POO.dat");
            ObjectInputStream serializador = new ObjectInputStream(fis)) {
            
            SaveState save = (SaveState) serializador.readObject();
            faseSalva = save.faseAtual;
            
        } catch (Exception ex) {
            System.err.println("Erro ao carregar 'POO.dat' para conversão: " + ex.getMessage());
            return;
        }

        if (faseSalva == null || faseSalva.isEmpty()) {
            System.err.println("Arquivo 'POO.dat' está vazio ou corrupto.");
            return;
        }

        // 2. Prepara para imprimir o código
        System.out.println("=========================================================");
        System.out.println("// --- INÍCIO DO CÓDIGO DA FASE (Copie e cole isso) ---");
        System.out.println("ArrayList<Personagem> fase = new ArrayList<>();");
        System.out.println("");
        
        // Trata o Herói (Índice 0)
        Personagem hero = faseSalva.get(0);
        System.out.println("// 1. Adiciona o Herói (posição inicial)");
        System.out.println("fase.add('Heroi.png', " 
                + hero.getPosicao().getLinha() + ", " 
                + hero.getPosicao().getColuna() + "));");
        System.out.println("");
        System.out.println("// 2. Adiciona os outros elementos da fase");

        // 3. Itera e identifica cada Personagem (pulando o Herói no índice 0)
        for (int i = 1; i < faseSalva.size(); i++) {
            Personagem p = faseSalva.get(i);
            int L = p.getPosicao().getLinha(); // Linha
            int C = p.getPosicao().getColuna(); // Coluna
            
            String linhaCodigo = "// Classe desconhecida: " + p.getClass().getSimpleName();
            
            if (p instanceof Parede) {
                linhaCodigo = "fase.add(new Parede(\"FogoParede.png\", " + L + ", " + C + ")); // ou FogoPiso1.png";
            }
            else if (p instanceof Bau) {
                linhaCodigo = "fase.add(new Bau(\"ZBau.png\", " + L + ", " + C + "));";
            }
            else if (p instanceof Artefato) {
                linhaCodigo = "fase.add(new ItemChave(\"FogoArtefato.png\", " + L + ", " + C + "));";
            }
            else if (p instanceof Portal p_portal) { // Usa 'pattern matching'
                linhaCodigo = "Portal p" + i + " = new Portal(\"ZPortaLobby.png\", " + L + ", " + C + ");\n";
                linhaCodigo += "        p" + i + ".setDestinoFase(" + p_portal.getDestinoFase() + "); // <-- Verifique o destino!\n";
                linhaCodigo += "        fase.add(p" + i + ");";
            }
            else if (p instanceof Chave) {
                linhaCodigo = "fase.add(new Chave(\"ZChave.png\", " + L + ", " + C + "));";
            }
            
            System.out.println("        " + linhaCodigo);
        }

        System.out.println("");
        System.out.println("return fase;");
        System.out.println("// --- FIM DO CÓDIGO DA FASE ---");
        System.out.println("=========================================================");
    }
}
