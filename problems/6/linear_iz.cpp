#include <bits/stdc++.h>

using namespace std;

int main() {
    freopen("linear.in", "r", stdin);
    freopen("linear.out", "w", stdout);

    int n;
    scanf("%d", &n);
    vector<int> st;
    vector<int> a;
    for (int i = 0; i < n; i++) {
        int x, y;
        scanf("%d%d", &x, &y);
        if (y == +1) st.push_back(x);
        else if (!st.empty()) {
            a.push_back(x - st.back());
            st.pop_back();
        }
    }
    sort(a.begin(), a.end());
    int m;
    scanf("%d", &m);
    for (int i = 0; i < m; i++) {
        int t;
        scanf("%d", &t);
        cout << n - (upper_bound(a.begin(), a.end(), t * 2) - a.begin()) * 2 << endl;
    }
}