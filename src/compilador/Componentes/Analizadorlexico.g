header
{
/**
 * Lexer for the Mile language.
 * 
 *
 */

package compilador.Componentes;
}

class AnalizadorLexico extends Lexer;
options{
    charVocabulary = '\3'..'\377';
    exportVocab=AnalizadorLexicoVocab;
    testLiterals=false;
    k=2; // lookahead
    defaultErrorHandler=false;
}

tokens
{
	TIPO_ENTERO_PR	="entero";
	TIPO_REAL_PR	="real";
	TIPO_BOOLEANO	="booleano";
	CONST_PR		="constante";	
	
	INICIO			="inicio";
	DEL				="del";
	PROGRAMA		="programa";
	PRINCIPAL		="principal";
	
	AVANZAR_PR		="avanzar";
	MANTENER_PR 	="mantener";
	RETROCEDER_PR	="retroceder";
	APAGAR_PR		="apagar";
	IMPRIMIR_PR		="imprimir";
	GIRAR_PR		="girar";
	SEGUNDOS_PR		="segundos";
	ADELANTE_PR		="adelante";
	ATRAS_PR		="atras";
	SINCRONIZADOS_PR	="sincronizados";//Nueva incorporacion para sincronizar los motores
		
	MOTOR_APR		="MOTOR_A";
	MOTOR_BPR		="MOTOR_B";
	MOTOR_CPR		="MOTOR_C";
	
	SI 				="si";
	ENTONCES		="entonces";
	SINO			="sino";
	FIN				="fin";
	
	Y_RW			="Y";
	O_RW			="O";
	
	ITERAR			="iterar";
	VECES			="veces";
		
	MIENTRAS		="mientras";
	
	REPETIR 		="repetir";
	DESDE			="desde";
	HASTA 			="hasta";	
	MODIFICANDO		="modificando";
	
	REAL_LITERAL;
	ENTERO_LITERAL;
		
}

//Puntuacion
//====================================
//PUNTOYCOMA 	:	';';
//COMA 			:	',';
//PUNTO 			:	'.';
//DOSPUNTOS 	:	':';
NEGACION		:	'!';
GUION_BAJO	:	'_';

//Parentesis
//====================================
//REDONDO_IZQ		:	'(';
//REDONDO_DER		:	')';

//Operadores Matematicos y Basicos
//====================================
SUMA			:	'+';
GUION			:	'-';
MULTIPLICACION	:	'*';
DIVISION		:	'/';
INCREMENTAR		: SUMA SUMA;
DECREMENTAR		: GUION GUION;

//Operadores Logicos
//====================================
IGUAL_QUE		: 	'=''=';
DISTINTO_QUE	:	'!''=';
ASIGN			:	'=';

//Operadores Relacional
//====================================
MENOR_ESTRICTO	:	'<';
MAYOR_ESTRICTO	:	'>';
MENOR_IGUAL		:	'<''=';
MAYOR_IGUAL		:	'>''=';

//===================================================
// Digits

protected DIGITO : '0'..'9';
   
//====================================   
//Numeros 

NUM_LITERAL : ((DIGITO)+ "." (DIGITO)+ ) =>
(DIGITO)+ '.' ( DIGITO )+ { $setType (REAL_LITERAL); }
| (DIGITO)+ { $setType (ENTERO_LITERAL); }
;

//===================================================
// LETRA

protected LETRA
      : 'a'..'z'
      | 'A'..'Z'
      ;

//===================================================
// Identificadores, no puede tener como nombre una palabra reservada

IDENTIFICADOR options {testLiterals=true;} // Chequea si es una palabra reservada
      : (LETRA) (LETRA|DIGITO|GUION_BAJO)*
      ;
      
//====================================================
//String literals
COMILLAS : '"';
CADENA_LITERAL : COMILLAS !
                  ( ~('"'|'\n'|'\r') )*
                  COMILLAS !
                ;                   
//==================================================
// Espacios Blancos. No seran considerados para el parser

WS :    ((' ' | '\t' | '\f') | ('\n'| '\r' ) { newline(); }) { _ttype = Token.SKIP;  };

COMENTARIO_LINEA: ('/''/' ( ~('\n'))*) { _ttype = Token.SKIP;  };

COMENTARIO_BLOQUE: "/*" ('*' ~('/') | ~ ('*') )* "*/" {$setType(Token.SKIP);};