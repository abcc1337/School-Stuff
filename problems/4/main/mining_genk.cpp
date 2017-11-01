#include <bits/stdc++.h>

using namespace std;

const int N = 123;

int w, h, s, q, t;
int x[N], y[N];
int b[N], m[N];
long long n[N];

vector < pair <int, int> > at[N];
int mob[N];

int n_areas;
long long areas[1234567];

void gen_areas(int v) {
  if (v == s) {
    long long area = 0;
    for (int mask = 1; mask < (1 << s); mask++) {
      int xa = 0, ya = 0;
      int xb = w - 1, yb = h - 1;
      int sign = -1;
      for (int i = 0; i < s; i++) {
        if (mask & (1 << i)) {
          xa = max(xa, x[i] - mob[i]);
          ya = max(ya, y[i] - mob[i]);
          xb = min(xb, x[i] + mob[i]);
          yb = min(yb, y[i] + mob[i]);
          sign *= -1;
        }
      }
      if (xa <= xb && ya <= yb) {
        area += (long long) sign * (xb - xa + 1) * (yb - ya + 1);
      }
    }
    areas[n_areas++] = area;
    return;
  }
  mob[v] = -1;
  gen_areas(v + 1);
  for (int j = 0; j < (int) at[v].size(); j++) {
    mob[v] = at[v][j].first;
    gen_areas(v + 1);
  }
}

long long cur_n[N];
int pos_areas;
long long overhead;
long long result = 0;

void check(int v, long long users) {
  if (v == s) {
    overhead = max(overhead, users - q * areas[pos_areas++]);
    return;
  }
  check(v + 1, users);
  for (int j = 0; j < (int) at[v].size(); j++) {
    users += cur_n[at[v][j].second];
    check(v + 1, users);
  }
}

bool possible(int bound) {
  for (int i = 0; i < t; i++) {
    cur_n[i] = (i < bound ? n[i] : 0);
  }
  pos_areas = 0;
  overhead = 0;
  check(0, 0);
  if (overhead != 0) {
    result = overhead;
  }
  return (overhead == 0);
}

int main() {
  freopen("mining.in", "r", stdin);
  freopen("mining.out", "w", stdout);
  cin >> w >> h >> s >> q;
  for (int i = 0; i < s; i++) {
    cin >> x[i] >> y[i];
    x[i]--; y[i]--;
  }
  cin >> t;
  for (int i = 0; i < t; i++) {
    cin >> b[i] >> n[i] >> m[i];
    b[i]--;
    at[b[i]].push_back(make_pair(m[i], i));
  }
  for (int i = 0; i < s; i++) {
    sort(at[i].begin(), at[i].end());
  }
  gen_areas(0);
  int low = 0, high = t + 1;
  while (low + 1 < high) {
    int mid = (low + high) >> 1;
    if (possible(mid)) {
      low = mid;
    } else {
      high = mid;
    }
  }
  cout << low << " " << n[low] - result << endl;
  return 0;
}
