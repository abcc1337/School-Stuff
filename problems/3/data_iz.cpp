#include <bits/stdc++.h>

using namespace std;

const int MAXN = 1 << 18;
const int MOD = (int)(1e9) + 7;

int n, m;
vector<int> e[MAXN], g[MAXN];
int in[MAXN], out[MAXN], tmr;
int c[MAXN], cc;
vector<int> comp[MAXN];

void dfs1(int v, int pr) {
    in[v] = out[v] = ++tmr;
    for (int to : e[v]) {
        if (to == pr) continue;
        if (in[to] == 0) {
            dfs1(to, v);
            out[v] = min(out[v], out[to]);
        } else {
            out[v] = min(out[v], in[to]);
        }
    }
}

void dfs2(int v, int color) {
    c[v] = color;
    comp[color].push_back(v);
    for (int to : e[v]) {
        if (c[to] != -1) continue;
        if (out[to] > in[v]) {
            cc++;
            g[color].push_back(cc - 1);
            g[cc - 1].push_back(color);
            dfs2(to, cc - 1);
        } else {
            dfs2(to, color);
        }
    }
}

int main() {
    freopen("data.in", "r", stdin);
    freopen("data.out", "w", stdout);

    scanf("%d%d", &n, &m);
    for (int i = 0; i < m; i++) {
        int u, v;
        scanf("%d%d", &u, &v);
        --u; --v;
        e[u].push_back(v);
        e[v].push_back(u);
    }
    dfs1(0, -1);
    memset(c, -1, sizeof(c));
    cc = 1;
    dfs2(0, 0);

    int mn = 0;
    int ans = 1;
    for (int i = 0; i < cc; i++) {
        if ((int)g[i].size() <= 1) {
            mn++;
            ans = 1LL * ans * comp[i].size() % MOD;
        }
    }
    cout << mn << " " << ans << endl;

    return 0;
}