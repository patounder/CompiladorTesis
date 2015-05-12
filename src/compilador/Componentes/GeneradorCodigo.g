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

  
class GeneradorCodigo extends TreeParser;
options
{
    importVocab=AnalizadorSintacticoVocab;
    buildAST = false;
    defaultErrorHandler=false;
}

{
private Escritor   cG = new Escritor();
private AST auxAst ;
private ArrayList<AST> listAst = new ArrayList<AST>();
int nTab=1;
private boolean sinc = false;//Paara indicar si se señalo sincronizar la salida o motores 

}


///=======================================================================
//Regla Base

programa : #(PROGRAMA_ declaraciones  {cG.addMain();} cuerpo {cG.end();});

//=======================================================================
// Declaraciones. Area donde se podra definir variables, constantes (en un futuro se podra definir funciones)

declaraciones	:	#(DECLARACIONES	(dec_var|dec_const)* );

dec_var			:	#(VARIABLE td:tipo_dato i:IDENTIFICADOR {cG.addVar(td.getFirstChild(),i);cG.printPtoYComa();cG.println();} );

dec_const 		: 	#(CONSTANTE td:tipo_dato i:IDENTIFICADOR l:literal {cG.addConst(td.getFirstChild(),i,l.getFirstChild());cG.println();});

tipo_dato		:	#(TIPO_DATO (TIPO_ENTERO_PR|TIPO_REAL_PR));

literal 		:	#(VALOR (REAL_LITERAL|ENTERO_LITERAL));

//=======================================================================
//Cuerpo. En NXC corresponde a task main

cuerpo 		: #(CUERPO ({cG.printTab(nTab);}(movimientos|assign{cG.println();}|imprimir|dec_var|si|repetitivas))*);//Puse el pto y coma de la asign considerando el for

//================ Instrucciones de  movimiento (relacionadas con motores)

movimientos : 	(avanzar|mantener|apagar|retroceder|girar);

avanzar		: 	#(AVANZAR_PR {listAst.clear();} (out1:salida {listAst.add(out1);})+ (SINCRONIZADOS_PR{sinc=true;})?
				(l:literal|i:IDENTIFICADOR) {if(l!=null)auxAst=l.getFirstChild(); else auxAst=i;}
				{cG.addAvanzar(sinc);cG.printOut(listAst);cG.printVelocidad(sinc);cG.printRedDer();cG.printPtoYComa();cG.println();
				cG.printTab(nTab);cG.addMantener();cG.printNum(auxAst,false,true);cG.printRedDer();cG.printPtoYComa();cG.println();});

//Mantener aun no esta implementada completamente
mantener	:	#(MANTENER_PR {cG.addMantener();}(e:ENTERO_LITERAL{cG.printNum(e,true,false);}|i:IDENTIFICADOR{cG.printId(i);}) 
				{cG.printRedDer();cG.printPtoYComa();cG.println();});

retroceder	:	#(RETROCEDER_PR {listAst.clear();sinc=false;}(out1:salida {listAst.add(out1);})+ (SINCRONIZADOS_PR{sinc=true;})?
				(l:literal|i:IDENTIFICADOR) {if(l!=null)auxAst=l.getFirstChild(); else auxAst=i;}{cG.addRetrocede(sinc);cG.printOut(listAst);
				cG.printVelocidad(sinc);cG.printRedDer();cG.printPtoYComa();cG.println();cG.printTab(nTab);cG.addMantener();cG.printNum(auxAst,false,true);
				cG.printRedDer();cG.printPtoYComa();cG.println();});

apagar		:	#(APAGAR_PR {listAst.clear();} (out1:salida {listAst.add(out1);})+ 
			{cG.addApagar();cG.printOut(listAst);cG.printRedDer();cG.printPtoYComa();cG.println();});

girar 		: #(GIRAR_PR (fwd:ADELANTE_PR|rev:ATRAS_PR) out:salida {cG.printGiro(fwd,rev,out);cG.println();});

salida		:	(MOTOR_APR|MOTOR_BPR|MOTOR_CPR);

