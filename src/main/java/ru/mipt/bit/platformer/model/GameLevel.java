package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {

    private final GridPoint2 size;
    private final List<GameObject> objects = new ArrayList<>();
    private final List<LevelListener> listeners = new ArrayList<>();

    public GameLevel(GridPoint2 size) {
        this.size = new GridPoint2(size);
    }

    public void addListener(LevelListener listener) {
        listeners.add(listener);
    }

    public void add(GameObject object) {
        objects.add(object);
        for (LevelListener listener : listeners) {
            listener.objectAdded(object);
        }
    }

    public void remove(GameObject object) {
        objects.remove(object);
        for (LevelListener listener : listeners) {
            listener.objectRemoved(object);
        }
    }

    public void live(float tick) {
        for (GameObject object : new ArrayList<>(objects)) {
            object.live(tick);
        }
    }

    public List<GameObject> getObjects() {
        return objects;
    }

    public GameObject objectAt(GridPoint2 cell) {
        for (GameObject object : objects) {
            if (object.collidesAt(cell)) {
                return object;
            }
        }
        return null;
    }

    public boolean isInside(GridPoint2 cell) {
        return cell.x >= 0 && cell.x < size.x && cell.y >= 0 && cell.y < size.y;
    }
}
