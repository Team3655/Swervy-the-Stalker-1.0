package gameutil.math.geom;

import gameutil.math.geom.g2D.PointR2;

public abstract class Brane extends Figure{
	protected double iSpace; //inner space (e.g. area, volume, content)
	protected double sSpace; //surrounding space (e.g. perimeter, surface area)
	private Tuple center; //the center of the object (the same as position)
	
	
	protected Brane(/*int p,*/double iSpace,double sSpace,Tuple center) {
		//super(p);
		this.iSpace=iSpace;
		this.sSpace=sSpace;
		this.center=center;
	}
	
	protected Brane(/*int p,*/double iSpace,double sSpace,Point pos) {
		//super(p);
		this.iSpace=iSpace;
		this.sSpace=sSpace;
		this.center=pos.tuple;
	}
	
	public Point getCenter() {
		return new Point(center);
	}
	
	protected void setCenter(Point newCenter) {
		center=newCenter.tuple;
	}
	
}
