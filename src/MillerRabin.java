
import java.math.BigInteger;
import java.util.ArrayList;

public class MillerRabin {

    private int Smax;
    private BigInteger d;
    private BigInteger n;
    private ArrayList<Integer> a;
    private ArrayList<Boolean> results;

    public void solve(BigInteger n, ArrayList<Integer> a) {
        this.a = a;
        this.n = n;
        this.Smax = 0;
        this.d = n.subtract(new BigInteger("1"));
        while(d.mod(BigInteger.valueOf(2)).intValue() == 0) {
            d = d.divide(new BigInteger("2"));
            Smax++;
        }

        results = new ArrayList<>();
        for(Integer i : a) {
            results.add(solveBase(i));
        }
    }

    public boolean solveBase(int a) {
        GyorsHatvanyozas gyorshatvany = new GyorsHatvanyozas();

        if(gyorshatvany.solve(BigInteger.valueOf(a), d, n).intValue() == 1) {
            return true;
        }

        for(int i = 0;i < Smax; i++) {
            if(gyorshatvany.solve(BigInteger.valueOf(a), d.multiply(new BigInteger("2").pow(i)), n).intValue() == n.intValue()-1){
                return true;
            }
        }
        return false;
    }

    public int getSmax() {
        return Smax;
    }

    public BigInteger getD() {
        return d;
    }

    public boolean isPrime() {
        for(int i = 0;i < a.size(); i++) {
            if(!results.get(i)) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("------------------------\n" +
                "Miller-Rabin primteszt\n"
                + "Smax = " + Smax
                + "\nd = " + d + "\n");

        int count = 0;
        for(int i = 0;i < a.size(); i++) {
            if(results.get(i)) count++;
            s.append("base = " + a.get(i) + " - prime = " + results.get(i) + "\n");
        }

        if(count == results.size()) {
            s.append("Result: Valószínűleg prím\n");
        }
        else {
            s.append("Result: Összetett\n");
        }
        s.append("------------------------\n");

        return s.toString();
    }
}
