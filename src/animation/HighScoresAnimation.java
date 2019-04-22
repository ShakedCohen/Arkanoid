package animation;

import biuoop.DrawSurface;
import score.HighScoresTable;

import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {

    private score.HighScoresTable table;


    /**
     * Instantiates a new High scores animation.
     *
     * @param table the table
     */
    public HighScoresAnimation(HighScoresTable table) {
        this.table = table;

    }

    /**
     * draw one frame of he animation.
     *
     * @param d  the drawsurface
     * @param dt the dt
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {

        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
        d.setColor(Color.BLACK);
        d.drawText(51, 50, "High Scores", 32);
        d.drawText(49, 50, "High Scores", 32);
        d.drawText(50, 51, "High Scores", 32);
        d.drawText(50, 49, "High Scores", 32);
        d.setColor(Color.YELLOW);
        d.drawText(50, 50, "High Scores", 32);
        d.setColor(Color.BLACK);
        d.drawText(101, 120, "Player Name", 24);
        d.drawText(99, 120, "Player Name", 24);
        d.drawText(100, 121, "Player Name", 24);
        d.drawText(100, 119, "Player Name", 24);
        d.setColor(Color.GREEN);
        d.drawText(100, 120, "Player Name", 24);
        d.setColor(Color.BLACK);
        d.drawText(451, 120, "Score", 24);
        d.drawText(449, 120, "Score", 24);
        d.drawText(450, 121, "Score", 24);
        d.drawText(450, 119, "Score", 24);
        d.setColor(Color.GREEN);
        d.drawText(450, 120, "Score", 24);
        d.setColor(Color.BLACK);
        d.drawLine(100, 130, 700, 130);
        d.setColor(Color.GREEN);
        d.drawLine(100, 131, 700, 131);
        d.setColor(Color.BLACK);
        d.drawLine(100, 132, 700, 132);
        d.setColor(Color.BLACK);

        for (int i = 0; i < this.table.getNumOfExistingScores(); ++i) {
            String curName = this.table.getHighScores().get(i).getName();
            int curScore = this.table.getHighScores().get(i).getScore();

            int nameX = 100;
            int scoreX = 450;
            int entryY = 170 + i * 40;
            d.setColor(Color.BLACK);
            d.drawText(nameX + 1, entryY, curName, 24);
            d.drawText(nameX - 1, entryY, curName, 24);
            d.drawText(nameX, entryY + 1, curName, 24);
            d.drawText(nameX, entryY - 1, curName, 24);
            d.setColor(Color.ORANGE);
            d.drawText(nameX, entryY, curName, 24);
            d.setColor(Color.BLACK);
            d.drawText(scoreX + 1, entryY, "" + curScore, 24);
            d.drawText(scoreX - 1, entryY, "" + curScore, 24);
            d.drawText(scoreX, entryY + 1, "" + curScore, 24);
            d.drawText(scoreX, entryY - 1, "" + curScore, 24);
            d.setColor(Color.ORANGE);
            d.drawText(scoreX, entryY, "" + curScore, 24);
        }
        d.setColor(Color.BLACK);
        d.drawText(200, 500, "Press space to continue", 32);


    }

    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    @Override
    public boolean shouldStop() {
        return false;
    }
}