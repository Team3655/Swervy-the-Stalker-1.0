package gameutil.math.geom;

public class Hypercube extends Orthotope {
	double length;
	
	public Hypercube(Point p,double length) {
		super(p,length);
		this.length=length;
	}
	
	public void setCenter(Point p) {
		this.p1=new Point(p.tuple.$S$(length/2));
		this.p2=new Point(p1.tuple.$A$(length));
	}
	
	public void setPos(Point p) {
		this.p1=p;
		this.p2=new Point(p1.tuple.$A$(length));
	}
	
	/**Move along a vector v
	 * 
	 * @param v
	 */
	public void move(Vector v) {
		this.p1=new Point(new Vector(this.p1.tuple).$A$(v));
		this.p2=new Point(new Vector(this.p2.tuple).$A$(v));
	}
	
	@Override
	public boolean contains(Point p) {
		//p1 has the same dimensionality as p2 so we can use either to determine dims of hypercube
		int dims=p1.tuple.n();
		for (int i=0; i<dims;i++) {
			double p1val=p1.tuple.i(i);
			double pval=p.tuple.i(i);
			//kind of like rectangle or voxel checks for containing points but in however many dimensions the hypercube is in
			//this criteria must be met for the point to be contained in the hypercube so return false if it is not met.
			//also nice and easy because it's not oriented
			if (!(p1val<=pval&&pval<=p1val+length)) {
				return false;
			}
		}
		int extraDims=p.tuple.n()-dims;
		for(int i=0; i<extraDims; i++) {
			//0 is the default value for all unassigned tuple values so if p has more dimensions but is at 0 in all of those dimensions it will still be contained in the hypercube
			//in other words, if the converse is true then return false
			if (p1.tuple.i(dims+i)!=p.tuple.i(dims+i)) {
				return false;
			}
		}
		//all criteria were met so the point is contained within the hypercube
		return true;
	}
	
	
	
	
}
