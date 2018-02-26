import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	ArrayList<Object> objects;
	Player player;

	public ObjectManager() {
		objects = new ArrayList<Object>();
	}

	public void addObject(Object o) {
		objects.add(o);
		System.out.println(o + " succesfully added to manager!");
	}

	public void update() {
		for (int i = 0; i < objects.size(); i++) {
			Object o = objects.get(i);
			o.update();
		}
		purgeObjects();
	}

	public void draw(Graphics g) {
		for (int i = 0; i < objects.size(); i++) {
			Object o = objects.get(i);
			o.draw(g);
			// System.out.println("manager drawing object " + i);
		}
	}

	public void purgeObjects() {
		for (int i = 0; i < objects.size(); i++) {
			if (!objects.get(i).isAlive) {
				objects.remove(i);
			}
		}
	}

	void spitArray() {
		for (Object o : objects) {
			// System.out.println(o);
		}
	}
}
