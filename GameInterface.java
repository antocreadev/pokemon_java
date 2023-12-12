import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameInterface extends JFrame {

    private int playerX = 50;
    private int playerY = 50;

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;

    private Image offscreenBuffer;
    private boolean inGreenZone = false;

    private Sprite playerSprite;


    
    public GameInterface() {
        setTitle("Pokemon by antocreadev");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        addKeyListener(new KeyInputHandler());
        setFocusable(true);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });
        init();
    }

    private void init() {
        playerSprite = new Sprite("/img/character/", 2, "down"); 
    }

    private class KeyInputHandler implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            handleKeyPress(e, true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            handleKeyPress(e, false);
        }

        private void handleKeyPress(KeyEvent e, boolean pressed) {
            int keyCode = e.getKeyCode();

            switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    leftPressed = pressed;
                    break;
                case KeyEvent.VK_RIGHT:
                    rightPressed = pressed;
                    break;
                case KeyEvent.VK_UP:
                    upPressed = pressed;
                    break;
                case KeyEvent.VK_DOWN:
                    downPressed = pressed;
                    break;
            }
            gameLoop();
        }
    }

    private void handleResize() {
        offscreenBuffer = null;
        repaint();
    }

    private void gameLoop() {
        int speed = 10;

        if (leftPressed) {
            playerX -= speed;
            playerSprite.updateSprite("left");

        }
        if (rightPressed) {
            playerX += speed;
            playerSprite.updateSprite("right");

        }
        if (upPressed) {
            playerY -= speed;
            playerSprite.updateSprite("up");

        }
        if (downPressed) {
            playerY += speed;
            playerSprite.updateSprite("down");

        }

        System.out.println("Player Position - X: " + playerX + ", Y: " + playerY);

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
        return playerX >= greenZoneX && playerX <= greenZoneX + greenZoneWidth &&
               playerY >= greenZoneY && playerY <= greenZoneY + greenZoneHeight;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        drawPlayer(g);

        // affiche le perso au demarrage
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
        int scaledPlayerX = (int) ((playerX - scaledPlayerSize / 2) * (double) screenWidth / 400);
        int scaledPlayerY = (int) ((playerY - scaledPlayerSize / 2) * (double) screenHeight / 400);
        
        g.setColor(Color.BLUE);

        g.drawImage(playerSprite.getCurrentSprite(), scaledPlayerX, scaledPlayerY, scaledPlayerSize, scaledPlayerSize, this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameInterface game = new GameInterface();
            game.setVisible(true);
        });
    }
}