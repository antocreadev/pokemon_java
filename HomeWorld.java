import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class HomeWorld extends JFrame {
    private Image offscreenBuffer;
    private boolean inHuntingWorld = false;

    private Sprite playerSprite;
    private String playerName;

    private Dresseur dresseur;
    private KeyInputHandler keyInputHandler;
    private BufferStrategy bufferStrategy; 
    private Image backgroundImage;
    private Image grassImage;

    private Clip backgroundMusic;

    // constructeur
    public HomeWorld(Dresseur dresseur) {
        this.dresseur = dresseur;
        this.playerName = dresseur.getName();
        setTitle("Pokemon by antocreadev");
        setSize(400, 400);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        keyInputHandler = new KeyInputHandler();
        addKeyListener(keyInputHandler);
        setFocusable(true);

        init();
        backgroundMusic.start();

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
        backgroundImage = new ImageIcon("img/decors/wall.png").getImage();
        grassImage = new ImageIcon("img/decors/grass.png").getImage();

        loadBackgroundMusic("sounds/home.wav");
    }

    private void changeToWorld(JFrame newWorld) {
        this.dispose();
        newWorld.setVisible(true);
    }

    private void loadBackgroundMusic(String path) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            // Définir la boucle
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void stopBackgroundMusic() {
        backgroundMusic.stop();
    }

    // Bouger le dresseur
    private void handlePlayerMovement() {
        int speed = 10;

        if (keyInputHandler.isLeftPressed()) {
            dresseur.incrementPlayerX(-speed);
            playerSprite.updateSprite("left");
        }
        if (keyInputHandler.isRightPressed()) {
            dresseur.incrementPlayerX(speed);
            playerSprite.updateSprite("right");
        }
        if (keyInputHandler.isUpPressed()) {
            dresseur.incrementPlayerY(-speed);
            playerSprite.updateSprite("up");
        }
        if (keyInputHandler.isDownPressed()) {
            dresseur.incrementPlayerY(speed);
            playerSprite.updateSprite("down");
        }
    }


    private void gameLoop() {
        this.handlePlayerMovement();
    
        inHuntingWorld = isPlayerInHuntingWorld();
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(grassImage, 0, 0, getWidth(), getHeight(), this);

        draw(g);
    
        bufferStrategy.show();
    
        g.dispose();
        Random random = new Random();
        int nombreAleatoire = random.nextInt(11);
        if (inHuntingWorld && nombreAleatoire ==1) {
            System.out.println("VERT");
            changeToWorld(new HuntingWorld(dresseur));
            stopBackgroundMusic();
        }
    }

    private boolean isPlayerInHuntingWorld() {
    int[] xPoints = {269, 269, 285, 285, 381, 381, 365, 365};
    int[] yPoints = {285, 331, 331, 375, 375, 329, 329, 285};
    
    Polygon greenZone = new Polygon(xPoints, yPoints, xPoints.length);
    
    return greenZone.contains(dresseur.playerX, dresseur.playerY);
}

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (bufferStrategy == null) {
            createBufferStrategy(2);
            bufferStrategy = getBufferStrategy();
        }

        draw(g);

        if (offscreenBuffer != null) {
            g.drawImage(offscreenBuffer, 0, 0, this);
        }
    }

    private void draw(Graphics g) {

        int screenWidth = getWidth();
        int screenHeight = getHeight();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        int[] xPoints = {269, 269, 285, 285, 381, 381, 365, 365};
        int[] yPoints = {285, 331, 331, 375, 375, 329, 329, 285};
        int nbPoints = xPoints.length;
        g.setColor(null);
        g.fillPolygon(xPoints, yPoints, nbPoints);

        g.drawImage(grassImage, 0, 0, getWidth(), getHeight(), this);


        int scaledPlayerSize = (int) (60 * (double) screenWidth / 400);
        int scaledPlayerX = (int) ((dresseur.playerX - scaledPlayerSize / 2) * (double) screenWidth / 400);
        int scaledPlayerY = (int) ((dresseur.playerY - scaledPlayerSize / 2) * (double) screenHeight / 400);
        g.setColor(Color.BLACK);
        g.drawString(playerName, scaledPlayerX, scaledPlayerY);
        g.setColor(Color.BLUE);
        g.drawImage(playerSprite.getCurrentSprite(), scaledPlayerX, scaledPlayerY, scaledPlayerSize, scaledPlayerSize, this);
    }
}
