header
{
/**
 * Semantic analysis for the Mile Language.
 * 
 * @author Ricardo Soto
 * @since 1.5
 */
package compilador.Componentes;
import java.util.ArrayList;
}

  
class AnalizadorSemantico extends TreeParser;
options
{
    importVocab=AnalizadorSintacticoVocab;
    buildAST = false;
    defaultErrorHandler=false;
}

{
/**
*	Objeto que se utiliza un objeto que ayudara a realizar el analisis semantico
*/
private InspectorSemantico   sI = new InspectorSemantico();


/**
*	Lista que se utiliza para almacenar temporalmente a variables de una asignacion
*/
private ArrayList<AST> listTempOperandos = new ArrayList<AST>();
}

//=======================================================================
//Regla Base

programa : #(PROGRAMA_ declaraciones  cuerpo);

//=======================================================================
// Declaraciones. Area donde se podra definir variables, constantes (en un futuro se podra definir funciones)

declaraciones	:	#(DECLARACIONES	(dec_var|dec_const)* );

dec_var			:	#(VARIABLE td:tipo_dato i:IDENTIFICADOR {sI.addOperando(td.getFirstChild(),i,false,"");});
												
dec_const 		: 	#(CONSTANTE td:tipo_dato i:IDENTIFICADOR l:literal {sI.addOperando(td.getFirstChild(),i,true,l.getFirstChild().toString());});
			
tipo_dato		:	#(TIPO_DATO (TIPO_ENTERO_PR|TIPO_REAL_PR));

literal 		:	#(VALOR (REAL_LITERAL|ENTERO_LITERAL));

//=======================================================================
//Cuerpo. En NXC corresponde a task main

cuerpo 		: #(CUERPO	(movimientos|assign|imprimir|dec_var|si|repetitivas)*);

//================ Instrucciones de  movimiento (relacionadas con motores)

movimientos : 	(avanzar|mantener|retroceder|apagar|girar);

avanzar		: 	#(AVANZAR_PR {sI.clearListaNombreSalidas();} (out1:salida {sI.addAndCheckSalidas(out1);})+ 
				(SINCRONIZADOS_PR)? (literal|i:IDENTIFICADOR{sI.checkOperando(i);}) );

//Mantener aun no esta implementada completamente
mantener	:	#(MANTENER_PR (ENTERO_LITERAL|i:IDENTIFICADOR{sI.checkOperando(i);}) );

retroceder	:	#(RETROCEDER_PR {sI.clearListaNombreSalidas();} (out1:salida {sI.addAndCheckSalidas(out1);})+ 
				(SINCRONIZADOS_PR)? (literal|i:IDENTIFICADOR{sI.checkOperando(i);}) ) ;

apagar		:	#(APAGAR_PR {sI.clearListaNombreSalidas();} (out1:salida {sI.addAndCheckSalidas(out1);})+ );

girar 		: #(GIRAR_PR (ADELANTE_PR|ATRAS_PR) salida );

salida		:	(MOTOR_APR|MOTOR_BPR|MOTOR_CPR);

assign		:	#(ASIGNACION {listTempOperandos.clear();} i:IDENTIFICADOR{sI.checkOperando(i);
											listTempOperandos.add(i);
											sI.constanteNoIzquerda(i);} 
				(i2:IDENTIFICADOR{sI.checkOperando(i2);listTempOperandos.add(i2);
												sI.checkTipos(listTempOperandos);}|l:literal{sI.checkLiteralEnAsign(listTempOperandos,l.getFirstChild());})												
				(op_mat (i3:IDENTIFICADOR{sI.checkOperando(i3);listTempOperandos.add(i3);
											sI.checkTipos(listTempOperandos);}|l1:literal{sI.checkLiteralEnAsign(listTempOperandos,l1.getFirstChild());}))* );

op_mat		:	(SUMA|GUION|MULTIPLICACION|DIVISION);

imprimir	:	#(IMPRIMIR_PR (CADENA_LITERAL|i:IDENTIFICADOR{sI.checkOperando(i);}|literal ) );

//================ Condicionales

si 			:	#(SI condicion entonces_hacer (sino)?);

entonces_hacer: #(ENTONCES cuerpo);

sino		:	#(SINO cuerpo);

condicion 	: #(CONDICION {sI.clearOperatorTable();} expr);

//================ Expresiones

expr 
  : #(op:	O_RW          	expr expr{ sI.addOperator(op);} ) 
  | #(op1: 	Y_RW          	expr expr{ sI.addOperator(op1);} ) 
  | #(op2: 	IGUAL_QUE           expr expr{ sI.addOperator(op2);} ) 
  | #(op4: 	DISTINTO_QUE        expr expr{ sI.addOperator(op4);} ) 
  | #(op5: 	MENOR_IGUAL     expr expr{ sI.addOperator(op5);}) 
  | #(op6: 	MAYOR_IGUAL              check1:expr check2:expr {listTempOperandos.clear();sI.addOperator(op6);})
  | #(op7: 	MAYOR_ESTRICTO              expr expr {sI.addOperator(op7);})
  | #(op8: 	MENOR_ESTRICTO              expr expr { sI.addOperator(op8);}) 
  | #(op9: 	SUMA            expr expr{ sI.addOperator(op9);}) 
  | #(op10: GUION             expr expr{ sI.addOperator(op10);}) 
  | #(op11: MULTIPLICACION            expr expr{ sI.addOperator(op11);}) 
  | #(op12: DIVISION           expr expr{ sI.addOperator(op12);}) 
  | #(op13:	UN_MINUS             expr{ sI.addOperator(op13);})
  | id:IDENTIFICADOR  {sI.checkOperando(id);}
  | literal
  ;

//================ Ciclos
repetitivas : (repetir|mientras);

//==iterar
repetir : #(REPETIR (literal|id:IDENTIFICADOR  {sI.checkOperando(id);} ) cuerpo);

//==mientras			
mientras : #(MIENTRAS condicion entonces_hacer);

//==repetir
iterar : #(ITERAR desde hasta modificando entonces_hacer );

desde : #(DESDE assign {;});

hasta : #(HASTA condicion); 

modificando: #(MODIFICANDO incremento);

incremento: #(id:IDENTIFICADOR  {sI.checkOperando(id);} (INCREMENTAR|DECREMENTAR));  