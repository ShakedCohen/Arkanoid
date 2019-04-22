package collision;

import game.GameLevel;
import gameobjects.Ball;
import gameobjects.Block;
import gameobjects.Counter;

/**
 * The type Ball remover.
 */
public class BallRemover implements HitListener {

    //that will be in charge of removing balls, and updating an availabe-balls counter.

    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel      the game level
     * @param remainingBalls the remaining balls
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }

    /**
     * This method is called whenever the beingHit object is hit.
     *
     * @param beingHit the block thats being hit
     * @param hitter   The hitter parameter is the gameobjects.Ball that's doing the hitting.
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.gameLevel);
        hitter.removeHitListener(this);
        this.remainingBalls.decrease(1);

    }


}
