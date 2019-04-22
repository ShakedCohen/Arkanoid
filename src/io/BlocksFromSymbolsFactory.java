package io;

import gameobjects.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {

    private Map<String, Integer> spacerWidths = new HashMap<String, Integer>();
    private Map<String, BlockCreator> blockCreators = new HashMap<String, BlockCreator>();

    /**
     * Add spacer width.
     *
     * @param s the s
     * @param i the
     */
    public void addSpacerWidth(String s, Integer i) {
        this.spacerWidths.put(s, i);
    }

    /**
     * Addblock creator.
     *
     * @param s  the s
     * @param bc the bc
     */
    public void addblockCreator(String s, BlockCreator bc) {
        this.blockCreators.put(s, bc);
    }


    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        return this.spacerWidths.containsKey(s);
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
// returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        return this.blockCreators.containsKey(s);
    }


    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
// Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }


    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return the block
     */
// Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }
}