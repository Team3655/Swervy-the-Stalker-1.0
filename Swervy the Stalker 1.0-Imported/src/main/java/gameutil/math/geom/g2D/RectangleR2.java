package gameutil.math.geom.g2D;

import java.awt.*;

import gameutil.math.geom.g2D.PointR2;
import gameutil.math.geom.g2D.RectangleR2;
import gameutil.math.geom.g2D.ShapeR2;
import gameutil.math.geom.Vector;

public class RectangleR2 extends ShapeR2{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public static final int ID=3;
	
	private double width;
	private double height;
	
	
	/**Creates a rectangle centered around the point (x,y)
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public RectangleR2(double x,double y,double width,double height) {
		super(Math.abs(width)*Math.abs(height),2*Math.abs(width)+2*Math.abs(height),new PointR2(x,y));
		center = new PointR2(x,y);
		this.width=Math.abs(width);
		this.height=Math.abs(height);
	}
	
	/**not tested...
	 * 
	 * 
	 * @param p1
	 * @param p2
	 */
	public RectangleR2(PointR2 p1,PointR2 p2) {
		super(0,0,null);
		width=p1.getX()-p2.getX();
		height=p1.getY()-p2.getY();
		width=Math.abs(width);
		height=Math.abs(height);
		double x;
		if (p2.getX()>p1.getX()) {
			x=p2.getX()-width/2;
		} else {
			x=p1.getX()-width/2;
		}
		double y;
		if (p2.getY()>p1.getY()) {
			y=p2.getY()-height/2;
		} else {
			y=p1.getY()-height/2;
		}
		center = new PointR2(x,y);
		
		this.area=Math.abs(width)*Math.abs(height);
		this.perimeter=2*Math.abs(width)+2*Math.abs(height);
		center=new PointR2(x,y);
	}
	
	public RectangleR2(Rectangle r) {
		super(Math.abs(r.width)*Math.abs(r.height),2*Math.abs(r.width)+2*Math.abs(r.height),new PointR2(r.getCenterX(),r.getCenterY()));
		width=r.getWidth();
		height=r.getHeight();
	}
	
	//make an intersection rectangle function
	public RectangleR2 intersection(Rectangle r) throws Exception {
		if (intersects(r)) {
			return new RectangleR2(10,10,10,10);
		} else {
			throw new Exception();
		}
	}
	
	//intersects line function
	
	//intersects circle function
	
	//intersects line segment function
	
	
	
	public boolean setPos(double x,double y) {
		center.move(x, y);
		return true;
	}
	
	/**Checks if the specified point is contained within this rectangle
	 * 
	 * @param p
	 * @return
	 */
	public boolean contains(PointR2 p) {
		return p.getX()>=getMinX()&&p.getX()<=getMaxX()&&p.getY()>=getMinY()&&p.getY()<=getMaxY();
	}
	
	/**checks if this rectangle intersects another specified rectangle
	 * 
	 * @param r
	 * @return
	 */
	public boolean intersects(Rectangle r) {
		//if to the left of, to the right of, above, or below the other rectangle then they don't intersect
		if (getMaxX()<r.getMinX()||getMinX()>r.getMaxX()||getMinY()>r.getMaxY()||getMaxY()<r.getMinY()) {
			return false;
		}
		//otherwise they do.
		return true;
	}
	
	
	
	public double getMaxX() {
		return center.getX()+width/2;
	}
	
	public double getMinX() {
		return center.getX()-width/2;
	}
	
	public double getMaxY() {
		return center.getY()+height/2;
	}
	
	public double getMinY() {
		return center.getY()-height/2;
	}
	
	@Override
	public RectangleR2 clone() {
		return new RectangleR2(center.getX(),center.getY(),width,height);
	}
	
	@Override
	public int ID() {
		return ID;
	}

}
