bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program  
extern exit, fopen, fclose, printf, fread
import exit msvcrt.dll   
import fclose msvcrt.dll 
import fopen msvcrt.dll    
import printf msvcrt.dll    
import fread msvcrt.dll  

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    mod_acces db "r", 0
    nume_fisier db "input.txt", 0
    d times 100 db 0FFh
    nrY dd 0
    nrZ dd 0
    descriptor_fis dd -1
    format_output db "In fisier, cifra y apare de %d ori, iar cifra z apare de %d ori", 0

; our code starts here
segment code use32 class=code
    start:
        ; Exercitiul 16: Se da un fisier text. Sa se citeasca continutul fisierului, sa se contorizeze numarul de litere 'y' si 'z' si sa se afiseze aceaste valori. Numele fisierului text este definit in segmentul de date.
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add ESP, 4 * 2
        
        cmp EAX, 0
        jz final ; verificam daca fisierul exista. Daca nu, sarim la final.
        
        mov [descriptor_fis], EAX
        
        loop_true: ; un loop care se va opri doar cand vom fi citit tot din fisier
            push dword[descriptor_fis]
            push dword 100
            push dword 1
            push dword d
            call [fread]
            add ESP, 4 * 4
            
            cmp EAX, 0 
            je inchidere_fisier; ; daca nu am mai avut nimic de citit, inchidem loop_true
            
            mov ECX, EAX
            
            mov ESI, d
            
            repeta: ; loop-ul in care parcurgem ultimele (maxim) 100 de caractere citite
                lodsb
                
                cmp AL, 'y'
                jz egaly
                
                cmp AL, 'z'
                jz egalz
                
                jmp niciuna
                
                egaly: ; cazul in care am gasit un y
                    mov EDX, [nrY]
                    inc EDX
                    mov [nrY], EDX
                    jmp niciuna
                egalz: ; cazul in care am gasit un z
                    mov EDX, [nrZ]
                    inc EDX
                    mov [nrZ], EDX
                niciuna: ; cazul in care nu am gasit nici y nici z
            loop repeta
        jmp loop_true
        
        inchidere_fisier:
            push dword [descriptor_fis]
            call [fclose]
            add ESP, 4
        
        push dword [nrZ]
        push dword [nrY]
        push dword format_output
        call [printf]
        add ESP, 4 * 3
        
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
