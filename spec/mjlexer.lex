package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;

%%

%{

	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}

%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r" 	{ }
"\n"	{ }
"\f" 	{ }

//Keywords

"program"   { return new_symbol(sym.PROG, yytext()); }
"break"   	{ return new_symbol(sym.BREAK, yytext()); }
"class"	   	{ return new_symbol(sym.CLASS, yytext()); }
"else"		{ return new_symbol(sym.ELSE, yytext()); }
"const"   	{ return new_symbol(sym.CONST, yytext()); }
"if"   		{ return new_symbol(sym.IF, yytext()); }
"while"   	{ return new_symbol(sym.WHILE, yytext()); }
"new"   	{ return new_symbol(sym.NEW, yytext()); }
"print"   	{ return new_symbol(sym.PRINT, yytext()); }
"read"   	{ return new_symbol(sym.READ, yytext()); }
"return"   	{ return new_symbol(sym.RETURN, yytext()); }
"void"   	{ return new_symbol(sym.VOID, yytext()); }
"extends"  	{ return new_symbol(sym.EXTENDS, yytext()); }
"continue" 	{ return new_symbol(sym.CONTINUE, yytext()); }
"foreach"   { return new_symbol(sym.FOREACH, yytext()); }
("true"|"false") 		   {return new_symbol(sym.BOOL, yytext());}
"findAny"   { return new_symbol(sym.FINDANY, yytext()); }

"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"=" 		{ return new_symbol(sym.ASSIGN, yytext()); }
";" 		{ return new_symbol(sym.SEMI, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LPAREN, yytext()); }
")" 		{ return new_symbol(sym.RPAREN, yytext()); }
"{" 		{ return new_symbol(sym.LBRACE, yytext()); }
"}"			{ return new_symbol(sym.RBRACE, yytext()); }
"["			{ return new_symbol(sym.LSQBRACE, yytext()); }
"]"			{ return new_symbol(sym.RSQBRACE, yytext()); }
"-"			{ return new_symbol(sym.MINUS, yytext()); }
"*"			{ return new_symbol(sym.MUL, yytext()); }
"/"			{ return new_symbol(sym.DIV, yytext()); }
"%"			{ return new_symbol(sym.MOD, yytext()); }
"=>"		{ return new_symbol(sym.ARROW, yytext()); }


//Operatori
"++"		{ return new_symbol(sym.INC, yytext()); }
"--"		{ return new_symbol(sym.DEC, yytext()); }
"||"		{ return new_symbol(sym.OR, yytext()); }
"&&"		{ return new_symbol(sym.AND, yytext()); }

"=="		{ return new_symbol(sym.EQUAL, yytext()); }
"!="		{ return new_symbol(sym.DIFF, yytext()); }
">"			{ return new_symbol(sym.GRT, yytext()); }
">="		{ return new_symbol(sym.GRTEQ, yytext()); }
"<"			{ return new_symbol(sym.LSS, yytext()); }
"<="		{ return new_symbol(sym.LSSEQ, yytext()); }


<YYINITIAL> "//" 		     { yybegin(COMMENT); }
<COMMENT> .      { yybegin(COMMENT); }
<COMMENT> "\r\n" { yybegin(YYINITIAL); }

"'"[ -~]"'" 			   {return new_symbol(sym.CHAR, new Character(yytext().charAt(1)) ); }
[0-9]+  { return new_symbol(sym.NUMBER, new Integer (yytext())); }
([a-z]|[A-Z])[a-zA-Z0-9_]*	{return new_symbol (sym.IDENT, yytext()); }
"." 						{ return new_symbol(sym.DOT, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1)); }






