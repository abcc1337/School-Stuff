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
    int l = a, r = a + k - 1;
    int t = 2;
    int ans = (b /a + 1) * a;
    while (l <= b)
    {
        if (r >= b)
        {
            ans = b; 
            break;
        }
        t++;
        l = t * a;
        r = t * (a + k - 1);
    }
    cout << ans << endl;
    return 0;
}