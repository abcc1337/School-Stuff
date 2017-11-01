import java.io.*;
import java.util.*;

public class power_ak_n2 {

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

    static void insert(ArrayList<Long> list, long val) {
        int pos;
        for (pos = 0; pos < list.size() && list.get(pos) > val; pos++) {
        }
        list.add(pos, val);
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

        ArrayList<Long> t = new ArrayList<>();
        for (int i = 0; i < k - 1; i++) {
            insert(t, a[i].y);
        }
        long best = 0;
        for (int i = k - 1; i < n; i++) {
            insert(t, a[i].y);
            long minX = a[i].x;
            long minY = t.get(k - 1);
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
