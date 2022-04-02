package gameutil.math;

public class Function {
	
	boolean unknown=true;
	double value;
	
	/**Creates an unknown function (also commonly known as an unknown variable -- variables can be thought of as functions).
	 * 
	 */
	public Function() {
		
	}
	
	/**Creates a function that always maps to <code>value</code>.
	 * 
	 * @param value
	 */
	public Function(double value) {
		this.value=value;
		this.unknown=false;
	}
	
	public Function apply(Function[] funcs) {
		if (unknown) {
			return new Function();
		} else {
			return new Function(value);
		}
	}
}
