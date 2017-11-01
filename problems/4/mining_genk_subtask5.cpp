#include <bits/stdc++.h>

using namespace std;

const int N = 1234567;

int x[N], y[N];
long long space[N];

int main() {
  freopen("mining.in", "r", stdin);
  freopen("mining.out", "w", stdout);
  int w, h, s, q;
  cin >> w >> h >> s >> q;
  for (int i = 0; i < s; i++) {
    cin >> x[i] >> y[i];
    x[i]--; y[i]--;
  }
  for (int mob = 0; mob < N; mob++) {
    int xa = max(0, x[0] - mob);
    int ya = max(0, y[0] - mob);
    int xb = min(w - 1, x[0] + mob);
    int yb = min(h - 1, y[0] + mob);
    space[mob] = (long long) (xb - xa + 1) * (yb - ya + 1) * q;
  }
  for (int mob = N - 1; mob > 0; mob--) {
    space[mob] -= space[mob - 1];
  }
  int t;
  cin >> t;
  for (int i = 0; i < t; i++) {
    int b, m;
    long long n;
    cin >> b >> n >> m;
    b--;
    long long taken = 0;
    for (int mob = m; mob >= 0; mob--) {
      long long take = min(n - taken, space[mob]);
      space[mob] -= take;
      taken += take;
    }
    if (taken < n) {
      cout << i << " " << taken << endl;
      return 0;
    }
  }
  cout << t << " " << 0 << endl;
  return 0;
}
