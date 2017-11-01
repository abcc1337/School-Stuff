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

    vector<int> order1, order2;
    for (int i = 0; i < 3; ++i) {
        order1.puba(i);
        order2.puba(i);
    }

    ll ans = n;
    do {
        do {
            ll tmp = n;
            int na = a;
            int nb = b;
            int nc = c;
            while (na || nb || nc) {
                if (tmp & 1) {
                    for (int i: order1) {
                        if (i == 0) {
                            if (na) {
                                tmp = tmp / 2;
                                --na;
                                break;
                            }
                        }
                        if (i == 1) {
                            if (nb) {
                                tmp = (tmp + 1) / 2;
                                --nb;
                                break;
                            }
                        }
                        if (i == 2) {
                            if (nc) {
                                tmp = (tmp - 1) / 2;
                                --nc;
                                break;
                            }
                        }
                    }
                } else {
                    for (int i: order2) {
                        if (i == 0) {
                            if (na) {
                                tmp = tmp / 2;
                                --na;
                                break;
                            }
                        }
                        if (i == 1) {
                            if (nb) {
                                tmp = (tmp + 1) / 2;
                                --nb;
                                break;
                            }
                        }
                        if (i == 2) {
                            if (nc) {
                                tmp = (tmp - 1) / 2;
                                --nc;
                                break;
                            }
                        }
                    }
                }
            }
            ans = min(ans, tmp);
        } while (next_permutation(bend(order2)));
    } while (next_permutation(bend(order1)));

    cout << ans << "\n";

    #ifdef LOCAL
        cerr << "time: " << clock() << endl;
    #endif
    return 0;
}