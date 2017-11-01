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

    vector<int> order;
    for (int i = 0; i < a; ++i) {
        order.puba(0);
    }
    for (int i = 0; i < b; ++i) {
        order.puba(1);
    }
    for (int i = 0; i < c; ++i) {
        order.puba(2);
    }

    ll ans = n;
    do {
        ll tmp = n;
        for (int num: order) {
            if (num == 0) {
                tmp /= 2;
            } else if (num == 1) {
                tmp = (tmp + 1) / 2;
            } else {
                tmp = (tmp - 1) / 2;
            }
        }
        ans = min(ans, tmp);
    } while (next_permutation(bend(order)));

    cout << ans << "\n";

    #ifdef LOCAL
        cerr << "time: " << clock() << endl;
    #endif
    return 0;
}