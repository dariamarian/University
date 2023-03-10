bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dw 0111011101010111b
    b dw 1001101110111110b
    c dd 0

; our code starts here
segment code use32 class=code
    start:
        ;Se dau cuvintele A si B. Sa se obtina dublucuvantul C:
        mov EBX, 0  ;in EBX calculam rezultatul
        
        ;bitii 0-2 ai lui C coincid cu bitii 12-14 ai lui A
        mov EAX, 0
        mov AX, [a] ;izolam bitii 12-14 ai lui A
        and EAX, 0111000000000000b
        mov CL, 12
        ror EAX, CL ;rotim 12 pozitii spre dreapta
        or  EBX, EAX ;punem bitii in rezultat
        
        ;bitii 3-8 ai lui C coincid cu bitii 0-5 ai lui B
        mov EAX, 0
        mov AX, [b] ;izolam bitii 0-5 ai lui B
        and EAX, 0000000000111111b
        mov CL, 3
        rol EAX, CL ;rotim 3 pozitii spre stanga
        or  EBX, EAX ;punem bitii in rezultat
        
        ;bitii 9-15 ai lui C coincid cu bitii 3-9 ai lui A
        mov EAX, 0
        mov AX, [a] ;izolam bitii 3-9 ai lui A
        and EAX, 0000001111111000b
        mov CL, 6
        rol EAX, CL ;rotim 6 pozitii spre stanga
        or  EBX, EAX ;punem bitii in rezultat
        
        ;bitii 16-31 ai lui C coincid cu bitii lui A
        mov EAX, 0
        mov AX, [a] 
        mov CL, 16
        rol EAX, CL ;rotim 16 pozitii spre stanga
        or  EBX, EAX ;punem bitii in rezultat
        
        mov [c], EBX
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
