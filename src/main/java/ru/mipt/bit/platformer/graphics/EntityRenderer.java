package ru.mipt.bit.platformer.graphics;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.util.TileMovement;

import static ru.mipt.bit.platformer.util.GdxGameUtils.createBoundingRectangle;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class EntityRenderer {

    private final Texture texture;
    private final TextureRegion region;
    private final Rectangle rectangle;
    private final TileMovement tileMovement;
    private final GameObject entity;

    public EntityRenderer(String texturePath, GameObject entity, TileMovement tileMovement) {
        this.texture = new Texture(texturePath);
        this.region = new TextureRegion(texture);
        this.rectangle = createBoundingRectangle(region);
        this.entity = entity;
        this.tileMovement = tileMovement;
    }

    public void render(Batch batch) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, entity.getCoordinates(), entity.getDestinationCoordinates(), entity.getMovementProgress());
        drawTextureRegionUnscaled(batch, region, rectangle, entity.getRotation());
    }

    public void dispose() {
        texture.dispose();
    }
}
