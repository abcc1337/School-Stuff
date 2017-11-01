#include <bits/stdc++.h>

using namespace std;

int main() {
    freopen("building.in", "r", stdin);
    freopen("building.out", "w", stdout);

    int n, k, x, y, q;
    cin >> n >> k >> x >> y >> q;
    for (int test = 0; test < q; test++) {
        long long a;
        cin >> a;
        --a;

        long long xx = 1LL * x * (n / k) + 1LL * y * (n - n / k);
        a %= xx;

        int ans = 1;
        long long yy = x + 1LL * y * (k - 1);
        ans += a / yy * k;
        a %= yy;

        ans += min((long long)k - 1, a / y);
        cout << ans << endl;
    }

    return 0;
}