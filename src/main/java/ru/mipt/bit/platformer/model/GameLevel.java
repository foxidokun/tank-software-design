package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.List;

public class GameLevel {

    private final GridPoint2 size;
    private final int bulletDamage;
    private final float bulletSpeed;
    private final List<GameObject> objects = new ArrayList<>();
    private final List<LevelListener> listeners = new ArrayList<>();

    public GameLevel(GridPoint2 size, int bulletDamage, float bulletSpeed) {
        this.size = new GridPoint2(size);
        this.bulletDamage = bulletDamage;
        this.bulletSpeed = bulletSpeed;
    }

    public float getBulletSpeed() {
        return bulletSpeed;
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
        if (objects.remove(object)) {
            for (LevelListener listener : listeners) {
                listener.objectRemoved(object);
            }
        }
    }

    public void live(float tick) {
        for (GameObject object : new ArrayList<>(objects)) {
            if (objects.contains(object)) {
                object.live(tick);
            }
        }
    }

    public List<GameObject> getObjects() {
        return new ArrayList<>(objects);
    }

    public GameObject objectAt(GridPoint2 cell) {
        for (GameObject object : objects) {
            if (object.collidesAt(cell)) {
                return object;
            }
        }
        return null;
    }

    public boolean hitAt(GridPoint2 cell) {
        GameObject target = objectAt(cell);
        if (target == null) {
            return false;
        }
        target.hit(bulletDamage);
        return true;
    }

    public boolean isInside(GridPoint2 cell) {
        return cell.x >= 0 && cell.x < size.x && cell.y >= 0 && cell.y < size.y;
    }
}
