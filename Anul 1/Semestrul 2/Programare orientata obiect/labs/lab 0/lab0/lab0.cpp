#include <stdio.h>

int main()
{
    int n, s;
    n = 1234;
    s = 0;
    while (n != 0)
    {
        s = s + n % 10;
        n = n / 10;
    }
    printf("%d",s);
    return 0;
}
