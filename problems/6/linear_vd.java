import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Linn on 28-Oct-16.
 */
public class linear_vd {

    public int[] solve(int[] x, int[] d, int[] t)
    {
        int[] xStack = new int[x.length];
        int[] dStack = new int[x.length];

        ArrayList<Integer> times = new ArrayList<Integer>();

        int tail = 0;

        for (int i = 0; i < x.length; i++)
        {
            xStack[tail] = x[i];
            dStack[tail++] = d[i];
            if (tail > 1 && dStack[tail-1] == -1 && dStack[tail-2] == 1)
            {
                times.add(xStack[tail-1] - xStack[tail-2]);
                tail -= 2;
            }
        }

        int[] ans = new int[t.length];
        Collections.sort(times);
        int pos = 0;
        for (int i = 0; i < t.length; i++)
        {
            while (pos < times.size() && times.get(pos) <= 2 * t[i])
            {
                pos++;
            }
            ans[i] = x.length - 2 * pos;
        }
        return ans;
    }

    public void solve() throws IOException {
        int n = nextInt();
        int[] x = new int[n];
        int[] d = new int[n];
        for (int i = 0; i < n; i++)
        {
            x[i] = nextInt();
            d[i] = nextInt();
        }
        int m = nextInt();
        int[] t = new int[m];
        for (int i = 0; i < m; i++)
        {
            t[i] = nextInt();
        }
        int[] ans = solve(x,d,t);
        for (int i = 0; i < ans.length; i++)
        {
            out.println(ans[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        new linear_vd().run();
    }

    public void run() throws IOException {
        in = new BufferedReader(new FileReader(new File("linear.in")));
        out = new PrintWriter("linear.out");
        solve();
        out.close();
    }

    BufferedReader in;
    PrintWriter out;
    StringTokenizer st;

    public String next() throws IOException {
        while (st == null || !st.hasMoreElements()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }

    public Long nextLong() throws IOException {
        return Long.parseLong(next());
    }

    public Integer nextInt() throws IOException {
        return Integer.parseInt(next());
    }

}
