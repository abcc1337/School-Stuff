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

ll solve(ll n, int a, int b, int c) {
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

    return d[a][b][c];
}


int main() {
    freopen(TASK_NAME ".in", "r", stdin);
    freopen(TASK_NAME ".out", "w", stdout);

    ll tc = 0;
    while (true) {
        ll n = (((((((ll) rand() << 15) | rand()) << 15) | rand()) << 15) | rand()) / 2;
        int a = rand() % 30;
        int b = rand() % 30;
        int c = rand() % 30;
        //cerr << n << " " << a << " " << b << " " << c << "\n";
        ++tc;
        if (tc % 100000 == 0) {
            cerr << "were generated " << tc << " tests\n";
        }
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

        ll tmp = solve(n, a, b, c);
        if (ans != tmp) {
            cerr << n << " " << a << " " << b << " " << c << endl;
            cerr << "find! " << ans << " " << tmp << endl;
            return 0;
        }
    }

    #ifdef LOCAL
        cerr << "time: " << clock() << endl;
    #endif
    return 0;
}