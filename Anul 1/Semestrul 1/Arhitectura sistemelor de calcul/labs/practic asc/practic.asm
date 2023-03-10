bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit, fprintf, fclose, fopen     ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll
import fclose msvcrt.dll
import fprintf msvcrt.dll
import fopen msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    numeFisier db "output.txt", 0
    sir db "ana are mere", 0
    modAcces db "w",0
    string db "%s", 0 
    intreg db "%d", 0
    caracter db "%c", 0
    descriptor dd 0
    numar dd -1
    solutie times 100 db 0

; our code starts here
segment code use32 class=code
    start:
        ;in data segment avem un nume de fisier si un text. textul contine litere mici si spatii. inlocuiti toate literele de pe pozitii impare cu indexul pozitiei lor
        ;creati un fisier cu acel nume si scrieti textul generat in acel fisier
        ; ana are mere -> a1a a5e m9r11
        
        ;fopen(numeFisier, modAcces)
        push dword modAcces
        push dword numeFisier
        call [fopen]
        add esp, 4*2
        
        cmp eax,0
        je final
        mov [descriptor],eax
        
        ;bucla pentru parcurgerea sirului caracter cu caracter si afisarea formatului nou
        mov esi, sir 
        mov edi, solutie
        parcurge:
            mov eax, 0
            lodsb   ;eax = sir[i]
            inc dword[numar]
            cmp al, 0
            je outLoop
            cmp al, ' '
            je skip
            
            mov ebx, dword[numar]
            test bl, 1
            jz skip
            mov eax, dword[numar]
            
            push word 0
            push ax
            pop eax
            
            ;fprintf(descriptor, "%d", eax)
            push dword eax
            push dword intreg
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
            
            skip:
            ;fprintf(descriptor, "%c", eax)
            literaMare:
            push dword eax
            push dword caracter
            push dword [descriptor]
            call [fprintf]
            add esp, 4*3
        jmp parcurge
        
        outLoop:
        
        ;fprintf(descriptor, "%s", solutie)
        push dword solutie
        push dword string
        push dword [descriptor]
        call [fprintf]
        add esp, 4*4
        
        ;fclose(descriptor)
        push dword[descriptor]
        call [fclose]
        add esp, 4
    
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
