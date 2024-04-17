%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>
	#include <stdbool.h>
    #include <ctype.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int currentLine;
    void yyerror(const char *s);
	
	FILE* outputFile;
    char* filename;

    #define MAX 1000
    char declarations[MAX][MAX], sourceCode[MAX][MAX], imports[MAX][MAX];
    int lenDeclarations = 0, lenSourceCode = 0, lenImports = 0;
    char variablesRead[MAX][MAX];
    int n = 0, nr = 0;
    char expressions[MAX][MAX];
    int lenExpressions = 0;

    bool found(char col[][MAX], int n, char* var);
    void parseExpression(char* element);
    void printDeclarationSegment();
    void printCodeSegment();
    void printImports();
%}

%union {
	char * value;
}

%token INT
%token CIN
%token COUT
%token RETURN
%token MAIN
%token ASSIGN
%token SEMICOLON
%token LPR
%token RPR
%token LAC
%token RAC
%token READ
%token WRITE
%token ID
%token CONST
%token PLUS 
%token MINUS 
%token MUL 
%token DIV 
%left PLUS MINUS
%left MUL DIV
%%     

program: function
        ;

function: header compound_statement
        ;

header: INT MAIN LPR RPR
        ;

compound_statement: LAC instr_comp RAC
        ;

instr_comp: instr instr_comp
        | instr
        ;

instr: assign SEMICOLON
    | decl SEMICOLON
    | input_instr SEMICOLON
    | output_instr SEMICOLON
    | return_instr SEMICOLON
        ;

assign: ID ASSIGN expr_arit
				{
					char tmp[MAX];
					strcpy(tmp, $<value>3);
					char* token = strtok(tmp, " ");
					while (token != NULL) {
						strcpy(expressions[lenExpressions], token);
						lenExpressions++;
						token = strtok(NULL, " ");
					}
					parseExpression($<value>1);
				}
        ;


expr_arit: term PLUS term
				{
					char tmp[MAX];
					strcpy(tmp, $<value>1);
					strcat(tmp, " ");
					strcat(tmp, $<value>2);
					strcat(tmp, " ");
					strcat(tmp, $<value>3);
					$<value>$ = strdup(tmp);
				}
		 | term MINUS term
				{
					char tmp[MAX];
					strcpy(tmp, $<value>1);
					strcat(tmp, " ");
					strcat(tmp, $<value>2);
					strcat(tmp, " ");
					strcat(tmp, $<value>3);
					$<value>$ = strdup(tmp);
				}
		 | term MUL term
				{
					char tmp[MAX];
					strcpy(tmp, $<value>1);
					strcat(tmp, " ");
					strcat(tmp, $<value>2);
					strcat(tmp, " ");
					strcat(tmp, $<value>3);
					$<value>$ = strdup(tmp);
				}
		 | term DIV term
				{
					char tmp[MAX];
					strcpy(tmp, $<value>1);
					strcat(tmp, " ");
					strcat(tmp, $<value>2);
					strcat(tmp, " ");
					strcat(tmp, $<value>3);
					$<value>$ = strdup(tmp);
				}
         ;
		 
term:
	ID
	| CONST
	;

decl: INT ID
				{
					char tmp[100];
					strcpy(tmp, " ");
					strcat(tmp, $<value>2);
					if (!found(declarations, lenDeclarations, tmp)) {
						strcpy(declarations[lenDeclarations], strcat(tmp, " times 4 db 0"));
						lenDeclarations++;
					}
				}
        ;

input_instr: CIN READ ID
				{
					strcpy(sourceCode[lenSourceCode], "push dword ");
					strcat(sourceCode[lenSourceCode++], $<value>3);
					strcat(sourceCode[lenSourceCode++], "push dword format");
					strcat(sourceCode[lenSourceCode++], "call [scanf]");
					strcat(sourceCode[lenSourceCode++], "add ESP, 4 * 2\n");

					if (!found(imports, lenImports, "scanf")) {
						strcpy(imports[lenImports], "scanf");
						lenImports++;
					}
					if (!found(declarations, lenDeclarations, "format")) {
						strcpy(declarations[lenDeclarations], " format db \"%d\", 0");
					}
				}
        ;

output_instr: COUT WRITE expr_arit
				{
					if (!found(imports, lenImports, "printf")) {
						strcpy(imports[lenImports], "printf");
						lenImports++;
					}
					if (!found(declarations, lenDeclarations, "format")) {
						strcpy(declarations[lenDeclarations], " format db \"%d\", 0");
						lenDeclarations++;
					}
					strcpy(sourceCode[lenSourceCode], "push dword [");
					strcat(sourceCode[lenSourceCode], $<value>3);
					strcat(sourceCode[lenSourceCode++], "]");
					strcpy(sourceCode[lenSourceCode++], "push dword format");
					strcpy(sourceCode[lenSourceCode++], "call [printf]");
					strcpy(sourceCode[lenSourceCode++], "add ESP, 4 * 1\n");
				}
        ;

