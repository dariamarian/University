bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a - byte, b - word, c - double word, d - qword - Interpretare fara semn
    a db 8
    b dw 6
    c dd 4
    d dq 11
    
; our code starts here
segment code use32 class=code
    start:
        ;exercitiul 15: a+b-c+(d-a)=a+b-c+d-a
        mov EAX, 0
        mov EBX, 0
        mov ECX, 0
        mov EDX, 0
        mov AL, byte[a]      ;AL=a
        mov CX, word[b]      ;CX=b
        add EAX, ECX         ;EAX=a+b
        mov EBX, dword[c]    ;EBX=c
        sub EAX, EBX         ;EAX=a+b-c
        mov EBX, 0
        mov BL, byte[a]      ;BL=a
        sub EAX, EBX         ;EAX=a+b-c-a
        add EAX, dword[d]    
        adc EDX, dword[d+4]  ;EDX:EAX=a+b-c+d-a
        ;rezultatul in EDX:EAX
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
