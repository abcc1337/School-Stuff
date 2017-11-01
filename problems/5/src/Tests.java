import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.sqrt;

public class Tests {
    static int[] MINK = {1, 2, 1, 1, 1};
    static int[] MAXK = {1, 2, 100, 40_000, 1_000_000_000};
    static int[] MAXX = {100, 100, 100, 40_000, 1_000_000_000};
    int group;

    Random rnd = new Random(3289214075201L);

    String folder;

    PrintWriter desc;

    public static void main(String[] args) throws FileNotFoundException {
        new Tests().gen();
    }

    static int tests = 1;

    public void writeTest(int k, int x, int y, String description) {
        try {
            PrintWriter out = new PrintWriter(String.format(folder + "%02d", tests++));

            System.err.println("Writing test " + (tests - 1));

            desc.println(String.format(folder + "%02d", tests - 1) + "\t" + description);

            out.println(k);
            out.println(x);
            out.println(y);


            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void genRandom(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int x = rnd.nextInt(MAXX[i]) + 1;
        int y = rnd.nextInt(MAXX[i]) + 1;

        writeTest(k, x, y, String.format("Random test: %d %d %d", k, x, y));
    }

    public void genMAX(int i) {
        int k = MAXK[i];
        int x = MAXX[i];
        int y = MAXX[i];

        writeTest(k, x, y, String.format("Max test: %d %d %d", k, x, y));
    }

    public void genMAXSpecial1(int i) {
        int k = MAXK[i];
        int x = 1;
        int y = MAXX[i];

        writeTest(k, x, y, String.format("Max test x = 1: %d %d %d", k, x, y));
    }

    public void genMAXSpecial2(int i) {
        int k = MAXK[i];
        int x = MAXX[i];
        int y = 1;

        writeTest(k, x, y, String.format("Max test y = 1: %d %d %d", k, x, y));
    }

    public void genMAX1(int i) {
        int k = MAXK[i];
        int x = rnd.nextInt(MAXX[i]) + 1;
        int y = MAXX[i];

        writeTest(k, x, y, String.format("Max test: %d %d %d", k, x, y));
    }

    public void genMAX2(int i) {
        int k = MAXK[i];
        int y = rnd.nextInt(MAXX[i]) + 1;
        int x = MAXX[i];

        writeTest(k, x, y, String.format("Max test: %d %d %d", k, x, y));
    }

    public void genMAX3(int i) {
        int k = MAXK[i];
        int y = rnd.nextInt(MAXX[i]) + 1;
        int x = 1;

        writeTest(k, x, y, String.format("Max test Over Int: %d %d %d", k, x, y));
    }

    public void genMAX4(int i) {
        int k = MAXK[i];
        int y = MAXX[i];
        int x = rnd.nextInt(20) + 3;
        while (true) {
            boolean ok = true;
            for (int j = 2; j < Math.min(x, (int) Math.sqrt(x) + 2); j++) {
                if (x % j == 0) ok = false;
            }
            if (ok)
                break;
            x++;
        }
        writeTest(k, x, y, String.format("Max test Over Int, small x: %d %d %d", k, x, y));
    }


    public void genSmallK(int i) {
        int k = rnd.nextInt(10) + 1;
        int x = rnd.nextInt(MAXX[i]) + 1;
        int y = rnd.nextInt(MAXX[i]) + 1;

        writeTest(k, x, y, String.format("small k test: %d %d %d", k, x, y));
    }

    public void genYLessX(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int x = rnd.nextInt(MAXX[i]) + 1;
        int y = rnd.nextInt(x) + 1;

        writeTest(k, x, y, String.format("y < x test: %d %d %d", k, x, y));
    }

    public void genSpecial(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int y = rnd.nextInt(MAXX[i] - k) + k + 1;
        int x = y - k + 1;

        writeTest(k, x, y, String.format("x = (y - k) + 1 test: %d %d %d", k, x, y));
    }

    public void genSpecial2(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int y = rnd.nextInt(MAXX[i]) + 1;
        int x = y - 1;

        writeTest(k, x, y, String.format("x = (y - 1) test: %d %d %d", k, x, y));
    }

    public void genSpecial1(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int x = 10;
        int y = x * (1 + rnd.nextInt(max(1, MAXX[i] / x - k))) + k - 1;

        writeTest(k, x, y, String.format("x * c = (y - k) + 1 test: %d %d %d", k, x, y));
    }


    public void genEquals(int i) {
        int k = rnd.nextInt(MAXK[i] - MINK[i] + 1) + MINK[i];
        int x = rnd.nextInt(MAXX[i]) + 1;
        int y = x;

        writeTest(k, x, y, String.format("x == y test: %d %d %d", k, x, y));
    }


    public void gen() {
        try {
            desc = new PrintWriter("../tests.desc");
        } catch (IOException e) {
//
        }

        tests = 1;
        folder = "../preliminary/";
        if (!new File(folder).exists())
            new File(folder).mkdir();
        writeTest(2, 7, 20, "Sample test");

        tests = 1;
        folder = "../tests/";
        if (!new File(folder).exists())
            new File(folder).mkdir();

        for (group = 0; group < MAXK.length; group++) {
            folder = String.format("../tests/subtask%d/", (group + 1));
            if (!new File(folder).exists())
                new File(folder).mkdir();

            genRandom(group);
            genRandom(group);
            genRandom(group);
            genEquals(group);
            genYLessX(group);
            genMAX(group);
            genMAX1(group);
            genMAX2(group);
            if (group > 1)
                genSmallK(group);
            genMAX3(group);
            genMAX4(group);
            genSpecial(group);
            genSpecial1(group);
            genSpecial2(group);
            genMAXSpecial1(group);
            genMAXSpecial2(group);
        }
        genSpecial(MAXK.length - 1);
        writeTest(50000000, 232322, 232322231, "hand test: 50000000 232322 232322231");
        writeTest(1, 1, MAXK[MAXK.length - 1], "hand test: 1 1 1000000000");
        desc.close();
    }
}
