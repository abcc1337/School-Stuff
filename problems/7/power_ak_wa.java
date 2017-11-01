import java.io.*;
import java.util.*;

public class power_ak_wa {

    public static long check(int[] x, int[] y, int k, int selX) {
        ArrayList<Integer> y2 = new ArrayList<>();
        for (int i = 0; i < x.length; i++) {
            if (x[i] >= selX) {
                y2.add(y[i]);
            }
        }
        Collections.sort(y2);
        return y2.get(y2.size() - k) * (long)selX;
    }

    public static long solve(int[] x, int[] y, int k) {
        int[] sortedX = x.clone();
        Arrays.sort(sortedX);
        int left = 0;
        int right = sortedX.length - k;
        long best = Math.max(check(x, y, k, sortedX[left]), check(x, y, k, sortedX[right]));
        while (right - left > 2) {
            int c1 = (left * 2 + right) / 3;
            int c2 = (left + right * 2) / 3;
            long res1 = check(x, y, k, sortedX[c1]);
            long res2 = check(x, y, k, sortedX[c2]);
            if (res1 > res2) {
                best = Math.max(best, res1);
                right = c2;
            } else {
                best = Math.max(best, res2);
                left = c1;
            }
        }
        best = Math.max(best, check(x, y, k, sortedX[(left + right) / 2]));
        return best;
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(new FileReader("power.in"));
        PrintWriter out = new PrintWriter("power.out");
        int n = in.nextInt();
        int k = in.nextInt();
        int[] x = new int[n];
        int[] y = new int[n];
        for (int i = 0; i < n; i++) {
          x[i] = in.nextInt();
          y[i] = in.nextInt();
        }
        in.close();
        out.println(Math.max(solve(x, y, k), solve(y, x, k)));
        out.close();
    }

    static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner(Reader r) {
            this.br = new BufferedReader(r);
        }

        public String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                st = new StringTokenizer(br.readLine());
            }
            return st.nextToken();
        }

        public int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        public void close() throws IOException {
            br.close();
        }
    }
}
