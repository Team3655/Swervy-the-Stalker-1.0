package gameutil.math.geom;

import java.awt.geom.Line2D;

import gameutil.math.geom.g2D.LineOverlapException;
import gameutil.math.geom.g2D.LineR2;
import gameutil.math.geom.g2D.NoIntersectionException;
import gameutil.math.geom.g2D.OutsideOfDomainOrRangeException;
import gameutil.math.geom.g2D.PointR2;
//import gameutil.text.Console;

public class Line extends Figure{
	protected Vector P1;//used as b vector
	protected Vector P2;
	protected Vector v;//the vector parallel to the line
	
	/**initializes a line that intersects the tails of the input vectors
	 * 
	 * @param v1
	 * @param v2
	 */
	public Line(Vector v1,Vector v2){
		this.P1=v1;
		this.P2=v2;
		v=P2. $S$ (P1);
	}
	
	/**initializes a line that intersects the specified points
	 * 
	 * @param v1
	 * @param v2
	 */
	public Line(Point v1,Point v2){
		this.P1=new Vector(v1);
		this.P2=new Vector(v2);
		v=P2. $S$ (P1);
	}
	
	/**initializes a line that contains point <code> p </code> and has a unit direction vector <code> v </code>
	 * 
	 * @param v
	 * @param p
	 */
	public Line(Vector v, Point p) {
		this.P1=new Vector(p);
		this.v=v;
		P2=P1.$A$(v);
	}
	
	public boolean intersects(Point p) {
		return contains(p);
	}
	
	public boolean intersects(Ray r) {
		return r.intersects(this);
	}
	
	//functional
	public boolean intersects(Line l) {
		
		
		if (intersection(l).equals(Figure.SPACE)) {
			return false;
		} else {
			return true;
		}
	}
	
	public void translateAlongLine(Line l,double t) {
		Vector movementVector=l.v.$X$(t);
		P1=P1.$A$(movementVector);
		P2=P2.$A$(movementVector);
	}
	
	
	
	//Functional!
	public boolean contains(Point p){
		
		int dims;
		if (p.tuple.n()>v.n()) {
			//if the point has more dimensions set number of dimensions to the dimensions that the point exists in.
			dims=p.tuple.n();
		} else {
			//other wise set the number of dimensions to the dimension that the vector parallel to this line exists in (if v.n>p.n then v has higher dimensions and if v.n==p.n then they have equal dimensions)
			dims=v.n();
			if (dims<=1) {//a line in 1 dimension will always intersect any point in the same dimension
				return true;
			}
		}
		
		//create a list of all dimensional velocities
		LineR2[] dimVs=new LineR2[dims];
		double tValue = 0;
		for (int i=0; i<dimVs.length;i++) {
			dimVs[i]=new LineR2(v.getSpds().i(i),P1.getSpds().i(i));
		}
		
		int state=0;
		//check if a line that contains the specified point intersects this line at that point
		boolean intersects=true;
		for (int i=0; i<dimVs.length;i++) {
			switch(state) {
				case 0://find tValue
					try {
						tValue=dimVs[i].xFromY(p.tuple.i(i));
						state++;
					} catch (OutsideOfDomainOrRangeException e) {
						return false; //value of p[i] not contained on line
					} catch (Exception e) {
						
					}
				break;
				case 1://check the rest of the dimensional velocities to see that they match the t value or contain the t value
					try {
						double tToCheck=dimVs[i].xFromY(p.tuple.i(i));
						if (tToCheck!=tValue) {
							return false; //dimensional velocity does not have the same t value as the rest so does not contain the point
						}
					} catch (OutsideOfDomainOrRangeException e) {
						return false; //value of p[i] not contained on line
					} catch (Exception e) {
						//dimensional velocity contains t value
					}
				break;
			}
		}
		
		return intersects;
	}
	
	public boolean contains(Ray r) {
		//if the intersection is a ray then the ray is contained within the line
		if (intersection(r) instanceof Ray) {
			return true;
		}
		return false;
	}
	
	public Vector equation(double t){
		return (v. $X$ (t)). $A$ (P1);
	}
	
