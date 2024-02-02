package pt.upskill.projeto1.game;

import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.Items.Weapons.Weapon;
import pt.upskill.projeto1.rooms.Room;
import pt.upskill.projeto1.rooms.RoomManager;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.characters.Hero;

import java.io.*;
import java.util.*;

/**
 *
 * Class for managing the game. Includes methods for starting a new game, saving & loading game files,
 * creating & reading the leaderboard file, as well as methods for displaying relevant interfaces
 * (e.g.: pause menu, through which the player can save/load games, check the leaderboard, check the hero status, etc.)
 *
 * Implements the Singleton pattern.
 *
 */

public class GameManager implements Serializable {

    private static final GameManager INSTANCE = new GameManager();

    private Engine engine;

    private boolean isGameRunning = false;

    private GameManager() {
    }

    public static GameManager getInstance() {
        return INSTANCE;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    public boolean isGameRunning() {
        return isGameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        isGameRunning = gameRunning;
    }

    public void welcome(Engine engine) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        this.setEngine(engine);
        Boolean invalidInput = true;
        try {
            while (invalidInput) {
                String playerInput = gui.showInputDialog("Welcome!", "Welcome! What would you like to do?\n\n1. New game\n\n2. Load game\n\n3. Tutorial\n\n");
                switch (playerInput) {
                    case "1":
                        this.newGame();
                        invalidInput = false;
                        break;
                    case "2":
                        this.loadGame();
                        invalidInput = false;
                        break;
                    case "3":
                        this.showControls();
                        break;
                    default:
                        gui.showMessage("Invalid input!", "Invalid key, please try again.");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Exiting...");
            System.exit(0);
        }
    }

    public void newGame() {
        Hero hero = Hero.getInstance();
        RoomManager roomManager = hero.getRoomManager();
        File[] roomFiles = new File("rooms/").listFiles();
        for (File roomFile :
                roomFiles) {
            try {
                roomManager.buildRoom(roomFile);
            } catch (FileNotFoundException e) {
                System.out.println("Room file not found!");
                e.printStackTrace();
            }
        }
        if (hero.getPosition() == null) {
            throw new NullPointerException("No hero found in room files. Please provide hero position in first room file.");
        }
        String roomName = roomFiles[0].getName();
        //Assuming the first room in the folder (alphabetically sorted) is the starting point
        Room firstRoom = roomManager.getRooms().get(roomName);
        hero.init(firstRoom);
        this.getEngine().init();
    }

    public List<Score> readLeaderboard() {
        List<Score> leaderboard = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("gameData/leaderboard/leaderboard.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            leaderboard = (List<Score>) in.readObject();
            in.close();
            fileIn.close();
        } catch (IOException e) {
            System.out.println("File not found, created a new leaderboard.dat file");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            ImageMatrixGUI.getInstance().showMessage("Error!", "Error converting leaderboard file!");
        }
        return leaderboard;
    }

    public List<Score> saveToLeaderboard(Score score) {
        //Read even if there's no file, it'll create it and give us an empty list
        List<Score> leaderboard = readLeaderboard();
        leaderboard.add(score);
        //Sort according to score
        Collections.sort(leaderboard);
        //I only want top 5
        if (leaderboard.size() > 5) {
            leaderboard.remove(leaderboard.size() - 1);
        }
        for (int i = 0; i < leaderboard.size(); i++) {
            leaderboard.get(i).setIndex(i + 1);
        }
        try {
            FileOutputStream fileOut = new FileOutputStream("gameData/leaderboard/leaderboard.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(leaderboard);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            ImageMatrixGUI.getInstance().showMessage("Error!", "Error writing to leaderboard file!");
        }
        return leaderboard;
    }

    public void youWon() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Hero hero = Hero.getInstance();
        String playerName = gui.showInputDialog("You won!", "Congratulations, you beat the game! \n\nYour score: " + hero.getScore() + "\n\nEnter your name:\n\n");
        if (playerName == null) {
            playerName = "(no name provided)";
        }
        Score score = new Score(playerName, hero.getScore());
        List<Score> updatedLeaderboard = saveToLeaderboard(score);
        String formattedLeaderboard = "";
        for (Score s :
                updatedLeaderboard) {
            formattedLeaderboard += s + "\n\n";
        }
        gui.showMessage("Leaderboard", "Top 5: \n\n" + formattedLeaderboard);
        boolean invalidInput = true;
        try {
            while (invalidInput) {
                String playerInput = gui.showInputDialog("Play again?", "Play again?\n\n1. Yes\n\n2. No (exit)\n\n");
                if (playerInput.equals("1")) {
                    this.newGame();
                    invalidInput = false;
                } else if (playerInput.equals("2")) {
                    System.out.println("Exiting...");
                    System.exit(0);
                } else
                    gui.showMessage("Invalid input!", "Invalid input, please try again.");
            }
        } catch (NullPointerException e) {
            System.out.println("Game won! Exiting...");
            System.exit(0);
        }
    }

    public void gameOver() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        boolean invalidInput = true;
        try {
            while (invalidInput) {
                String playerInput = gui.showInputDialog("Game over", "You died! Your score: " + Hero.getInstance().getScore() +
                        "\n\nWhat would you like to do?\n" +
                        "\n1. Start a new game\n" +
                        "\n2. Load saved game\n" +
                        "\n3. Quit\n\n");
                switch (playerInput) {
                    case "1":
                        this.newGame();
                        invalidInput = false;
                        break;
                    case "2":
                        this.loadGame();
                        invalidInput = false;
                        break;
                    case "3":
                        System.out.println("Exiting...");
                        System.exit(0);
                    default:
                        gui.showMessage("Invalid input!", "Invalid input, please try again.");
                }
            }
        } catch (NullPointerException e2) {
            System.out.println("Game over! Exiting...");
            System.exit(0);
        }
    }

    public void saveGame() {
        try {
            FileOutputStream fileOut = new FileOutputStream("gameData/saves/save.dat");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(Hero.getInstance());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            System.out.println("Error saving file!");
            e.printStackTrace();
        }
        ImageMatrixGUI.getInstance().showMessage("Success!", "Game saved!");
    }

    public void loadGame() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        try {
            FileInputStream fileIn = new FileInputStream("gameData/saves/save.dat");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            Hero hero = (Hero) in.readObject();
            in.close();
            fileIn.close();
            Room currentRoom = hero.getCurrentRoom();
            currentRoom.getRoomTiles().add(hero);
            this.getEngine().init();
        } catch (IOException e) {
            boolean invalidInput = true;
            try {
                while (invalidInput) {
                    String userInput = gui.showInputDialog("No game data found", "No save data found, start a new game?\n\n1. Yes\n\n2. No\n\n");
                    if (userInput.equals("1")) {
                        this.newGame();
                        invalidInput = false;
                    } else if (userInput.equals("2")) {
                        if (!this.isGameRunning() || Hero.getInstance().getHp() == 0) {
                            System.out.println("Exiting...");
                            System.exit(0);
                        }
                        invalidInput = false;
                    } else
                        gui.showMessage("Invalid input!", "Invalid input, please try again.");
                }
            } catch (NullPointerException e2) {
                if (!this.isGameRunning() || Hero.getInstance().getHp() == 0) {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error converting save file!");
            e.printStackTrace();
        }
    }

    public void showLeaderboard() {
        List<Score> leaderboard = readLeaderboard();
        String formattedLeaderboard = "";
        for (Score s :
                leaderboard) {
            formattedLeaderboard += s + "\n\n";
        }
        ImageMatrixGUI.getInstance().showMessage("Leaderboard", "Top 5: \n\n" + formattedLeaderboard);
    }

    public void openPauseMenu() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        boolean invalidInput = true;
        try {
            while (invalidInput) {
                String userInput = gui.showInputDialog("Pause", "What would you like to do?\n" +
                        "\n1. Save game\n" +
                        "\n2. Load game\n" +
                        "\n3. New game\n" +
                        "\n4. Show hero status\n" +
                        "\n5. Check leaderboard\n" +
                        "\n6. Tutorial\n" +
                        "\n7. Quit\n\n");
                switch (userInput) {
                    case "1": {
                        this.saveGame();
                        invalidInput = false;
                        break;
                    }
                    case "2": {
                        this.loadGame();
                        invalidInput = false;
                        break;
                    }
                    case "3": {
                        boolean invalid = true;
                        try {
                            while (invalid) {
                                String confirmNewGame = gui.showInputDialog("New game?", "Start a new game?\n\n1. Yes\n\n2. No\n\n");
                                if (confirmNewGame.equals("1")) {
                                    this.newGame();
                                    invalid = invalidInput = false;
                                } else if (confirmNewGame.equals("2")) {
                                    invalid = invalidInput = false;
                                } else {
                                    gui.showMessage("Invalid input", "Invalid key, please try again!");
                                }
                            }
                        } catch (NullPointerException e) {
                            //Player closed the window, carry on
                        }
                        break;
                    }
                    case "4": {
                        this.showHeroStatus();
                        break;
                    }
                    case "5": {
                        this.showLeaderboard();
                        break;
                    }
                    case "6": {
                        this.showControls();
                        break;
                    }
                    case "7": {
                        boolean invalid = true;
                        try {
                            while (invalid) {
                                String confirmQuit = gui.showInputDialog("Quit game?", "Are you sure you want to quit?\n\n1. Yes\n\n2. No\n\n");
                                if (confirmQuit.equals("1")) {
                                    System.out.println("Exiting...");
                                    System.exit(0);
                                } else if (confirmQuit.equals("2")) {
                                    invalid = invalidInput = false;
                                } else {
                                    gui.showMessage("Invalid input", "Invalid key, please try again!");
                                }
                            }
                        } catch (NullPointerException e) {
                            //Player closed the window, carry on
                        }
                        break;
                    }
                    default:
                        gui.showMessage("Invalid input!", "Invalid key, please try again.");
                }
            }
        } catch (NullPointerException e) {
            //Carry on
        }
    }

    public void showControls() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        try {
            gui.showMessage("Tutorial", "- Press the arrow keys to move\n" +
                    "\n- Attack enemies by moving onto them\n" +
                    "\n- Press the spacebar to throw fireballs\n" +
                    "\n- Interact with items by moving to their tiles\n" +
                    "\n- Press 1,2 or 3 to drop/use items\n" +
                    "\n- Open closed doors by moving onto them, with the correct key in the inventory\n" +
                    "\n- Press ESC to open the pause menu\n" +
                    "\n - Win the game by making it to the treasure room!\n\n");
        } catch (NullPointerException e) {
            //Exception comes from closing the popup, carry on
        }
    }

    public void showHeroStatus() {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Hero hero = Hero.getInstance();
        Item[] inventory = hero.getInventory();
        String inventoryItems = "";
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                inventoryItems += "\n> " + inventory[i];
            }
        }
        String weaponsEquipped = "";
        for (Weapon weapon :
                hero.getWeaponsWielded()) {
            weaponsEquipped += "\n> " + weapon;
        }
        try {
            gui.showMessage("Hero status",
                    "Current HP: \n" + hero.getHp() + "\n\n" +
                            "Current damage: \n" + hero.getDamage() + "\n\n" +
                            "Current score: \n" + hero.getScore() + "\n\n" +
                            "Items in inventory: \n" + inventoryItems + "\n\n" +
                            "Weapons equipped: \n" + weaponsEquipped + "\n\n"

            );
        } catch (NullPointerException e) {
            //Exception comes from closing the popup, carry on
        }
    }

}
