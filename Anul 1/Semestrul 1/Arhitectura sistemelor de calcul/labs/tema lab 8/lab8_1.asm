bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, scanf               
import exit msvcrt.dll
import scanf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    a dd 0
    b dd 0
    rezultat dd 0
	format  db "%d", 0  

; our code starts here
segment code use32 class=code
    start:
        ; Exercitiul 13: Sa se citeasca de la tastatura doua numere a si b (in baza 10) si sa se calculeze: (a+b) * (a-b). Rezultatul inmultirii se va salva in memorie in variabila "rezultat" (definita in segmentul de date).
        
        ;citirea lui a
        push dword a
        push dword format
        call [scanf]
        add esp, 4*2
        
        ;citirea lui b
        push dword b
        push dword format
        call [scanf]
        add esp, 4*2
        
        ;a+b
        mov EAX, dword[a]
        adc EAX, dword[b]
        
        ;a-b
        mov EBX, dword[a]
        sbb EBX, dword[b]
        
        ;(a+b) * (a-b)
        mov EDX, 0
        imul EBX
        
        ;mutarea rezultatului in variabila rezultat
        mov dword [rezultat], EAX 
        
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
