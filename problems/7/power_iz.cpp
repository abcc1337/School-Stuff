#include <bits/stdc++.h>
using namespace std;

int main() {
    freopen("power.in", "r", stdin);
    freopen("power.out", "w", stdout);
    
    int n, k;
    scanf("%d%d", &n, &k);
    vector<pair<int, int> > a(n);
    for (int i = 0; i < n; i++) scanf("%d%d", &a[i].first, &a[i].second);
    sort(a.begin(), a.end());
    priority_queue<int> q;
    long long ans = 0;
    for (int i = n - 1; i >= 0; i--) {
        q.push(-a[i].second);
        if (n - i > k) q.pop();
        if (n - i >= k) ans = max(ans, 1LL * a[i].first * -q.top());
    }
    cout << ans << endl;

    return 0;
}