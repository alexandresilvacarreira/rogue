package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.environment.Bones;

/**
 *
 * Thread for displaying bones on enemy death.
 *
 */

public class BonesThread extends Thread {

    private Bones bones;

    public BonesThread(Bones bones) {
        this.bones = bones;
    }

    @Override
    public void run() {
        try {
            ImageMatrixGUI.getInstance().addImage(this.bones);
            sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ImageMatrixGUI.getInstance().removeImage(this.bones);
    }
}
