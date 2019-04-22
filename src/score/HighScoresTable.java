package score;

import game.Constants;


import java.io.Serializable;
import java.io.File;
import java.io.IOException;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    /**
     * The Scores.
     */
    private List<ScoreInfo> scores;
    private int size;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
// Create an empty high-scores table with the specified size.
    // The size means that the table holds up to size top scores.
    public HighScoresTable(int size) {
        this.size = size;
        this.scores = new ArrayList<ScoreInfo>();
    }

    /**
     * Load from file high scores table.
     *
     * @param fileName the file name
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(String fileName) {
        ObjectInputStream oin = null; // in order to finally to recognize it.
        // if file exist try to read from it
        try {
            FileInputStream fis = new FileInputStream(fileName);
            oin = new ObjectInputStream(fis);
            return (HighScoresTable) oin.readObject();
        } catch (Exception e) {
            // Couldn't find or read the file
            return new HighScoresTable(Constants.HIGHSCORE_TABLE_SIZE);
        } finally {
            // close the ObjectInputStream
            try {
                if (oin != null) {
                    oin.close();
                }
            } catch (Exception e2) {
                System.out.println("Odd problem while reading: " + e2);
            }
        }
    }

    /**
     * Add.
     *
     * @param score the score
     */
// Add a high-score.
    public void add(ScoreInfo score) {
        //full table && score is lower
        if (this.getRank(score.getScore()) > this.size) {
            return;
        }

        List<ScoreInfo> temp = new ArrayList<ScoreInfo>(this.scores);
        temp.add(score);
        temp.sort(this.comparator());
        this.scores.clear();
        //if too much values - apply only the firsts
        if (temp.size() > this.size()) {
            for (int i = temp.size() - 1; i > this.size() - 1; i--) {
                temp.remove(i);
            }
            this.scores = temp;
        } else {
            //not too many values - apply all
            this.scores.addAll(temp);
        }
    }

    /**
     * Size int.
     *
     * @return the int
     */
// Return table size.
    public int size() {
        return this.size;
    }

    /**
     * Gets num of existing scores.
     *
     * @return the num of existing scores
     */
    public int getNumOfExistingScores() {
        return this.scores.size();
    }

    /**
     * Gets high scores.
     *
     * @return the high scores
     */
// Return the current high scores.
    // The list is sorted such that the highest
    // scores come first.
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Gets rank.
     *
     * @param score the score
     * @return the rank
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        //first score
        if (this.scores.isEmpty()) {
            return 1;
        }

        //find rank of next score
        for (int i = 0; i < this.scores.size(); i++) {
            if (score > this.scores.get(i).getScore()) {
                return i + 1;
                //i+1 means the index+1 meaning the actual place in array
            }
        }
        return this.scores.size() + 1;
    }

    /**
     * Is score should be added boolean.
     *
     * @param score the score
     * @return the boolean
     */
    public boolean isScoreShouldBeAdded(int score) {
        int ans = this.getRank(score);
        return !(ans > this.size());
    }

    /**
     * Clear.
     */
// Clears the table
    public void clear() {
        this.scores.clear();
    }

    /**
     * Load.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Load table data from file.
    // Current table data is cleared.
    public void load(File filename) throws IOException {
        this.clear();
        this.scores = HighScoresTable.loadFromFile(filename.getName()).scores;
        this.size = HighScoresTable.loadFromFile(filename.getName()).size;
    }

    /**
     * Save.
     *
     * @param fileName the file name
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(String fileName) throws IOException {
        Collections.sort(this.scores);
        FileOutputStream fos = new FileOutputStream(fileName);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        try {
            oos.writeObject(this);
        } finally {
            if (oos != null) {
                oos.close();
            }
        }
    }

    /**
     * comperator.
     *
     * @return comparator
     */
    private Comparator<ScoreInfo> comparator() {
        return new Comparator<ScoreInfo>() {
            @Override
            public int compare(ScoreInfo scoreInfo, ScoreInfo t1) {
                return scoreInfo.compareTo(t1);
            }
        };
    }


}