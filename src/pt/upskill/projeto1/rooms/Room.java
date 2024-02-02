package pt.upskill.projeto1.rooms;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.characters.enemies.Enemy;
import pt.upskill.projeto1.objects.environment.Doors.Door;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 *
 * Defines Room object. A room is essentially a list of ImageTiles.
 * This class provides methods for getting and managing tiles in a given room.
 *
 */

public class Room implements Serializable {

    private String name;
    private List<Enemy> enemies;
    private List<ImageTile> roomTiles;
    private List<Door> doors;

    public Room(String name, List<Enemy> enemies, List<ImageTile> roomTiles, List<Door> doors) {
        this.name = name;
        this.enemies = enemies;
        this.roomTiles = roomTiles;
        this.doors = doors;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public List<ImageTile> getRoomTiles() {
        return roomTiles;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    // Method getTile() returns the tile corresponding to the input position
    // It has two implementations, one in which the hero is included as a possible result, and one in which it isn't
    // Version 1 -> with the hero accepted as result tile
    public ImageTile getTile(Position position) {
        List<ImageTile> roomTiles = this.getRoomTiles();
        ImageTile tile = null;
        for (int i = 0; i < roomTiles.size(); i++) {
            ImageTile candidateTile = roomTiles.get(i);
            if (candidateTile.getPosition().equals(position)) {
                tile = roomTiles.get(i);
            }
        }
        return tile;
    }

    // Version 2 -> without the hero accepted as result tile
    // It's only used in case I want to access a null tile, i.e., changing rooms
    public ImageTile getTile(Position position, boolean bool) {
        List<ImageTile> roomTiles = this.getRoomTiles();
        ImageTile tile = null;
        for (int i = 0; i < roomTiles.size(); i++) {
            ImageTile candidateTile = roomTiles.get(i);
            //I only want non-hero tiles (so I can get what's underneath the hero)
            if (candidateTile.getPosition().equals(position) && !(candidateTile instanceof Hero)) {
                tile = roomTiles.get(i);
            }
        }
        return tile;
    }

    public void clearTile(ImageTile tile){
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        this.getRoomTiles().remove(tile);
        gui.removeImage(tile);
        if (tile instanceof Enemy){
            this.getEnemies().remove(tile);
        }
    }

    public void addItem(Item itemToDrop, Position position) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        itemToDrop.setPosition(position);
        this.getRoomTiles().add(itemToDrop);
        // Sort tiles list, so that enemies are on top of items
        TileInstanceCompare tileInstanceCompare = new TileInstanceCompare();
        Collections.sort(this.getRoomTiles(), tileInstanceCompare);
        gui.addImage(itemToDrop);
        this.getEnemies().forEach(enemy -> gui.removeImage(enemy));
        gui.newImages(this.getEnemies());
        gui.addImage(Hero.getInstance());
    }

    public void addTile(ImageTile tile) {
        this.getRoomTiles().add(tile);
        TileInstanceCompare tileInstanceCompare = new TileInstanceCompare();
        Collections.sort(this.getRoomTiles(), tileInstanceCompare);
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.clearImages();
        gui.newImages(this.getRoomTiles());
    }

}
