#include <iostream>

typedef long long ll;
using namespace std;

const int F = 200;
int n, k, x, y;
int a[F];

int main()
{
#ifndef LOCAL
	freopen("building.in", "r", stdin);
	freopen("building.out", "w", stdout);
#endif
	ios::sync_with_stdio(false);

	cin >> n >> k >> x >> y;
	int m = 1;
	while (m < F)
		for (int i = 1; i <= n && m < F; ++i)
			for (int j = 0; j < (i % k == 0 ? x : y) && m < F; ++j)
				a[m++] = i;

	int q;
	cin >> q;
	while (q--)
	{
		int c;
		cin >> c;
		cout << a[c] << "\n";
	}

	return 0;
}
