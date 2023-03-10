from ui import run
from batche import run_batch


def main():
    while True:
        print("Alegeti tipul de meniu:")
        print("1.Meniul normal")
        print("2.Meniul batch mode")
        print("3.Exit")
        cmd = input("Introduceti optiunea dorita:")
        if cmd == "3":
            return
        elif cmd == "1":
            run()
        elif cmd == "2":
            run_batch()
        else:
            print("comanda invalida")

main()