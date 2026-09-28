package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileMovementTest {

    private static final int TILE_SIZE = 128;

    private final GridPoint2 from = new GridPoint2(2, 1);
    private final GridPoint2 to = new GridPoint2(3, 1);

    private TiledMapTileLayer groundLayer;
    private TileMovement tileMovement;
    private Rectangle rectangle;

    @Before
    public void setUp() {
        groundLayer = new TiledMapTileLayer(10, 8, TILE_SIZE, TILE_SIZE);
        tileMovement = new TileMovement(groundLayer, Interpolation.linear);
        rectangle = new Rectangle().setWidth(TILE_SIZE).setHeight(TILE_SIZE);
    }

    @Test
    public void progressZeroKeepsRectangleAtSourceTileCenter() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, from, to, 0f);

        assertCenterIsAtTile(rectangle, 2f, 1f);
    }

    @Test
    public void progressOneMovesRectangleToTargetTileCenter() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, from, to, 1f);

        assertCenterIsAtTile(rectangle, 3f, 1f);
    }

    @Test
    public void progressHalfMovesRectangleHalfWayBetweenTileCenters() {
        tileMovement.moveRectangleBetweenTileCenters(rectangle, from, to, 0.5f);

        assertCenterIsAtTile(rectangle, 2.5f, 1f);
    }

    @Test
    public void interpolationShapesTheMovement() {
        TileMovement smoothMovement = new TileMovement(groundLayer, Interpolation.smooth);

        smoothMovement.moveRectangleBetweenTileCenters(rectangle, from, to, 0.25f);

        assertCenterIsAtTile(rectangle, 2f + smoothStep(0.25f), 1f);
    }

    private static float smoothStep(float progress) {
        return progress * progress * (3 - 2 * progress);
    }

    private static void assertCenterIsAtTile(Rectangle rectangle, float tileX, float tileY) {
        Vector2 center = rectangle.getCenter(new Vector2());
        assertEquals(tileX * TILE_SIZE + TILE_SIZE / 2f, center.x, 1e-4f);
        assertEquals(tileY * TILE_SIZE + TILE_SIZE / 2f, center.y, 1e-4f);
    }
}
