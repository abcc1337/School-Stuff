import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.SyncFailedException;
import java.util.*;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;

/**
 * Created by vitalii on 06.07.16.
 */
public class TestGen {

    public static Random rnd = new Random(17);

    String basePrefix = "..";
    String newPrefix = "";


    int maxDepthSize;

    public int[] solve(int[] x, int[] d, int[] t) {
        int[] xStack = new int[x.length];
        int[] dStack = new int[x.length];

        ArrayList<Integer> times = new ArrayList<Integer>();

        int tail = 0;

        maxDepthSize = 0;

        for (int i = 0; i < x.length; i++) {
            xStack[tail] = x[i];
            dStack[tail] = d[i];
            tail++;
            if (tail > 1 && dStack[tail - 1] == -1 && dStack[tail - 2] == 1) {
                times.add(xStack[tail - 1] - xStack[tail - 2]);
                tail -= 2;
            }
            maxDepthSize = Math.max(maxDepthSize, tail);
        }

        int[] ans = new int[t.length];
        Collections.sort(times);
        int pos = 0;
        for (int i = 0; i < t.length; i++) {
            while (pos < times.size() && times.get(pos) <= 2 * t[i]) {
                pos++;
            }
            ans[i] = x.length - 2 * pos;
        }
        return ans;
    }

    int testNumber = 1;

    private ArrayList<String> desks;
    public void printTest(ArrayList<Integer> x, ArrayList<Integer> d, ArrayList<Integer> t, String desk) throws FileNotFoundException {
        String folderName = basePrefix+"/"+newPrefix+"/";
        File file = new File(folderName);
        if (!file.exists())
        {
            file.mkdir();
        }

        PrintWriter out = new PrintWriter(folderName + testNumber / 10 + "" + testNumber % 10);
        out.println(x.size());
        for (int i = 0; i < x.size(); i++) {
            out.println(x.get(i) + " " + d.get(i));
        }
        out.println(t.size());
        for (int i = 0; i < t.size() - 1; i++) {
            out.print(t.get(i) + " ");
        }
        out.println(t.get(t.size() - 1));
        out.close();
        String str = folderName + testNumber / 10 + "" + testNumber % 10+ "   ";
        str += "n = "+x.size() + " m = "+t.size() + " range = ["+x.get(0) +";"+ x.get(x.size()-1)+"] ";
        if (t.size()  == 1)
        {
            str += "t[0] = "+t.get(0) + " ans = "+solve(toIntArray(x), toIntArray(d), toIntArray(t))[0]+" ";
        }
        str += desk;
        testNumber++;
        desks.add(str);
    }

    private int[] toIntArray(ArrayList<Integer> x) {
        int[] arr = new int[x.size()];
        for (int i = 0; i < x.size(); i++) {
            arr[i] = x.get(i);
        }
        return arr;
    }

    public void generateSubTask1() throws FileNotFoundException {
        newPrefix = "tests/subtask1";

        generatePairwise(-50, 50, 1, 1, 1);
        generatePairwise(-100, 20, 6, 1, 2);
        generatePairwise(-100, 20, 6, -1, 2);
        generateBigPair(50, 1, 23, 1);
        generateBigPair(50, -1, 100, 1);

        generateByPattern("((()))()(())", 20, -100, -4);
        generateByPattern(")))((()))", 40, -100, -5);
        generateByPattern(")(((()()())))(", 20, -100, -4);
        generateByPattern("()(())()(())()(())()((()))()())()", 8, -100, 2);
        generateByPattern("((()((()((()(", 20, -100, -7);
    }

    public void generateSubTask2() throws FileNotFoundException {
        newPrefix = "tests/subtask2";

        int mul = (int) (1e+7);
        generatePairwise(-100 * mul, 20, 6 * mul, 1, 2 * mul);
        generateBigPair(50, 1, 23 * mul, mul);
        generateByPattern(")(((()()())))(", 20 * mul, -100 * mul, -4);
        generateByPattern("()(())()(())()(())()((()))()())()", 8 * mul, -100 * mul, 2);
        generateByPattern("((()((()((()(", 20 * mul, -100 * mul, -7);

    }

