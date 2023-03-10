bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a4 db 6
    b4 db 8
    c4 db 4

; our code starts here
segment code use32 class=code
    start:
        ;inmultiri impartiri a doua parte, ex 6: (a*b)/c
        mov AL, byte[a4]    ;AL=a
        mov AH, byte[b4]    ;AH=b
        mul AH              ;AL=AL*AH=a*b
        mov BL, byte[c4]    ;BL=c
        div BL              ;AL=AL/BL=(a*b)/c
        ;rezultatul in AL
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