return_instr: RETURN expr_arit
        ;
                    
%%

int main(int argc, char* argv[]) {
    FILE* f = NULL;
    	if (argc > 1) { 
		        printf("Input file: %s\n", argv[1]);
    		f = fopen(argv[1], "r");
        }
	
	if(!f) {
		perror("Could not open file! Switching to console...\n");
		yyin = stdin;
	}
    	else {
		yyin = f;
	}
    
    	strcpy(imports[lenImports++], "exit"); 
     	
	while(!feof(yyin)) {
		yyparse();
	}

	printf("The file is sintactically correct!");
    
    outputFile = fopen("asmCode.asm", "w+");
    
    fprintf(outputFile, "bits 32\nglobal start\n\n");
    
    printImports();
    
    fprintf(outputFile, "segment data use32 class=data\n");
    printDeclarationSegment();
    
    fprintf(outputFile, "\nsegment code use32 class=code\n\tstart:\n");
    strcpy(sourceCode[lenSourceCode++], "push dword 0");
    strcpy(sourceCode[lenSourceCode++], "call [exit]");
    printCodeSegment();
	return 0;
}

void yyerror(const char *s) {
    fprintf(stderr, "Error at line %d: %s\n", currentLine, s);
    exit(1);
}

void parseExpression(char*  element) {
	if(isdigit(expressions[0][0])){
		strcpy(sourceCode[lenSourceCode], "mov AL, ");
		strcat(sourceCode[lenSourceCode++], expressions[0]);
	}
	else{
		strcpy(sourceCode[lenSourceCode], "mov AL, [");
		strcat(sourceCode[lenSourceCode], expressions[0]);
		strcat(sourceCode[lenSourceCode++], "]");
	}
	int i;
	for (i = 1; i < lenExpressions - 1; i+=2) {
		if (strcmp(expressions[i], "*") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "mov DL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
				strcpy(sourceCode[lenSourceCode++], "mul DL");
			} else {
				strcpy(sourceCode[lenSourceCode], "mul byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "-") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "sub AL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
			} else {
				strcpy(sourceCode[lenSourceCode], "sub AL, byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "/") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode++], "mov AH, 0");
				strcpy(sourceCode[lenSourceCode], "mov DL, ");
				strcat(sourceCode[lenSourceCode++], expressions[i + 1]);
				strcpy(sourceCode[lenSourceCode++], "div DL");
			} else {
				strcpy(sourceCode[lenSourceCode], "div byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
		else if (strcmp(expressions[i], "+") == 0) {
			if (isdigit(expressions[i + 1][0])) {
				strcpy(sourceCode[lenSourceCode], "add AL, ");
				strcpy(sourceCode[lenSourceCode++], expressions[i + 1]);
			} else {
				strcpy(sourceCode[lenSourceCode], "add AL, byte [");
				strcat(sourceCode[lenSourceCode], expressions[i + 1]);
				strcat(sourceCode[lenSourceCode++], "]");
			}
		}
 	}

	strcpy(sourceCode[lenSourceCode], "mov [");
	strcat(sourceCode[lenSourceCode], element);
	strcat(sourceCode[lenSourceCode++], "], AL\n");
	lenExpressions = 0;
}

/* Determine whether a variable is found in the collection. */
bool found(char col[][MAX], int n, char* var) {
	char tmp[MAX];
	strcpy(tmp, var);
	strcat(tmp, " ");
	int i;
	for (i = 0; i < n; i++) {
		if (strstr(col[i], tmp) != NULL) {
			return true;
		}
	}
	return false;
}

/* Write to output file the ASM imports generated. */
void printImports() {
	int i;
	for (i = 0; i < lenImports; i++) {
		fprintf(outputFile, "extern %s\nimport %s msvcrt.dll\n\n", imports[i], imports[i]);
	}
}

/* Write to output file the ASM declaration segment generated. */
void printDeclarationSegment() {
	int i;
	for (i = 0; i < lenDeclarations; i++) {
		fprintf(outputFile, "\t%s\n", declarations[i]);
	}
}

/* Write to output file the ASM code segment generated. */
void printCodeSegment() {
	int i;
	for (i = 0; i < lenSourceCode; i++) {
		fprintf(outputFile, "\t\t%s\n", sourceCode[i]);
	}
}