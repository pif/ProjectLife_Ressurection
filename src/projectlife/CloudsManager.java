package projectlife;

import processing.core.PVector;
import processing.xml.XMLElement;

public class CloudsManager extends OverlayManager implements IOverlayManager{

	public CloudsManager(Main applet,  XMLElement preferences) {
		super(applet);	
	}
	
	public void click(PVector point) {
		//do nothing... or let the rain fall...hmmm...
	}

	public void display() {
		
		
	}

	public void hide() {
		super.hide();
		
	}

	public void show() {
		super.show();
		
	}

}
