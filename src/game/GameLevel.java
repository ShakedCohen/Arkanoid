package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import animation.CountdownAnimation;


import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import collision.HitListener;
import collision.Collidable;
import collision.BallRemover;
import collision.BlockRemover;
import collision.ScoreTrackingListener;
import gameobjects.Block;
import gameobjects.Velocity;
import gameobjects.Counter;
import gameobjects.Ball;
import gameobjects.Paddle;
import gameobjects.FrameBlock;
import gameobjects.DeathBlock;
import geometry.Point;
import geometry.Rectangle;
import indicators.LevelIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import levels.LevelInformation;
import sprite.RectBackground;
import sprite.RectColorBackground;
import sprite.Sprite;
import sprite.SpriteCollection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * game.GameLevel class. defines the game course
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private KeyboardSensor keyboard;
    //NOT INCLUDING DEATH BLOCKS
    private Counter remainingBlocks;
    private Counter remainingBalls = new Counter();
    private Paddle gamePaddle = null;
    private java.util.List gameBallList = new ArrayList<Ball>();
    private java.util.List blockHitListeners = new ArrayList<HitListener>();
    private Counter score;
    private Counter numberOfLives;
    private AnimationRunner runner;
    private boolean running;
    private boolean gameStart = true;
    private LevelInformation levelInfo;


    /**
     * constructor for the game- creates game environment and sprite.Sprite collection.
     *
     * @param levelInfo        the level info
     * @param keyboardSensor1  the keyboard sensor 1
     * @param animationRunner1 the animation runner 1
     * @param curLives         the cur lives
     * @param curScore         the cur score
     */
    public GameLevel(LevelInformation levelInfo, KeyboardSensor keyboardSensor1, AnimationRunner animationRunner1,
                     Counter curLives, Counter curScore) {
        this.keyboard = keyboardSensor1;
        this.levelInfo = levelInfo;
        this.runner = animationRunner1;
        this.numberOfLives = curLives;
        this.score = curScore;

        this.remainingBlocks = new Counter(this.levelInfo.numberOfBlocksToRemove());
    }

    /**
     * adds a collidable to the list.
     *
     * @param collidableObj the obj
     */
    public void addCollidable(Collidable collidableObj) {
        this.environment.addCollidable(collidableObj);
    }

    /**
     * adds a sprite to the list.
     *
     * @param spriteObj obj
     */
    public void addSprite(Sprite spriteObj) {
        this.sprites.addSprite(spriteObj);
    }

    /**
     * Remove collidable.
     *
     * @param c the c
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Remove sprite.
     *
     * @param s the s
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }


    /**
     * Initialize.
     */
    public void initialize() {

        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        this.blockHitListeners.add(blockRemover);
        ScoreTrackingListener scoreTracker = new ScoreTrackingListener(score);
        this.blockHitListeners.add(scoreTracker);

        BallRemover ballRemover = new BallRemover(this, remainingBalls);

        this.sprites.addSprite(this.levelInfo.getBackground());

        createScoreIndicator();
        createLivesIndicator();
        createLevelIndicator();


        frameBlockCreator(0, Constants.SCORE_PANEL_HEIGHT, 800,
                Constants.FRAME_BLOCK_WIDTH, 1000);

        //left
        frameBlockCreator(0, Constants.SCORE_PANEL_HEIGHT, Constants.FRAME_BLOCK_WIDTH,
                600, 1000);

        //right
        frameBlockCreator(Constants.GUI_WIDTH - Constants.FRAME_BLOCK_WIDTH,
                Constants.SCORE_PANEL_HEIGHT, Constants.FRAME_BLOCK_WIDTH, 600, 1000);

        //bottom DEATH BLOCK
        deathBlockCreator(0, 606, 800, 5, Color.magenta,
                100, ballRemover);

        this.createBlocks(this.blockHitListeners, this.levelInfo.blocks());
    }


    /**
     * Create blocks.
     *
     * @param listeners     the listeners
     * @param blockToCreate the block to create
     */
    public void createBlocks(List<HitListener> listeners, List<Block> blockToCreate) {
        for (Block block : blockToCreate) {
            for (HitListener hLis : listeners) {
                block.addHitListener(hLis);
            }
            block.addToGame(this);
        }
    }

    /**
     * Run the game create paddle and start the animation loop.
     */
    public void playOneTurn() {
        //if there is a paddle, remove him.
        if (this.gamePaddle != null) {
            this.gamePaddle.removeFromGame(this);
        }
        //create paddle
        paddleCreator(Constants.GUI_WIDTH / 2 - this.levelInfo.paddleWidth() / 2, 585,
                this.levelInfo.paddleWidth(), Constants.PADDLE_HEIGHT, Color.yellow, this.levelInfo.paddleSpeed());

        this.createBallsOnTopOfPaddle(); // or a similar method

        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.
        this.runner.run(this);
    }


    /**
     * Gets remaining blocks.
     *
     * @return the remaining blocks
     */
    public Counter getRemainingBlocks() {
        return remainingBlocks;
    }

    /**
     * draw one frame of he animation.
     *
     * @param d  the drawsurface
     * @param dt dt val
     */
    @Override
    public void doOneFrame(DrawSurface d, double dt) {
        // the logic from the previous playOneTurn method goes here.
        // the `return` or `break` statements should be replaced with
        // this.running = false;

        if (this.gameStart) {
            this.runner.run(new CountdownAnimation(2, 3, this.sprites));
            this.gameStart = false;
        }
        //pause
        if (this.keyboard.isPressed("p")) {
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, KeyboardSensor.SPACE_KEY, new PauseScreen()));
        }
        //no blocks remaining
        if (this.remainingBlocks.getValue() <= 0) {
            score.increase(100);
            this.running = false;
        }
        //no balls remaining
        if (this.remainingBalls.getValue() <= 0) {
            this.numberOfLives.decrease(1);
            this.running = false;
        }
        //draw the sprites
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
    }


    /**
     * checks the stop condition for the animation.
     *
     * @return true if the animation should run, else false
     */
    @Override
    public boolean shouldStop() {
        return !this.running;
    }



    /**
     * Frame block creator.
     *
     * @param xUpperLeft the x upper left
     * @param yUpperLeft the y upper left
     * @param width      the width
     * @param height     the height
     * @param hitPoints  the hit points
     */
    public void frameBlockCreator(int xUpperLeft, int yUpperLeft, int width, int height, int hitPoints) {
        HashMap<Integer, RectBackground> a = new HashMap<Integer, RectBackground>();
        a.put(1, new RectColorBackground(Color.lightGray));

        FrameBlock b = new FrameBlock(new Rectangle(new Point(xUpperLeft, yUpperLeft),
                width, height), null, hitPoints, a);
        b.addToGame(this);
    }

    /**
     * Death block creator.
     *
     * @param xUpperLeft  the x upper left
     * @param yUpperLeft  the y upper left
     * @param width       the width
     * @param height      the height
     * @param color       the color
     * @param hitPoints   the hit points
     * @param ballRemover the ball remover
     */
    public void deathBlockCreator(int xUpperLeft, int yUpperLeft, int width, int height,
                                  Color color, int hitPoints, BallRemover ballRemover) {
        Block b = new DeathBlock(new Rectangle(new Point(xUpperLeft, yUpperLeft),
                width, height), color, hitPoints, ballRemover);
        b.addToGame(this);
    }

    /**
     * Ball creator.
     *
     * @param xCenter  the x center
     * @param yCenter  the y center
     * @param radius   the radius
     * @param color    the color
     * @param velocity the velocity
     */
    public void ballCreator(int xCenter, int yCenter, int radius, Color color, Velocity velocity) {
        Ball ball = new Ball(new Point(xCenter, yCenter), radius, color, this.environment);
        ball.setVelocity(velocity);
        this.gameBallList.add(ball);
        this.remainingBalls.increase(1);
        ball.addToGame(this);
    }

    /**
     * Paddle creator.
     *
     * @param xUpperLeft  the x upper left
     * @param yUpperLeft  the y upper left
     * @param width       the width
     * @param height      the height
     * @param color       the color
     * @param paddleSpeed the paddle speed
     */
    public void paddleCreator(int xUpperLeft, int yUpperLeft, int width, int height, Color color, int paddleSpeed) {
        Paddle paddle = new Paddle(new Rectangle(new Point(xUpperLeft, yUpperLeft), width, height),
                color, paddleSpeed, keyboard);
        this.gamePaddle = paddle;
        paddle.addToGame(this);
    }

    /**
     * Create score indicator.
     */
    public void createScoreIndicator() {
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score);
        this.addSprite(scoreIndicator);
    }

    /**
     * Create lives indicator.
     */
    public void createLivesIndicator() {
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        this.addSprite(livesIndicator);
    }

    /**
     * Create level indicator.
     */
    public void createLevelIndicator() {
        LevelIndicator levelIndicator = new LevelIndicator(this.levelInfo.levelName());
        this.addSprite(levelIndicator);
    }

    /**
     * Create balls on top of paddle.
     */
    public void createBallsOnTopOfPaddle() {
        if (this.gamePaddle == null) {
            throw new RuntimeException("cant create balls on top of a paddle if there is no paddle,"
                    + " what the hell dude?");
        }

        for (int i = 0; i < this.levelInfo.numberOfBalls(); i++) {
            ballCreator((int) gamePaddle.getCollisionRectangle().getTopLine().middle().getX(),
                    (int) gamePaddle.getCollisionRectangle().getTopLine().middle().getY()
                            - Constants.PADDLE_HEIGHT / 2 - 1,
                    Constants.BALL_SIZE, Color.darkGray, this.levelInfo.initialBallVelocities().get(i));

        }

    }

}












