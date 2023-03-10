def BubbleSort(list, key=lambda x: x, cmp=lambda x, y: x < y, reverse=False):
    n = len(list)
    if reverse is False:
        for i in range(n-1):
            for j in range(0, n-i-1):
                if cmp(key(list[j+1]), key(list[j])):
                    list[j], list[j + 1] = list[j + 1], list[j]
    else:
        for i in range(n-1):
            for j in range(0, n-i-1):
                if cmp(key(list[j]), key(list[j+1])):
                    list[j], list[j + 1] = list[j + 1], list[j]


def ShellSort(list, n, key=lambda x: x, cmp=lambda x, y: x < y, reverse=False):
    interval = n // 2
    if reverse is False:
        while interval > 0:
            for i in range(interval, n):
                temp = list[i]
                j = i
                while j >= interval and cmp(key(temp), key(list[j - interval])):
                    list[j] = list[j - interval]
                    j -= interval
                list[j] = temp
            interval //= 2
    else:
        while interval > 0:
            for i in range(interval, n):
                temp = list[i]
                j = i
                while j <= interval or cmp(key(list[j - interval]), key(temp)):
                    list[j] = list[j - interval]
                    j -= interval
                list[j] = temp
            interval //= 2

