import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GameInterface extends JFrame {
    private Image offscreenBuffer;
    private boolean inGreenZone = false;

    private Sprite playerSprite;
    private String playerName;

    private Dresseur dresseur;
    private KeyInputHandler keyInputHandler;

    // constructeur
    public GameInterface(Dresseur dresseur) {
        this.dresseur = dresseur;
        this.playerName = dresseur.getName();
        setTitle("Pokemon by antocreadev");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        keyInputHandler = new KeyInputHandler();
        addKeyListener(keyInputHandler);
        setFocusable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });
        init();
            // Ajoutez le code suivant pour démarrer la boucle de jeu
    Timer timer = new Timer(100, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            gameLoop();
        }
    });
    timer.start();
    }

    private void init() {
        playerSprite = dresseur.getPlayerSprite();
    }

    // Bouger le dresseur 
    private void handlePlayerMovement() {
        int speed = 10;

        if (keyInputHandler.isLeftPressed()) {
            dresseur.incrementPlayerX(-speed);
            playerSprite.updateSprite("left");
            System.out.println("LEFT : " + dresseur.playerX);
        }
        if (keyInputHandler.isRightPressed()) {
            dresseur.incrementPlayerX(speed);
            playerSprite.updateSprite("right");
            System.out.println("RIGHT : " + dresseur.playerX);
        }
        if (keyInputHandler.isUpPressed()) {

            dresseur.incrementPlayerY(-speed);
            playerSprite.updateSprite("up");
            System.out.println("UP : " + dresseur.playerY);
        }
        if (keyInputHandler.isDownPressed()) {
            dresseur.incrementPlayerY(speed);
            playerSprite.updateSprite("down");
            System.out.println("DOWN : " + dresseur.playerY);
        }
    }

    private void handleResize() {
        offscreenBuffer = null;
        repaint();
    }

    private void gameLoop() {
        this.handlePlayerMovement();

        System.out.println("Player Position - X: " + dresseur.playerX + ", Y: " + dresseur.playerY);

        inGreenZone = isPlayerInGreenZone();

        offscreenBuffer = createImage(getWidth(), getHeight());

        Graphics offscreenGraphics = offscreenBuffer.getGraphics();
        drawPlayer(offscreenGraphics);

        if (inGreenZone) {
            System.out.println("VERT");
        }
        repaint();
    }

    private boolean isPlayerInGreenZone() {
        int greenZoneX = 100;
        int greenZoneY = 100;
        int greenZoneWidth = 100;
        int greenZoneHeight = 100;
        return dresseur.playerX >= greenZoneX && dresseur.playerX <= greenZoneX + greenZoneWidth &&
               dresseur.playerY >= greenZoneY && dresseur.playerY <= greenZoneY + greenZoneHeight;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawPlayer(g);

        if (offscreenBuffer != null) {
            g.drawImage(offscreenBuffer, 0, 0, this);
        }
    }

    private void drawPlayer(Graphics g) {
        int screenWidth = getWidth();
        int screenHeight = getHeight();
    
        g.setColor(Color.GREEN);
        g.fillRect(100, 100, 100, 100);
    
        int scaledPlayerSize = (int) (60 * (double) screenWidth / 400); 
        int scaledPlayerX = (int) ((dresseur.playerX- scaledPlayerSize / 2) * (double) screenWidth / 400);
        int scaledPlayerY = (int) ((dresseur.playerY - scaledPlayerSize / 2) * (double) screenHeight / 400);
        // Dessiner le nom du joueur à côté du personnage
        g.setColor(Color.BLACK);
        g.drawString(playerName, scaledPlayerX, scaledPlayerY);
        g.setColor(Color.BLUE);
        g.drawImage(playerSprite.getCurrentSprite(), scaledPlayerX, scaledPlayerY, scaledPlayerSize, scaledPlayerSize, this);
    }

    public static void main(String[] args) {
        Dresseur giovanni = new Dresseur("Giovanni", new ArrayList<Pokemon>());
        SwingUtilities.invokeLater(() -> {
            GameInterface game = new GameInterface(giovanni);
            game.setVisible(true);
        });
    }
}