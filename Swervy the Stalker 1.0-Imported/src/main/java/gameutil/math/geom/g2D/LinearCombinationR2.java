package gameutil.math.geom.g2D;

import java.util.ArrayList;

import gameutil.math.geom.Tuple;

public class LinearCombinationR2 extends FigureR2{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3751894413087842531L;

	@Override
	public int ID() {
		
		return 8;
	}
	
	protected ArrayList<Double> coef;
	protected ArrayList<Integer> vars;
	//use var id -1 for x^0 = 1 so that [n,-1] is n*1 which is just n?
	
	public LinearCombinationR2(Tuple coefficients, int[] ids) {
		
		if (ids.length!=coefficients.n()) {
			System.err.println("Warning! Encountered linear combination with different number of coefficients than variables!");
			System.err.println("Truncating anomalous linear combination to lowest value...");
			if (coefficients.n()>ids.length) {
				coefficients=coefficients.$S$(new Tuple(1,ids.length));
			} else if (coefficients.n()<ids.length) {
				int[] oldIds=ids;
				ids= new int[coefficients.n()];
				for (int i=0;i<coefficients.n();i++) {
					ids[i]=oldIds[i];
				}
			}
		}
		vars = new ArrayList<Integer>();
		coef = new ArrayList<Double>();
		for (int i=0;i<ids.length;i++) {
			vars.add(ids[i]);
			coef.add(coefficients.i(i));
		}
	}
	
	public LinearCombinationR2(ArrayList<Double> coefficients,ArrayList<Integer> ids) {
		if (ids.size()!=coefficients.size()) {
			System.err.println("Warning! Encountered linear combination with different number of coefficients than variables!");
			System.err.println("Truncating anomalous linear combination to lowest value...");
			if (coefficients.size()>ids.size()) {
				coefficients=(ArrayList<Double>) coefficients.subList(0, ids.size()-1);
			} else if (coefficients.size()<ids.size()) {
				ids=(ArrayList<Integer>) ids.subList(0, coefficients.size()-1);
			}
		}
		coef=coefficients;
		vars=ids;
	}
	
	public LinearCombinationR2 simplify() {
		
		LinearCombinationR2 simplified = this.clone();
		
		//combine like terms
		ArrayList<Integer> toRemove=new ArrayList<>(); //terms to remove after loop (if we did it in the middle then the indexes would be off)
		for (int i=0; i<simplified.vars.size(); i++) {
			if (toRemove.contains(i)) {
				//make sure that once a variable has had it's coefficients simplified we don't repeat the process
				//(especially because if we did it from this new starting point then the one instance that wasn't added to the toRemove list would get added and then the end result would be inaccurate)
				continue;
			}
			int varID=simplified.vars.get(i);
			double value = simplified.coef.get(i);
			//check through the rest of the unremoved parts of the linear combination for like terms
			for (int j=0; j<simplified.vars.size();j++) {
				if (j!=i&&!toRemove.contains(j)) {
					if (simplified.vars.get(j)==varID) {
						//like term found!
						//add its coefficient to the total coefficient of that variable
						//(combine like terms)
						value+=simplified.coef.get(j);
						//add it's index to the toRemove list
						toRemove.add(j);
					}
				}
			}
			//set's the final value of the coefficient of this variable to the final sum of coefficients of that variable
			simplified.coef.set(i, value);
		}
		//remove all those terms that we put in the toRemove list
		for (int i:toRemove) {
			coef.remove(i);
			vars.remove(i);
		}
		
		//simplify 0 products (e.g. 0*x+3*y+0*z+1*w simplifies to 3*y+1*w)
		
		for (int i=0;i<simplified.vars.size();i++) {
			if (coef.get(i)==0) {
				//toss that 0 out
				simplified.coef.remove(i);
				simplified.vars.remove(i);
			}
		}
		
		//I'm pretty sure that's all we can do... I mean, I guess we could do GCF but... if that's actually useful then we can put that in it's own function
		//plus GCF only applies to special cases where the coefficients are integers
		//I'll add it later if I need it
		
		//return the simplified LinearCombination!
		return simplified;
	}
	
	public LinearCombinationR2 $A$(LinearCombinationR2 b) {
		LinearCombinationR2 sum=this.clone();
		sum.coef.addAll(b.coef);
		sum.vars.addAll(b.vars);
		return sum;
	}
	
	public LinearCombinationR2 $S$(LinearCombinationR2 b) {
		LinearCombinationR2 dif=this.clone();
		dif.coef.addAll(b.coef);
		dif.vars.addAll(b.vars);
		//go through all the coefficients of the difference from b and make them negative 
		for (int i=coef.size()-1;i<dif.coef.size();i++) {
			dif.coef.set(i, dif.coef.get(i)*-1);
		}
		return dif;
	}
	
	public LinearCombinationR2 clone() {
		return new LinearCombinationR2((ArrayList<Double>) coef.clone(),(ArrayList<Integer>) vars.clone());
		
	}
	
	
}
