package collision;

/**
 * The interface Hit notifier.
 */
public interface HitNotifier {
    //

    /**
     * Add hl as a listener to hit events.
     *
     * @param hl listener to add
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl listener to remove
     */
    void removeHitListener(HitListener hl);
}