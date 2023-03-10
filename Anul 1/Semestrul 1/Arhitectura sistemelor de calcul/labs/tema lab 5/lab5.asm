bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db '+', '4', '2', 'a', '8', '4', 'X', '5' ; declararea sirului s1
    len1 equ $-s1 ; stabilirea lungimii sirului s1, len1
    s2 db 'a', '4', '5' ;declararea sirului s2
    len2 equ $-s2 ; stabilirea lungimii sirului s2, len2
	d times len1 db 0 ; rezervarea unui spatiu de dimensiune len1 pentru sirul destinatie d si initializarea acestuia

; our code starts here
segment code use32 class=code
    start:
        ; exercitiul 25: Se dau doua siruri de caractere S1 si S2. Sa se construiasca sirul D ce contine toate elementele din S1 care nu apar in S2.
        mov ecx, len1 ; punem lungimea in ECX pentru a putea realiza bucla loop de ecx ori
        mov esi, 0
        mov edx, 0
        
        repeta: ; parcurgem toate elementele din sirul s1
              
           mov al, [s1+esi]
           inc esi
           mov edi, 0
           
           repeta1: ; comparam elementul curent din s1 cu toate elementele din s2
           
              mov bl, [s2+edi]
              inc edi
              cmp bl, al ; verificam daca elementul curent din s1 este egal cu cel curent din s2
              jz final ; daca sunt egale atunci sirul d nu va contine acel element
              cmp edi, len2
              
           jnz repeta1
  
           mov [d+edx], al ; adaugam elementul din s1 in sirul d
           inc edx

           final: 
           
        loop repeta 
        
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
