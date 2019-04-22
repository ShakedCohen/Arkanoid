package menu;

/**
 * The type Quit game task.
 */
public class QuitGameTask implements Task<Void> {


    @Override
    public Void run() {
        System.exit(0);
        return null;
    }
}
