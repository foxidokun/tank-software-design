package ru.mipt.bit.platformer.util;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class GdxGameUtilsTest {

    @Test
    public void continueProgressAdvancesByDeltaTimeOverSpeed() {
        float progress = GdxGameUtils.continueProgress(0f, 0.1f, 0.4f);

        assertEquals(0.25f, progress, 1e-6f);
    }

    @Test
    public void continueProgressClampsAtOne() {
        float progress = GdxGameUtils.continueProgress(0.9f, 1f, 0.1f);

        assertEquals(1f, progress, 0f);
    }

    @Test
    public void getSingleLayerReturnsTheOnlyLayer() {
        TiledMap map = new TiledMap();
        MapLayer layer = new MapLayer();
        map.getLayers().add(layer);

        assertSame(layer, GdxGameUtils.getSingleLayer(map));
    }

    @Test(expected = NoSuchElementException.class)
    public void getSingleLayerFailsWhenMapHasNoLayers() {
        GdxGameUtils.getSingleLayer(new TiledMap());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getSingleLayerFailsWhenMapHasMoreThanOneLayer() {
        TiledMap map = new TiledMap();
        map.getLayers().add(new MapLayer());
        map.getLayers().add(new MapLayer());

        GdxGameUtils.getSingleLayer(map);
    }
}
