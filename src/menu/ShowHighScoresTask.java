package menu;

import animation.Animation;
import animation.AnimationRunner;

/**
 * The type Show high scores task.
 */
public class ShowHighScoresTask implements Task<Void> {

    private AnimationRunner runner;
    private Animation highScoresAnimation;

    /**
     * Instantiates a new Show high scores task.
     *
     * @param runner              the runner
     * @param highScoresAnimation the high scores animation
     */
    public ShowHighScoresTask(AnimationRunner runner, Animation highScoresAnimation) {
        this.runner = runner;
        this.highScoresAnimation = highScoresAnimation;
    }

    /**
     * runs.
     * @return null
     */
    public Void run() {
        this.runner.run(this.highScoresAnimation);
        return null;
    }
}
