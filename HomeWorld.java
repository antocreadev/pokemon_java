import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class HomeWorld extends World {
    private Image grassImage;
    private Image combatImage;

    private Image iconSquad;
    private Image iconSac;
    private Rectangle squad_square;
    private Rectangle sac_square;


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
        combatImage = new ImageIcon("assets/img/decors/combat.png").getImage();

        iconSquad = new ImageIcon("assets/img/decors/iconSquad.png").getImage();
        iconSac = new ImageIcon("assets/img/decors/iconSac.png").getImage();
        loadBackgroundMusic("assets/sounds/home.wav");

        squad_square = new Rectangle(368, 37, 24, 22);
        sac_square = new Rectangle(368, 72, 24, 22);
    }

    protected void draw(Graphics g) {
        // background
        g.drawImage(backgroundImage, 0, 0, screenWidth, screenHeight, this);

        // grass zone
        int[] xPoints = { 269, 269, 285, 285, 381, 381, 365, 365 };
        int[] yPoints = { 285, 331, 331, 375, 375, 329, 329, 285 };
        int nbPoints = xPoints.length;
        g.setColor(new Color(0, 0, 0, 0));
        g.fillPolygon(xPoints, yPoints, nbPoints);
        g.drawImage(grassImage, 0, 0, screenWidth, screenHeight, this);
        g.drawImage(combatImage, 0, 0, screenWidth, screenHeight, this);

        // rect opacity 0
        g.setColor(new Color(0, 0, 0, 0));
        g.drawRect(184, 240, 30, 15);

        // player
        int scaledPlayerSize = (int) (60 * (double) screenWidth / 400);
        int scaledPlayerX = (int) ((dresseur.playerX - scaledPlayerSize / 2) * (double) screenWidth / 400);
        int scaledPlayerY = (int) ((dresseur.playerY - scaledPlayerSize / 2) * (double) screenHeight / 400);
        g.setColor(Color.BLACK);
        g.drawString(playerName, scaledPlayerX, scaledPlayerY);
        g.setColor(Color.BLUE);
        g.drawImage(playerSprite.getCurrentSprite(), scaledPlayerX, scaledPlayerY, scaledPlayerSize, scaledPlayerSize,
                this);

        // icon squad
        g.drawImage(iconSquad, 0, 20, screenWidth, screenHeight, this);
        // Dessiner le carré rouge
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect((int) squad_square.getX(), (int) squad_square.getY(),
                (int) squad_square.getWidth(), (int) squad_square.getHeight());
        // icon sac
        g.drawImage(iconSac, 0, 30, screenWidth, screenHeight, this);
        // Dessiner le carré rouge
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect((int) sac_square.getX(), (int) sac_square.getY(),
                (int) sac_square.getWidth(), (int) sac_square.getHeight());
    }

    // override gameLoop method for add condition to change world
    @Override
    protected void gameLoop() {
        super.gameLoop();
        this.handlePlayerMovement();

        // Utilisez les informations de la souris pour détecter les clics
        if (mouseInputHandler.isMouseClicked()) {
            int mouseX = mouseInputHandler.getMouseX();
            int mouseY = mouseInputHandler.getMouseY();

            // SQUAD SQUARE CLICK
            if (squad_square.contains(mouseX, mouseY)) {
                System.out.println("SQUAD");
                changeToWorld(new SquadWord(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }

            // SAC SQUARE CLICK
            if (sac_square.contains(mouseX, mouseY)) {
                System.out.println("SAC");
                changeToWorld(new BagWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }

          
        }
    }

    // method specific to HomeWorld
    protected void handlePlayerMovement() {
        int speed = 10;

        if (keyInputHandler.isLeftPressed()) {
            int newPlayerX = dresseur.playerX - speed;
            if (!isInsideRestrictedZone(newPlayerX, dresseur.playerY)) {
                dresseur.incrementPlayerX(-speed);
                playerSprite.updateSprite("left");
            }
            if (this.isPlayerInHuntingWorld()) {
                this.luckyChangeToWorld();
            }
            if (isInFightZone()) {
                changeToWorld(new FightWorld(dresseur, "Pokemon by antocreadev"));
                stopBackgroundMusic();
            }
        }
        if (keyInputHandler.isRightPressed()) {
            int newPlayerX = dresseur.playerX + speed;
            if (!isInsideRestrictedZone(newPlayerX, dresseur.playerY)) {
                dresseur.incrementPlayerX(speed);
                playerSprite.updateSprite("right");
                if (this.isPlayerInHuntingWorld()) {
                    this.luckyChangeToWorld();
                }
                if (isInFightZone()) {
                    changeToWorld(new FightWorld(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                }
            }
        }
        if (keyInputHandler.isUpPressed()) {
            int newPlayerY = dresseur.playerY - speed;
            if (!isInsideRestrictedZone(dresseur.playerX, newPlayerY)) {
                dresseur.incrementPlayerY(-speed);
                playerSprite.updateSprite("up");
                if (this.isPlayerInHuntingWorld()) {
                    this.luckyChangeToWorld();
                }
                if (isInFightZone()) {
                    changeToWorld(new FightWorld(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                }
            }
        }
        if (keyInputHandler.isDownPressed()) {
            int newPlayerY = dresseur.playerY + speed;
            if (!isInsideRestrictedZone(dresseur.playerX, newPlayerY)) {
                dresseur.incrementPlayerY(speed);
                playerSprite.updateSprite("down");
                if (this.isPlayerInHuntingWorld()) {
                    this.luckyChangeToWorld();
                }
                if (isInFightZone()) {
                    changeToWorld(new FightWorld(dresseur, "Pokemon by antocreadev"));
                    stopBackgroundMusic();
                }
            }
        }
    }

    private void luckyChangeToWorld() {
        Random random = new Random();
        int nombreAleatoire = random.nextInt(26);
        if (nombreAleatoire == 1) {
            changeToWorld(new HuntingWorld(dresseur, "Pokemon by antocreadev"));
            stopBackgroundMusic();
        }
    }

    private boolean isPlayerInHuntingWorld() {
        int[] xPoints = { 269, 269, 285, 285, 381, 381, 365, 365 };
        int[] yPoints = { 285, 331, 331, 375, 375, 329, 329, 285 };

        Polygon greenZone = new Polygon(xPoints, yPoints, xPoints.length);

        return greenZone.contains(dresseur.playerX, dresseur.playerY);
    }

    private boolean isInFightZone() {
        int rectX = 184;
        int rectY = 240;
        int rectWidth = 30;
        int rectHeight = 15;

        Rectangle rectangle = new Rectangle(rectX, rectY, rectWidth, rectHeight);

        return rectangle.contains(dresseur.playerX, dresseur.playerY);
    }

    private boolean isInsideRestrictedZone(int x, int y) {
        int[] xPoints = { 144, 144, 254, 254 };
        int[] yPoints = { 154, 239, 239, 154 };

        Polygon restrictedZone = new Polygon(xPoints, yPoints, xPoints.length);

        return restrictedZone.contains(x, y);
    }
}
