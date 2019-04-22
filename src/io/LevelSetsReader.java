package io;

import levels.LevelInformation;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Level sets reader.
 */
public class LevelSetsReader {

    private Map<Character, String> keyDescriptionMap = new HashMap<Character, String>();
    private Map<Character, List<LevelInformation>> levelSetsMap = new HashMap<Character, List<LevelInformation>>();


    /**
     * From reader map.
     *
     * @param reader the reader
     * @return the map
     * @throws IOException the io exception
     */
    public Map<Character, List<LevelInformation>> fromReader(Reader reader) throws IOException {


        String line;
        LineNumberReader lReader = new LineNumberReader(reader);
        LevelSpecificationReader l = new LevelSpecificationReader();
        Character key;

        while ((line = lReader.readLine()) != null) {


            //odd lines
            if (lReader.getLineNumber() % 2 == 1) {
                line = line.trim();

                List<String> keyValueList = l.splitBy(line, ":");
                key = keyValueList.get(0).charAt(0);
                this.addDescpription(key, keyValueList.get(1));

                //even line

                line = lReader.readLine();
                if (line == null) {
                    throw new RuntimeException("odd line at the end of level set file");
                }
                line = line.trim();
                String pathToLevelDef = line;

                List<LevelInformation> curLevelInfoList = extractLevelInfoList(pathToLevelDef);
                this.levelSetsMap.put(key, curLevelInfoList);
            }
        }
        return this.levelSetsMap;
    }

    /**
     * Extract level info list list.
     *
     * @param pathToFile the path to file
     * @return the list
     */
    public List<LevelInformation> extractLevelInfoList(String pathToFile) {

        LevelSpecificationReader l = new LevelSpecificationReader();

        InputStreamReader is = new InputStreamReader(ClassLoader.getSystemResourceAsStream(pathToFile));

        return l.fromReader(is);

    }


    /**
     * Add descpription.
     *
     * @param key         the key
     * @param description the description
     */
    public void addDescpription(Character key, String description) {
        this.keyDescriptionMap.put(key, description);
    }

    /**
     * Gets description.
     *
     * @param key the key
     * @return the description
     */
    public String getDescription(Character key) {
        return this.keyDescriptionMap.get(key);
    }


}