assign		:	#(ASIGNACION i:IDENTIFICADOR{cG.printId(i);cG.printAsign();} 
				(i2:IDENTIFICADOR{cG.printId(i2);}|l:literal{cG.printNum(l.getFirstChild(),false,false);})												
				(op_mat (i3:IDENTIFICADOR{cG.printId(i3);}|l1:literal{cG.printNum(l1.getFirstChild(),false,false);}))* {cG.printPtoYComa();});

op_mat		:	(SUMA{cG.printSuma();}|GUION{cG.printGuion();}|MULTIPLICACION{cG.printMulti();}|DIVISION{cG.printDivision();});

modificando: #(MODIFICANDO incremento);

incremento: #(id:IDENTIFICADOR {cG.printId(id);} (INCREMENTAR{cG.printIncrementar();}|DECREMENTAR{cG.printDecrementar();}));  //Falta el identificador

imprimir	:	#(IMPRIMIR_PR (text:CADENA_LITERAL{cG.printImprimir(text,"\"");}|i:IDENTIFICADOR{cG.printImprimir(i);}|l:literal{cG.printImprimir(l.getFirstChild());} ){ cG.printPtoYComa();cG.println();});

//================ Condicionales

si 			:	#(SI{cG.println();cG.printTab(nTab);cG.printIniSi();} condicion {cG.printRedDer();cG.printLlaveIzq();cG.println();}entonces_hacer {cG.printLlaveDer();} (sino)?);

entonces_hacer: #(ENTONCES {nTab++;} cuerpo {nTab--;cG.printTab(nTab);});

sino		:	#(SINO {cG.printTab(nTab);cG.printIniSino();nTab++;cG.println();} cuerpo {nTab--;cG.printTab(nTab);cG.printLlaveDer();cG.println();});

condicion 	: #(CONDICION expr);

//================ Expresiones

expr 
  : #(O_RW	expr {cG.printOr();} expr ) 
  | #(Y_RW	expr {cG.printAnd();} expr ) 
  | #(IGUAL_QUE	expr {cG.printAsign();cG.printAsign();} expr ) 
  | #(DISTINTO_QUE 	expr {cG.printNegacion();cG.printAsign();} expr )  
  | #(MENOR_IGUAL	expr {cG.printMenorEst();cG.printAsign();} expr) 
  | #(MAYOR_IGUAL	expr	{cG.printMayorEst();cG.printAsign();}	expr) 
  | #(MAYOR_ESTRICTO		check1:expr {cG.printMayorEst();} check2:expr  ) 
  | #(MENOR_ESTRICTO		expr {cG.printMenorEst();} expr ) 
  | #(SUMA	expr 	{ cG.printSuma();}	expr) 
  | #(GUION	expr	{ cG.printGuion();}	expr) 
  | #(MULTIPLICACION	expr	{cG.printMulti();}	expr) 
  | #(DIVISION expr	{cG.printDivision();}	expr) 
  | #(UN_MINUS	expr)
  | id:IDENTIFICADOR  {cG.printId(id);}
  | l:literal {cG.printNum(l.getFirstChild(),false,false);}
  ;
//================ Ciclos
repetitivas : (repetir|mientras);

//==iterar
repetir : #(REPETIR {cG.println();cG.printTab(nTab);cG.printRepeat();}
			(l:literal{cG.printNum(l.getFirstChild(),false,false);} |id:IDENTIFICADOR  {cG.printId(id);} )
			{cG.printEndRepeat();} {nTab++;} cuerpo {nTab--;cG.printTab(nTab);cG.printLlaveDer();});

//==mientras			
mientras : #(MIENTRAS {cG.println();cG.printTab(nTab);cG.printIniMientras();} condicion {cG.printRedDer();cG.printLlaveIzq();} entonces_hacer {cG.printLlaveDer();} );

//==repetir
iterar : #(ITERAR {cG.println();cG.printTab(nTab);cG.printIniFor();} desde hasta {cG.printPtoYComa();} modificando {cG.printRedDer();cG.printLlaveIzq();cG.println();} entonces_hacer  {cG.printLlaveDer();} );

desde : #(DESDE assign);

hasta : #(HASTA condicion);
