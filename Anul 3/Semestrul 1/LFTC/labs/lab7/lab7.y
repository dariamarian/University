%{
    #include <string.h>
    #include <stdio.h>
    #include <stdlib.h>

    extern int yylex();
    extern int yyparse();
    extern FILE* yyin;
    extern int currentLine;
    void yyerror(const char *s);
%}
%token INT
%token FLOAT
%token DOUBLE
%token STRUCT
%token IF
%token ELSE
%token WHILE
%token FOR
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
%token GT LT NE E ELT EGT
%token PLUS MINUS MUL DIV
%left PLUS MINUS
%left MUL DIV
%nonassoc LOWER_THAN_ELSE
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
    | if_instr
    | loop_instr
    | input_instr SEMICOLON
    | output_instr SEMICOLON
    | return_instr SEMICOLON
        ;

assign: ID ASSIGN expr_arit
        ;

expr_arit: expr_arit PLUS expr_arit
         | expr_arit MINUS expr_arit
         | expr_arit MUL expr_arit
         | expr_arit DIV expr_arit
         | ID
         | CONST
         ;

decl: type ID
        ;

type: INT
    | FLOAT
    | DOUBLE
    | STRUCT
  	;

if_instr: IF LPR cond RPR compound_statement %prec LOWER_THAN_ELSE
        | IF LPR cond RPR compound_statement ELSE compound_statement
        ;

cond: expr_arit GT expr_arit
     | expr_arit LT expr_arit
     | expr_arit NE expr_arit
     | expr_arit E expr_arit
     | expr_arit ELT expr_arit
     | expr_arit EGT expr_arit
     ;

loop_instr: WHILE LPR cond RPR compound_statement
          | FOR LPR ASSIGN SEMICOLON cond SEMICOLON ASSIGN RPR compound_statement
          ;

input_instr: CIN READ ID
        ;

output_instr: COUT WRITE expr_arit
        ;

return_instr: RETURN expr_arit
        ;
                    
%%

int main(int argc, char* argv[]) {
    ++argv, --argc;
    
    // sets the input for flex file
    if (argc > 0) 
        yyin = fopen(argv[0], "r"); 
    else 
        yyin = stdin; 
    
    //read each line from the input file and process it
    while (!feof(yyin)) {
        yyparse();
    }
    printf("The file is sintactically correct!\n");
    return 0;
}

void yyerror(const char *s) {
    fprintf(stderr, "Error at line %d: %s\n", currentLine, s);
    exit(1);
}