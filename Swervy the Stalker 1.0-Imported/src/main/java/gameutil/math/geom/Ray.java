package gameutil.math.geom;

public class Ray extends Line{
	
	enum AXIOM {negative,zero,positive};
	
	/**initializes a ray that extends from the position vector <code>v1</code> and has a unit direction vector <code>v2</code>
	 * 
	 * @param v1
	 * @param v2
	 */
	public Ray(Point v1, Point v2) {
		super(v1, v2);
		// TODO Auto-generated constructor stub
	}
	
	/**initializes a ray that extends from the position vector <code>v1</code> and has a unit direction vector <code>v2</code>
	 * 
	 * @param v1
	 * @param v2
	 */
	public Ray(Vector v1, Vector v2) {
		super(v1, v2);
		// TODO Auto-generated constructor stub
	}
	
	/**initializes a ray that extends from point <code> p </code> and has a unit direction vector <code> v </code>
	 * 
	 * @param v
	 * @param p
	 */
	public Ray(Vector v, Point p) {
		super(p,new Point(v));
	}
	
	//Functional
	public boolean contains(Point p) {
		if (super.contains(p)) {
			AXIOM[] axioms=getAxioms();
			for (int i=0;i<axioms.length;i++) {
				double point=p.tuple.i(i);
				double start=P1.getSpds().i(i);
				switch (axioms[i]) {
					case negative:
						if (point>start) {
							return false;
						}
					break;
					case positive:
						if (point<start) {
							return false;
						}
					break;
					case zero:
						if (point!=start) {
							return false;
						}
					break;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	
	//Funtional
	public AXIOM[] getAxioms() {
		int dims;
		//System.out.println("v.n: "+v.n());
		//System.out.println("l.v.n: "+l.v.n());
		if (v.n()>P1.n()) {
			//if the other line has more dimensions set number of dimensions to the dimensions that the other line exists in.
			dims=v.n();
		} else {
			//other wise set the number of dimensions to the dimensions that the vector parallel to this line exists in (if v.n>p.n then v has higher dimensions and if v.n==p.n then they have equal dimensions)
			dims=P1.n();
		}
		
		AXIOM[] axioms=new AXIOM[dims];
		
		for (int i=0; i<dims; i++) {
			double start=P1.getSpds().i(i);
			double secondPoint=P2.getSpds().i(i);
			if (secondPoint<start) {
				axioms[i]=AXIOM.negative;
			} else if (secondPoint>start) {
				axioms[i]=AXIOM.positive;
			} else {
				axioms[i]=AXIOM.zero;
			}
		}
		
		return axioms;
	}
	
	//Functional
	public boolean intersects(Point p) {
		return contains(p);
	}
	
	//To be tested
	public boolean intersects(Ray r) {
		//intersects if the intersection is not SPACE
		return !(intersection(r).equals(SPACE));
	}
	
	//To be tested
	public boolean intersects(Line l) {
		//intersects if the intersection is not SPACE
		return !(intersection(l).equals(SPACE));
	}
	
	
	//Functional
	public Figure intersection(Point p) {
		if (contains(p)) {
			return p.clone();
		} else {
			return SPACE;
		}
	}
	
	//To be tested
	public Figure intersection(Line l) {
		Figure intersection=super.intersection(l);
		if (intersection instanceof Point&&contains((Point) intersection)) {
			return intersection;
		} else if (intersection instanceof Line){
			return clone();
		} else {
			return SPACE;
		}
	}
	
	//To be tested
	public Figure intersection(Ray r) {
		Point P1=new Point(this.P1);
		Point rP1=new Point(r.P1);
		//get the intersection of this ray and the line that the other ray is contained by
		Figure intersection=intersection(new Line(r.getEndPoints()[0],r.getEndPoints()[1]));
		
		if (intersection instanceof Ray) {
			
			//yes
			if (r.contains(P1)&&contains(rP1)) {
				//rays must point away from each other but intersect so return a line segment
				return new LineSeg(P1,rP1);//a line segment consisting of the bases
			//yes
			} else if (r.contains(P1)) {
				
				//if the other ray contains the origin of this ray but this ray doesn't contain the origin of the other ray
				//the rays must be pointing the same direction. In this case return this ray.
				return clone();
			//yes
			} else if (contains(rP1)) {
				System.out.println("case yes");
				//if this ray contains the origin of the other ray but that ray doesn't contain the origin of this ray
				//the rays must be pointing the same direction. In this case return the other ray.
				return r.clone();
			} else {
				//the rays point away from each other and the bases do not touch so 
				return SPACE;
			}
		} else if (intersection instanceof Point && r.contains((Point) intersection)) {
			//if the intersection is a point and the other ray contains that point then
			return intersection;
		} else {
			return SPACE;
		}
	}
	
	//finish
	public Figure intersection(LineSeg ls) {
		Figure intersection=intersection((Line) ls.ray1);
		if (intersection instanceof Point&&contains((Point) intersection)&&ls.contains((Point) intersection)) {
			return intersection;
		} else if (intersection instanceof Ray) {
			
		}
		return SPACE;
	}
	
	//functional
	public Ray clone() {
		return new Ray(P1,P2);
	}
	
	public Point[] getEndPoints() {
		return new Point[] {new Point(P1),new Point(P2)};
	}

}
