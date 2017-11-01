#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

const ll INF = (ll)2e18;

int w, h, s, q, t;
int x[4], y[4];
int b[111], m[111];
ll n[111];

struct Edge {
    int from, to;
    ll cap, flow;

    Edge() {}
    Edge(int from, int to, ll cap) : from(from), to(to), cap(cap), flow(0) {}
};

struct Graph {

    int n;
    vector<Edge> edges;
    vector<vector<int> > e;
    vector<int> c, d;

    Graph(int n) {
        this->n = n;
        e.resize(n);
    }

    void addEdge(int from, int to, ll cap) {
        e[from].push_back(edges.size());
        edges.push_back(Edge(from, to, cap));
        e[to].push_back(edges.size());
        edges.push_back(Edge(to, from, 0));
    }

    bool bfs() {
        c.assign(n, 0);
        d.assign(n, n + 1);
        d[0] = 0;
        queue<int> q;
        q.push(0);
        while (!q.empty()) {
            int v = q.front();
            q.pop();
            for (int i = 0; i < (int)e[v].size(); i++) {
                Edge cur = edges[e[v][i]];
                if (d[cur.to] > d[v] + 1 && cur.flow < cur.cap) {
                    d[cur.to] = d[v] + 1;
                    q.push(cur.to);
                }
            }
        }
        return d[n - 1] != n + 1;
    }

    ll dfs(int v, ll flow) {
        if (flow == 0) return 0;
        if (v == n - 1) return flow;
        for (int &i = c[v]; i < (int)e[v].size(); i++) {
            Edge cur = edges[e[v][i]];
            if (d[cur.to] != d[v] + 1) continue;
            ll pushed = dfs(cur.to, min(flow, cur.cap - cur.flow));
            if (pushed) {
                edges[e[v][i]].flow += pushed;
                edges[e[v][i] ^ 1].flow -= pushed;
                return pushed;
            }
        }
        return 0;
    }

    ll flow() {
        //for (int i = 0; i < (int)edges.size(); i++) edges[i].flow = 0;
        ll res = 0;
        while (bfs()) {
            while (1) {
                ll pushed = dfs(0, INF);
                if (!pushed) break;
                res += pushed;
            }
        }
        return res;
    }
};

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
    }

    vector<int> xx, yy;
    for (int i = 0; i < t; i++) {
        xx.push_back(max(0, x[b[i]] - m[i]));
        xx.push_back(min(w, x[b[i]] + m[i] + 1));
        yy.push_back(max(0, y[b[i]] - m[i]));
        yy.push_back(min(h, y[b[i]] + m[i] + 1));
    }
    sort(xx.begin(), xx.end());
    xx.resize(unique(xx.begin(), xx.end()) - xx.begin());
    sort(yy.begin(), yy.end());
    yy.resize(unique(yy.begin(), yy.end()) - yy.begin());

    int sx = (int)xx.size() - 1;
    int sy = (int)yy.size() - 1;

    int sz = 1 + t + sx * sy + 1;
    Graph gr(sz);
    for (int i = 0; i < t; i++) {
        int lx1 = max(0, x[b[i]] - m[i]);
        int rx1 = min(w, x[b[i]] + m[i] + 1);
        int ly1 = max(0, y[b[i]] - m[i]);
        int ry1 = min(h, y[b[i]] + m[i] + 1);
        for (int cx = 0; cx < sx; cx++) {
            for (int cy = 0; cy < sy; cy++) {
                int lx2 = xx[cx], rx2 = xx[cx + 1];
                int ly2 = yy[cy], ry2 = yy[cy + 1];
                if (lx1 <= lx2 && rx2 <= rx1 && ly1 <= ly2 && ry2 <= ry1) {
                    //cerr << i << " " << cx << " " << cy << " " << 1LL * (xx[cx + 1] - xx[cx]) * (yy[cy + 1] - yy[cy]) * q << endl;
                    gr.addEdge(1 + i, 1 + t + cx * sy + cy, INF);
                }
            }
        }
    }
    for (int cx = 0; cx < sx; cx++) {
        for (int cy = 0; cy < sy; cy++) {
            gr.addEdge(1 + t + cx * sy + cy, 1 + t + sx * sy, 1LL * (xx[cx + 1] - xx[cx]) * (yy[cy + 1] - yy[cy]) * q);
        }
    }
    int ans1 = 0;
    ll ans2 = 0;
    ll all = 0;
    for (int i = 0; i < t; i++) {
        all += n[i];
        gr.addEdge(0, 1 + i, n[i]);
        ll flow = gr.flow();
        if (flow != n[i]) {
            ans2 = flow;
            break;
        }
        ans1++;
    }
    cout << ans1 << " " << ans2 << endl;

    return 0;
}