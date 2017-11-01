import java.io.*;
import java.util.*;

public class power_ak_treeset {

    static class BoundedSet<T> extends TreeSet<T> {
        final private int k;

        BoundedSet(int k) {
            this.k = k;
        }

        @Override
        public boolean add(T t) {
            if (size() == k) {
                pollFirst();
            }
            return super.add(t);
        }
    }

    static class Pair implements Comparable<Pair> {
        final long x, y;
        final int id;

        Pair(long x, long y, int id) {
            this.x = x;
            this.y = y;
            this.id = id;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }

        @Override
        public int compareTo(Pair p) {
            int cmp = Long.compare(y, p.y);
            if (cmp != 0) {
                return cmp;
            }
            cmp = Long.compare(x, p.x);
            if (cmp != 0) {
                return cmp;
            }
            return Long.compare(id, p.id);
        }
    }

    public static void main(String[] args) throws IOException {
        MyScanner in = new MyScanner(new FileReader("power.in"));
        PrintWriter out = new PrintWriter("power.out");
        int n = in.nextInt();
        int k = in.nextInt();
        Pair[] a = new Pair[n];
        for (int i = 0; i < n; i++) {
            a[i] = new Pair(in.nextInt(), in.nextInt(), i);
        }
        in.close();
        Arrays.sort(a, new Comparator<Pair>() {
            @Override
            public int compare(Pair p1, Pair p2) {
                return -Long.compare(p1.x, p2.x);
            }
        });

        BoundedSet<Pair> set = new BoundedSet<>(k);
        for (int i = 0; i < k - 1; i++) {
            set.add(a[i]);
        }
        long best = 0;
        for (int i = k - 1; i < n; i++) {
            set.add(a[i]);
            long minX = a[i].x;
            long minY = set.first().y;
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
