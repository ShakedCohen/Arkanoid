package io;

import java.awt.Color;
import java.lang.reflect.Field;
import java.util.List;

/**
 * The type Color parser.
 */
public class ColorParser {
    private static final String RGB = "RGB";
    private static final String COLOR_PREFIX = "color(";


    /**
     * Color from string java . awt . color.
     *
     * @param line the line
     * @return the java . awt . color
     */
    public java.awt.Color colorFromString(String line) {

        //erase the first one
        line = line.replace(COLOR_PREFIX, "");

        line = line.replace(")", "");
        line = line.trim();

        if (line.contains(RGB)) {
            line = line.replace(RGB + "(", "");
            line = line.replace(")", "");
            line = line.trim();
            LevelSpecificationReader l = new LevelSpecificationReader();
            List<String> colorNums = l.splitBy(line, ",");
            List<Integer> intColorNums = l.strListToIntegerList(colorNums);
            return new Color(intColorNums.get(0), intColorNums.get(1), intColorNums.get(2));

        } else {
            try {
                Field field = Color.class.getField(line);
                return (Color) field.get(null);
            } catch (Exception e) {
                throw new RuntimeException("invalid color" + line);
            }
        }

    }


}
