#include <stdio.h>

int asmConcat(char *sir1 , char *sir2 , char *sir3 , char* sirfinal);

int main()
{
    char sir1[31] , sir2[31] , sir3[31] , sirFinal[91]; 
    int x;

    printf("sir1 = ");
    scanf("%s" , sir1);
    
    
    printf("sir2 = ");
    scanf("%s" , sir2);

    
    printf("sir3 = ");
    scanf("%s" , sir3);

    int lenSirFinal = asmConcat(sir1 , sir2 , sir3 , sirFinal);

    printf("Sir final = %s" , sirFinal);

    scanf("%d" , &x); // sa nu se inchida consola

    return 0;
}