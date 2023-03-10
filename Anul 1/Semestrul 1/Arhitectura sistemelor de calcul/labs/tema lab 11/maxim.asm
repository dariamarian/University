bits 32 
global maxim      
segment code use32 public code 
maxim:
        mov esi, [esp+4] ;ebx = sir[0]
        mov ecx, [esp+8] ;ecx = n 
        
        mov ebx, 0
        mov ebx, [esi] ;initializam maxim cu ebx 
        repetat:
            lodsd
            cmp eax, ebx 
            jbe looping
            mov ebx, eax 
        looping:
            loop repetat 
        mov [esp+12], ebx 
        
        ret 4*2 