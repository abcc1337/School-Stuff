#include <bits/stdc++.h>
using namespace std;

int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    int n, x, x1, z = 0, k = 0;
    cin >> n;
    if (n <= 300)
    {
        vector < int > ans(n);
        for (int i = 1; i < n; i++)
        {
            cout << "? " << i << ' ' << i + 1 << endl;
            cin >> x;
            z += (x == 2);
            ans[i] = (ans[i - 1] + x - 1) % 2;
        }
        cout << "Ready!" << endl;
        cout << (z > 0) + 1 << endl;
        for (auto& y : ans)
            cout << y + 1 << ' ';
    }
    else
    {
        vector < int > ans(n);
        vector < int > res(n);
        ans[0] = ++k;
        res[0] = 1;
        for (int i = 1; i < n; i++)
        {
            cout << "? " << 1 << ' ' << i + 1 << endl;
            cin >> x;
            res[i] = x;
            if (x != res[i - 1])
            {
                ans[i] = ++k;
            }
            else
            {
                x1 = x;
                int left = max(i - 30, 1);
                while (x1 == x && x != 1)
                {
                    left++;
                    cout << "? " << left << ' ' << i + 1 << endl;
                    cin >> x;
                    cout << "? " << left << ' ' << i << endl;
                    cin >> x1;
                }
                if (x == 1)
                {
                    ans[i] = ans[i - 1];
                }
                else
                {
                    ans[i] = ans[left - 2];
                }
            }
        }
        cout << "Ready!" << endl;
        cout << res[n - 1] << endl;
        for (auto& x : ans)
        {
            cout << x << ' ';
        }
    }
    return 0;
}
