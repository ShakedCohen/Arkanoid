package game;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import gameobjects.Counter;
import levels.LevelInformation;
import score.HighScoresTable;
import score.ScoreInfo;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {

    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private Counter numberOfLives = new Counter(Constants.LIVES);
    private Counter score = new Counter(0);
    private HighScoresTable highScores = new HighScoresTable(Constants.HIGHSCORE_TABLE_SIZE);
    private DialogManager dialogManager;


    /**
     * Instantiates a new Game flow.
     *
     * @param animationRunner1 the animation runner 1
     * @param keyboardSensor1  the keyboard sensor 1
     * @param dialogManager1   the dialog manager 1
     */
    public GameFlow(AnimationRunner animationRunner1, KeyboardSensor keyboardSensor1, DialogManager dialogManager1) {
        this.animationRunner = animationRunner1;
        this.keyboardSensor = keyboardSensor1;
        this.dialogManager = dialogManager1;
        try {
            this.highScores.load(new File(Constants.HIGHSCORES_FILE_NAME));
        } catch (IOException e) {
            throw new RuntimeException("you know");
        }
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {

        for (LevelInformation levelInfo : levels) {

            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner, numberOfLives, score);
            level.initialize();

            while (this.numberOfLives.getValue() > 0 && level.getRemainingBlocks().getValue() > 0) {
                level.playOneTurn();
            }

            if (this.numberOfLives.getValue() <= 0) {
                break;
            }

        }

        if (this.highScores.isScoreShouldBeAdded(this.score.getValue())) {

            String name = this.dialogManager.showQuestionDialog("Name", "What is your name?", "");
            this.highScores.add(new ScoreInfo(name, this.score.getValue()));
            try {
                this.highScores.save(Constants.HIGHSCORES_FILE_NAME);
            } catch (IOException e) {
                throw new RuntimeException("you know");
            }
        }

        animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, KeyboardSensor.SPACE_KEY, new EndScreen(score, numberOfLives)));

        animationRunner.run(new KeyPressStoppableAnimation(
                this.keyboardSensor, KeyboardSensor.SPACE_KEY, new HighScoresAnimation(this.highScores)));
    }
}