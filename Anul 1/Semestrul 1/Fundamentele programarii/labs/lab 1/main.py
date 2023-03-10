# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

def cmmdc(a, b):
    if a==0: return b
    if b==0: return a
    while a!=b:
        if a>b:
            a=a-b
        else:
            b=b-a
    return a


print(cmmdc(2, 6))


def prim(n):
    if n < 2: return 0
    if n % 2 == 0 & n != 2: return 0
    d=3
    while d*d<=n:
        if n%d==0: return 0
        d=d+2
    return 1


print(prim(12))


# See PyCharm help at https://www.jetbrains.com/help/pycharm/
