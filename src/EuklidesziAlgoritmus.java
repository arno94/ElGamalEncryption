
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class EuklidesziAlgoritmus {

	private ArrayList<BigInteger> rk;
	private ArrayList<BigInteger> qk;
	private ArrayList<BigInteger> xk;
	private ArrayList<BigInteger> yk;
	
	public void solve(BigInteger p, BigInteger q) {
		if(q.compareTo(p) == -1) {
			BigInteger c;
			c = p;
			p = q;
			q = c;
		}		
		rk = new ArrayList<>(Arrays.asList(p, q));
		qk = new ArrayList<>(Arrays.asList(new BigInteger("0")));
		xk = new ArrayList<>(Arrays.asList(new BigInteger("1"),new BigInteger("0")));
		yk = new ArrayList<>(Arrays.asList(new BigInteger("0"),new BigInteger("1")));
		
		while(rk.get(rk.size()-1).compareTo(new BigInteger("0")) == 1) {
			BigInteger div = (rk.get(rk.size() -2).divide(rk.get(rk.size() -1)));
            BigInteger s = (rk.get(rk.size() -2).mod((rk.get(rk.size() -1))));
			rk.add(s);
			qk.add(div);		
			
			/*if(s.compareTo(new BigInteger("0")) != 0) {
				xk.add(xk.get(xk.size()-1).multiply(qk.get(qk.size()-1)) + xk.get(xk.size()-2));
				yk.add(yk.get(yk.size()-1) * qk.get(qk.size()-1) + yk.get(yk.size()-2));
			}			*/
		}
	}	
	
	public BigInteger getLnko() {
		return rk.get(rk.size() - 2);
	}
	
	/*public int getXk() {
		return (int)Math.pow(-1, getK()) * xk.get(xk.size()-1);
	}*/
	
	/*public int getYk() {
		return (int)Math.pow(-1, getK()+1) * yk.get(yk.size()-1);
	}*/
	
	public int getK() {
		return yk.size()-1;
	}
	
	public boolean areRelativePrimes() {
		return getLnko().compareTo(new BigInteger("1"))==0;
	}
	
	@Override
	public String toString() {
		return "------------------------\n" + 
				"Euklideszi algoritmus\n"
				+ "lnko = " + getLnko()
				//+ "\nxk = " + getXk()
				//+ "\nyk = " + getYk()
				+ "\nrelativ primek = " + areRelativePrimes()
				+ "\n------------------------";
	}
	
}
