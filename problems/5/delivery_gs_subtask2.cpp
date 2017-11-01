#include <cstdio>
#include <iostream>
#include <cassert>
using namespace std;

int main()
{
    freopen("delivery.in", "rt", stdin);
    freopen("delivery.out", "wt", stdout);
    int k, a, b;
    cin >> k >> a >> b;
    int ans = 1e9;
    for (int i = 0; i <= b; i++)
        for (int j = 0; j <= b; j++)
        {
            int tmp = a * i +  (a + k - 1) * j; 
            if (tmp >= b && tmp < ans)
                ans = tmp;
        }
    cout << ans;
    return 0;
}