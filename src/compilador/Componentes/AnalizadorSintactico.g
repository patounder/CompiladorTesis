header
{
/**
 * Parser for the Mile language.
 * 
 * @author Ricardo Soto
 * @since 1.5
 */

package compilador.Componentes;

}
//======================================================
// Object allowing syntactic analysis

class AnalizadorSintactico extends Parser;
options
{
    k = 2;
    importVocab = AnalizadorLexicoVocab;
    exportVocab = AnalizadorSintacticoVocab;
    buildAST = true;
    defaultErrorHandler=false;
}


tokens
{
//=======================================================
// Tokens Imaginarios

    PROGRAMA_; //OK
    DECLARACIONES;
    CUERPO;
    CONSTANTE;
    
    VARIABLE;
    TIPO_DATO;
    ID;
    VALOR;
    
    AVANZAR;
    MANTENER;
    RETROCEDER;
    APAGAR;
    ASIGNACION;
    IMPRIMIR;
        
    SI_;
    CONDICION;
    SINO_;
    UN_MINUS;
    
    REPETIR_;
    ITERAR_;
}

//=======================================================================
//Regla Principal

programa :	declaraciones INICIO! DEL! PROGRAMA! cuerpo FIN! DEL! PROGRAMA!
			{## = #( #[PROGRAMA_, "programa"] ,##);};
		
//======================================================================
// Declaraciones. Area donde se podra definir variables, constantes (en un futuro se podra definir funciones)

declaraciones : (dec_var|dec_const)*
				{## = #( #[DECLARACIONES, "declaraciones"] ,##);};

dec_var	:	tipo_dato IDENTIFICADOR 
         		{## = #( #[VARIABLE, "variable"] ,##);};

dec_const : CONST_PR! tipo_dato IDENTIFICADOR literal
			{## = #( #[CONSTANTE, "constante"] ,##);};

tipo_dato	:	(TIPO_ENTERO_PR|TIPO_REAL_PR)
				{## = #( #[TIPO_DATO, "tipo_dato"] ,##);};

literal 	:	(REAL_LITERAL|ENTERO_LITERAL)
			{## = #( #[VALOR, "valor"] ,##);};
//======================================================================
//Cuerpo. En NXC corresponde a task main

cuerpo	 	:	(movimientos|assign|imprimir|dec_var|si|repetitivas)*
				{## = #( #[CUERPO, "cuerpo"] ,##);};

//================ Instrucciones de  movimiento (relacionadas con motores)

movimientos : (avanzar|mantener|retroceder|apagar|girar);

avanzar		: 	AVANZAR_PR^ (salida)+ (SINCRONIZADOS_PR)? (literal|IDENTIFICADOR) SEGUNDOS_PR!;
//Mantener aun no esta implementada completamente
mantener	:	MANTENER_PR^ (ENTERO_LITERAL|IDENTIFICADOR) SEGUNDOS_PR!;

retroceder	:	RETROCEDER_PR^ (salida)+ (SINCRONIZADOS_PR)? (literal|IDENTIFICADOR) SEGUNDOS_PR!;

apagar		:	APAGAR_PR^ (salida)+;

girar		: 	GIRAR_PR^ (ADELANTE_PR|ATRAS_PR) salida;

salida 		:	(MOTOR_APR|MOTOR_BPR|MOTOR_CPR);

assign		:	IDENTIFICADOR ASIGN! (IDENTIFICADOR|literal)(op_mat (IDENTIFICADOR|literal))*
				{## = #( #[ASIGNACION, "asignacion"] ,##);};

op_mat		:	(SUMA|GUION|MULTIPLICACION|DIVISION);

imprimir	:	IMPRIMIR_PR^ (CADENA_LITERAL|IDENTIFICADOR|literal);

//================ Condicionales

si 			:	SI^ condicion entonces_hacer (sino)? FIN! SI!;

entonces_hacer: 	ENTONCES^ cuerpo;

sino		:	 SINO^ cuerpo;

condicion 	: expr {## = #( #[CONDICION, "condicion"] ,##);};

//================ Expresiones

expr		:  expAND  (O_RW^ expAND)*;

expAND		:  expCons (Y_RW^ expCons)*;

expCons		:  expSum  ((IGUAL_QUE^| DISTINTO_QUE^| MENOR_IGUAL^| MAYOR_IGUAL^| MENOR_ESTRICTO^| MAYOR_ESTRICTO^) expSum)* ; 

expSum      :  expProduct  ((SUMA^| GUION^) expProduct)* ; 

expProduct  :  expMinus ((MULTIPLICACION^ | DIVISION^) expMinus)* ;  

expMinus    : (GUION! expNot){ ##=#(#[UN_MINUS, "UN_MINUS"], ##) ;} | ((SUMA!)? expNot);

expNot		: (NEGACION^)? (IDENTIFICADOR|literal);

//================ Ciclos
repetitivas : (repetir|mientras);

//==iterar
repetir : REPETIR^ (literal|IDENTIFICADOR) VECES! cuerpo FIN! REPETIR!;

//==mientras			
mientras : MIENTRAS^ condicion entonces_hacer FIN! MIENTRAS!;

//***********repetir //FOR que no estara por ahora considerado****************
iterar : ITERAR^ desde hasta modificando entonces_hacer FIN! ITERAR!;

desde : DESDE^ assign;

hasta : HASTA^ condicion; 

modificando: MODIFICANDO^ incremento;

incremento: IDENTIFICADOR^ (INCREMENTAR|DECREMENTAR);

//================ Funciones
