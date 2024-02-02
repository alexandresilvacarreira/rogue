package pt.upskill.projeto1.game;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.rogue.utils.Direction;
import java.awt.event.KeyEvent;


public class Engine {
    public void init() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearImages();
        gui.clearStatus();
        Hero hero = Hero.getInstance();
        gui.newStatusImages(hero.getStatusBar().getStatusTiles());
        gui.newImages(hero.getCurrentRoom().getRoomTiles());
        gui.setEngine(this);
        gui.go();
        gui.setStatus("Score: " + hero.getScore());
        GameManager.getInstance().setGameRunning(true);
    }

    public void notify(int keyPressed) {
        Hero hero = Hero.getInstance();
        if (keyPressed == KeyEvent.VK_DOWN) {
            hero.evaluate(Direction.DOWN);
        }
        if (keyPressed == KeyEvent.VK_UP) {
            hero.evaluate(Direction.UP);
        }
        if (keyPressed == KeyEvent.VK_LEFT) {
            hero.evaluate(Direction.LEFT);
        }
        if (keyPressed == KeyEvent.VK_RIGHT) {
            hero.evaluate(Direction.RIGHT);
        }
        if (keyPressed == KeyEvent.VK_1) {
            hero.dropOrUseItem(0);
        }
        if (keyPressed == KeyEvent.VK_2) {
            hero.dropOrUseItem(1);
        }
        if (keyPressed == KeyEvent.VK_3) {
            hero.dropOrUseItem(2);
        }
        if (keyPressed == KeyEvent.VK_SPACE) {
            hero.throwFireball();
        }
        if (keyPressed == KeyEvent.VK_ESCAPE) {
            GameManager.getInstance().openPauseMenu();
        }
        // Forcing update here to smooth animations, instead of doing it on init(),
        // otherwise we run into troubles with the loop when loading game from a save
        ImageMatrixGUI.getInstance().update();
    }


    public static void main(String[] args) {
        Engine engine = new Engine();
        GameManager.getInstance().welcome(engine);
    }

}
