import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

public abstract class World extends JFrame {
    protected Sprite playerSprite;
    protected String playerName;
    protected Dresseur dresseur;

    protected KeyInputHandler keyInputHandler;
    protected MouseInputHandler mouseInputHandler;

    protected Image offscreenBuffer;
    protected BufferStrategy bufferStrategy;

    protected Image backgroundImage;
    protected Clip backgroundMusic;

    static final public int screenHeight = 400;
    static final public int screenWidth = 400;

    // Added Timer field for game loop
    protected Timer gameTimer;

    public World(Dresseur dresseur, String title) {
        this.dresseur = dresseur;
        this.playerName = dresseur.getName();
        setTitle(title);
        setSize(screenWidth, screenHeight);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        keyInputHandler = new KeyInputHandler();
        addKeyListener(keyInputHandler);
        mouseInputHandler = new MouseInputHandler();
        addMouseListener(mouseInputHandler);
        setFocusable(true);

        init();
        backgroundMusic.start();

        // Modified Timer initialization
        gameTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameLoop();
            }
        });
        gameTimer.start();
    }

    protected abstract void init();

    protected void stopGameLoop() {
        gameTimer.stop();
    }

    protected void startGameLoop() {
        gameTimer.start();
    }
    protected void changeToWorld(JFrame newWorld) {
        stopGameLoop();
        this.dispose();
        newWorld.setVisible(true);
    }

    protected void loadBackgroundMusic(String path) {
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

    protected void stopBackgroundMusic() {
        backgroundMusic.stop();
    }


    protected void gameLoop() {
        Graphics g = bufferStrategy.getDrawGraphics();
        draw(g);
        bufferStrategy.show();
        g.dispose();
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

    protected abstract void draw(Graphics g);
}
