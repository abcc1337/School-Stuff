#include <iostream>

typedef long long ll;
using namespace std;

int n, k, x, y;

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
		int c;
		cin >> c;
		bool okay = false;
		while (!okay)
			for (int i = 1; i <= n; ++i)
			{
				int cc = (i % k == 0 ? x : y);
				if (c <= cc)
				{
					cout << i << "\n";
					okay = true;
					break;
				}
				c -= cc;
			}
	}

	return 0;
}
