#include <iostream>

typedef long long ll;
using namespace std;

ll n, k, x, y;

int main()
{
#ifndef LOCAL
	freopen("building.in", "r", stdin);
	freopen("building.out", "w", stdout);
#endif
	ios::sync_with_stdio(false);

	cin >> n >> k >> x >> y;
	int q;
	cin >> q;
	while (q--)
	{
		ll c;
		cin >> c;
		c = (c - 1) % (n*x);
		c = c / x;
		cout << c + 1 << "\n";
	}

	return 0;
}
