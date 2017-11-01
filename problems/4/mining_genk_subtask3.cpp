#include <bits/stdc++.h>

using namespace std;

const int N = 1234567;

vector <int> g[N];
int x[N], y[N];
int was[N], pa[N], pb[N];
int pos;

bool dfs(int v) {
  was[v] = pos;
  int sz = g[v].size();
  for (int j = 0; j < sz; j++) {
    int u = g[v][j];
    if (pb[u] == -1 || (was[pb[u]] != pos && dfs(pb[u]))) {
      pa[v] = u;
      pb[u] = v;
      return true;
    }
  }
  return false;
}

int main() {
  freopen("mining.in", "r", stdin);
  freopen("mining.out", "w", stdout);
  int w, h, s, q;
  cin >> w >> h >> s >> q;
  for (int i = 0; i < s; i++) {
    cin >> x[i] >> y[i];
    x[i]--; y[i]--;
  }
  for (int i = 0; i < w * h * q; i++) {
    was[i] = -1;
    pb[i] = -1;
  }
  int t;
  cin >> t;
  pos = 0;
  for (int id = 0; id < t; id++) {
    int b, n, m;
    cin >> b >> n >> m;
    b--;
    for (int z = 0; z < n; z++) {
      for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
          int dist = max(abs(x[b] - i), abs(y[b] - j));
          if (dist <= m) {
            for (int k = 0; k < q; k++) {
              g[pos].push_back((i * h + j) * q + k);
            }
          }
        }
      }
      pa[pos] = -1;
      if (!dfs(pos)) {
        cout << id << " " << z << endl;
        return 0;
      }
      pos++;
    }
  }
  cout << t << " " << 0 << endl;
  return 0;
}
