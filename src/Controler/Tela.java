package Controler;

import Modelo.Personagem;
import Modelo.Caveira;
import Modelo.Hero;
import Modelo.Chaser;
import Modelo.BichinhoVaiVemHorizontal;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import Modelo.BichinhoVaiVemVertical;
import Modelo.Esfera;
import Modelo.ZigueZague;
import auxiliar.Posicao;
import java.awt.FlowLayout;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JButton;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import Modelo.Fases.IFase;
import Modelo.InimigoCircular;
import Modelo.InimigoDiagonal;
import Modelo.Portal;
import Modelo.ItemChave;
import Modelo.Mensagem;
import Modelo.RoboAtirador;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Random;

public class Tela extends javax.swing.JFrame implements MouseListener, KeyListener, DropTargetListener {

    private GerenciadorFase gFase;
    private IFase configFaseAtual;
    private int nivelAtual;
    private int vidas;
    private int pontuacao;
    private int itensColetados;
    private String backgroundTile;
    private ArrayList<Personagem> faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private final Set<Integer> teclasPressionadas = new HashSet<>();
    private javax.swing.ImageIcon iCoracaoHUD;
    private int faseTimer; // NOVO: Timer da fase (para sobrevivência)
    private int spawnTimer; // NOVO: Timer para criar inimigos na Fase 5
    private java.util.Set<Integer> fasesConcluidas; // NOVO: A "Memória" do jogo
    private boolean isGamePaused = false;
    private int idFaseAtual = -1;
    
    
    public Tela() {
        Desenho.setCenario(this);
        initComponents();
        this.addMouseListener(this);
        this.addKeyListener(this);
        new DropTarget(this, this);
        this.setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);

        // --- LÓGICA DE INICIALIZAÇÃO MODIFICADA ---
        this.gFase = new GerenciadorFase();
        this.vidas = 10;
        this.pontuacao = 0;
        this.fasesConcluidas = new java.util.HashSet<>();
        
        this.faseAtual = new ArrayList<Personagem>(); // Inicia a lista vazia
        this.iniciarFase(this.idFaseAtual); // Inicia o jogo no Lobby (Nível 0)
        
