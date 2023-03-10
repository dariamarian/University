bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a - byte, b - word, c - double word, d - qword - Interpretare cu semn
    a db 8
    b dw 6
    c dd -4
    d dq 11

; our code starts here
segment code use32 class=code
    start:
        ;exercitiul 17: (c+d-a)-(d-c)-b=c+d-a-d+c-b
        mov AX, word[c]
        cwde                 ;EAX=c
        mov EBX, dword[d+0]
        mov ECX, dword[d+4]
        sub EBX, EAX
        sbb ECX, EAX         ;EBX:ECX=d-c
        
        mov EDX, dword[c]    ;EDX=c
        mov AL, byte[a]      ;AL=a
        cbw
        cwde                 ;EAX=a
        sub EDX, EAX         ;EDX=c-a
        mov EAX, EDX         ;EAX=c-a
        add EAX, dword[d+0]
        adc EDX, dword[d+4]  ;EAX:EDX=c+d-a
        
        sub EAX, EBX
        sbb EDX, ECX         ;EAX:EDX=c+d-a-(d-c)
        mov EBX, EAX
        mov ECX, EDX         ;EBX:ECX=c+d-a-(d-c)
        mov AX, word[b]      ;AX=b
        cwde                 ;EAX=b
        sub EBX, EAX
        sbb ECX, EAX         ;EBX:ECX=c+d-a-(d-c)-b
        ;rezultatul in EBX:ECX
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
