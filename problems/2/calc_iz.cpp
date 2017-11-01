#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

const ll INF = (ll)2e18;

int main() {
    freopen("calc.in", "r", stdin);
    freopen("calc.out", "w", stdout);

    ll n;
    int a, b, c;
    cin >> n >> a >> b >> c;
    vector<vector<vector<ll> > > dp(a + 1, vector<vector<ll> >(b + 1, vector<ll>(c + 1, INF)));
    dp[0][0][0] = n;
    for (int i = 0; i <= a; i++) {
        for (int j = 0; j <= b; j++) {
            for (int k = 0; k <= c; k++) {
                if (dp[i][j][k] == INF) continue;
                if (i < a) {
                    dp[i + 1][j][k] = min(dp[i + 1][j][k], dp[i][j][k] / 2);
                }
                if (j < b) {
                    dp[i][j + 1][k] = min(dp[i][j + 1][k], (dp[i][j][k] + 1) / 2);
                }
                if (k < c) {
                    dp[i][j][k + 1] = min(dp[i][j][k + 1], (dp[i][j][k] - 1) / 2);
                }
            }
        }
    }
    cout << dp[a][b][c] << endl;

    return 0;
}