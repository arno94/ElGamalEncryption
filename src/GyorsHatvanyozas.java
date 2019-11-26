import java.math.BigInteger;

public class GyorsHatvanyozas {

    public BigInteger solve(BigInteger n, BigInteger pow, BigInteger mod) {
        BigInteger finalMod = new BigInteger("1");

        String binary = pow.toString(2);

        BigInteger num = n.mod(mod);
        for(int i = 0;i < binary.length(); i++) {
            if(binary.charAt(binary.length() - 1 - i) == '1') {
                finalMod = finalMod.multiply(num);
            }
            num = (num.multiply(num)).mod(mod);
        }
        finalMod = finalMod.mod(mod);
        if(finalMod.compareTo(new BigInteger("0")) == -1) finalMod = finalMod.add(mod);
        return finalMod;
    }

}
