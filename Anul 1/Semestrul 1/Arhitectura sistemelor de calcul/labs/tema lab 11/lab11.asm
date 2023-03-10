bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
  
extern maxim 
extern exit, fopen, fclose, scanf, fprintf, printf        
import exit msvcrt.dll  
import fopen msvcrt.dll
import fclose msvcrt.dll
import scanf msvcrt.dll
import fprintf msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    n dd 0
    x dd 0
    sir times 100 dd 0
    format_citire db 'Introduceti numarul elementelor= ', 0
    citire db '%d', 0
    format_sir db 'Introduceti elementul= ', 0
    string db '%s', 0
    format_hexa db 'Cel mai mare element in baza 16 este: ', 0
    rezultat db '%x', 0
    descriptor dd -1 
    nume_fisier db 'max.txt', 0
    mod_acces db 'w', 0

; our code starts here
segment code use32 class=code
    start:
        ; Se citeste de la tastatura un sir de numere in baza 10, cu semn. Sa se determine valoarea maxima din sir si sa se afiseze in fisierul max.txt (fisierul va fi creat) valoarea maxima, in baza 16.
    
        ;create file 
        ;eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4*2
        
        mov [descriptor], eax 
        
        cmp eax, 0
        je final
        
        ;printf(string, format_citire)
        push dword format_citire
        push dword string 
        call [printf]
        add esp, 4*2
        
        ;scanf(citire, n)
        push dword n
        push dword citire 
        call [scanf]
        add esp, 4*2
        
        ;citim de la tastatura
        mov edi, sir 
        mov ebx, [n] 
    repeta: 
        ;printf(string, format_sir)
        push dword format_sir
        push dword string
        call [printf]
        add esp, 4*2
        
        ;scanf(citire, n)
        push dword x
        push dword citire 
        call [scanf]
        add esp, 4*2
        
        mov eax, [x]
        stosd
        dec ebx
        cmp ebx, 0
    ja repeta 
        
        ;eax = maxim(sir, n, rezultat)
        push dword 0          ;ESP = ESP + 12
        push dword [n]        ;ESP = ESP + 8
        push dword sir        ;ESP = ESP + 4
        call maxim
        pop dword [x]
        
        ;fprintf(descriptor, format_hexa)
        push dword format_hexa
        push dword [descriptor]
        call [fprintf]
        add esp, 4*2 
        
        ;fprintf(descriptor, rezultat, x)
        push dword [x]
        push dword rezultat
        push dword [descriptor]
        call [fprintf]
        add esp, 4*3
        
        ;close file 
        ;fclose(descriptor)
        push dword [descriptor]
        call [fclose]
        add esp, 4
        
    final:
        push    dword 0      
        call    [exit]       

