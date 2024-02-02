package pt.upskill.projeto1.objects.statusBar;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.statusBar.Fire.Fireball;
import pt.upskill.projeto1.objects.statusBar.Health.HealthGreen;
import pt.upskill.projeto1.objects.statusBar.Health.HealthRed;
import pt.upskill.projeto1.objects.statusBar.Health.HealthRedGreen;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Defines StatusBar object. Provided methods for building the list of
 * tiles needed to populate the status bar.
 *
 */

public class StatusBar implements Serializable {

    private List<ImageTile> healthBar = new ArrayList<>();

    private List<ImageTile> statusTiles = new ArrayList<>();

    public StatusBar() {
        init();
    }

    public List<ImageTile> getHealthBar() {
        return healthBar;
    }

    public void setHealthBar(List<ImageTile> healthBar) {
        this.healthBar = healthBar;
    }

    public List<ImageTile> getStatusTiles() {
        return statusTiles;
    }
    
    public void setStatusTiles(List<ImageTile> statusTiles) {
        this.statusTiles = statusTiles;
    }

    // init() method is used to build the Status Bar from scratch, when a new game is started
    public void init() {
        Hero hero = Hero.getInstance();
        //Build fireballs and inventory items
        for (int i = 0; i < 3; i++) {
            Fireball fireball = hero.getFireballs()[i];
            if (fireball != null) {
                this.getStatusTiles().add(new Black(fireball.getPosition()));
                this.getStatusTiles().add(fireball);
            } else {
                this.getStatusTiles().add(new Black(new Position(i, 0)));
            }
            Item item = hero.getInventory()[i];
            if (item != null) {
                this.getStatusTiles().add(new Black(item.getPosition()));
                this.getStatusTiles().add(item);
            } else
                //Inventory starts at index 7
                this.getStatusTiles().add(new Black(new Position(i + 7, 0)));
        }

        //Build healthbar
        for (int i = 3; i < 7; i++) {
            HealthGreen healthGreen = new HealthGreen(new Position(i, 0));
            this.getStatusTiles().add(healthGreen);
            this.getHealthBar().add(healthGreen);
        }
    }

    // Updates the health bar according to hero HP
    public void updateHealthBar() {
        Hero hero = Hero.getInstance();
        int currentHp = hero.getHp();
        List<ImageTile> newHealthBar = new ArrayList<>();
        switch (currentHp) {
            case 8:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                    newHealthBar.add(healthGreen);
                    this.getStatusTiles().add(healthGreen);
                }
                break;
            case 7:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    if (i < 3) {
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else {
                        HealthRedGreen healthRedGreen = new HealthRedGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRedGreen);
                        this.getStatusTiles().add(healthRedGreen);
                    }
                }
                break;
            case 6:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    if (i < 3) {
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else {
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 5:
                for (int i = 0; i < 4; i++) {
                    if (i < 2) {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else if (i == 2) {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthRedGreen healthRedGreen = new HealthRedGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRedGreen);
                        this.getStatusTiles().add(healthRedGreen);
                    } else {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 4:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    if (i < 2) {
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else {
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 3:
                for (int i = 0; i < 4; i++) {
                    if (i < 1) {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else if (i == 1) {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthRedGreen healthRedGreen = new HealthRedGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRedGreen);
                        this.getStatusTiles().add(healthRedGreen);
                    } else {
                        ImageTile healthTile = this.getHealthBar().get(i);
                        this.getStatusTiles().remove(healthTile);
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 2:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    if (i < 1) {
                        HealthGreen healthGreen = new HealthGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthGreen);
                        this.getStatusTiles().add(healthGreen);
                    } else {
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 1:
                for (int i = 0; i < 4; i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    if (i < 1) {
                        HealthRedGreen healthRedGreen = new HealthRedGreen(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRedGreen);
                        this.getStatusTiles().add(healthRedGreen);
                    } else {
                        HealthRed healthRed = new HealthRed(getHealthBar().get(i).getPosition());
                        newHealthBar.add(healthRed);
                        this.getStatusTiles().add(healthRed);
                    }
                }
                break;
            case 0:
                for (int i = 0; i < getHealthBar().size(); i++) {
                    ImageTile healthTile = this.getHealthBar().get(i);
                    this.getStatusTiles().remove(healthTile);
                    HealthRed healthRed = new HealthRed(healthTile.getPosition());
                    newHealthBar.add(healthRed);
                    this.getStatusTiles().add(healthRed);
                }
                break;
        }
        this.setHealthBar(newHealthBar);
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.newStatusImages(newHealthBar);
    }

    // Rebuilds the status bar (used on game load)
    public void rebuildStatusBar() {
        //Reset status tiles
        this.setStatusTiles(new ArrayList<>());
        Hero hero = Hero.getInstance();
        //Build fireballs and inventory items
        for (int i = 0; i < 3; i++) {
            Fireball fireball = hero.getFireballs()[i];
            if (fireball != null) {
                this.getStatusTiles().add(new Black(fireball.getPosition()));
                this.getStatusTiles().add(fireball);
            } else {
                this.getStatusTiles().add(new Black(new Position(i, 0)));
            }
            Item item = hero.getInventory()[i];
            if (item != null) {
                this.getStatusTiles().add(new Black(item.getPosition()));
                this.getStatusTiles().add(item);
            } else
                //Inventory starts at index 7
                this.getStatusTiles().add(new Black(new Position(i + 7, 0)));
        }
        //Build health bar
        this.updateHealthBar();
    }

}
