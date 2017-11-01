#include <bits/stdc++.h>

using namespace std;

const int inf = (int) 1e9;
const int N = 1234567;

int fin, V, E;
int from[N], to[N], c[N], f[N], rev[N];
int pred[N], last[N];

void add(int u, int v, int cap, int r) {
  from[E] = u;
  to[E] = v;
  c[E] = cap;
  rev[E] = r;

  pred[E] = last[u];
  last[u] = E;

  E++;
}

void add(int u, int v, int cap) {
  add(u, v, cap, E + 1);
  add(v, u, 0, E - 1);
}

int q[N], d[N];

bool exists() {
  for (int i = 0; i < V; i++) {
    d[i] = -1;
  }
  int b = 0, e = 1;
  q[0] = 0;
  d[0] = 0;
  while (b < e) {
    int j = last[q[b]];
    while (j >= 0) {
      if (c[j] > f[j] && d[to[j]] == -1) {
        q[e] = to[j];
        d[to[j]] = d[q[b]] + 1;
        e++;
        if (to[j] == fin) {
          return true;
        }
      }
      j = pred[j];
    }
    b++;
  }
  return false;
}

int dfs(int v, int w) {
  if (v == fin) {
    return w;
  }
  int r = 0;
  int j = last[v];
  while (j >= 0) {
    if (c[j] > f[j] && d[to[j]] == d[v] + 1) {
      int ww = min(w - r, c[j] - f[j]);
      int t = dfs(to[j], ww);
      if (t != 0) {
        f[j] += t;
        f[rev[j]] -= t;
        r += t;
        if (r == w) {
          break;
        }
      }
    }
    last[v] = pred[j];
    j = pred[j];
  }
  return r;
}

int st[N];

int flow() {
  for (int i = 0; i < V; i++) {
    st[i] = last[i];
  }
  int ans = 0;
  while (exists()) {
    ans += dfs(0, inf);
    for (int i = 0; i < V; i++) {
      last[i] = st[i];
    }
  }
  return ans;
}

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
  memset(last, -1, sizeof(last));
  fin = 1;
  V = 2;
  E = 0;
  for (int i = 0; i < w * h; i++) {
    add(V++, fin, q);
  }
  int t;
  cin >> t;
  for (int id = 0; id < t; id++) {
    int b, n, m;
    cin >> b >> n >> m;
    b--;
    add(0, V, n);
    for (int i = 0; i < w; i++) {
      for (int j = 0; j < h; j++) {
        int dist = max(abs(x[b] - i), abs(y[b] - j));
        if (dist <= m) {
          add(V, i * h + j + 2, n);
        }
      }
    }
    V++;
    int z = flow();
    if (z < n) {
      cout << id << " " << z << endl;
      return 0;
    }
  }
  cout << t << " " << 0 << endl;
  return 0;
}
