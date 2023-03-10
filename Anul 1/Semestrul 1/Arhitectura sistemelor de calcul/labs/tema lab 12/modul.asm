bits 32

extern _printf

global _asmConcat

segment data public data use32
    aSirCurent dd -1
    aSirFinal dd -1
    lungimeFinal dd 0

segment code public code use32

;6. Se citesc trei siruri de caractere. Sa se determine si sa se afiseze rezultatul concatenarii lor.

lenSir:
    mov esi , ebx
    
    mov eax , 0
    mov ebx , 0 ; contorizam numarul de caractere efectiv
    cld
    
    repeta:
        lodsb
        cmp al , 0x00
        je final
        
        inc ebx
        
        jmp repeta
    
    final:
    
    mov eax , ebx ; returnam in eax

    ret
    
; int asmConcat(char *sir1 , char *sir2 , char *sir3 , char* sirfinal)

_asmConcat:
    push ebp
    mov ebp , esp
    
    mov eax ,  [ebp + 20] ;sir final
    mov [aSirFinal] , eax
    
    mov eax , [ebp + 8] ;sir 1
    mov [aSirCurent] , eax ;sir 1
    
    mov ebx , [aSirCurent]
    call lenSir ; eax = nr de caractere efectiv
    add [lungimeFinal] , eax
    
    cld
    mov edi , [aSirFinal]
    mov esi , [aSirCurent]
    mov ecx , eax
    rep movsb
    
    mov eax , [ebp + 12] ;sir 2
    mov [aSirCurent] , eax ;sir 2
    
    mov ebx , [aSirCurent]
    call lenSir ; eax = nr de caractere efectiv
    add [lungimeFinal] , eax
    
    cld
    mov esi , [aSirCurent] ; actualizam sirul sursa
    mov ecx , eax
    rep movsb
    
    mov eax , [ebp + 16] ;sir 3
    mov [aSirCurent] , eax ;sir 3
    
    mov ebx , [aSirCurent]
    call lenSir ; eax = nr de caractere efectiv
    add [lungimeFinal] , eax
    
    cld
    mov esi , [aSirCurent] ; actualizam sirul sursa
    mov ecx , eax
    rep movsb
    
    mov al , 0
    stosb ;adaugam caracterul de final
    
    mov eax , [lungimeFinal] ; returnam in eax numarul de caractere efectiv
    
    mov esp, ebp
    pop ebp
    
    ret