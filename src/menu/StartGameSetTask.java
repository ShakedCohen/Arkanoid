package menu;

import animation.AnimationRunner;
import biuoop.DialogManager;
import biuoop.KeyboardSensor;
import game.GameFlow;
import levels.LevelInformation;

import java.util.List;

/**
 * The type Start game set task.
 */
public class StartGameSetTask implements Task<Void> {


    private List<LevelInformation> levelSet;


    private AnimationRunner runner;
    private KeyboardSensor sensor;
    private DialogManager dManager;



    /**
     * Instantiates a new Start game set task.
     *
     * @param levelSet the level set
     * @param runner   the runner
     * @param sensor   the sensor
     * @param dManager the d manager
     */
    public StartGameSetTask(List<LevelInformation> levelSet, AnimationRunner runner,
                            KeyboardSensor sensor, DialogManager dManager) {
        this.levelSet = levelSet;
        this.runner = runner;
        this.sensor = sensor;
        this.dManager = dManager;

    }

    @Override
    public Void run() {
        GameFlow game = new GameFlow(this.runner, this.sensor, this.dManager);
        game.runLevels(this.levelSet);
        return null;
    }


}


