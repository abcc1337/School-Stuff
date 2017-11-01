import java.io.*;
import java.util.Scanner;

public class power_ak_2n {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("power.in"));
        PrintWriter out = new PrintWriter("power.out");
        int n = in.nextInt();
        int k = in.nextInt();
        long[] x = new long[n];
        long[] y = new long[n];
        for (int i = 0; i < n; i++) {
            x[i] = in.nextInt();
            y[i] = in.nextInt();
        }
        in.close();
        long best = 0;
        for (int mask = 0; mask < (1 << n); mask++) {
            int cnt = 0;
            long minX = Long.MAX_VALUE;
            long minY = Long.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (((1 << i) & mask) == 0) {
                    continue;
                }
                minX = Math.min(minX, x[i]);
                minY = Math.min(minY, y[i]);
                cnt++;
            }
            if (cnt == k) {
                best = Math.max(best, minX * minY);
            }
        }
        out.println(best);
        out.close();
    }
}
