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

    while (a || b || c) {
        if (n & 1) {
            if (a) {
                n = n / 2;
                --a;
            } else if (c) {
                n = (n - 1) / 2;
                --c;
            } else {
                n = (n + 1) / 2;
                --b;
            }
        } else {
            if (c) {
                n = (n - 1) / 2;
                --c;
            } else if (b) {
                n = (n + 1) / 2;
                --b;
            } else {
                n = n / 2;
                --a;
            }
        }
    }

    cout << n << "\n";

    #ifdef LOCAL
        cerr << "time: " << clock() << endl;
    #endif
    return 0;
}