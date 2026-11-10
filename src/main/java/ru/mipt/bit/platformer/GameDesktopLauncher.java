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
import ru.mipt.bit.platformer.ai.RandomBotStrategy;
import ru.mipt.bit.platformer.ai.BotStrategy;
import ru.mipt.bit.platformer.config.FileLevelProvider;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.config.Level;
import ru.mipt.bit.platformer.config.LevelProvider;
import ru.mipt.bit.platformer.config.RandomLevelProvider;
import ru.mipt.bit.platformer.config.RandomTankProvider;
import ru.mipt.bit.platformer.graphics.HealthBarDisplay;
import ru.mipt.bit.platformer.graphics.LevelRenderer;
import ru.mipt.bit.platformer.graphics.ToggleHealthBarsCommand;
import ru.mipt.bit.platformer.input.FuncButtonHandler;
import ru.mipt.bit.platformer.input.GdxKeyStateProvider;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.KeyStateProvider;
import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.model.ShootCommand;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private final GameConfig config;

    private Batch batch;

    private TiledMap tiledMap;
    private MapRenderer mapRenderer;
    private TileMovement tileMovement;

    private GameLevel gameLevel;
    private LevelRenderer levelRenderer;
    private MovableEntity player;
    private final List<Bot> bots = new ArrayList<>();

    private InputHandler inputHandler;

    public GameDesktopLauncher() {
        this(GameConfig.builder().build());
    }

    public GameDesktopLauncher(GameConfig config) {
        this.config = config;
    }

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        tiledMap = new TmxMapLoader().load(config.getLevelPath());
        mapRenderer = createSingleLayerMapRenderer(tiledMap, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(tiledMap);
        tileMovement = new TileMovement(groundLayer, config.getMovementInterpolation());

        Level level = new RandomTankProvider(config.getLevelProvider(), 5).provide();

        gameLevel = new GameLevel(level.getSize(), config.getBulletDamage(), config.getBulletSpeed());
        HealthBarDisplay healthBarDisplay = new HealthBarDisplay();
        player = new MovableEntity(level.getPlayerCoordinates(), gameLevel, config.getMovementSpeed(), config.getTankHealth());
        levelRenderer = new LevelRenderer(config, player, tileMovement, healthBarDisplay);
        gameLevel.addListener(levelRenderer);

        gameLevel.add(player);
        for (GridPoint2 coordinates : level.getObstacleCoordinates()) {
            gameLevel.add(new GameObject(coordinates));
        }

        BotStrategy strategy = new RandomBotStrategy();
        for (GridPoint2 coordinates : level.getTankCoordinates()) {
            MovableEntity tank = new MovableEntity(coordinates, gameLevel, config.getMovementSpeed(), config.getTankHealth());
            gameLevel.add(tank);
            bots.add(new Bot(tank, strategy));
        }

        KeyStateProvider keyState = new GdxKeyStateProvider();

        inputHandler = new InputHandler();
        for (Map.Entry<Direction, int[]> binding : config.getKeyBindings().entrySet()) {
            Direction direction = binding.getKey();
            inputHandler.add(FuncButtonHandler.whileHeld(keyState, () -> new MoveCommand(player, direction), binding.getValue()));
        }
        inputHandler.add(FuncButtonHandler.whileHeld(keyState, () -> new ShootCommand(player), config.getShootKey()));
        inputHandler.add(FuncButtonHandler.onPressed(keyState, () -> new ToggleHealthBarsCommand(healthBarDisplay), config.getToggleHealthBarsKey()));
    }

    @Override
    public void render() {
        // clear the screen
        Gdx.gl.glClearColor(0f, 0f, 0.2f, 1f);
        Gdx.gl.glClear(GL_COLOR_BUFFER_BIT);

        // get time passed since the last render
        float deltaTime = Gdx.graphics.getDeltaTime();

        // create commands as a reaction to pressed buttons and bot AI
        List<Command> commands = inputHandler.createCommands();
        for (Bot bot : bots) {
            commands.add(bot.createCommand());
        }

        // execute the created commands
        for (Command command : commands) {
            command.execute();
        }

        // let every object of the level live through the tick
        gameLevel.live(deltaTime);

        // render all objects of the level
        mapRenderer.render();
        batch.begin();
        levelRenderer.render(batch);
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
        levelRenderer.dispose();
        tiledMap.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        GameConfig config = GameConfig.builder()
                .levelProvider(levelProvider(args))
                .build();

        Lwjgl3ApplicationConfiguration appConfig = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        appConfig.setWindowedMode(config.getWindowWidth(), config.getWindowHeight());
        new Lwjgl3Application(new GameDesktopLauncher(config), appConfig);
    }

    private static LevelProvider levelProvider(String[] args) {
        String mode = args.length > 0 ? args[0] : "file";
        if (mode.equals("random")) {
            return new RandomLevelProvider(10, 8, 15);
        }
        if (mode.equals("file")) {
            return new FileLevelProvider("level.txt");
        }
        throw new IllegalArgumentException("Unknown level provider: " + mode);
    }
}
