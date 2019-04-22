package main;

import animation.AnimationRunner;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.Constants;
import io.LevelSetsReader;
import levels.LevelInformation;

import menu.Task;
import menu.Menu;
import menu.MenuAnimation;
import menu.QuitGameTask;
import menu.ShowHighScoresTask;
import menu.StartGameSetTask;
import score.HighScoresTable;

import java.io.Reader;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * the ASS6Game class.
 * runs the game.
 */
public class Ass6Game {

    /**
     * main that runs the program.
     * 0 is not a valid level
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        try {

            Menu<Task<Void>> mainMenu;

            GUI gui = new GUI("Arkanoid", Constants.GUI_WIDTH, Constants.GUI_HEIGHT);

            AnimationRunner runner = new AnimationRunner(gui, Constants.FRAMES_PER_SECOND);
            KeyboardSensor sensor = gui.getKeyboardSensor();
            DialogManager dManager = gui.getDialogManager();
            String fileLocation;

            if (args.length == 0) {
                fileLocation = "level_sets.txt";
            } else {
                fileLocation = args[0];
            }


            while (true) {
                mainMenu = initMenu(runner, sensor, dManager, fileLocation);
                runner.run(mainMenu);

                // wait for user selection
                Task<Void> task = mainMenu.getStatus();

                task.run();
            }

        } catch (IOException i) {
            System.out.println("problem with IO - check your file");
            System.exit(1);
        }
//        catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.exit(2);
//        }

    }

    /**
     * initiates the menu.
     * @param runner animation runner
     * @param sensor the keyboard sensor
     * @param dManager the dialog manager
     * @param fileLocation the file location
     * @return returns a menu
     * @throws IOException if there is problem with file
     */
    private static Menu<Task<Void>> initMenu(AnimationRunner runner, KeyboardSensor sensor,
                                             DialogManager dManager, String fileLocation) throws IOException {

        //this part reads the levels from level def file
        List<LevelInformation> levels;

        //now lets read level set file...

        InputStream is = null;
        is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileLocation);
        Reader reader = new BufferedReader(new InputStreamReader(is));

        LevelSetsReader levelSetsReader = new LevelSetsReader();
        Map<Character, List<LevelInformation>> levelSetsMap = levelSetsReader.fromReader(reader);

        MenuAnimation<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("choose level set", runner, sensor);

        Set<Character> keySet = levelSetsMap.keySet();

        for (Character representorKey : keySet) {
            levelSetsMap.get(representorKey);
            String lSetDescription = levelSetsReader.getDescription(representorKey);
            Task<Void> curLevelSetTask = new StartGameSetTask(levelSetsMap.get(representorKey)
                    , runner, sensor, dManager);

            subMenu.addSelection(("" + representorKey), lSetDescription, curLevelSetTask);

        }


        Menu<Task<Void>> mainMenu = new MenuAnimation<Task<Void>>("Arkanoid", runner, sensor);

        mainMenu.addSubMenu("s", "Start Game", subMenu);

        mainMenu.addSelection("h", "High Scores",
                new ShowHighScoresTask(runner, new KeyPressStoppableAnimation(
                        sensor, KeyboardSensor.SPACE_KEY, new HighScoresAnimation(loadHighScores()))));

        mainMenu.addSelection("q", "quit", new QuitGameTask());
        return mainMenu;
    }

    /**
     * try to load highscore table.
     * @return highscore table
     */
    private static HighScoresTable loadHighScores() {

        HighScoresTable highScores = new HighScoresTable(Constants.HIGHSCORE_TABLE_SIZE);

        try {
            highScores.load(new File(Constants.HIGHSCORES_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("you know");
        }



        return highScores;
    }

}
