package gameutil.math.geom.g2D;

import gameutil.math.geom.g2D.Circle;
import gameutil.math.geom.g2D.PointR2;
import gameutil.math.geom.g2D.ShapeR2;

/*To Do:
 * Make all the circle maths...
 * 
 */
public class Circle extends ShapeR2{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public static final int id=6;
	protected double r;

	public Circle(PointR2 center, double r) {
		super(Math.PI*Math.pow(r, 2),Math.PI*2*r,center);
		this.r = r;
	}

	//TODO make intersection functions
	//TODO make intersects functions
	//TODO make contains functions
	
	@Override
	public Circle clone() {
		return new Circle(center.clone(),r);
	}
	
	@Override
	public int ID() {
		return ID;
	}

}
