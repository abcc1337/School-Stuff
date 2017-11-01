#include "testlib.h"

#include <algorithm>
#include <vector>
#include <iostream>

using namespace std;

int main(int argc, char *argv[]) {
    registerTestlibCmd(argc, argv);

    int pAns = ouf.readInt();
    int pAnsc = ouf.readInt();
    int jAns = ans.readInt();
    int jAnsc = ans.readInt();

    if (pAns != jAns || pAnsc != jAnsc) {
        quitf(_wa, "Expected (%d, %d), but found (%d, %d)", jAns, jAnsc, pAns, pAnsc);
    }
    quitf(_ok, "OK, (%d, %d)", jAns, jAnsc);
    return 0;
}
