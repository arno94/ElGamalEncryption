import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;

public class ElGamal {

    private BigInteger p;
    private BigInteger g;
    private BigInteger alpha;
    private BigInteger h;

    public ElGamal() {

        final SecureRandom random = new SecureRandom();
        final GyorsHatvanyozas gyorsHatvanyozas = new GyorsHatvanyozas();
        final MillerRabin millerRabin = new MillerRabin();
        final EuklidesziAlgoritmus euklidesziAlgoritmus = new EuklidesziAlgoritmus();
        // kulcsgenerálás
        System.out.println("Kulcsgenerálás");

        // bitek száma
        int numBits = 15;
        p = new BigInteger(numBits, random);
        // nagy prím választása
        while(true) {
            millerRabin.solve(p, new ArrayList<>(Arrays.asList(3, 5)));
            if(millerRabin.isPrime()) break;
            do {
                p = new BigInteger(numBits, random);
            } while (p.compareTo(BigInteger.valueOf(3)) <= 0);
        }

        // g primitív gyök választása, ami relatív prím p-vel
        while(true) {
            do {// itt nem jó
                g = new BigInteger(p.bitLength(), random);
            } while (g.compareTo(p) != -1 && g.compareTo(BigInteger.valueOf(0)) > 0);
            euklidesziAlgoritmus.solve(p, g);

            if (euklidesziAlgoritmus.areRelativePrimes()) break;
        }

        // véletlen alpha kiválasztása
        do {
            alpha = new BigInteger(p.bitLength(), random);
        } while (alpha.compareTo(p) > 0);

        // h = g^alpha mod p
        h = gyorsHatvanyozas.solve(g, alpha, p);

        System.out.println("Prime = " + p);
        System.out.println("Primitív gyök: " + g);
        System.out.println("Alpha: " + alpha);
        System.out.println("h = " + h);

        System.out.println("~~~~~~~~~~");
        System.out.println("SK = (a) = (" + alpha + ")");
        System.out.println("PK = (p, g, h) = (" + p + ", " + g + ", " + h + ")");
        System.out.println("~~~~~~~~~~");

        //final BigInteger m = BigInteger.valueOf(150);
        BigInteger m;
        do {
            m = new BigInteger(p.bitLength(), random);
        } while (m.compareTo(p) >= 0);
        System.out.println("Encrypt (" + m + ")");
        BigInteger[] c = encrypt(m);
        System.out.println("c1 = " + c[0]);
        System.out.println("c2 = " + c[1]);
        System.out.println("~~~~~~~~~~");
        System.out.println("Decrypt");
        System.out.println(decrypt(c[0], c[1]));
    }

    private BigInteger[] encrypt(final BigInteger m) {
        SecureRandom random = new SecureRandom();
        final GyorsHatvanyozas gyorsHatvanyozas = new GyorsHatvanyozas();
        BigInteger k;
        do {
            k = new BigInteger(p.bitLength(), random);
        } while (!(k.compareTo(p.subtract(new BigInteger("2"))) <= 0 && k.compareTo(BigInteger.valueOf(0)) == 1));
        System.out.println("k = " + k);
        BigInteger c1 = gyorsHatvanyozas.solve(g, k, p);
        BigInteger c2 = m.mod(p).multiply(gyorsHatvanyozas.solve(h, k, p)).mod(p);
        BigInteger c21 = m.mod(p);
        BigInteger c22 = gyorsHatvanyozas.solve(h, k, p);
        c2 = (c21.multiply(c22)).mod(p);
        return new BigInteger[]{c1, c2};
    }

    private BigInteger decrypt(BigInteger c1, BigInteger c2) {
        final GyorsHatvanyozas gyorsHatvanyozas = new GyorsHatvanyozas();

//        c2 = BigInteger.valueOf(12);
//        c1 = BigInteger.valueOf(10);
//        p = BigInteger.valueOf(17);
//        g = BigInteger.valueOf(14);
//        alpha = BigInteger.valueOf(9);

        BigInteger c11 = c2.mod(p);
        BigInteger c22 = gyorsHatvanyozas.solve(c1, p.subtract(BigInteger.valueOf(1)).subtract(alpha), p);
        return (c11.multiply(c22)).mod(p);
    }

}
