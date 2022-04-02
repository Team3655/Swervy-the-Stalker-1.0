package gameutil.math;

public class Sum extends Function{
	Function f1;
	Function f2;
	public Sum(Function f1,Function f2) {
		super();
		if (!(f1.unknown||f2.unknown)) {
			this.unknown=false;
			this.value=f1.value+f2.value;
		}
		this.f1=f1;
		this.f2=f2;
	}
	
	public Function[] getTerms() {
		return new Function[] {f1,f2};
	}
}
