package ru.mipt.bit.platformer;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.graphics.EntityRenderer;
import ru.mipt.bit.platformer.input.InputHandler;
import ru.mipt.bit.platformer.input.MoveButtonHandler;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.badlogic.gdx.graphics.GL20.GL_COLOR_BUFFER_BIT;
import static ru.mipt.bit.platformer.util.GdxGameUtils.createSingleLayerMapRenderer;
import static ru.mipt.bit.platformer.util.GdxGameUtils.getSingleLayer;

public class GameDesktopLauncher implements ApplicationListener {

    private static final float MOVEMENT_SPEED = 0.4f;

    private Batch batch;

    private TiledMap level;
    private MapRenderer levelRenderer;
    private TileMovement tileMovement;

    private MovableEntity player;
    private EntityRenderer playerRenderer;
    private final Map<GameObject, EntityRenderer> obstacleRenderers = new HashMap<>();

    private InputHandler inputHandler;

    @Override
    public void create() {
        batch = new SpriteBatch();

        // load level tiles
        level = new TmxMapLoader().load("level.tmx");
        levelRenderer = createSingleLayerMapRenderer(level, batch);
        TiledMapTileLayer groundLayer = getSingleLayer(level);
        tileMovement = new TileMovement(groundLayer, Interpolation.smooth);

        player = new MovableEntity(new GridPoint2(1, 1));
        playerRenderer = new EntityRenderer("images/tank_blue.png", player, tileMovement);

        GameObject tree = new GameObject(new GridPoint2(1, 3));
        obstacleRenderers.put(tree, new EntityRenderer("images/greenTree.png", tree, tileMovement));

        Set<GameObject> obstacles = obstacleRenderers.keySet();
        inputHandler = new InputHandler();
        inputHandler.add(new MoveButtonHandler(Direction.UP, player, obstacles, Keys.UP, Keys.W));
        inputHandler.add(new MoveButtonHandler(Direction.LEFT, player, obstacles, Keys.LEFT, Keys.A));
        inputHandler.add(new MoveButtonHandler(Direction.DOWN, player, obstacles, Keys.DOWN, Keys.S));
        inputHandler.add(new MoveButtonHandler(Direction.RIGHT, player, obstacles, Keys.RIGHT, Keys.D));
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
        player.update(deltaTime, MOVEMENT_SPEED);

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
        level.dispose();
        batch.dispose();
    }

    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        // level width: 10 tiles x 128px, height: 8 tiles x 128px
        config.setWindowedMode(1280, 1024);
        new Lwjgl3Application(new GameDesktopLauncher(), config);
    }
}
