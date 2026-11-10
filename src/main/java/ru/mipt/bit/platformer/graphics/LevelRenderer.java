package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.config.GameConfig;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.LevelListener;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.LinkedHashMap;
import java.util.Map;

public class LevelRenderer implements LevelListener {

    private final GameConfig config;
    private final GameObject player;
    private final TileMovement tileMovement;
    private final HealthBarDisplay healthBarDisplay;
    private final Map<GameObject, ObjectRenderer> renderers = new LinkedHashMap<>();

    public LevelRenderer(GameConfig config, GameObject player, TileMovement tileMovement, HealthBarDisplay healthBarDisplay) {
        this.config = config;
        this.player = player;
        this.tileMovement = tileMovement;
        this.healthBarDisplay = healthBarDisplay;
    }

    @Override
    public void objectAdded(GameObject object) {
        ObjectRenderer renderer = new EntityRenderer(texturePath(object), object, tileMovement);
        if (object instanceof MovableEntity) {
            renderer = new HealthBarRenderer(renderer, (MovableEntity) object, tileMovement, healthBarDisplay);
        }
        renderers.put(object, renderer);
    }

    @Override
    public void objectRemoved(GameObject object) {
        renderers.remove(object).dispose();
    }

    public void render(Batch batch) {
        for (ObjectRenderer renderer : renderers.values()) {
            renderer.render(batch);
        }
    }

    public void dispose() {
        for (ObjectRenderer renderer : renderers.values()) {
            renderer.dispose();
        }
    }

    private String texturePath(GameObject object) {
        if (object == player) {
            return config.getPlayerTexturePath();
        }
        if (object instanceof Bullet) {
            return config.getBulletTexturePath();
        }
        if (object instanceof MovableEntity) {
            return config.getTankTexturePath();
        }
        return config.getTreeTexturePath();
    }
}
