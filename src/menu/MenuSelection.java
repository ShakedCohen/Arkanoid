package menu;

/**
 * The type Menu selection.
 *
 * @param <T> the type parameter
 */
public class MenuSelection<T> {
    private String keyToWaitFor;
    private String lineToPrint;
    private T whatToReturn;


    /**
     * Instantiates a new Menu selection.
     *
     * @param keyToWaitFor the key to wait for
     * @param lineToPrint  the line to print
     * @param whatToReturn the what to return
     */
    public MenuSelection(String keyToWaitFor, String lineToPrint, T whatToReturn) {
        this.keyToWaitFor = keyToWaitFor;
        this.lineToPrint = lineToPrint;
        this.whatToReturn = whatToReturn;
    }


    /**
     * Gets key to wait for.
     *
     * @return the key to wait for
     */
    public String getKeyToWaitFor() {
        return keyToWaitFor;
    }

//    public void setKeyToWaitFor(String keyToWaitFor) {
//        this.keyToWaitFor = keyToWaitFor;
//    }

    /**
     * Gets line to print.
     *
     * @return the line to print
     */
    public String getLineToPrint() {
        return lineToPrint;
    }

//    public void setLineToPrint(String lineToPrint) {
//        this.lineToPrint = lineToPrint;
//    }

    /**
     * Gets what to return.
     *
     * @return the what to return
     */
    public T getWhatToReturn() {
        return whatToReturn;
    }

//    public void setWhatToReturn(T whatToReturn) {
//        this.whatToReturn = whatToReturn;
//    }
}