    public void generateSubTask3() throws FileNotFoundException {
        newPrefix = "tests/subtask3";

        generateBigDeepTest(200000, 10, 1, false);
        generateBigDeepTest(200000, 100, 2, false);
        generateBigDeepTest(200000, 5000, 3, false);
        generateBigDeepTest(200000, 5000, 4, false);
        generateVeryBigPair(100000, false);

    }

    public void generateSubTask4() throws FileNotFoundException {
        newPrefix = "tests/subtask4";


        generateBigDeepTest(200000, 10, 1, true);
        generateBigDeepTest(200000, 100, 1, true);
        generateBigDeepTest(200000, 5000, 1, true);

        generateBigDeepTest(200000, 10, 2, true);
        generateBigDeepTest(200000, 100, 2, true);

        generateBigDeepTest(200000, 10, 3, true);
        generateBigDeepTest(200000, 100, 3, true);
        generateBigDeepTest(200000, 5000, 3, true);

        generateBigDeepTest(200000, 5000, 4, true);
        generateVeryBigPair(100000, true);
    }

    public static void main(String[] args) throws FileNotFoundException {
        TestGen generator = new TestGen();
        generator.desks = new ArrayList<>();
        generator.genSample();

        if (!new File("../tests/").exists())
        {
            new File("../tests/").mkdir();
        }

        generator.generateSubTask1();
        generator.generateSubTask2();
        generator.generateSubTask3();
        generator.generateSubTask4();

        PrintWriter out = new PrintWriter("../tests.desc");

        for (String str : generator.desks)
        {
            out.println(str);
        }
        out.close();

    }

    public void generateVeryBigPair(int n, boolean manyTimes) throws FileNotFoundException {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        int max = (int)1e+9;
        for (int i = 0; i < n; i++)
        {
            x.add(-max + i);
            d.add(1);
        }
        for (int i = 0; i < n; i++)
        {
            x.add(max-n+1+i);
            d.add(-1);
        }
        ArrayList<Integer> t = new ArrayList<>();
        if (manyTimes)
        {
            for (int i = 0; i < n; i++)
            {
                t.add(max - n + 1 + i);
            }
        } else
        {
            t.add(max - n/2);
        }
        printTest(x, d, t, "(((...))), big test");
    }

    public void generateR(int n, int dist, ArrayList<Integer> x, ArrayList<Integer> d, int pos, int mode) {
        if (n == 0) {
            return;
        }

        if (n == 1) {
            x.add(pos);
            d.add(rnd.nextBoolean() ? 1 : -1);
            return;
        }

        if (mode != 4 && rnd.nextInt(20) < 2) {
            if (rnd.nextBoolean()) {
                x.add(pos);
                d.add(-1);
                pos += rnd.nextInt(dist) + 1;
                generateR(n - 1, dist, x, d, pos, mode);
            } else {
                generateR(n - 1, dist, x, d, pos, mode);
                pos = x.get(x.size() - 1);
                pos += rnd.nextInt(dist) + 1;
                x.add(pos);
                d.add(1);
            }
            return;
        }

        int len = 0;

        if (mode == 1) {
            len = n - rnd.nextInt(Math.min(n - 1, 5));
        }
        if (mode == 2) {
            len = n - rnd.nextInt(Math.min(n - 1, 10));
        }
        if (mode == 3) {
            if (n < 10) {
                len = 2 + rnd.nextInt(n - 1);
            } else {
                len = rnd.nextInt(n / 3) + n / 3;
            }
        }
        if (mode == 4) {
            len = (rnd.nextInt(Math.min(8, n - 1)) + 2) / 2 * 2;
        }

        if (rnd.nextBoolean()) {
            x.add(pos);
            d.add(1);
            pos += rnd.nextInt(dist) + 1;
            generateR(len - 2, dist, x, d, pos, mode);
            pos = x.get(x.size() - 1) + rnd.nextInt(dist) + 1;
            x.add(pos);
            d.add(-1);
            pos += rnd.nextInt(dist) + 1;
            generateR(n - len, dist, x, d, pos, mode);
        } else {
            generateR(n - len, dist, x, d, pos, mode);
            if (x.size() > 0) {
                pos = x.get(x.size() - 1) + rnd.nextInt(dist) + 1;
            }
            x.add(pos);
            d.add(1);
            pos += rnd.nextInt(dist) + 1;
            generateR(len - 2, dist, x, d, pos, mode);
            pos = x.get(x.size() - 1) + rnd.nextInt(dist) + 1;
            x.add(pos);
            d.add(-1);
        }
    }


