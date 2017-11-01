#include <cstdio>
#include <iostream>
using namespace std;

int main()
{
    freopen("delivery.in", "rt", stdin);
    freopen("delivery.out", "wt", stdout);
    long long k, a, b;
    cin >> k >> a >> b;
    long long min_ans;
    if (b % a == 0 || (b / a) * (k - 1) >= b % a)
        min_ans = b;
    else
        min_ans = a * (b / a + 1);
    cout << min_ans << endl;
    return 0;
}