#include <bits/stdc++.h>

using namespace std;

typedef long long ll;

int main() {
    freopen("delivery.in", "r", stdin);
    freopen("delivery.out", "w", stdout);

    ll k, x, y;
    cin >> k >> x >> y;

    ll mn1 = x;
    ll mx1 = x + k - 1;

    ll z = y / mn1;
    if (mx1 * z >= y) {
        cout << y << endl;// << " " << y - 1 + mx1 << endl;
    } else {
        cout << mn1 * (z + 1) << endl; // << " " << mx1 * (z + 1) << endl;
    }

    return 0;
}