        // NOVO: Carrega a imagem do coração para o HUD
        try {
            iCoracaoHUD = new javax.swing.ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + "coracao.png");
            // Redimensiona o ícone do HUD para um tamanho bom (ex: 25x25)
            java.awt.Image img = iCoracaoHUD.getImage();
            java.awt.image.BufferedImage bi = new java.awt.image.BufferedImage(25, 25, java.awt.image.BufferedImage.TYPE_INT_ARGB);
            java.awt.Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, 25, 25, null);
            iCoracaoHUD = new javax.swing.ImageIcon(bi);
        } catch (java.io.IOException ex) {
            System.out.println("Erro ao carregar imagem do HUD: " + ex.getMessage());
        }
    }
    
    /**
     * NOVO MÉTODO:
     * Centraliza a lógica de carregar, reiniciar ou trocar de fase.
     * @param idFase O número da fase a ser carregada.
     */
    public void iniciarFase(int idFase) {
        // Limpa a fase antiga
        this.faseAtual.clear();
        this.teclasPressionadas.clear();

        // Salva o histórico de IDs
        int idFaseAnterior = this.idFaseAtual; // De onde viemos
        this.idFaseAtual = idFase; // Para onde vamos

        // Carrega a nova configuração de fase
        this.configFaseAtual = gFase.getFase(this.idFaseAtual);
        if (this.configFaseAtual instanceof Modelo.Fases.Lobby) {
            ((Modelo.Fases.Lobby) this.configFaseAtual).atualizarFasesConcluidas(this.fasesConcluidas);
        }
        if (this.configFaseAtual == null) {
            System.err.println("ERRO: Tentando carregar fase inexistente ID: " + this.idFaseAtual);
            return;
        }

        // Carrega os personagens da nova fase
        this.faseAtual.addAll(this.configFaseAtual.carregarPersonagensIniciais());

        // Define o background
        this.backgroundTile = this.configFaseAtual.getBackgroundTile();

        // --- LÓGICA DE MENSAGEM (A GRANDE MUDANÇA) ---

        String msgParaMostrar = null;

        // CASO 1: Estamos voltando ao Lobby (ID 0) vindo de uma fase (ID > 0)?
        if (this.idFaseAtual == 0 && idFaseAnterior > 0) {
            // Sim! Busque a mensagem de VITÓRIA da fase ANTERIOR.
            IFase configFaseAntiga = gFase.getFase(idFaseAnterior);
            if(configFaseAntiga != null){
                msgParaMostrar = configFaseAntiga.getMensagemVitoria();
            }
        } 
        // CASO 2: Qualquer outra situação (Início do jogo, Lobby -> Fase, Fase -> Fase)
        else {
            // Apenas mostre a mensagem INICIAL da fase ATUAL.
            msgParaMostrar = this.configFaseAtual.getMensagemInicial();
        }

//
        // CASO 3: Se a fase que acabamos de carregar for a de Créditos,
        // precisamos anexar a mensagem de "vitória" (os autores) à
        // mensagem "inicial" (o "FIM DE JOGO!").
        if (this.configFaseAtual instanceof Modelo.Fases.CreditosFinais) {
            String msgAutores = this.configFaseAtual.getMensagemVitoria();
            if (msgAutores != null && !msgAutores.isEmpty()) {
                // Concatena as duas mensagens
                msgParaMostrar = msgParaMostrar + "\n\n" + msgAutores;
            }
        }

        // Reseta a pontuação de coleta (lógica dos 3 itens)
        this.itensColetados = 0;
    }
    
    public Hero getHero() {
        if (this.faseAtual.isEmpty() || !(this.faseAtual.get(0) instanceof Hero)) {
            // Isso indica um problema sério no carregamento da fase
            System.out.println("ERRO: Herói não é o primeiro elemento da fase!");
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
        // V-V-V- LÓGICA NOVA (O "PORTEIRO") V-V-V
        if (umPersonagem instanceof Mensagem) {
            Mensagem novaMsg = (Mensagem) umPersonagem;

            // Se o jogo JÁ ESTÁ pausado por outra mensagem...
            if (this.isGamePaused) {
                // ... IGNORAMOS a nova mensagem para evitar overlap.
                System.out.println("WARN: Jogo pausado, mensagem nova ignorada: " + novaMsg.getTexto());
                return; // Sai do método, não adiciona a mensagem
            }

            // Se o jogo NÃO está pausado, mas esta MENSAGEM quer pausar...
            if (novaMsg.isBlocking()) {
                // ...nós ativamos a pausa AGORA.
                this.setGamePaused(true);
            }
        }
        // ^-^-^- FIM DA LÓGICA NOVA -^-^-^

        // Adiciona o personagem (seja a msg ou qualquer outro)
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
        
        /**
         * *********** Desenha cenário de fundo (AGORA TEMÁTICO) *************
         */
        if (this.backgroundTile != null) { // Garante que o tile existe
            try {
                Image newImage = Toolkit.getDefaultToolkit().getImage(
                        new java.io.File(".").getCanonicalPath() + Consts.PATH + this.backgroundTile);
                
                for (int i = 0; i < Consts.RES; i++) {
                    for (int j = 0; j < Consts.RES; j++) {
                        g2.drawImage(newImage,
                                j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                                Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // --- LÓGICA DE JOGO MODIFICADA ---
        if (!this.faseAtual.isEmpty()) {

            // <<-- PASSO 1: ATUALIZAR LÓGICA (SEMPRE RODA) >>
            // Esta chamada agora está FORA do 'if' de pausa
            // e passa o estado de pausa para o controlador.
            this.cj.atualizarTudo(faseAtual, getHero(), this.isGamePaused);
            
            // <<-- PASSO 2: PROCESSAR COLISÕES (SÓ RODA SE NÃO PAUSADO) >>
            // Este 'if' agora protege apenas a lógica de jogo, não o timer.
            if (!this.isGamePaused) {
                
                String status = this.cj.processaTudo(faseAtual);
                
                // PASSO 3: AGIR DE ACORDO COM O STATUS
                switch (status) {
                    case "HERO_DIED":
                        this.vidas--;
                        if (this.vidas <= 0) {
                            this.gameOver();
                        } else {
                            this.reiniciarFase();
                        }
                        break;
                        
                    case "ITEM_COLETADO":
                        this.processarColeta(); // Lógica principal da fase (3 itens)
                        break;

                    // --- NOSSAS NOVAS MECÂNICAS ---
                    case "REJEITADO":
                        getHero().voltaAUltimaPosicao(); // Empurra o herói (porta/baú trancado)
                        break;
                        
                    case "CHAVE_COLETADA":
                        getHero().adicionarChave(); // Adiciona chave no "inventário"
                        this.pontuacao += 10;
                        break;
                        
                    case "BAU_ABERTO":
                        processarAberturaBau(); // Roda a lógica de sorte/azar
                        break;
                    // --- FIM DAS NOVAS MECÂNICAS ---

                    // Lógica de Portais (do Lobby)
                    case "PORTAL_FASE_0":
                        this.iniciarFase(0); // Vai para o Lobby
                        break;
                    case "PORTAL_FASE_1":
                        this.iniciarFase(1); // Vai para Fase 1
                        break;
                    case "PORTAL_FASE_2":
                        this.iniciarFase(2); // Vai para Fase 2
                        break;
                    case "PORTAL_FASE_3":
                        this.iniciarFase(3); // Vai para Fase 3
                        break;
                    case "PORTAL_FASE_4":
                        this.iniciarFase(4); // Vai para Fase 4
                        break;
                    case "PORTAL_FASE_5":
                        this.iniciarFase(5); // Vai para Fase 5
                        break;
                    
                    case "FASE_CONCLUIDA": // Status do Portal de saída (destino 0)
                        this.proximaFase();
                        break;

                    case "GAME_RUNNING":
                        // Não faz nada, o jogo continua
                        break;
                } // Fim do switch(status)
                if (this.idFaseAtual == 5) {
                    processarFase5();
                }
                
            } // <--- Fim do "if (!this.isGamePaused)"
            // -----------------------------------------------------------------
            // FIM DA LÓGICA DE PAUSE
            // -----------------------------------------------------------------
            
            
            // PASSO 4: DESENHAR TUDO
            // O desenho ocorre MESMO SE ESTIVER PAUSADO.
            // É isso que permite a Mensagem (bloqueante) aparecer!
            this.cj.desenharTudo(faseAtual);
            
        } else if (this.nivelAtual == 6) {
             // LÓGICA DA FASE 6 (Créditos)
             g2.setColor(java.awt.Color.WHITE);
             g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 18));
             g2.drawString("PARABENS, VOCE ZEROU O JOGO!", 180, 200);
             g2.drawString("Criado por: [Seu Nome Aqui] e [Nome Colega 1]", 180, 250);
        }
        
        // ==========================================================
        // NOVO: Desenhando o HUD (Vidas e Pontos)
        // ==========================================================
        // Define a fonte e a cor
        g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        g2.setColor(java.awt.Color.WHITE);

        // Desenha a Pontuação (no canto superior direito)
        String textoPontos = "Pontos: " + this.pontuacao;
        g2.drawString(textoPontos, 430, 25); // (Posição X, Y)

        // Desenha as Vidas (no canto superior esquerdo)
        if (iCoracaoHUD != null) {
            for (int i = 0; i < this.vidas; i++) {
                // Desenha um coração para cada vida, com espaçamento
                iCoracaoHUD.paintIcon(this, g2, 10 + (i * 30), 5);
            }
        }
        // ==========================================================
        // FIM DO HUD
        // ==========================================================
        
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    /**
     * NOVO MÉTODO:
     * Implementa a lógica do brainstorm de 3 itens.
     */
    private void processarColeta() {
        this.pontuacao += 50; // Ganha pontos por item
        this.itensColetados++;
        System.out.println("Item coletado! Total: " + this.itensColetados);

        if (this.itensColetados == 1) {
            // Adiciona as barreiras
            this.faseAtual.addAll(this.configFaseAtual.getPersonagensColeta_1());
        } else if (this.itensColetados == 2) {
            // Adiciona o "chefão"
            this.faseAtual.addAll(this.configFaseAtual.getPersonagensColeta_2());
        } else if (this.itensColetados == 3) {
            // Adiciona o portal de saída
            this.faseAtual.add(this.configFaseAtual.getPersonagemColeta_3());
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
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }
    
    public void keyPressed(KeyEvent e) {
        if (this.isGamePaused) {
            return; // Ignora qualquer tecla se o jogo estiver pausado
        }
        
        try {
            if (teclasPressionadas.contains(e.getKeyCode()))
                    return;

            teclasPressionadas.add(e.getKeyCode());
            
            if (e.getKeyCode() == KeyEvent.VK_T) {
                this.faseAtual.clear();
                ArrayList<Personagem> novaFase = new ArrayList<Personagem>();

                /*Cria faseAtual adiciona personagens*/
                Hero novoHeroi = new Hero("Robbo.png", 10, 10);
                //hero.setPosicao(10, 10);
                novaFase.add(novoHeroi);
                this.atualizaCamera();

                ZigueZague zz = new ZigueZague("bomba.png", 0, 0);
                novaFase.add(zz);

                Esfera es = new Esfera("esfera.png", 4, 4);
                novaFase.add(es);

                faseAtual = novaFase;
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                getHero().moveUp();
            } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                getHero().moveDown();
            } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                getHero().moveLeft();
            } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                getHero().moveRight();
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                File tanque = new File("POO.dat");
                tanque.createNewFile();
                FileOutputStream canoOut = new FileOutputStream(tanque);
                ObjectOutputStream serializador = new ObjectOutputStream(canoOut);
                serializador.writeObject(faseAtual);
            } else if (e.getKeyCode() == KeyEvent.VK_L) {
                File tanque = new File("POO.dat");
                FileInputStream canoOut = new FileInputStream(tanque);
                ObjectInputStream serializador = new ObjectInputStream(canoOut);
                faseAtual = (ArrayList<Personagem>)serializador.readObject();
            } else if (e.getKeyCode() == KeyEvent.VK_K) {
            // CHEAT: Salva uma Caveira para teste de Drag-and-Drop
            try (   FileOutputStream fos = new FileOutputStream("caveira.gz");
                    GZIPOutputStream gzos = new GZIPOutputStream(fos);
                    ObjectOutputStream oos = new ObjectOutputStream(gzos)) {

                Caveira c = new Caveira("caveira.png", 0, 0); // Posição não importa
                oos.writeObject(c);
                System.out.println("Arquivo 'caveira.gz' criado para teste!");

            } catch (Exception ex) {
                System.err.println("Erro ao criar arquivo de teste: " + ex.getMessage());
            }
        }

            this.atualizaCamera();
            this.setTitle("-> Cell: " + (getHero().getPosicao().getLinha()) + ", " + (getHero().getPosicao().getColuna()));

            //repaint(); /*invoca o paint imediatamente, sem aguardar o refresh*/
        } catch (Exception ee) {

        }
    }
    public void keyReleased(KeyEvent e) {
        teclasPressionadas.remove(e.getKeyCode());        
    }    

    public void mousePressed(MouseEvent e) {
        if (this.isGamePaused) {
            return; // Ignora clique do mouse se o jogo estiver pausado
        }
        
        
        /* Clique do mouse desligado*/
        int x = e.getX();
        int y = e.getY();

        this.setTitle("X: " + x + ", Y: " + y
                + " -> Cell: " + (y / Consts.CELL_SIDE) + ", " + (x / Consts.CELL_SIDE));

        // Calcula a posição no *GRID* (levando em conta a câmera)
        int dropLinha = (y / Consts.CELL_SIDE) + getCameraLinha();
        int dropColuna = (x / Consts.CELL_SIDE) + getCameraColuna();

        this.getHero().getPosicao().setPosicao(dropLinha, dropColuna);

        repaint();
    }
    
    /**
     * Controla a lógica de "Sorte ou Azar" do baú de bônus.
     */
    private void processarAberturaBau() {
        Random rand = new Random();
        int sorte = rand.nextInt(100); // Sorteia um número de 0 a 99

        if (sorte < 20) { // 20% de chance
            // Recompensa Máxima: Pular a Fase!
            addPersonagem(new Mensagem("Um Gerador de Portal!\nVocê escapou da fase!", true));
            this.proximaFase(); // Usa a lógica que já temos!

        } else if (sorte < 60) { // 40% de chance (de 20 a 59)
            // Recompensa Ótima: Vida Extra!
            this.vidas++;
            addPersonagem(new Mensagem("Uma Poção Mágica!\nVocê recuperou uma vida!", true));

        } else if (sorte < 90) { // 30% de chance (de 60 a 89)
            // Recompensa Boa: Pontos!
            this.pontuacao += 200;
            addPersonagem(new Mensagem("Um Tesouro!\n+200 Pontos!", true));
            
        } else { // 10% de chance (de 90 a 99)
            // Piada (Azar, mas sem punição)
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
        this.iniciarFase(this.idFaseAtual); //
    }
    
    public void proximaFase() {
        this.pontuacao += 100;
        System.out.println("Passou de fase! Pontos: " + this.pontuacao);
        
        if (this.idFaseAtual >= 1 && this.idFaseAtual <= 4) { // <-- MUDOU
            this.fasesConcluidas.add(this.idFaseAtual); // <-- MUDOU
            System.out.println("Fases Concluidas: " + this.fasesConcluidas.toString());
            this.iniciarFase(0);
        } 
        else if (this.idFaseAtual == 5) { // <-- MUDOU
            this.iniciarFase(6);
        }
        // Outros casos (Lobby 0, Créditos 6) reiniciam o jogo
        else {
            this.iniciarFase(0); 
        }
    }
    
    // SUBSTITUIR gameOver()
    public void gameOver() {
        System.out.println("GAME OVER!");
        // Reinicia o jogo do zero (volta ao Lobby)
        this.vidas = 10;
        this.pontuacao = 0;
        this.iniciarFase(0);
    }
    
/**
     * NOVO MÉTODO: (VERSÃO TURBINADA)
     * Controla a lógica da Fase 5 (Sobrevivência).
     */
    private void processarFase5() {
        this.faseTimer++;
        this.spawnTimer++;

        // 1 minuto = 400 ticks (400 * 150ms = 60000ms)
        if (this.faseTimer > 400) {
            this.iniciarFase(6); // 6 é CreditosFinais
            return;
        }

        // --- LÓGICA DE DIFICULDADE PROGRESSIVA ---
        int spawnMaxTicks = 20; // Padrão: 3 segundos
        if (this.faseTimer > 200) { // Na metade do tempo (30s)
            spawnMaxTicks = 10; // Dobra a velocidade de spawn! (1.5s)
        }
        // --- FIM DA LÓGICA ---

        if (this.spawnTimer > spawnMaxTicks) {
            this.spawnTimer = 0; // Reseta o timer de spawn
            
            java.util.Random rand = new java.util.Random();
            int tipoInimigo = rand.nextInt(5); // AGORA SPAWNA 5 TIPOS!
            int linha = rand.nextInt(14) + 1; // Posição aleatória (evitando bordas)
            int coluna = rand.nextInt(14) + 1;
            
            // Evita spawnar em cima das paredes internas da Fase 5
            if((linha == 4 || linha == 11) && (coluna >= 4 && coluna <= 11)){
                linha = 7; // Joga pro meio se cair na parede
                coluna = 7;
            }

            switch(tipoInimigo) {
                case 0:
                    // O Perseguidor (Clássico)
                    this.addPersonagem(new Chaser("chaser.png", linha, coluna));
                    break;
                case 1:
                    // O Atirador de Bombas! (Mais perigoso que a Caveira)
                    this.addPersonagem(new RoboAtirador("robo.png", linha, coluna));
                    break;
                case 2:
                    // O Caótico ZigueZague
                    this.addPersonagem(new ZigueZague("skoot.png", linha, coluna));
                    break;
                case 3:
                    // O Patrulha Circular
                    this.addPersonagem(new InimigoCircular("roboPink.png", linha, coluna));
                    break;
                case 4:
                    // O Patrulha Diagonal
                    this.addPersonagem(new InimigoDiagonal("roboPink.png", linha, coluna));
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    // ---------------------------------------------------------------- //
    // MÉTODOS DO REQUISITO 6 (DRAG-AND-DROP)
    // ---------------------------------------------------------------- //

    @Override
    public void drop(DropTargetDropEvent e) {
        try {
            // Aceita o "drop"
            e.acceptDrop(DnDConstants.ACTION_COPY);
            
            // Pega os dados que foram soltos
            Transferable transferable = e.getTransferable();
            
            // Verifica se são arquivos
            if (transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                
                // Pega a posição do mouse (relativa ao painel do jogo)
                Point dropPoint = e.getLocation();

                // Converte a lista de dados para uma Lista de Arquivos
                List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                
                // Processa cada arquivo solto
                for (File file : files) {
                    System.out.println("Arquivo solto: " + file.getName());
                    
                    // Verifica se é um .gz (como sugere o PDF e o GZIPInputStream)
                    if (file.getName().toLowerCase().endsWith(".gz")) {
                        adicionarPersonagemDoArquivo(file, dropPoint);
                    }
                }
            }
            e.dropComplete(true);
        } catch (Exception ex) {
            System.err.println("Erro no Drop: " + ex.getMessage());
            ex.printStackTrace();
            e.dropComplete(false);
        }
    }

    private void adicionarPersonagemDoArquivo(File file, Point dropPoint) {
        try (   FileInputStream fis = new FileInputStream(file);
                GZIPInputStream gzis = new GZIPInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(gzis)) {

            // Lê e desserializa o objeto Personagem do arquivo
            Personagem pNovo = (Personagem) ois.readObject();

            // Calcula a posição no *GRID* (levando em conta a câmera)
            // (Esta é a lógica correta que também devemos aplicar ao mousePressed)
            int dropLinha = (dropPoint.y / Consts.CELL_SIDE) + getCameraLinha();
            int dropColuna = (dropPoint.x / Consts.CELL_SIDE) + getCameraColuna();

            // Define a nova posição do personagem
            pNovo.setPosicao(dropLinha, dropColuna);
            
            // Adiciona o personagem à fase atual
            this.faseAtual.add(pNovo);
            System.out.println("Adicionado: " + pNovo.getClass().getSimpleName() + " em " + dropLinha + "," + dropColuna);

        } catch (Exception ex) {
            System.err.println("Falha ao ler objeto do arquivo: " + ex.getMessage());
        }
    }

    // Métodos obrigatórios do DropTargetListener que não usaremos, mas precisam existir.
    @Override
    public void dragEnter(DropTargetDragEvent e) {
         // Aceita apenas arquivos
        if (e.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            e.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            e.rejectDrag();
        }
    }
    @Override
    public void dragOver(DropTargetDragEvent e) { } // Não precisa
    @Override
    public void dropActionChanged(DropTargetDragEvent e) { } // Não precisa
    @Override
    public void dragExit(DropTargetEvent e) { } // Não precisa
    
    // ---------------------------------------------------------------- //
    // FIM DOS MÉTODOS DE DRAG-AND-DROP
    // ---------------------------------------------------------------- //
}
