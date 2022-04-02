package gameutil.math.geom;

//import gameutil.text.Console;

public class Plane extends Figure{
	Vector v1;
	Vector v2;
	Vector b;
	
	public Plane(Vector v1,Vector v2, Vector b) {
		this.v1=v1;
		this.v2=v2;
		this.b=b;
	}
	
	public Plane(Vector v1,Vector v2, Point p) {
		this.v1=v1;
		this.v2=v2;
		this.b=new Vector(p);
	}
	
	public Plane(Point v1,Point v2, Point p) {
		this.v1=new Vector(v1);
		this.v2=new Vector(v2);
		this.b=new Vector(p);
	}
	
	public Point equation(double t,double s) {
		return new Point(v1. $X$ (t). $A$ (v2. $X$ (s)). $A$ (b));
	}
	
	public boolean equals(Plane p) {
		//check that all components of all normals are proportional (need to figure out how to get normal in n-dimensions)
		return false;
		
	}
	public boolean contains(Point p) {
		//(t)v1+(s)v2+b=p where t and s are scalars
		//subtract b from the input point
		p=new Point(new Vector(p.tuple).$S$(b));
		//now (t)v1+(s)v2=p where t and s are scalars and p is the new value of p. This is an equivalent statement to the original.
		int dims=p.tuple.n();
		if (v1.getSpds().n()>dims) {
			dims=v1.getSpds().n();
		}
		if (v2.getSpds().n()>dims) {
			dims=v2.getSpds().n();
		}
		//find elements of v1 and v2 that are non zero and in different dimensions
		int i=0;
		int j=0;
		int nonZeroIndex=-1;
		boolean cont=false;
		while (i<dims && !cont) {
			//search through v1
			if (v1.getSpds().i(i)!=0) {
				nonZeroIndex=i;
				j=0;
				//search through v2
				while (j<dims && !cont) {
					if (v2.getSpds().i(j)!=0&&j!=i) {
						System.out.println("We found our linear equations!");
						//we found our linear equations!
						cont=true;
					}
					j++;
				}
				j--;
			}
			i++;
		}
		i--;
		if (!cont) {
			System.err.println("Plane poorly defined... may have been defined with colinear vectors or zero vectors (resulting the span of a line or a point)");
			if (nonZeroIndex>=0) {
				//(t)a+(s)b=c where a is the value at the nonZeroIndex of v1, b is the value at the same index of v2, and c is value at the same index of p
				//there is definitely a solution
				return true;
			}
			//if no nonZeroIndex value was found in v1 then look in v2
			i=0;
			while (i<dims && !cont) {
				if (v2.getSpds().i(i)!=0) {
					//we found a non zero value in v2!
					//(t)a+(s)b=c same as the other equation except at the index i (the non zero value at index i of v2)
					//there is definitely a solution (or many)
					return true;
				}
				i++;
			}
			i--;
			//no non zero values were found anywhere so this 'plane' was defined with the span of a point (the origin)
			//it contains the point if the point is the origin.
			return p.equals(Point.origin(dims));
		}
		
		//if all went well then we can now solve a system of linear equations and then check that our solution is correct (to avoid extraneous solutions)
		//(t)v1[i]+(s)v2[i]=p[i] *we know that v1[i] is not zero
		//(t)v1[j]+(s)v2[j]=p[j] *we know that v2[j] is not zero
		//
		//t=(p[i]-(s)v2[i])/v1[i] *we know we aren't dividing by zero
		//(p[i]-(s)v2[i])v1[j]/v1[i]+(s)v2[j]=p[j]
		//(algebra done on paper)
		//s=(v1[i]p[j]-p[i]v1[j])/(v1[i](v2[j]-v2[i]v1[j])) when (v2[j]-v2[i]v1[j])!=0 if (v2[j]-v2[i]v1[j])=0 then s may be any real number as long as v1[i]p[j]-p[i]v1[j]=0. If v1[i]p[j]-p[i]v1[j]!=0 and (v2[j]-v2[i]v1[j])=0 then there are no solutions for s.
		//let's put v1[i] in a variable called ai, v1[j] in a variable called aj,
		//v2[i] in a variable called bi, v2[j] in a variable called bj,
		//p[i] in a variable called pi, and pj
		//for convenience we will also store (v2[j]-v2[i]v1[j]) in d
		double ai=v1.getSpds().i(i);
		double aj=v1.getSpds().i(j);
		double bi=v2.getSpds().i(i);
		double bj=v2.getSpds().i(j);
		double pi=p.tuple.i(i);
		double pj=p.tuple.i(j);
		double d=bj-bi*aj;
		double s;
		double t;
		if (d==0) {
			if (ai*pj-pi*aj==0) {
				/*Console.s.println("Warning: s value found to be all real numbers");
				Console.s.println("i="+i);
				Console.s.println("j="+j);*/
				
				//s can be any real number
				//I think... unless another potential system restricts it... hopefully it's fine...
				//it's probably fine...
				//TODO make sure it's fine
				//set s to 0 so it's easy to get t
				s=0;
				t=pi/ai;
				System.err.println("Warning: s value found to be all real numbers");
				new Point(p.tuple.$A$(b.getSpds())).printVals("Point");
				System.out.println("Plane: ");
				v1.getSpds().printVals("Basis Vector v1");
				v2.getSpds().printVals("Basis Vector v2");
			} else {
				//in this case the system is inconsistent so the point is definitely not contained
				return false;
			}
		} else {
			//otherwise we get to just calculate the unique s and t values! yay!
			s=(ai*pj-pi*aj)/(ai*(bj-bi*aj));
			t=(pi-s*bi)/ai;
			//Console.s.println("(t,s) = ("+t+","+s+")");
		}
		//if the warning is received and this reports false then look into it as a specific case
		//that should help give more insight into the problem (if there is one)
		
		//this tests the s and t values and reports the result
		return (v1.$X$(t).$A$(v2.$X$(s)).equals(new Vector(p)));
	}
	
	public boolean intersects(Point p) {
		return contains(p);
	}
	
	public boolean contains(Line l) {
		//if the plane contains two points on l then l and the plane are coplanar
		return contains(l.equation(0))&&contains(l.equation(1));
	}
	
	public boolean intersects(Line l) {
		//check if coplanar
		if (contains(l)) {
			return true;
		}
		// 
		return false;
	}
	
	
}