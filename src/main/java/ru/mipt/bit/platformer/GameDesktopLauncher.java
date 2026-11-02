package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.ai.Bot;
import ru.mipt.bit.platformer.ai.RandomMoveStrategy;
import ru.mipt.bit.platformer.config.FileLevelProvider;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.config.Level;
import ru.mipt.bit.platformer.config.LevelProvider;
import ru.mipt.bit.platformer.config.ObstacleSpec;
import ru.mipt.bit.platformer.config.RandomLevelProvider;
import ru.mipt.bit.platformer.config.RandomTankProvider;
import ru.mipt.bit.platformer.graphics.EntityRenderer;
import ru.mipt.bit.platformer.input.GdxKeyStateProvider;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.KeyStateProvider;
import ru.mipt.bit.platformer.input.MoveButtonHandler;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private final GameConfig config;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private MovableEntity player;
    private EntityRenderer playerRenderer;
    private final Map<GameObject, EntityRenderer> obstacleRenderers = new LinkedHashMap<>();
    private final Map<GameObject, EntityRenderer> tankRenderers = new LinkedHashMap<>();
    private final List<Bot> bots = new ArrayList<>();

    private InputHandler inputHandler;

    public GameDesktopLauncher() {
        this(new GameConfig());
    }

    public GameDesktopLauncher(GameConfig config) {
        this.config = config;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load(config.getLevelPath());
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, config.getMovementInterpolation());

        Level gameLevel = new RandomTankProvider(config.getLevelProvider(), 5).provide();

        KeyStateProvider keyState = new GdxKeyStateProvider();

        player = new MovableEntity(gameLevel.getPlayerCoordinates(), gameLevel.getSize());
        playerRenderer = new EntityRenderer(config.getPlayerTexturePath(), player, tileMovement);

        List<GameObject> obstacles = new ArrayList<>();
        obstacles.add(player);
        for (ObstacleSpec obstacle : gameLevel.getObstacles()) {
            GameObject object = new GameObject(obstacle.getCoordinates());
            obstacleRenderers.put(object, new EntityRenderer(obstacle.getTexturePath(), object, tileMovement));
            obstacles.add(object);
        }

        RandomMoveStrategy strategy = new RandomMoveStrategy();
        for (GridPoint2 coordinates : gameLevel.getTankCoordinates()) {
            MovableEntity tank = new MovableEntity(coordinates, gameLevel.getSize());
            tankRenderers.put(tank, new EntityRenderer(config.getTankTexturePath(), tank, tileMovement));
            obstacles.add(tank);
            bots.add(new Bot(tank, strategy, obstacles));
        }

        inputHandler = new InputHandler();
        for (Map.Entry<Direction, int[]> binding : config.getKeyBindings().entrySet()) {
            inputHandler.add(new MoveButtonHandler(binding.getKey(), player, obstacles, keyState, binding.getValue()));
        }
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        // react to pressed buttons
        inputHandler.handle();

        // update player model state
        player.update(deltaTime, config.getMovementSpeed());

        // update bot tanks driven by AI
        for (Bot bot : bots) {
            bot.update(deltaTime, config.getMovementSpeed());
        }

        // render each tile of the level
        levelRenderer.render();

        // start recording all drawing commands
        batch.begin();

        // render player
        playerRenderer.render(batch);

        // render obstacles
        for (EntityRenderer renderer : obstacleRenderers.values()) {
            renderer.render(batch);
        }

        // render tanks
        for (EntityRenderer renderer : tankRenderers.values()) {
            renderer.render(batch);
        }

        // submit all drawing requests
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        // do not react to window resizing
    }

    @Override
    public void pause() {
        // game doesn't get paused
    }

    @Override
    public void resume() {
        // game doesn't get paused
    }

    @Override
    public void dispose() {
        // dispose of all the native resources (classes which implement com.badlogic.gdx.utils.Disposable)
        playerRenderer.dispose();
        for (EntityRenderer renderer : obstacleRenderers.values()) {
            renderer.dispose();
        }
        for (EntityRenderer renderer : tankRenderers.values()) {
            renderer.dispose();
        }
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        GameConfig config = new GameConfig(levelProvider(args));

        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        appConfig.setWindowedMode(config.getWindowWidth(), config.getWindowHeight());
        new Lwjgl3Application(new GameDesktopLauncher(config), appConfig);
    }

    private static LevelProvider levelProvider(String[] args) {
        String mode = args.length > 0 ? args[0] : "file";
        if (mode.equals("file")) {
            return new FileLevelProvider("level.txt", "images/greenTree.png");
        }
        if (mode.equals("random")) {
            return new RandomLevelProvider(10, 8, 15, "images/greenTree.png");
        }
        throw new IllegalArgumentException("Unknown level provider: " + mode);
    }
}
