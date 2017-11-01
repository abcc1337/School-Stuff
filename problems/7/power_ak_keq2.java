import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class power_ak_keq2 {

    static class Pair {
        final long x, y;

        Pair(long x, long y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(new FileReader("power.in"));
        PrintWriter out = new PrintWriter("power.out");
        int n = in.nextInt();
        int k = in.nextInt();
        Pair[] a = new Pair[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Pair(in.nextInt(), in.nextInt());
        }
        in.close();
        Arrays.sort(a, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return -Long.compare(p1.x, p2.x);
            }
        });

        long max = 0;
        for (int i = 0; i < k - 1; i++) {
            max = Math.max(max, a[i].y);
        }
        long best = 0;
        for (int i = k - 1; i < n; i++) {
            long minX = a[i].x;
            long minY = Math.min(a[i].y, max);
            max = Math.max(max, a[i].y);
            best = Math.max(best, minX * minY);
        }
        out.println(best);
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
