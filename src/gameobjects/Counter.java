package gameobjects;

/**
 * The type Counter.
 */
public class Counter {
    /**
     * The Value.
     */
    private int value;

    /**
     * Instantiates a new Counter.
     */
// add number to current count.
    public Counter() {
        this.value = 0;
    }

    /**
     * Instantiates a new Counter.
     *
     * @param n the n
     */
    public Counter(int n) {
        this.value = n;
    }

    /**
     * Increase.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Decrease.
     *
     * @param number the number
     */
// subtract number from current count.
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Gets value.
     *
     * @return the value
     */
    // get current count.
    public int getValue() {
        return this.value;
    }
}