#include <bits/stdc++.h>

int main() {
  freopen("data.in", "r", stdin);
  freopen("data.out", "w", stdout);
  int n, m;
  scanf("%d%d", &n, &m);
  std::vector<int> deg(n, 0);
  for (int i = 0; i < m; i++) {
    int v, u;
    scanf("%d%d", &v, &u);
    --v, --u;
    deg[v]++;
    deg[u]++;
  }
  int ans = 0;
  for (int i = 0; i < n; i++) {
    ans += deg[i] == 1;
  }
  printf("%d 1\n", ans);
  return 0;
}