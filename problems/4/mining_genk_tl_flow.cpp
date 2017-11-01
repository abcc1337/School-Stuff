#include <bits/stdc++.h>

using namespace std;

const long long inf = (long long) 1e18;
const int N = 3234567;

int fin, V, E;
int from[N], to[N], rev[N];
long long c[N], f[N];
int pred[N], last[N];

void add(int u, int v, long long cap, int r) {
  from[E] = u;
  to[E] = v;
  c[E] = cap;
  rev[E] = r;

  pred[E] = last[u];
  last[u] = E;

  E++;
}

void add(int u, int v, long long cap) {
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

long long dfs(int v, long long w) {
  if (v == fin) {
    return w;
  }
  long long r = 0;
  int j = last[v];
  while (j >= 0) {
    if (c[j] > f[j] && d[to[j]] == d[v] + 1) {
      long long ww = min(w - r, c[j] - f[j]);
      long long t = dfs(to[j], ww);
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

long long flow() {
  for (int i = 0; i < V; i++) {
    st[i] = last[i];
  }
  long long ans = 0;
  while (exists()) {
    ans += dfs(0, inf);
    for (int i = 0; i < V; i++) {
      last[i] = st[i];
    }
  }
  return ans;
}

int x[N], y[N];
int b[N], m[N];
long long n[N];

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
  int t;
  cin >> t;
  for (int i = 0; i < t; i++) {
    cin >> b[i] >> n[i] >> m[i];
    b[i]--;
  }
  vector <int> xs, ys;
  xs.push_back(0);
  ys.push_back(0);
  xs.push_back(w);
  ys.push_back(h);
  for (int i = 0; i < t; i++) {
    xs.push_back(max(0, x[b[i]] - m[i]));
    xs.push_back(min(w, x[b[i]] + m[i] + 1));
    ys.push_back(max(0, y[b[i]] - m[i]));
    ys.push_back(min(h, y[b[i]] + m[i] + 1));
  }
  sort(xs.begin(), xs.end());
  xs.resize(unique(xs.begin(), xs.end()) - xs.begin());
  sort(ys.begin(), ys.end());
  ys.resize(unique(ys.begin(), ys.end()) - ys.begin());
  int xcnt = xs.size() - 1;
  int ycnt = ys.size() - 1;
  for (int i = 0; i < xcnt; i++) {
    for (int j = 0; j < ycnt; j++) {
      add(V++, fin, (long long) q * (xs[i + 1] - xs[i]) * (ys[j + 1] - ys[j]));
    }
  }
  for (int id = 0; id < t; id++) {
    add(0, V, n[id]);
    for (int i = 0; i < xcnt; i++) {
      for (int j = 0; j < ycnt; j++) {
        int dist = max(abs(x[b[id]] - xs[i]), abs(y[b[id]] - ys[j]));
        if (dist <= m[id]) {
          add(V, i * ycnt + j + 2, n[id]);
        }
      }
    }
    V++;
    long long z = flow();
    if (z < n[id]) {
      cout << id << " " << z << endl;
      cerr << "Time: " << clock() << " ms" << endl;
      return 0;
    }
  }
  cout << t << " " << 0 << endl;
  cerr << "Time: " << clock() << " ms" << endl;
  return 0;
}
