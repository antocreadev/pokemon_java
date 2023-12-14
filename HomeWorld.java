import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HomeWorld extends World {
    private Image grassImage;

    public HomeWorld(Dresseur dresseur, String playerName) {
        super(dresseur, playerName);
    }

    // implements abstract method
    protected void init() {
        System.out.println(dresseur.getPlayerSprite());
        System.out.println(dresseur);
        playerSprite = dresseur.getPlayerSprite();
        backgroundImage = new ImageIcon("assets/img/decors/wall.png").getImage();
        grassImage = new ImageIcon("assets/img/decors/grass.png").getImage();
        loadBackgroundMusic("assets/sounds/home.wav");
    }

    protected void draw(Graphics g) {
    // background
    g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);

    // grass zone
    int[] xPoints = {269, 269, 285, 285, 381, 381, 365, 365};
    int[] yPoints = {285, 331, 331, 375, 375, 329, 329, 285};
    int nbPoints = xPoints.length;
    g.setColor(null);
    g.fillPolygon(xPoints, yPoints, nbPoints);
    g.drawImage(grassImage, 0, 0, screenWidth, screenHeight, this);

    // player
    int scaledPlayerSize = (int) (60 * (double) screenWidth / 400);
    int scaledPlayerX = (int) ((dresseur.playerX - scaledPlayerSize / 2) * (double) screenWidth / 400);
    int scaledPlayerY = (int) ((dresseur.playerY - scaledPlayerSize / 2) * (double) screenHeight / 400);
    g.setColor(Color.BLACK);
    g.drawString(playerName, scaledPlayerX, scaledPlayerY);
    g.setColor(Color.BLUE);
    g.drawImage(playerSprite.getCurrentSprite(), scaledPlayerX, scaledPlayerY, scaledPlayerSize, scaledPlayerSize, this);
}

    // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
        // condition to change world
        this.handlePlayerMovement();
    }

    // method specific to HomeWorld
    protected void handlePlayerMovement() {
        int speed = 10;

        if (keyInputHandler.isLeftPressed()) {
            dresseur.incrementPlayerX(-speed);
            playerSprite.updateSprite("left");

        }
        if (keyInputHandler.isRightPressed()) {
            dresseur.incrementPlayerX(speed);
            playerSprite.updateSprite("right");
            if (this.isPlayerInHuntingWorld()){
                this.luckyChangeToWorld();
            }

        }
        if (keyInputHandler.isUpPressed()) {
            dresseur.incrementPlayerY(-speed);
            playerSprite.updateSprite("up");
            if (this.isPlayerInHuntingWorld()){
                this.luckyChangeToWorld();
            }

        }
        if (keyInputHandler.isDownPressed()) {
            dresseur.incrementPlayerY(speed);
            playerSprite.updateSprite("down");
            if (this.isPlayerInHuntingWorld()){
                this.luckyChangeToWorld();
            }
        }
    }

    private void luckyChangeToWorld() {

        Random random = new Random();
        int nombreAleatoire = random.nextInt(26);
        if (nombreAleatoire == 1) {
            System.out.println("VERT");
            changeToWorld(new HuntingWorld(dresseur, playerName));
            stopBackgroundMusic();
        }
    }
    private boolean isPlayerInHuntingWorld() {
    int[] xPoints = {269, 269, 285, 285, 381, 381, 365, 365};
    int[] yPoints = {285, 331, 331, 375, 375, 329, 329, 285};
    
    Polygon greenZone = new Polygon(xPoints, yPoints, xPoints.length);
    
    return greenZone.contains(dresseur.playerX, dresseur.playerY);
}

}
