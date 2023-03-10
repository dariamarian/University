"""
 8. Pentru un număr natural n dat găsiți numărul natural maxim m format cu
 aceleași cifre. Ex. n=3658, m=8653.
"""
m = 0
p = 1
cif = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
n = int(input("n = "))
while n > 0:
    cif[int(n % 10)] = cif[int(n % 10)] + 1
    n = n // 10
for i in range(10):
    while cif[i] > 0:
        m = m + p * i
        p = p * 10
        cif[i] = cif[i] - 1
print(m)

"""
9.Palindromul unui număr este numărul obținut prin scrierea cifrelor in ordine
inversa (Ex. palindrom(237) = 732). Pentru un n dat calculați palindromul sau.
"""
n = int(input("n = "))
rez = 0
while n > 0:
    rez = rez * 10 + n % 10
    n = n // 10
print(rez)
