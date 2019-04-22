package io;

import biuoop.DrawSurface;
import gameobjects.Block;
import gameobjects.Velocity;
import levels.LevelInformation;
import sprite.Sprite;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {


    private static final String START_LEVEL = "START_LEVEL";
    private static final String END_LEVEL = "END_LEVEL";
    private static final String BLOCK_DEFINITIONS = "block_definitions";
    private static final String ROW_HEIGHT = "row_height";
    private static final String BLOCKS_START_X = "blocks_start_x";
    private static final String BLOCKS_START_Y = "blocks_start_y";
    private static final String START_BLOCKS = "START_BLOCKS";
    private static final String END_BLOCKS = "END_BLOCKS";
    private static final String LEVEL_NAME = "level_name";
    private static final String BACKGROUND = "background";
    private static final String BALL_VELOCITIES = "ball_velocities";
    private static final String PADDLE_SPEED = "paddle_speed";
    private static final String PADDLE_WIDTH = "paddle_width";
    private static final String NUM_BLOCKS = "num_blocks";
    private static final String RGB_PREFIX = "color(RGB(";
    private static final String RGB_POSTFIX = "))";
    private static final String COLOR_PREFIX = "color(";
    private static final String COLOR_POSTFIX = ")";
    private static final String IMAGE_PREFIX = "image(";
    private static final String IMAGE_POSTFIX = ")";
    private static final String RGB = "RGB";


    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {

        List<LevelInformation> levelSet = new ArrayList<LevelInformation>();
        //splited to levels
        List<String> levels = splitToLevels(reader, START_LEVEL, END_LEVEL);
        for (String level : levels) {
            levelSet.add(singleLevelParse(level));
        }
        return levelSet;

    }


    /**
     * Single level parse level information.
     * given level in string, parse it to level information object
     *
     * @param strLevel the str level
     * @return the level information
     */
    public LevelInformation singleLevelParse(String strLevel) {
        List<Velocity> initialBallVelocities = null;
        Integer paddleSpeed = null;
        Integer paddleWidth = null;
        String levelName = "";
        Sprite background = null;
        List<Block> blocks = null;
        Integer numberOfBlocksToRemove = null;
        Integer startX = null;
        Integer startY = null;
        Integer rowHeight = null;
        String blockSpecificationStr = "";
        BlocksFromSymbolsFactory blocksFactory = null;
        String line;
        String[] temp = strLevel.split("\n");
        List<String> lines = new ArrayList<String>();
        for (int i = 0; i < temp.length; i++) {
            lines.add(i, temp[i]);
        }
        int i;
        for (i = 0; i < lines.size(); i++) {
            line = lines.get(i);
            if (line.startsWith("#") || line.equals(START_LEVEL) || line.isEmpty()) {
                continue;
            }
            if (line.startsWith(LEVEL_NAME)) {
                line = line.replace(LEVEL_NAME + ":", "");
                line = line.trim();
                levelName = line;
            }
            if (line.startsWith(BALL_VELOCITIES)) {
                line = line.replace(BALL_VELOCITIES + ":", "");
                line = line.trim();
                initialBallVelocities = analyzeBallVeloFormat(line);
            }
            if (line.startsWith(BACKGROUND)) {
                line = line.replace(BACKGROUND + ":", "");
                line = line.trim();
                background = backgroundAnalyzer(line);
            }
            if (line.startsWith(PADDLE_SPEED)) {
                line = line.replace(PADDLE_SPEED + ":", "");
                line = line.trim();
                paddleSpeed = Integer.parseInt(line);
            }
            if (line.startsWith(PADDLE_WIDTH)) {
                line = line.replace(PADDLE_WIDTH + ":", "");
                line = line.trim();
                paddleWidth = Integer.parseInt(line);
            }
            if (line.startsWith(BLOCK_DEFINITIONS)) {
                line = line.replace(BLOCK_DEFINITIONS + ":", "");
                line = line.trim();
                InputStreamReader is = null;
                try {
                    is = new InputStreamReader(ClassLoader.getSystemResourceAsStream(line));
                    blocksFactory = BlocksDefinitionReader.fromReader(is);
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e.getMessage());
                        }
                    }
                }
            }
            if (line.startsWith(BLOCKS_START_X)) {
                line = line.replace(BLOCKS_START_X + ":", "");
                line = line.trim();
                startX = Integer.parseInt(line);
            }
            if (line.startsWith(BLOCKS_START_Y)) {
                line = line.replace(BLOCKS_START_Y + ":", "");
                line = line.trim();
                startY = Integer.parseInt(line);
            }
            if (line.startsWith(ROW_HEIGHT)) {
                line = line.replace(ROW_HEIGHT + ":", "");
                line = line.trim();
                rowHeight = Integer.parseInt(line);
            }
            if (line.startsWith(NUM_BLOCKS)) {
                line = line.replace(NUM_BLOCKS + ":", "");
                line = line.trim();
                numberOfBlocksToRemove = Integer.parseInt(line);
            }
            if (line.startsWith(START_BLOCKS)) {
                break;
            }
        }
        //reached i is the index of START BLOCKS
        for (; i < lines.size(); i++) {
            line = lines.get(i);
            if (line.startsWith(END_LEVEL)) {
                break;
            }
            blockSpecificationStr = blockSpecificationStr.concat("\n" + line);
        }
        //i is at END LEVEL
        //need to generate blocks now//////////////////////
        blocks = generateBlocks(blocksFactory, blockSpecificationStr, startX, startY, rowHeight);
        final List<Velocity> ibv = initialBallVelocities;
        final Integer ps = paddleSpeed;
        final Integer pw = paddleWidth;
        final String ln = levelName;
        final Sprite bg = background;
        final List<Block> blo = blocks;
        final Integer btr = numberOfBlocksToRemove;
        if (ibv == null || ps == null || pw == null || ln == null || bg == null || blo == null || btr == null) {
            throw new RuntimeException(" missing property: ibv" + ibv.toString() + " ps " + ps + " pw " + pw + " ln "
                    + ln + " bg " + " cant show " + " blo " + blo.toString() + " btr " + btr);
        }
        return new LevelInformation() {
            @Override
            public int numberOfBalls() {
                return ibv.size();
            }
            @Override
            public List<Velocity> initialBallVelocities() {
                return ibv;
            }
            @Override
            public int paddleSpeed() {
                return ps;
            }
            @Override
            public int paddleWidth() {
                return pw;
            }
            @Override
            public String levelName() {
                return ln;
            }
            @Override
            public Sprite getBackground() {
                return bg;
            }
            @Override
            public List<Block> blocks() {
                return blo;
            }
            @Override
            public int numberOfBlocksToRemove() {
                return btr;
            }
        };
    }

    /**
     * generates blocks.
     * @param factory the factory
     * @param layout the layout
     * @param startX the startX
     * @param startY the startY
     * @param rowHeight the row height
     * @return list of blocks
     */
    private List<Block> generateBlocks(BlocksFromSymbolsFactory factory, String layout,
                                       int startX, int startY, int rowHeight) {
        List<Block> blocks = new ArrayList<Block>();
        String line;
        List<String> lines = splitBy(layout, "\n");

        int curX = startX;
        int curY = startY;

        for (int i = 0; i < lines.size(); i++) {
            line = lines.get(i).trim();

            if (line.equals("") || line.startsWith("#") || line.startsWith(START_BLOCKS)) {
                continue;
            }
            if (line.startsWith(END_BLOCKS)) {
                break;
            }
            for (int ch = 0; ch < line.length(); ch++) {
                if (factory.isSpaceSymbol(String.valueOf(line.charAt(ch)))) {
                    curX += factory.getSpaceWidth(String.valueOf(line.charAt(ch)));
                } else {
                    //symbol is a block
                    Block b = factory.getBlock(String.valueOf(line.charAt(ch)), curX, curY);
                    blocks.add(b);
                    curX += b.getShape().getWidth();
                }
            }
            curX = startX;
            curY += rowHeight;
        }
        return blocks;

    }


    /**
     * Background analyzer sprite.
     *
     * @param line the line
     * @return the sprite
     */
    public Sprite backgroundAnalyzer(String line) {
        if (line.startsWith(COLOR_PREFIX)) {
            //2 color options
            return analyzeColor(line);
        }
        // case that line.startsWith(IMAGE_PREFIX)
        //image option
        return analyzeImage(line);

    }

    /**
     * analyzes image.
     * @param line image location
     * @return the sprite
     */
    private Sprite analyzeImage(String line) {
        try {
            line = line.replace(IMAGE_PREFIX, "");
            line = line.replace(")", "");
            line = line.trim();
            InputStream is = null;
            try {
                is = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
                BufferedImage image = ImageIO.read(is);
                return new Sprite() {
                    @Override
                    public void drawOn(DrawSurface d) {
                        d.drawImage(0, 0, image);
                    }

                    @Override
                    public void timePassed(double dt) {
                    }
                };


            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                is.close();
            }
        } catch (Exception e1) {
            throw new RuntimeException(e1.getMessage());
        }
    }

    /**
     * analyzes color.
     * @param line line to analyze
     * @return sprite
     */
    private Sprite analyzeColor(String line) {
        ColorParser colorParser = new ColorParser();
        Color c = colorParser.colorFromString(line);
        return new Sprite() {
            @Override
            public void drawOn(DrawSurface d) {
                d.setColor(c);
                d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            }

            @Override
            public void timePassed(double dt) {

            }
        };
    }

    /**
     * Str list to integer list list.
     *
     * @param strs the strs
     * @return the list
     */
    public List<Integer> strListToIntegerList(List<String> strs) {
        List<Integer> list = new ArrayList<Integer>();
        for (String s : strs) {
            list.add(Integer.parseInt(s));
        }
        return list;
    }

    /**
     * anlyzes ball format.
     * @param line format
     * @return list of velocitios
     */
    private List<Velocity> analyzeBallVeloFormat(String line) {
        List<Velocity> velocities = new ArrayList<Velocity>();

        String[] arr = line.split(" ");
        List<String> singleVeloInfoList = strArrToList(arr);
        for (String singleVelo : singleVeloInfoList) {
            String[] singleVeloArr = singleVelo.split(",");
            velocities.add(Velocity.fromAngleAndSpeed(Integer.parseInt(singleVeloArr[0])
                    , Integer.parseInt(singleVeloArr[1])));
        }
        return velocities;
    }


    /**
     * Split by list.
     *
     * @param line the line
     * @param s    the s
     * @return the list
     */
    public List<String> splitBy(String line, String s) {
        String[] arr = line.split(s);
        return strArrToList(arr);
    }


    /**
     * Split to levels list.
     *
     * @param reader    the reader
     * @param startLine the start line
     * @param endLine   the end line
     * @return the list
     */
//returns string list
    //1 string = 1 level
    public List<String> splitToLevels(java.io.Reader reader, String startLine, String endLine) {
        List<String> strLevelList = new ArrayList<String>();
        try {
            LineNumberReader lineReader = new LineNumberReader(reader);
            //read whole level file
            while (true) {
                String preLevel;
                preLevel = lineReader.readLine();
                //end of file
                if (preLevel == null) {
                    return strLevelList;

                } else if (preLevel.equals(startLine)) {
                    //read single level
                    String wholeLevel = "";
                    while (true) {
                        String line;
                        line = lineReader.readLine();
                        //reached end of file in the middle of the level
                        if (line == null) {
                            throw new RuntimeException("reached end of file in the middle level/block definition");
                        }
                        if (line.equals(endLine)) {
                            strLevelList.add(wholeLevel);
                            break;
                        }
                        //regular line
                        wholeLevel = wholeLevel.concat("\n" + line);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    /**
     * Str arr to list list.
     *
     * @param arr the arr
     * @return the list
     */
    public List<String> strArrToList(String[] arr) {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            list.add(i, arr[i]);
        }
        return list;
    }


}
