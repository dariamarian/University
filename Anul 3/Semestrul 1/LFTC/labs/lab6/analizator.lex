%{
#include <stdlib.h>
#include <string.h>

typedef struct {
	char atom[100];
	int codAtom;
	int codAtomTS;
}FIP;

typedef struct {
	char atom[100];
	int codAtomTS;
}TS;

int lenFIP = 0, lenTS = 0, codTS = 0;
FIP fip[300];
TS ts[300];
int currentLine = 1;

void addToFIP(char atom[], int codAtom, int codAtomTS) {
	lenFIP++;
	strcpy(fip[lenFIP - 1].atom, atom);
	fip[lenFIP - 1].codAtom = codAtom;
	fip[lenFIP - 1].codAtomTS = codAtomTS;
}

int addToTS(char atom[]) {
    int i, j;

    // Check if the symbol already exists in the table
    for (i = 0; i < lenTS; i++) {
        if (strcmp(ts[i].atom, atom) == 0) {
            return ts[i].codAtomTS;
        }
    }

    // Find the correct position to insert the new symbol lexicographically
    i = 0;
    while (i < lenTS && strcmp(ts[i].atom, atom) < 0) {
        i++;
    }

    // Shift elements to make room for the new one
    for (j = lenTS; j > i; j--) {
        ts[j] = ts[j - 1];
    }

    // Insert the new symbol
    strcpy(ts[i].atom, atom);
    ts[i].codAtomTS = codTS;
    codTS++;
    lenTS++;

    // Update the codAtomTS values after insertion
    for (i = 0; i < lenTS; i++) {
        ts[i].codAtomTS = i;
    }

    return codTS - 1;
}

void printTS() {
	printf("TABELA DE SIMBOLURI:\n");
	int i;
	for (i = 0; i < lenTS; i++)
		printf("%s  |  %d\n", ts[i].atom, ts[i].codAtomTS);
	printf("\n");
}

void printFIP() {
	printf("FORMA INTERNA A PROGRAMULUI:\n");
	int i;
	for (i = 0; i < lenFIP; i++)
		if (fip[i].codAtomTS == -1)
			printf("%s  |  %d  |  -\n", fip[i].atom, fip[i].codAtom);
		else
			printf("%s  |  %d  |  %d\n", fip[i].atom, fip[i].codAtom, fip[i].codAtomTS);
}

void resetSymbolCodesInFIP() {
    int i;
    for (i = 0; i < lenFIP; i++) {
        if (fip[i].codAtomTS != -1) {
            // Find the symbol in the symbol table and update its code in FIP
            int j;
            for (j = 0; j < lenTS; j++) {
                if (strcmp(fip[i].atom, ts[j].atom) == 0) {
                    fip[i].codAtomTS = ts[j].codAtomTS;
                    break;
                }
            }

            // If the symbol is not found in the symbol table, update its code as -1
            if (j == lenTS) {
                fip[i].codAtomTS = -1;
            }
        }
    }
}

%}

%option noyywrap

DIGIT [0-9]
ID [a-zA-Z_][a-zA-Z0-9_]{0,7}
KEYWORD int|float|double|struct|if|else|while|for|cin|cout|return|main
OPERATOR "+"|"-"|"*"|"/"|">"|"<"|"!="|"=="|"<="|">="|"="
SEPARATOR ";"|","|"("|")"|"{"|"}"|">>"|"<<"

%%

{KEYWORD} {
    int codAtom = -1;
    if (strcmp("int", yytext) == 0) codAtom = 2;
    if (strcmp("float", yytext) == 0) codAtom = 3;
    if (strcmp("double", yytext) == 0) codAtom = 4;
    if (strcmp("struct", yytext) == 0) codAtom = 5;
    if (strcmp("if", yytext) == 0) codAtom = 17;
    if (strcmp("else", yytext) == 0) codAtom = 18;
    if (strcmp("while", yytext) == 0) codAtom = 19;
    if (strcmp("for", yytext) == 0) codAtom = 20;
    if (strcmp("cin", yytext) == 0) codAtom = 21;
    if (strcmp("cout", yytext) == 0) codAtom = 22;
    if (strcmp("return", yytext) == 0) codAtom = 23;
    if (strcmp("main", yytext) == 0) codAtom = 32;
    addToFIP(yytext, codAtom, -1);
}

{ID} {
    if (strlen(yytext) > 8) {
        printf("Error on line %d. Identifier '%s' has more than 8 characters.\n", currentLine, yytext);
        exit(EXIT_FAILURE);
    } else {
        int codTS = addToTS(yytext);
        addToFIP(yytext, 0, codTS);
    }
}

{ID}{9,} {
    printf("Error on line %d. Identifier '%s' has more than 8 characters.\n", currentLine, yytext);
    exit(EXIT_FAILURE);
}

{DIGIT}"."{DIGIT}{DIGIT}* {
    int codTS = addToTS(yytext);
    addToFIP(yytext, 1, codTS);
}

[0-9a-fA-F]+ {
    int codTS = addToTS(yytext);
    addToFIP(yytext, 1, codTS);
}

{DIGIT}+ {
    int codTS = addToTS(yytext);
    addToFIP(yytext, 1, codTS);
}

{OPERATOR} {
    int codAtom = -1;
    if (strcmp("+", yytext) == 0) codAtom = 6;
    if (strcmp("-", yytext) == 0) codAtom = 7;
    if (strcmp("*", yytext) == 0) codAtom = 8;
    if (strcmp("/", yytext) == 0) codAtom = 9;
    if (strcmp(">", yytext) == 0) codAtom = 10;
    if (strcmp("<", yytext) == 0) codAtom = 11;
    if (strcmp("!=", yytext) == 0) codAtom = 12;
    if (strcmp("==", yytext) == 0) codAtom = 13;
    if (strcmp("<=", yytext) == 0) codAtom = 14;
    if (strcmp(">=", yytext) == 0) codAtom = 15;
    if (strcmp("=", yytext) == 0) codAtom = 16;
    addToFIP(yytext, codAtom, -1);
}

{SEPARATOR} {
    int codAtom = -1;
    if (strcmp(";", yytext) == 0) codAtom = 24;
    if (strcmp(",", yytext) == 0) codAtom = 25;
    if (strcmp("(", yytext) == 0) codAtom = 26;
    if (strcmp(")", yytext) == 0) codAtom = 27;
    if (strcmp("{", yytext) == 0) codAtom = 28;
    if (strcmp("}", yytext) == 0) codAtom = 29;
    if (strcmp(">>", yytext) == 0) codAtom = 30;
    if (strcmp("<<", yytext) == 0) codAtom = 31;
    addToFIP(yytext, codAtom, -1);
}

[\n] {
    currentLine++;
}

[ \t\n]+ ;

. {
    printf("Error on line %d. Unrecognized character: %s\n", currentLine, yytext);
    break;
}

%% 

int main(argc, argv)
int argc;
char **argv;
{
    ++argv, --argc;
    if (argc > 0)
        yyin = fopen(argv[0], "r");
    else
        yyin = stdin;
    yylex();
    printTS();
    resetSymbolCodesInFIP();
    printFIP();
}