	//Functional
	public Figure intersection(Line l) {
		//If l contains two points on this line then l is congruent to this line
				if(l.contains(new Point(P1))&&l.contains(new Point(P2))) {
					//Console.s.println("Congruent");
					return l.clone();
				}
				
				int dims;
				//System.out.println("v.n: "+v.n());
				//System.out.println("l.v.n: "+l.v.n());
				if (l.v.n()>v.n()) {
					//if the other line has more dimensions set number of dimensions to the dimensions that the other line exists in.
					dims=l.v.n();
				} else {
					//other wise set the number of dimensions to the dimensions that the vector parallel to this line exists in (if v.n>p.n then v has higher dimensions and if v.n==p.n then they have equal dimensions)
					dims=v.n();
				}
				//Console.s.println("Dims: "+dims);
				
				//System.out.println("dimVs");
				//create a list of all dimensional velocities for this line
				LineR2[] dimVs=new LineR2[dims];	
				for (int i=0; i<dims;i++) {
					dimVs[i]=new LineR2(v.getSpds().i(i),P1.getSpds().i(i));
				}
				//System.out.println("dimVs'");
				//create a list of all dimensional velocities for the other line
				LineR2[] dimVsl=new LineR2[dims];
				
				//System.out.println(dimVsl.length);
				for (int i=0; i<dims;i++) {
					dimVsl[i]=new LineR2(l.v.getSpds().i(i),l.P1.getSpds().i(i));
				}
				
				
				
				//CASES:
				/*
				 * Case 0:
				 *		mi==0 && mi'==0
				 * 
				 * Case 1:
				 * 		mi'!=0 
				 * 
				 * Case 2:
				 * 		mi!=0 && mi'==0
				 * 
				 * Case 3:
				 * 		mi==0 && m
				 * 		
				 * 
				 * 
				 */
				
				int state=0;
				LineR2 v1=null;//vq
				LineR2 vl1=null;//vq'
				double t=0;
				for (int i=0; i<dims;i++) {
					LineR2 vl=dimVsl[i];//vi'
					LineR2 v=dimVs[i];//vi
					//Console.s.println("i = "+i);
					//Console.s.println("Ms: "+v.getM()+"; "+vl.getM());
					//set v1 and vl1 to be substituted into later
					if (state==0) {
						v1=v;
						vl1=vl;
						if (vl.getM()==0&&v.getM()==0&&vl.getB()!=v.getB()) {
							//if slopes are zero and bs are different then lines can't intersect
							//Console.s.println("Zero slope velocities with differing bs");
							return SPACE;
						} else if (v.getM()!=0||vl.getM()!=0) {
							if (i==dims-1) {
								i=0;
							}
							state++;//<<<FIX>>>
						}
						//Console.s.println("state = "+state);
					//find t or determine that lines do not intersect
					} else if (state==1) {
						if (vl.getM()==0&&v.getM()==0) {
							if (vl.getB()!=v.getB()) {
								//if slopes are zero and bs are different then lines can't intersect
								//Console.s.println("Zero slope velocities with differing bs");
								return SPACE;
							}
							//Console.s.println("Same slope same bs");
							
						} else if (vl.getM()==0&&v.getM()!=0) {
							t=(vl.getB()-v.getB())/v.getM();
							//new Point(equation(t)).printVals("intersection t");
							//Console.s.println("May intersect (one m 0):"+l.contains(new Point(equation(t))));
							Point intersection=new Point(equation(t));
							if (l.contains(intersection)) {
								return intersection;
							} else {
								return SPACE;
							}
							
						} else if (v.getM()==0&&vl.getM()!=0) {
							//t'
							t=(v.getB()-vl.getB())/vl.getM();
							//new Point(l.equation(t)).printVals("intersection t' = "+t);
							//Console.s.println("May intersect (one m 0):"+contains(new Point(l.equation(t))));
							Point intersection=new Point(l.equation(t));
							if (contains(intersection)) {
								return intersection;
							} else {
								return SPACE;
							}
						} else if ((v1.getM()-(vl1.getM()*v.getM())/vl.getM())!=0){
							t=(-v1.getB()+(vl1.getM()*(v.getB()-vl.getB())/vl.getM())+vl1.getB())/(v1.getM()-(vl1.getM()*v.getM())/vl.getM());
							//new Point(equation(t)).printVals("intersection t = "+t);
							//Console.s.println("May intersect:"+l.contains(new Point(equation(t))));
							Point intersection=new Point(equation(t));
							if (l.contains(intersection)) {
								return intersection;
							} else {
								return SPACE;
							}
						}
					
					//System.out.println(dimVs[i].intersects(dimVsl[i]));
					}
				}
				
				return SPACE;
	}
	
	public Figure intersection(Point p) {
		if (intersects(p)) {
			return p.clone();
		} else {
			return Figure.SPACE;
		}
	}
	
	public Figure intersection(Ray r) {
		return r.intersection(this);
	}
	
	public boolean equals(Line l) {
		//if the line to compare to contains two points that also lie on this line then the lines are coincident
		return l.contains(new Point(equation(0)))&&l.contains(new Point(equation(1)));
	}
	public double distance(Point p) {
		//TODO make this
		return 0;
	}
	
	public Figure closestPoints(Point p) {
		//TODO make this and then use it to find line to orthotope intersections
		return Figure.SPACE;
	}
	
	public Line clone() {
		return new Line(P1.clone(),P2.clone());
	}
	
	//<INTERSECTION METHODS>
	
	
}