    public void generateBigDeepTest(int n, int dist, int mode, boolean manyTimes) throws FileNotFoundException {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();

        generateR(n, dist, x, d, 0, mode);


        ArrayList<Integer> t = new ArrayList<>();
        if (manyTimes) {
            int pos = 0;
            for (int i = 0; i <= n / 2; i++) {
                t.add(pos);
                pos += mode == 4 ? 1 : dist / 10;
            }
            for (int i = n / 2 + 1; i < n; i++) {
                t.add(pos);
                pos += mode == 4 ? 10 : dist / 2;
            }
            t.set(t.size() - 1, 1000000000);
        } else
        {
            t.add((mode == 4 ? 1 : dist / 10 ) * n/2);
        }

        String[] deep = new String[]{"linear", "linear_less", "log", "constant"};

        printTest(x, d, t, "big_test: pair_dist_amplitude = " + dist + " bracket parsing tree deepness : " + deep[mode - 1]);

    }


    public void generateByPattern(String str, int mRand, int start, int time) throws FileNotFoundException {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i < str.length(); i++) {
            x.add(start);
            d.add(str.charAt(i) == '(' ? 1 : -1);
            start += rnd.nextInt(mRand) + 1;
        }
        ArrayList<Integer> t = new ArrayList<>();
        int oldTime = time;
        if (time < 0) {
            int must = -time;
            time = 0;
            t.add(0);
            int l = 0;
            int r = (int) 1e+9;
            while (l < r - 1) {
                int mid = (l + r) / 2;
                t.set(0, mid);
                if (solve(toIntArray(x), toIntArray(d), toIntArray(t))[0] > must) {
                    l = mid;
                } else {
                    r = mid;
                }
            }
            t.set(0, r);
            if (solve(toIntArray(x), toIntArray(d), toIntArray(t))[0] != -oldTime)
            {
                throw new AssertionError();
            }
        } else {
            t.add(time);
        }
        printTest(x, d, t, "pair_dist_amplitude = " + mRand + " generated by pattern = "+str + ((oldTime < 0) ? "answer must be " + (-oldTime) : ""));

    }

    public void generateBigPair(int pairs, int shouldReverse, int time, int mul) throws FileNotFoundException {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            x.add((-pairs + i) * mul);
            d.add(shouldReverse);
        }

        for (int i = 0; i < pairs; i++) {
            x.add((i + 1) * mul);
            d.add(-shouldReverse);
        }
        ArrayList<Integer> t = new ArrayList<>();
        t.add(time);
        printTest(x, d, t, (shouldReverse == 1 ? "(((((....)))" : ")))))...((((")+" pair_dist_amplitude= "+mul);
    }

    public void generatePairwise(int startFrom, int pairs, int maxRand, int shouldReverse, int time) throws FileNotFoundException {
        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i < pairs; i++) {
            x.add(startFrom);
            d.add(shouldReverse);
            startFrom += rnd.nextInt(maxRand) + 1;
            x.add(startFrom);
            d.add(-shouldReverse);
            startFrom += rnd.nextInt(maxRand) + 1;
        }
        ArrayList<Integer> t = new ArrayList<>();
        t.add(time);
        String str = "";
        if (shouldReverse == 1)
        {
            str = "()()() ... ()(), pair_dist_amplitude = " + maxRand;
        }
        else
        {
            str = ")()()()(....)()(, pair_dist_amplitude = " + maxRand;
        }
        printTest(x, d, t, str);
    }

    private void genSample() throws FileNotFoundException {
        newPrefix = "preliminary";

        int[] xArr = new int[]{-1, 0, 1, 5};
        int[] dArr = new int[]{1, -1, 1, -1};
        int[] tArr = new int[]{0, 1, 2, 3};

        ArrayList<Integer> x = new ArrayList<>();
        ArrayList<Integer> d = new ArrayList<>();
        ArrayList<Integer> t = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            x.add(xArr[i]);
            d.add(dArr[i]);
            t.add(tArr[i]);
        }

        printTest(x, d, t, "sample");
        testNumber = 1;
    }


}
