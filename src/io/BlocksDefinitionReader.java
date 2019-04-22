package io;

import gameobjects.Block;
import geometry.Point;
import geometry.Rectangle;
import sprite.RectBackground;
import sprite.RectColorBackground;
import sprite.RectImageBackground;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    private static final String BDEF = "bdef";
    private static final String SDEF = "sdef";
    private static final String DEFAULT = "default";
    private static final String SYMBOL = "symbol";
    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String HIT_POINTS = "hit_points";
    private static final String FILL = "fill";
    private static final String STROKE = "stroke";


    /**
     * reads from block definition file.
     * returns blocks from symbols factory
     *
     * @param reader the reader
     * @return blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        //defaults
        Integer width = null;
        Integer height = null;
        Integer hitPoints = null;
        Map<Integer, RectBackground> backgroundMap = new HashMap<Integer, RectBackground>();
        Color stroke = null;
        BlocksFromSymbolsFactory factory = new BlocksFromSymbolsFactory();
        LevelSpecificationReader l = new LevelSpecificationReader();
        LineNumberReader readLine = new LineNumberReader(reader);
        String line;
        try {
            while ((line = readLine.readLine()) != null) {
                if (line.startsWith("#") || line.trim().equals("")) {
                    continue;
                }
                if (line.startsWith(DEFAULT)) {
                    line = line.replace(DEFAULT, "");
                    line = line.trim();
                    List<String> properties = l.splitBy(line, " ");
                    for (String s : properties) {
                        List<String> keyValueList = l.splitBy(s, ":");
                        if (keyValueList.get(0).equals(WIDTH)) {
                            width = Integer.parseInt(keyValueList.get(1));
                        }
                        if (keyValueList.get(0).equals(HEIGHT)) {
                            height = Integer.parseInt(keyValueList.get(1));
                        }
                        if (keyValueList.get(0).equals(HIT_POINTS)) {
                            hitPoints = Integer.parseInt(keyValueList.get(1));
                        }
                        //fill-k
                        if (keyValueList.get(0).equals(FILL + "-")) {
                            backgroundMap.put(Integer.parseInt("" + (keyValueList.get(0).
                                            charAt(keyValueList.get(0).length() - 1)))
                                    , rectBackgroundAnalyzer(keyValueList.get(1)));
                        } else if (keyValueList.get(0).equals(FILL)) {
                            //just fill
                            backgroundMap.put(1, rectBackgroundAnalyzer(keyValueList.get(1)));
                        }
                        if (keyValueList.get(0).equals(STROKE)) {
                            ColorParser cp = new ColorParser();
                            stroke = cp.colorFromString(keyValueList.get(1));
                        }
                    }
                }
                if (line.startsWith(BDEF)) {
                    //if there is a default - apply!
                    String symbolC = null;
                    Integer widthC = null;
                    Integer heightC = null;
                    Integer hitPointsC = null;
                    if (width != null) {
                        widthC = new Integer(width);
                    }
                    if (height != null) {
                        heightC = new Integer(height);
                    }
                    if (hitPoints != null) {
                        hitPointsC = new Integer(hitPoints);
                    }
                    Map<Integer, RectBackground> backgroundMapC = new HashMap<Integer, RectBackground>(backgroundMap);
                    Color strokeC = null;
                    if (stroke != null) {
                        strokeC = new Color(stroke.getRGB());
                    }
                    line = line.replace(BDEF, "");
                    line = line.trim();
                    List<String> properties = l.splitBy(line, " ");
                    for (String s : properties) {
                        List<String> keyValueList = l.splitBy(s, ":");
                        if (keyValueList.get(0).equals(SYMBOL)) {
                            symbolC = keyValueList.get(1);
                        }
                        if (keyValueList.get(0).equals(WIDTH)) {
                            widthC = Integer.parseInt(keyValueList.get(1));
                        }
                        if (keyValueList.get(0).equals(HEIGHT)) {
                            heightC = Integer.parseInt(keyValueList.get(1));
                        }
                        if (keyValueList.get(0).equals(HIT_POINTS)) {
                            hitPointsC = Integer.parseInt(keyValueList.get(1));
                        }
                        //fill-k
                        if (keyValueList.get(0).startsWith(FILL + "-")) {
                            int k = Integer.parseInt("" + (keyValueList.get(0).
                                    charAt(keyValueList.get(0).length() - 1)));
                            RectBackground rb = rectBackgroundAnalyzer(keyValueList.get(1));
                            backgroundMapC.put(k, rb);
                        } else if (keyValueList.get(0).equals(FILL)) {
                            //just fill
                            backgroundMapC.put(1, rectBackgroundAnalyzer(keyValueList.get(1)));
                        }
                        if (keyValueList.get(0).equals(STROKE)) {
                            ColorParser cp = new ColorParser();
                            strokeC = cp.colorFromString(keyValueList.get(1));
                        }
                    }
                    //fill the backgoundMapC
                    for (int i = 0; i <= hitPointsC; i++) {
                        if ((!backgroundMapC.containsKey(i)) || backgroundMapC.get(i) == null) {
                            backgroundMapC.put(i, backgroundMapC.get(1));
                        }
                    }
                    final Integer w = widthC;
                    final Integer h = heightC;
                    final Color s = strokeC;
                    final Integer hp = hitPointsC;
                    final Map<Integer, RectBackground> c = backgroundMapC;
                    if (w == null || h == null || hp == null || c.isEmpty()) {
                        throw new RuntimeException("missing property: " + "width: " + w + " height " + h + " hitpoints "
                                + h + " color list maybe empty");
                    }
                    BlockCreator creator = new BlockCreator() {
                        @Override
                        public Block create(int xpos, int ypos) {
                            return new Block(new Rectangle(
                                    new Point(xpos, ypos), w, h), s, hp, c);
                        }
                    };
                    factory.addblockCreator(symbolC, creator);
                }
                if (line.startsWith(SDEF)) {
                    String sdefSymbol = null;
                    Integer sdefWidth = null;
                    line = line.replace(SDEF, "");
                    line = line.trim();
                    List<String> properties = l.splitBy(line, " ");
                    for (String s : properties) {
                        List<String> keyValueList = l.splitBy(s, ":");
                        if (keyValueList.get(0).equals(SYMBOL)) {
                            sdefSymbol = keyValueList.get(1);
                        }
                        if (keyValueList.get(0).equals(WIDTH)) {
                            sdefWidth = Integer.parseInt(keyValueList.get(1));
                        }
                    }
                    if (sdefSymbol == null || sdefWidth == null) {
                        throw new RuntimeException("spacer missing property:"
                                + " symbol " + sdefSymbol + " width" + sdefWidth);
                    }
                    //create spacer factory
                    factory.addSpacerWidth(sdefSymbol, sdefWidth);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        return factory;
    }
    /**
     * Rect background analyzer rect background.
     *
     * @param line the line
     * @return the rect background
     * @throws IOException the io exception
     */
    public static RectBackground rectBackgroundAnalyzer(String line) throws IOException {
        //recieves the right part

        if (line.contains("color")) {
            //2 color options
            return rectAnalyzeColor(line);
        }
        // case that line.startsWith(IMAGE_PREFIX)
        //image option
        return rectAnalyzeImage(line);
    }

    /**
     * Rect analyze color rect background.
     *
     * @param line the line
     * @return the rect background
     */
    public static RectBackground rectAnalyzeColor(String line) {
        ColorParser colorParser = new ColorParser();
        Color c = colorParser.colorFromString(line);
        return new RectColorBackground(c);
    }

    /**
     * Rect analyze image rect background.
     *
     * @param line the line
     * @return the rect background
     * @throws IOException the io exception
     */
    public static RectBackground rectAnalyzeImage(String line) throws IOException {

        line = line.replace("image(", "");
        line = line.replace(")", "");
        line = line.trim();
        InputStream is = null;
        try {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
            BufferedImage image = ImageIO.read(is);
            return new RectImageBackground(image);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
