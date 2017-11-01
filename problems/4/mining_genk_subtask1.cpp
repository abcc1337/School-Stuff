#include <bits/stdc++.h>

using namespace std;

const int N = 1234;

int space[N][N];
int x[N], y[N];

int main() {
  freopen("mining.in", "r", stdin);
  freopen("mining.out", "w", stdout);
  int w, h, s, q;
  cin >> w >> h >> s >> q;
  for (int i = 0; i < s; i++) {
    cin >> x[i] >> y[i];
    x[i]--; y[i]--;
  }
  for (int i = 0; i < w; i++) {
    for (int j = 0; j < h; j++) {
      space[i][j] = q;
    }
  }
  int t;
  cin >> t;
  for (int id = 0; id < t; id++) {
    int b, n, m;
    cin >> b >> n >> m;
    b--;
    for (int z = 0; z < n; z++) {
      int best = -1;
      int bi = -1, bj = -1;
      for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
          int dist = max(abs(x[b] - i), abs(y[b] - j));
          if (space[i][j] > 0 && dist <= m) {
            if (dist > best) {
              best = dist;
              bi = i;
              bj = j;
            }
          }
        }
      }
      if (best == -1) {
        cout << id << " " << z << endl;
        return 0;
      }
      space[bi][bj]--;
    }
  }
  cout << t << " " << 0 << endl;
  return 0;
}
