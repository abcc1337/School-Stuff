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
    //assert(k == 1);
    cout << (b / a + (b % a != 0)) * a << endl;
    return 0;
}