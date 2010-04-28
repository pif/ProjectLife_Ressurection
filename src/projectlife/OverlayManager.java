package projectlife;

import processing.core.PVector;

public class OverlayManager extends MyObject implements IOverlayManager{
	public boolean visible;
	
	public Overlay[] overlays;

	public OverlayManager(Main applet) {
		super(applet);
		overlays = new Overlay[0];
		visible = false;
	}
	
	public void click(PVector point) {
	}

	public void display() {
		for (int i = 0; i < overlays.length; i++) {
			overlays[i].display();
		}
	}

	public void hide() {
		visible = false;	
	}

	public void show() {
		visible = true;
	}
	
	public int getClickedOverlay(PVector point) {
		for (int i = 0; i < overlays.length; i++) {
			if (overlays[i].topLeft.x <= point.x
					&& overlays[i].bottomRight.x >= point.x
					&& overlays[i].topLeft.y <= point.y
					&& overlays[i].bottomRight.y >= point.y)
				return i;
		}
		return -1;
	}
}
