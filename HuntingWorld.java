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

public class HuntingWorld extends JFrame {
    private Image offscreenBuffer;
    private boolean inHuntingWorld = false;

    private KeyInputHandler keyInputHandler;
    private BufferStrategy bufferStrategy; 
    private Image backgroundImage;
    private Image grassImage;

    private Clip backgroundMusic;

    // constructeur
    public HuntingWorld(Dresseur dresseur) {
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
        backgroundImage = new ImageIcon("img/decors/backgoundHunterWorld.png").getImage();

        loadBackgroundMusic("sounds/fight.wav");
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

    // Bouger le dresseur
    // private void handlePlayerMovement() {
    //     int speed = 10;

    //     if (keyInputHandler.isLeftPressed()) {
    //         dresseur.incrementPlayerX(-speed);
    //         playerSprite.updateSprite("left");
    //     }
    //     if (keyInputHandler.isRightPressed()) {
    //         dresseur.incrementPlayerX(speed);
    //         playerSprite.updateSprite("right");
    //     }
    //     if (keyInputHandler.isUpPressed()) {
    //         dresseur.incrementPlayerY(-speed);
    //         playerSprite.updateSprite("up");
    //     }
    //     if (keyInputHandler.isDownPressed()) {
    //         dresseur.incrementPlayerY(speed);
    //         playerSprite.updateSprite("down");
    //     }
    // }


    private void gameLoop() {
        // this.handlePlayerMovement();
    
        Graphics g = bufferStrategy.getDrawGraphics();
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(grassImage, 0, 0, getWidth(), getHeight(), this);

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

    private void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }
}
