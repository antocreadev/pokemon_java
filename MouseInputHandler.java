import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInputHandler implements MouseListener {
    private int mouseX;
    private int mouseY;
    private boolean mouseClicked;

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public boolean isMouseClicked() {
        if (mouseClicked) {
            // Réinitialiser mouseClicked après l'avoir lu
            mouseClicked = false;
            return true;
        }
        return false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
        mouseClicked = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Implement if needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Implement if needed
    }
}
