#include "bits/stdc++.h"
#define puba push_back
#define ff first
#define ss second
#define bend(_x) begin(_x), end(_x)
#define szof(_x) ((int) (_x).size())
#define TASK_NAME "calc"

using namespace std;
typedef long long ll;
typedef pair<int, int> pii;
const int INF = 1e9 + 7;
const double PI = atan2(0, -1);

ll n;
int a, b, c;

int main() {
    freopen(TASK_NAME ".in", "r", stdin);
    freopen(TASK_NAME ".out", "w", stdout);

    scanf("%lld%d%d%d", &n, &a, &b, &c);

    assert(n <= 1e9);
    assert(a + b + c <= 7);

    ll tmp = n;

    for (int i = 0; i < a; ++i) {
        tmp /= 2;
    }
    for (int i = 0; i < b; ++i) {
        tmp = (tmp + 1) / 2;
    }
    for (int i = 0; i < c; ++i) {
        tmp = (tmp - 1) / 2;
    }

    if (tmp == 0) {
        cout << tmp << "\n";
        return 0;
    }

    vector<vector<vector<ll>>> d(a + 1, vector<vector<ll>>(b + 1, vector<ll>(c + 1, n)));

    for (int i = 0; i <= a; ++i) {
        for (int j = 0; j <= b; ++j) {
            for (int k = 0; k <= c; ++k) {
                if (i) {
                    d[i][j][k] = min(d[i][j][k], d[i - 1][j][k] / 2);   
                }
                if (j) {
                    d[i][j][k] = min(d[i][j][k], (d[i][j - 1][k] + 1) / 2);   
                }
                if (k) {
                    d[i][j][k] = min(d[i][j][k], (d[i][j][k - 1] - 1) / 2);   
                }
            }
        }
    }

    cout << d[a][b][c] << "\n";

    #ifdef LOCAL
        cerr << "time: " << clock() << endl;
    #endif
    return 0;
}