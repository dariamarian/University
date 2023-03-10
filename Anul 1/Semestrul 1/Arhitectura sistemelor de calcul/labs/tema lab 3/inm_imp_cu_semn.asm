bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    ;a-byte b-doubleword c-qword
    a db 4
    b dd -1
    c dq 12
    
; our code starts here
segment code use32 class=code
    start:
        ;exercitiul 1: c+(a*a-b+7)/(2+a)-cu semn
        mov AL, byte[a]      ;AL=a 
        imul byte[a]         ;AX=a*a
        cwde                 ;EAX=a*a
        mov EBX, dword[b]    ;EBX=b
        sub EAX, EBX         ;EAX=a*a-b
        add EAX, 7           ;EAX=a*a-b+7
        mov EBX, EAX         ;EBX=a*a-b+7
        mov AL,byte[a]       ;AL=a
        add AL, 2            ;AL=2+a
        cbw                  ;AX=2+a
        mov CX, AX           ;CX=2+a 
        mov EAX, EBX         ;EAX=a*a-b+7
        push EAX
        pop AX
        pop DX               ;DX:AX=a*a-b+7
        idiv CX              ;DX:AX=(a*a-b+7)/(2+a)
        cwde                 ;EAX=(a*a-b+7)/(2+a)
        add EAX, dword[c]    
        adc EDX, dword[c+4]
        ;rezultatul in EAX:EDX
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
