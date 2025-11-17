import Controler.Tela;
import Auxiliar.Desenho;
import java.awt.dnd.DropTarget;

public class Main {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Tela tTela = new Tela();
                
                // INICIALIZAÇÃO MOVIDA: Deve estar aqui, depois da criação do objeto.
                Desenho.setCenario(tTela);
                tTela.addMouseListener(tTela);
                tTela.addKeyListener(tTela);
                new DropTarget(tTela, tTela);
                
                tTela.setVisible(true);
                tTela.createBufferStrategy(2);
                tTela.go();
            }
        });
    }
}