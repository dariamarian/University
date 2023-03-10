bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a1 db 6
    b1 db 4
    c1 db 8
    d1 db 12

; our code starts here
segment code use32 class=code
    start:
        ;adunari scaderi prima parte, ex 3: (c+d)-(a+d)+b
        mov AL, byte[c1]    ;AL=c
        add AL, byte[d1]    ;AL=AL+d=c+d
        
        mov AH, byte[a1]    ;AH=a
        add AH, byte[d1]    ;AH=AH+d=a+d
        
        sub AL, AH          ;AL=AL-AH=(c+d)-(a+d)
        add AL, byte[b1]    ;AL=AL+b=(c+d)-(a+d)+b
        ;rezultatul in AL
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
