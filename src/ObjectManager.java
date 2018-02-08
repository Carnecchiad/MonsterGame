import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
		ArrayList<Object> objects;
		Player player;
		
		
		public ObjectManager(){
			objects = new ArrayList<Object>();
		}
		
		public void addObject(Object o){
			objects.add(o);
		}
		
		public void update(){
			for (int i = 0; i < objects.size(); i++) {
				Object o = objects.get(i);
				o.update();
			}
			purgeObjects();
		}
		
		public void draw(Graphics g){
			for(int i = 0; i < objects.size(); i++){
				Object o = objects.get(i);
				o.draw(g);
			}
		}
		
		public void purgeObjects(){
			for (int i = 0; i < objects.size(); i++) {
				if(!objects.get(i).isAlive){
					objects.remove(i);
				}
				
			}
		}
		
		public void checkCollision() {
			for (int i = 0; i < objects.size(); i++) {
				if (player.collisionBox.intersects(objects.get(i).collisionBox)) {
					player.collision = true;
				}
			}

		}
		
		
}
