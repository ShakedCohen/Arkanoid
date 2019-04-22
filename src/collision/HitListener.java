package collision;

import gameobjects.Ball;
import gameobjects.Block;

/**
 * The interface Hit listener.
 */
public interface HitListener {

    /**
     * This method is called whenever the beingHit object is hit.
     * first dec the hit points, then calls the hit event
     *
     * @param beingHit the block thats being hit
     * @param hitter   The hitter parameter is the gameobjects.Ball that's doing the hitting.
     */
    void hitEvent(Block beingHit, Ball hitter);
}