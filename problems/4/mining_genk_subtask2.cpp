#include <bits/stdc++.h>

using namespace std;

const int N = 1234;

int space[N][N];
int w, h, s, q;
vector <int> mobs[N];
int x[N], y[N];

bool possible() {
  for (int i = 0; i < s; i++) {
    sort(mobs[i].begin(), mobs[i].end());
  }
  for (int i = 0; i < w; i++) {
    for (int j = 0; j < h; j++) {
      space[i][j] = q;
    }
  }
  for (int b = 0; b < s; b++) {
    int cnt = mobs[b].size();
    for (int z = 0; z < cnt; z++) {
      int m = mobs[b][z];
      int best = -1;
      int bi = -1, bj = -1;
      for (int i = 0; i < w; i++) {
        for (int j = 0; j < h; j++) {
          int dist = max(abs(x[b] - i), abs(y[b] - j));
          if (space[i][j] > 0 && dist <= m) {
            int other_dist = max(abs(x[s - 1] - i), abs(y[s - 1] - j));
            if (other_dist > best) {
              best = other_dist;
              bi = i;
              bj = j;
            }
          }
        }
      }
      if (best == -1) {
        return false;
      }
      space[bi][bj]--;
    }
  }
  return true;
}

int main() {
  freopen("mining.in", "r", stdin);
  freopen("mining.out", "w", stdout);
  cin >> w >> h >> s >> q;
  for (int i = 0; i < s; i++) {
    cin >> x[i] >> y[i];
    x[i]--; y[i]--;
  }
  int t;
  cin >> t;
  for (int id = 0; id < t; id++) {
    int b, n, m;
    cin >> b >> n >> m;
    b--;
    for (int z = 0; z < n; z++) {
      mobs[b].push_back(m);
      if (!possible()) {
        cout << id << " " << z << endl;
        return 0;
      }
    }
  }
  cout << t << " " << 0 << endl;
  return 0;
}
