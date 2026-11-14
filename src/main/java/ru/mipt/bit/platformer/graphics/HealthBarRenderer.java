package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.util.TileMovement;

public class HealthBarRenderer implements ObjectRenderer {

    private static final float BAR_WIDTH = 80f;
    private static final float BAR_HEIGHT = 6f;
    private static final float BAR_OFFSET_Y = 48f;
    private static final Color REMAINING_HEALTH_COLOR = Color.GREEN;
    private static final Color LOST_HEALTH_COLOR = Color.RED;

    private final ObjectRenderer delegate;
    private final MovableEntity tank;
    private final TileMovement tileMovement;
    private final HealthBarDisplay display;
    private final Texture barTexture = createWhiteTexture();
    private final Rectangle bounds = new Rectangle().setWidth(BAR_WIDTH).setHeight(BAR_HEIGHT);

    public HealthBarRenderer(ObjectRenderer delegate, MovableEntity tank, TileMovement tileMovement, HealthBarDisplay display) {
        this.delegate = delegate;
        this.tank = tank;
        this.tileMovement = tileMovement;
        this.display = display;
    }

    @Override
    public void render(Batch batch) {
        delegate.render(batch);
        if (display.isVisible()) {
            drawHealthBar(batch);
        }
    }

    @Override
    public void dispose() {
        delegate.dispose();
        barTexture.dispose();
    }

    private void drawHealthBar(Batch batch) {
        float originalColor = batch.getPackedColor();
        try {
            tileMovement.moveRectangleBetweenTileCenters(bounds, tank.getCoordinates(), tank.getDestinationCoordinates(), tank.getMovementProgress());
            float x = bounds.x;
            float y = bounds.y + BAR_OFFSET_Y;
            batch.setColor(LOST_HEALTH_COLOR);
            batch.draw(barTexture, x, y, BAR_WIDTH, BAR_HEIGHT);
            batch.setColor(REMAINING_HEALTH_COLOR);
            batch.draw(barTexture, x, y, BAR_WIDTH * tank.getHealthFraction(), BAR_HEIGHT);
        } finally {
            batch.setPackedColor(originalColor);
        }
    }

    private static Texture createWhiteTexture() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }
}
