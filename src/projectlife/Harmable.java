package projectlife;

import processing.core.PVector;

public interface Harmable {
	void harm(float damage);
	PVector getLocation();
	float getRadius();
}
