package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import ru.mipt.bit.platformer.util.TileMovement;

import java.util.List;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;
import static ru.mipt.bit.platformer.util.GdxGameUtils.drawTextureRegionUnscaled;

public class MovableEntity extends GameObject {

    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public MovableEntity(String texturePath, GridPoint2 coordinates, TiledMapTileLayer groundLayer) {
        super(texturePath, coordinates, groundLayer);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void tryMove(Direction direction, List<GameObject> obstacles) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 target = direction.move(coordinates);
            if (noCollision(target, obstacles)) {
                destinationCoordinates.set(target);
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public void update(float deltaTime, float speed, TileMovement tileMovement) {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, coordinates, destinationCoordinates, movementProgress);
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public void render(Batch batch) {
        drawTextureRegionUnscaled(batch, graphics, rectangle, rotation);
    }

    private boolean noCollision(GridPoint2 target, List<GameObject> obstacles) {
        for (GameObject obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(target)) {
                return false;
            }
        }
        return true;
    }
}
