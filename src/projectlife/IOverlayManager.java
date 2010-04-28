package projectlife;
import processing.core.PVector;


public interface IOverlayManager {
	void show();
	
	void hide();
	
	void display();
	
	void click(PVector point);
}
