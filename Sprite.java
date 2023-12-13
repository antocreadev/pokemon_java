import java.awt.Image;
import javax.swing.ImageIcon;

public class Sprite {

    private Image[] sprites;
    private int currentSpriteIndex;
    private String direction;

    public Sprite(String spritePath, int numFrames, String direction) {
        this.direction = direction;
        sprites = new Image[numFrames];
        loadSprites(spritePath);
        currentSpriteIndex = 0;
    }

    private void loadSprites(String spritePath) {
        for (int i = 0; i < sprites.length; i++) {
            String imagePath = spritePath + this.direction + "/" + i + ".png";
            ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
            sprites[i] = icon.getImage();
        }
    }
    

    public Image getCurrentSprite() {
        return sprites[currentSpriteIndex];
    }

    public void updateSprite(String SetDirection) {
        loadSprites("/img/character/");
        currentSpriteIndex = (currentSpriteIndex == 0) ? 1 : 0;
        direction = SetDirection;
    }
    
}
