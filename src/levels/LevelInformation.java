package levels;

import gameobjects.Block;
import gameobjects.Velocity;
import sprite.Sprite;

import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Number of balls int.
     *
     * @return the int
     */
    int numberOfBalls();

    // Note that initialBallVelocities().size() == numberOfBalls()

    /**
     * The initial velocity of each ball.
     * Initial ball velocities list.
     *
     * @return the list
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed int.
     *
     * @return paddle speed.
     */
    int paddleSpeed();

    /**
     * Paddle width int.
     *
     * @return paddle width.
     */
    int paddleWidth();

    // the level name will be displayed at the top of the screen.

    /**
     * Level name string.
     *
     * @return the level name.
     */
    String levelName();


    /**
     * Gets background.
     *
     * @return Returns a sprite with the background of the level.
     */
    Sprite getBackground();


    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return list
     */
    List<Block> blocks();


    /**
     * Number of blocks that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return Number of blocks that should be removed
     */
    int numberOfBlocksToRemove();

}