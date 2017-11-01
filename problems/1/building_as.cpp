#include <bits/stdc++.h>
using namespace std;

int main() {
    freopen("building.in", "r", stdin);
    freopen("building.out", "w", stdout);
    
    long long n, k, x, y;
    cin >> n >> k >> x >> y;
    int q;
    cin >> q;
    for (int i = 0; i < q; i++) {
        long long a;
        cin >> a;
        long long p = (n / k) * x + (n - n / k) * y;
        a = (a - 1) % p;
        long long b = x + (k - 1) * y;
        long long res = a / b * k + min((a % b) / y, k - 1) + 1;
        cout << res << endl;
    }
}
