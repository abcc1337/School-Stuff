#include <bits/stdc++.h>

void dfs(const std::vector<std::vector<int>> &graph,
         std::vector<bool> &used, const std::vector<int> &badEdge,
         std::vector<int> &vs, int v) {
  used[v] = true;
  vs.push_back(v);
  for (int u : graph[v]) {
    if ((v == badEdge[0] && u == badEdge[1]) || (v == badEdge[1] && u == badEdge[0])) {
      continue;
    }
    if (!used[u]) {
      dfs(graph, used, badEdge, vs, u);
    }
  }
}

bool isConnected(const std::vector<std::vector<int>> &graph, int n,
                 const std::vector<bool> &marked, const std::vector<int> &badEdge) {
  std::vector<bool> used(n, false);
  for (int i = 0; i < n; i++) {
    if (!used[i]) {
      std::vector<int> vs;
      dfs(graph, used, badEdge, vs, i);
      bool foundMarked = false;
      for (int v : vs) {
        if (marked[v]) {
          foundMarked = true;
        }
      }
      if (!foundMarked) {
        return false;
      }
    }
  }
  return true;
}

bool isGood(const std::vector<std::vector<int>> &graph, int n, int mask) {
  std::vector<bool> marked(n, false);
  for (int i = 0; i < n; i++) {
    if ((mask & (1 << i)) > 0) {
      marked[i] = true;
    }
  }
  for (int i = 0; i < n; i++) {
    for (int v : graph[i]) {
      if (!isConnected(graph, n, marked, {i, v})) {
        return false;
      }
    }
  }
  return true;
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
  int minAnswer = INT_MAX;
  int cntAnswer = 0;
  for (int msk = 0; msk < (1 << n); msk++) {
    if (isGood(graph, n, msk)) {
      int bits = __builtin_popcount(msk);
      if (minAnswer == bits) {
        cntAnswer++; 
      }
      if (minAnswer > bits) {
        minAnswer = bits;
        cntAnswer = 1;
      }
    }
  }
  printf("%d %d\n", minAnswer, cntAnswer);
  return 0;
}