#include <bits/stdc++.h>

const long long MOD = static_cast<long long>(1e9) + 7;

void dfs(const std::vector<std::vector<int>> &graph,
         std::vector<bool> &used,
         std::set<std::pair<int, int>> &bridges,
         std::vector<int> &tin,
         std::vector<int> &fup,
         int &timer, int v, int p) {
  used[v] = true;
  tin[v] = fup[v] = timer++;
  for (int u : graph[v]) {
    if (u == p) {
      continue;
    }
    if (!used[u]) {
      dfs(graph, used, bridges, tin, fup, timer, u, v);
      fup[v] = std::min(fup[v], fup[u]);
      if (fup[u] > tin[v]) {
        bridges.insert({std::min(v, u), std::max(v, u)});
      }
    } else {
      fup[v] = std::min(fup[v], tin[u]);
    }
  }
}

void findComponent(const std::vector<std::vector<int>> &graph,
                   std::vector<bool> &used,
                   const std::set<std::pair<int, int>> &bridges,
                   std::vector<int> &getComponent,
                   int component, int &vs, int v) {
  used[v] = true;
  getComponent[v] = component;
  vs++;
  for (int u : graph[v]) {
    std::pair<int, int> p = {std::min(v, u), std::max(v, u)};
    if (bridges.find(p) != bridges.end()) {
      continue;
    }
    if (!used[u]) {
      findComponent(graph, used, bridges, getComponent, component, vs, u);
    }
  }
}

int main() {
  freopen("data.in", "r", stdin);
  freopen("data.out", "w", stdout);
  int n, m;
  scanf("%d%d", &n, &m);
  std::vector<std::vector<int>> graph(n);
  for (int i = 0; i < m; i++) {
    int v, u;
    scanf("%d%d", &v, &u);
    --v, --u;
    graph[v].push_back(u);
    graph[u].push_back(v);
  }
  std::set<std::pair<int, int>> bridges;
  std::vector<bool> used(n, 0);
  std::vector<int> tin(n, 0);
  std::vector<int> fup(n, 0);
  int timer = 0;
  for (int i = 0; i < n; i++) {
    if (!used[i]) {
      dfs(graph, used, bridges, tin, fup, timer, i, -1);
    }
  }
  std::fill(used.begin(), used.end(), false);
  std::vector<int> componentSize(n, 0);
  std::vector<int> getComponent(n, 0);
  int component = 0;
  for (int i = 0; i < n; i++) {
    if (!used[i]) {
      int vs = 0;
      findComponent(graph, used, bridges, getComponent, component, vs, i);
      componentSize[component++] = vs;
    }
  }
  std::vector<int> deg(component, 0);
  for (const auto &bridge : bridges) {
    deg[getComponent[bridge.first]]++;
    deg[getComponent[bridge.second]]++;
  }
  int ans = 0;
  int cnt = 1;
  for (int i = 0; i < component; i++) {
    if (deg[i] <= 1) {
      ans++;
      cnt = static_cast<int>((cnt * 1LL * componentSize[i]) % MOD);
    }
  }
  printf("%d %d\n", ans, cnt);
  return 0;
}
