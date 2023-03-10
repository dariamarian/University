bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a3 db 2
    b3 db 5
    c3 db 3
    d3 dw 8
    
; our code starts here
segment code use32 class=code
    start:
        ;inmultiri impartiri prima parte, ex 26: d+[(a+b)*5-(c+c)*5]
        mov AL, byte[a3] ;AL=a
        add AL, byte[b3] ;AL=AL+b=a+b
        mov AH, 5        ;AH=5
        mul AH           ;AL=AL*AH=(a+b)*5
        
        mov BL, AL       ;BL=AL=(a+b)*5
        
        mov AL, byte[c3] ;AL=c
        add AL, byte[c3] ;AL=AL+c=c+c
        mov AH, 5        ;AH=5
        mul AH           ;AL=AL*AH=(c+c)*5
        
        sub BL, AL       ;BL=BL-AL=(a+b)*5-(c+c)*5
        movzx AX, BL     ;AX=BL=(a+b)*5-(c+c)*5    - muta byte in word
        add AX, word[d3] ;AX=AX+d=[(a+b)*5-(c+c)*5]+d
        ;rezultatul e in AX
    
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
