import java.io.*;
import java.util.*;

public class power_ak {


    static class Tree<T> {
        private static class Node<T> {
            final static Random rnd = new Random(3);
            T value;
            int priority;
            Node<T> left, right;
            int subtreeSize;

            public Node(T value) {
                this.value = value;
                this.priority = rnd.nextInt();
                left = right = null;
                subtreeSize = 1;
            }
        }

        private Node<T> root;
        private Comparator<T> comp;

        public Tree(Comparator<T> comp) {
            this.comp = comp;
        }

        private int size(Node<T> v) {
            return v == null ? 0 : v.subtreeSize;
        }

        private void update(Node<T> v) {
            if (v == null)
                return;
            v.subtreeSize = size(v.left) + 1 + size(v.right);
        }

        private Node<T>[] split(Node<T> t, T key) {
            if (t == null) {
                return new Node[] {null, null};
            }
            final Node<T>[] res;
            if (comp.compare(key, t.value) < 0) {
                res = split(t.left, key);
                t.left = res[1];
                res[1] = t;
            } else {
                res = split(t.right, key);
                t.right = res[0];
                res[0] = t;
            }
            update(t);
            return res;
        }

        private Node<T>[] splitAt(Node<T> t, int count) {
            if (t == null) {
                return new Node[] {null, null};
            }
            final Node<T>[] res;
            if (size(t.left) >= count) {
                res = splitAt(t.left, count);
                t.left = res[1];
                res[1] = t;
            } else {
                res = splitAt(t.right, count - size(t.left) - 1);
                t.right = res[0];
                res[0] = t;
            }
            update(t);
            return res;
        }

        private Node<T> merge(Node<T> t1, Node<T> t2) {
            if (t1 == null)
                return t2;
            if (t2 == null)
                return t1;
            if (t1.priority > t2.priority) {
                t1.right = merge(t1.right, t2);
                update(t1);
                return t1;
            } else {
                t2.left = merge(t1, t2.left);
                update(t2);
                return t2;
            }
        }

        public void insert(T key) {
            Node<T>[] res = split(root, key);
            root = merge(merge(res[0], new Node<T>(key)), res[1]);
        }

        private void collect(Node<T> t, ArrayList<T> acc) {
            if (t == null)
                return;
            collect(t.left, acc);
            acc.add(t.value);
            collect(t.right, acc);
        }

        public ArrayList<T> toList() {
            ArrayList<T> res = new ArrayList<T>();
            collect(root, res);
            return res;
        }

        public T getKth(int k) {
            Node<T>[] p = splitAt(root, k);
            Node<T> min = p[1];
            while (min.left != null)
                min = min.left;
            T res = min.value;
            root = merge(p[0], p[1]);
            return res;
        }

        @Override
        public String toString() {
            return toList().toString();
        }
    }

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

        Tree<Long> t = new Tree<>(new Comparator<Long>() {
            @Override
            public int compare(Long x1, Long x2) {
                return -Long.compare(x1, x2);
            }
        });
        for (int i = 0; i < k - 1; i++) {
            t.insert(a[i].y);
        }
        long best = 0;
        for (int i = k - 1; i < n; i++) {
            t.insert(a[i].y);
            long minX = a[i].x;
            long minY = t.getKth(k - 1);
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
