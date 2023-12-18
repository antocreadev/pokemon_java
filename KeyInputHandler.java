import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInputHandler implements KeyListener {

    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean upPressed = false;
    private boolean downPressed = false;
    private boolean yPressed = false;
    private boolean nPressed = false;
    private boolean sPressed = false;

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isUpPressed() {
        return upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public boolean isYPressed() {
        if (yPressed) {
            yPressed = false;  // Réinitialiser après l'avoir lu
            return true;
        }
        return false;
    }

    public boolean isNPressed() {
        if (nPressed) {
            nPressed = false;  // Réinitialiser après l'avoir lu
            return true;
        }
        return false;
    }

    public boolean isSPressed() {
        if (sPressed) {
            sPressed = false;  // Réinitialiser après l'avoir lu
            return true;
        }
        return false;
    }

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
            case KeyEvent.VK_Y:
                yPressed = pressed;
                break;
            case KeyEvent.VK_N:
                nPressed = pressed;
                break;
            case KeyEvent.VK_S:
                sPressed = pressed;
                break;
        }
    }
}
