import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameEngine implements Engine, KeyListener {
    DynamicSprite hero;
    Runnable escCallback;
    private boolean ctrlPressed = false; //Track if CTRL is pressed
    private boolean speedDoubled = false; // Flag to prevent multiple changes to speed

    public GameEngine(DynamicSprite hero) {
        this.hero = hero;
    }

    //Method to set Esc key to callback
    public void addEscListener(Runnable callback){
        this.escCallback = callback;
    }

    @Override
    public void update() {
        // Update speed based on CTRL key status
        if (ctrlPressed && !speedDoubled) {
            hero.setSpeed(hero.getSpeed() * 2); // Double the speed
            speedDoubled = true; // Prevent further doubling until released
        } else if (!ctrlPressed && speedDoubled) {
            hero.setSpeed(hero.getSpeed() / 2); // Reset to normal speed
            speedDoubled = false; // Reset flag
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP :
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_ESCAPE:
                //Call the callback is Esc is pressed
                if(escCallback != null){
                    escCallback.run();
                }
                break;
            case KeyEvent.VK_CONTROL:
                // Ctrl is being held down, double speed
                ctrlPressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_CONTROL){
            // Reset speed when CTRL is pressed
            ctrlPressed = false;
        }
    }
}
