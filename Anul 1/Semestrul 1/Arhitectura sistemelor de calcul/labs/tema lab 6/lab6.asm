bits 32 ; assembling for the 32 bits architecture

; declare the EntryPoint (a label defining the very first instruction of the program)
global start        

; declare external functions needed by our program
extern exit               ; tell nasm that exit exists even if we won't be defining it
import exit msvcrt.dll    ; exit is a function that ends the calling process. It is defined in msvcrt.dll
                          ; msvcrt.dll contains exit, printf and all the other important C-runtime specific functions

; our data is declared here (the variables needed by our program)
segment data use32 class=data
    s1 db 'a', 'd', 'e', 'g' 
    len1 equ $-s1
    s2 db 'a', 'c', 'f', 'g' 
    len2 equ $-s2
	len3 equ len1+len2
    s3 times len3 db 0

; our code starts here
segment code use32 class=code
    start:
        ; Exercitiul 16: Se dau doua siruri de caractere ordonate alfabetic s1 si s2. Sa se construiasca prin interclasare sirul ordonat s3 care sa contina toate elementele din s1 si s2.
        mov esi,0
		mov edi,0
		mov eax,0
        mov ebx,0
		mov al,len1 ;stochez lungimea primului sir in al
		mov ah,len2 ;stochez lungimea celui de-al doilea sir in ah
		; cmp ah,al ;compar lungimile sirurilor 
		; jb l1 
		; ja l2
		; l1:
			; mov ecx,len1; daca primul sir este mai lung, stochez len1 in ecx
		; l2:
			; mov ecx,len2; altfel stochez len2 in ecx
        mov ecx,len3
        dec ecx
		jecxz final
		
	repeta:
		mov al,[s1+esi]
		mov ah,[s2+edi]
		cmp al,ah ; compar caracterele din cele doua siruri
		jbe sir1 ;daca caracterul din s1 este mai mic sau egal, sarim la eticheta sir1 pentru a-l stoca in sirul s3
		ja sir2 ;daca caracterul din s2 este mai mic, sarim la eticheta sir2 pentru a-l stoca in sirul s3
		sir1:
			mov byte[s3+ebx], al; stocam in s3 caracterul din s1
			inc ebx
			inc esi
            jmp finalsir1
		
		sir2:
			mov al,[s2+edi]; punem in al caracterul din s2
			mov byte[s3+ebx], al; stocam in s3 caracterul din s2
			inc ebx
			inc edi
        finalsir1:
			
	loop repeta
	continua:
		cmp esi,edi; comparam esi si edi pentru a vedea daca am parcurs ambele siruri pana la final
		je final; daca esi si edi sunt egale inseamna ca nu mai avem caractere ramase si am terminat programul
		jb eti1; daca esi este mai mic, vom sari la eticheta eti1
		ja eti2; daca edi este mai mic, vom sari la eticheta eti2
		eti1:
			xor ecx,ecx
            mov cl,len2
			mov ch,len1
			sub cl,ch;aflam cate caractere mai avem de stocat in s3
			mov ch, 0; stocam in ecx numarul de caractere ramas
			;jmp continua_s1 ; sarim la eticheta continua_s1 pentru a stoca in s3 caracterele ramase din s1
			jecxz final
		continua_s1:
			mov al,[s1+esi]
			mov byte[s3+ebx], al;
			inc ebx
			inc esi
		loop continua_s1
		eti2:
			xor ecx,ecx
            mov cl,len1
			mov ch,len2
			sub cl,ch;aflam cate caractere mai avem de stocat in s3
			mov ch, 0;stocam in ecx numarul de caractere ramas
			;jmp continua_s2; sarim la eticheta continua_s2 pentru a stoca in s3 caracterele ramase din s2
			jecxz final
			
		continua_s2:
			mov al,[s2+edi]
			mov byte[s3+ebx], al;
			inc ebx
			inc edi
		loop continua_s2
        final:
        ; exit(0)
        push    dword 0      ; push the parameter for exit onto the stack
        call    [exit]       ; call exit to terminate the program
