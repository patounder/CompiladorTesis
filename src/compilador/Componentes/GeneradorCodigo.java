// $ANTLR : "GeneradorCodigo.g" -> "GeneradorCodigo.java"$

/**
 * Semantic analysis for the Mile Language.
 * 
 * @author Ricardo Soto
 * @since 1.5
 */

package compilador.Componentes;
import java.util.ArrayList;


import antlr.TreeParser;
import antlr.Token;
import antlr.collections.AST;
import antlr.RecognitionException;
import antlr.ANTLRException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.collections.impl.BitSet;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;


public class GeneradorCodigo extends antlr.TreeParser       implements GeneradorCodigoTokenTypes
 {

private Escritor   cG = new Escritor();
private AST auxAst ;
private ArrayList<AST> listAst = new ArrayList<AST>();
int nTab=1;
private boolean sinc = false;//Paara indicar si se señalo sincronizar la salida o motores 

public GeneradorCodigo() {
	tokenNames = _tokenNames;
}

	public final void programa(AST _t) throws RecognitionException {
		
		AST programa_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t413 = _t;
		AST tmp1_AST_in = (AST)_t;
		match(_t,PROGRAMA_);
		_t = _t.getFirstChild();
		declaraciones(_t);
		_t = _retTree;
		cG.addMain();
		cuerpo(_t);
		_t = _retTree;
		cG.end();
		_t = __t413;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void declaraciones(AST _t) throws RecognitionException {
		
		AST declaraciones_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t415 = _t;
		AST tmp2_AST_in = (AST)_t;
		match(_t,DECLARACIONES);
		_t = _t.getFirstChild();
		{
		_loop417:
		do {
			if (_t==null) _t=ASTNULL;
			switch ( _t.getType()) {
			case VARIABLE:
			{
				dec_var(_t);
				_t = _retTree;
				break;
			}
			case CONSTANTE:
			{
				dec_const(_t);
				_t = _retTree;
				break;
			}
			default:
			{
				break _loop417;
			}
			}
		} while (true);
		}
		_t = __t415;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void cuerpo(AST _t) throws RecognitionException {
		
		AST cuerpo_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t429 = _t;
		AST tmp3_AST_in = (AST)_t;
		match(_t,CUERPO);
		_t = _t.getFirstChild();
		{
		_loop432:
		do {
			if (_t==null) _t=ASTNULL;
			if ((_tokenSet_0.member(_t.getType()))) {
				cG.printTab(nTab);
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case AVANZAR_PR:
				case MANTENER_PR:
				case RETROCEDER_PR:
				case APAGAR_PR:
				case GIRAR_PR:
				{
					movimientos(_t);
					_t = _retTree;
					break;
				}
				case ASIGNACION:
				{
					assign(_t);
					_t = _retTree;
					cG.println();
					break;
				}
				case IMPRIMIR_PR:
				{
					imprimir(_t);
					_t = _retTree;
					break;
				}
				case VARIABLE:
				{
					dec_var(_t);
					_t = _retTree;
					break;
				}
				case SI:
				{
					si(_t);
					_t = _retTree;
					break;
				}
				case MIENTRAS:
				case REPETIR:
				{
					repetitivas(_t);
					_t = _retTree;
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
			}
			else {
				break _loop432;
			}
			
		} while (true);
		}
		_t = __t429;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void dec_var(AST _t) throws RecognitionException {
		
		AST dec_var_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST td = null;
		AST i = null;
		
		AST __t419 = _t;
		AST tmp4_AST_in = (AST)_t;
		match(_t,VARIABLE);
		_t = _t.getFirstChild();
		td = _t==ASTNULL ? null : (AST)_t;
		tipo_dato(_t);
		_t = _retTree;
		i = (AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getNextSibling();
		cG.addVar(td.getFirstChild(),i);cG.printPtoYComa();cG.println();
		_t = __t419;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void dec_const(AST _t) throws RecognitionException {
		
		AST dec_const_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST td = null;
		AST i = null;
		AST l = null;
		
		AST __t421 = _t;
		AST tmp5_AST_in = (AST)_t;
		match(_t,CONSTANTE);
		_t = _t.getFirstChild();
		td = _t==ASTNULL ? null : (AST)_t;
		tipo_dato(_t);
		_t = _retTree;
		i = (AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getNextSibling();
		l = _t==ASTNULL ? null : (AST)_t;
		literal(_t);
		_t = _retTree;
		cG.addConst(td.getFirstChild(),i,l.getFirstChild());cG.println();
		_t = __t421;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void tipo_dato(AST _t) throws RecognitionException {
		
		AST tipo_dato_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t423 = _t;
		AST tmp6_AST_in = (AST)_t;
		match(_t,TIPO_DATO);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case TIPO_ENTERO_PR:
		{
			AST tmp7_AST_in = (AST)_t;
			match(_t,TIPO_ENTERO_PR);
			_t = _t.getNextSibling();
			break;
		}
		case TIPO_REAL_PR:
		{
			AST tmp8_AST_in = (AST)_t;
			match(_t,TIPO_REAL_PR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t423;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void literal(AST _t) throws RecognitionException {
		
		AST literal_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t426 = _t;
		AST tmp9_AST_in = (AST)_t;
		match(_t,VALOR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case REAL_LITERAL:
		{
			AST tmp10_AST_in = (AST)_t;
			match(_t,REAL_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		case ENTERO_LITERAL:
		{
			AST tmp11_AST_in = (AST)_t;
			match(_t,ENTERO_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t426;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void movimientos(AST _t) throws RecognitionException {
		
		AST movimientos_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case AVANZAR_PR:
		{
			avanzar(_t);
			_t = _retTree;
			break;
		}
		case MANTENER_PR:
		{
			mantener(_t);
			_t = _retTree;
			break;
		}
		case APAGAR_PR:
		{
			apagar(_t);
			_t = _retTree;
			break;
		}
		case RETROCEDER_PR:
		{
			retroceder(_t);
			_t = _retTree;
			break;
		}
		case GIRAR_PR:
		{
			girar(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_retTree = _t;
	}
	
	public final void assign(AST _t) throws RecognitionException {
		
		AST assign_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		AST i2 = null;
		AST l = null;
		AST i3 = null;
		AST l1 = null;
		
		AST __t460 = _t;
		AST tmp12_AST_in = (AST)_t;
		match(_t,ASIGNACION);
		_t = _t.getFirstChild();
		i = (AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getNextSibling();
		cG.printId(i);cG.printAsign();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case IDENTIFICADOR:
		{
			i2 = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			cG.printId(i2);
			break;
		}
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			cG.printNum(l.getFirstChild(),false,false);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		{
		_loop464:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= SUMA && _t.getType() <= DIVISION))) {
				op_mat(_t);
				_t = _retTree;
				{
				if (_t==null) _t=ASTNULL;
				switch ( _t.getType()) {
				case IDENTIFICADOR:
				{
					i3 = (AST)_t;
					match(_t,IDENTIFICADOR);
					_t = _t.getNextSibling();
					cG.printId(i3);
					break;
				}
				case VALOR:
				{
					l1 = _t==ASTNULL ? null : (AST)_t;
					literal(_t);
					_t = _retTree;
					cG.printNum(l1.getFirstChild(),false,false);
					break;
				}
				default:
				{
					throw new NoViableAltException(_t);
				}
				}
				}
			}
			else {
				break _loop464;
			}
			
		} while (true);
		}
		cG.printPtoYComa();
		_t = __t460;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void imprimir(AST _t) throws RecognitionException {
		
		AST imprimir_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST text = null;
		AST i = null;
		AST l = null;
		
		AST __t473 = _t;
		AST tmp13_AST_in = (AST)_t;
		match(_t,IMPRIMIR_PR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case CADENA_LITERAL:
		{
			text = (AST)_t;
			match(_t,CADENA_LITERAL);
			_t = _t.getNextSibling();
			cG.printImprimir(text,"\"");
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			cG.printImprimir(i);
			break;
		}
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			cG.printImprimir(l.getFirstChild());
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		cG.printPtoYComa();cG.println();
		_t = __t473;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void si(AST _t) throws RecognitionException {
		
		AST si_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t476 = _t;
		AST tmp14_AST_in = (AST)_t;
		match(_t,SI);
		_t = _t.getFirstChild();
		cG.println();cG.printTab(nTab);cG.printIniSi();
		condicion(_t);
		_t = _retTree;
		cG.printRedDer();cG.printLlaveIzq();cG.println();
		entonces_hacer(_t);
		_t = _retTree;
		cG.printLlaveDer();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SINO:
		{
			sino(_t);
			_t = _retTree;
			break;
		}
		case 3:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t476;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void repetitivas(AST _t) throws RecognitionException {
		
		AST repetitivas_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case REPETIR:
		{
			repetir(_t);
			_t = _retTree;
			break;
		}
		case MIENTRAS:
		{
			mientras(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_retTree = _t;
	}
	
	public final void avanzar(AST _t) throws RecognitionException {
		
		AST avanzar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST out1 = null;
		AST l = null;
		AST i = null;
		
		AST __t436 = _t;
		AST tmp15_AST_in = (AST)_t;
		match(_t,AVANZAR_PR);
		_t = _t.getFirstChild();
		listAst.clear();
		{
		int _cnt438=0;
		_loop438:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				listAst.add(out1);
			}
			else {
				if ( _cnt438>=1 ) { break _loop438; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt438++;
		} while (true);
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SINCRONIZADOS_PR:
		{
			AST tmp16_AST_in = (AST)_t;
			match(_t,SINCRONIZADOS_PR);
			_t = _t.getNextSibling();
			sinc=true;
			break;
		}
		case IDENTIFICADOR:
		case VALOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		if(l!=null)auxAst=l.getFirstChild(); else auxAst=i;
		cG.addAvanzar(sinc);cG.printOut(listAst);cG.printVelocidad(sinc);cG.printRedDer();cG.printPtoYComa();cG.println();
						cG.printTab(nTab);cG.addMantener();cG.printNum(auxAst,false,true);cG.printRedDer();cG.printPtoYComa();cG.println();
		_t = __t436;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void mantener(AST _t) throws RecognitionException {
		
		AST mantener_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST e = null;
		AST i = null;
		
		AST __t442 = _t;
		AST tmp17_AST_in = (AST)_t;
		match(_t,MANTENER_PR);
		_t = _t.getFirstChild();
		cG.addMantener();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ENTERO_LITERAL:
		{
			e = (AST)_t;
			match(_t,ENTERO_LITERAL);
			_t = _t.getNextSibling();
			cG.printNum(e,true,false);
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			cG.printId(i);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		cG.printRedDer();cG.printPtoYComa();cG.println();
		_t = __t442;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void apagar(AST _t) throws RecognitionException {
		
		AST apagar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST out1 = null;
		
		AST __t451 = _t;
		AST tmp18_AST_in = (AST)_t;
		match(_t,APAGAR_PR);
		_t = _t.getFirstChild();
		listAst.clear();
		{
		int _cnt453=0;
		_loop453:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				listAst.add(out1);
			}
			else {
				if ( _cnt453>=1 ) { break _loop453; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt453++;
		} while (true);
		}
		cG.addApagar();cG.printOut(listAst);cG.printRedDer();cG.printPtoYComa();cG.println();
		_t = __t451;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void retroceder(AST _t) throws RecognitionException {
		
		AST retroceder_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST out1 = null;
		AST l = null;
		AST i = null;
		
		AST __t445 = _t;
		AST tmp19_AST_in = (AST)_t;
		match(_t,RETROCEDER_PR);
		_t = _t.getFirstChild();
		listAst.clear();sinc=false;
		{
		int _cnt447=0;
		_loop447:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				listAst.add(out1);
			}
			else {
				if ( _cnt447>=1 ) { break _loop447; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt447++;
		} while (true);
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SINCRONIZADOS_PR:
		{
			AST tmp20_AST_in = (AST)_t;
			match(_t,SINCRONIZADOS_PR);
			_t = _t.getNextSibling();
			sinc=true;
			break;
		}
		case IDENTIFICADOR:
		case VALOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		if(l!=null)auxAst=l.getFirstChild(); else auxAst=i;
		cG.addRetrocede(sinc);cG.printOut(listAst);
						cG.printVelocidad(sinc);cG.printRedDer();cG.printPtoYComa();cG.println();cG.printTab(nTab);cG.addMantener();cG.printNum(auxAst,false,true);
						cG.printRedDer();cG.printPtoYComa();cG.println();
		_t = __t445;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void girar(AST _t) throws RecognitionException {
		
		AST girar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST fwd = null;
		AST rev = null;
		AST out = null;
		
		AST __t455 = _t;
		AST tmp21_AST_in = (AST)_t;
		match(_t,GIRAR_PR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ADELANTE_PR:
		{
			fwd = (AST)_t;
			match(_t,ADELANTE_PR);
			_t = _t.getNextSibling();
			break;
		}
		case ATRAS_PR:
		{
			rev = (AST)_t;
			match(_t,ATRAS_PR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		out = _t==ASTNULL ? null : (AST)_t;
		salida(_t);
		_t = _retTree;
		cG.printGiro(fwd,rev,out);cG.println();
		_t = __t455;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void salida(AST _t) throws RecognitionException {
		
		AST salida_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case MOTOR_APR:
		{
			AST tmp22_AST_in = (AST)_t;
			match(_t,MOTOR_APR);
			_t = _t.getNextSibling();
			break;
		}
		case MOTOR_BPR:
		{
			AST tmp23_AST_in = (AST)_t;
			match(_t,MOTOR_BPR);
			_t = _t.getNextSibling();
			break;
		}
		case MOTOR_CPR:
		{
			AST tmp24_AST_in = (AST)_t;
			match(_t,MOTOR_CPR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_retTree = _t;
	}
	
	public final void op_mat(AST _t) throws RecognitionException {
		
		AST op_mat_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SUMA:
		{
			AST tmp25_AST_in = (AST)_t;
			match(_t,SUMA);
			_t = _t.getNextSibling();
			cG.printSuma();
			break;
		}
		case GUION:
		{
			AST tmp26_AST_in = (AST)_t;
			match(_t,GUION);
			_t = _t.getNextSibling();
			cG.printGuion();
			break;
		}
		case MULTIPLICACION:
		{
			AST tmp27_AST_in = (AST)_t;
			match(_t,MULTIPLICACION);
			_t = _t.getNextSibling();
			cG.printMulti();
			break;
		}
		case DIVISION:
		{
			AST tmp28_AST_in = (AST)_t;
			match(_t,DIVISION);
			_t = _t.getNextSibling();
			cG.printDivision();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_retTree = _t;
	}
	
	public final void modificando(AST _t) throws RecognitionException {
		
		AST modificando_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t468 = _t;
		AST tmp29_AST_in = (AST)_t;
		match(_t,MODIFICANDO);
		_t = _t.getFirstChild();
		incremento(_t);
		_t = _retTree;
		_t = __t468;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void incremento(AST _t) throws RecognitionException {
		
		AST incremento_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		
		AST __t470 = _t;
		id = _t==ASTNULL ? null :(AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getFirstChild();
		cG.printId(id);
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case INCREMENTAR:
		{
			AST tmp30_AST_in = (AST)_t;
			match(_t,INCREMENTAR);
			_t = _t.getNextSibling();
			cG.printIncrementar();
			break;
		}
		case DECREMENTAR:
		{
			AST tmp31_AST_in = (AST)_t;
			match(_t,DECREMENTAR);
			_t = _t.getNextSibling();
			cG.printDecrementar();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t470;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void condicion(AST _t) throws RecognitionException {
		
		AST condicion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t483 = _t;
		AST tmp32_AST_in = (AST)_t;
		match(_t,CONDICION);
		_t = _t.getFirstChild();
		expr(_t);
		_t = _retTree;
		_t = __t483;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void entonces_hacer(AST _t) throws RecognitionException {
		
		AST entonces_hacer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t479 = _t;
		AST tmp33_AST_in = (AST)_t;
		match(_t,ENTONCES);
		_t = _t.getFirstChild();
		nTab++;
		cuerpo(_t);
		_t = _retTree;
		nTab--;cG.printTab(nTab);
		_t = __t479;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void sino(AST _t) throws RecognitionException {
		
		AST sino_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t481 = _t;
		AST tmp34_AST_in = (AST)_t;
		match(_t,SINO);
		_t = _t.getFirstChild();
		cG.printTab(nTab);cG.printIniSino();nTab++;cG.println();
		cuerpo(_t);
		_t = _retTree;
		nTab--;cG.printTab(nTab);cG.printLlaveDer();cG.println();
		_t = __t481;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void expr(AST _t) throws RecognitionException {
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST check1 = null;
		AST check2 = null;
		AST id = null;
		AST l = null;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case O_RW:
		{
			AST __t485 = _t;
			AST tmp35_AST_in = (AST)_t;
			match(_t,O_RW);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printOr();
			expr(_t);
			_t = _retTree;
			_t = __t485;
			_t = _t.getNextSibling();
			break;
		}
		case Y_RW:
		{
			AST __t486 = _t;
			AST tmp36_AST_in = (AST)_t;
			match(_t,Y_RW);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printAnd();
			expr(_t);
			_t = _retTree;
			_t = __t486;
			_t = _t.getNextSibling();
			break;
		}
		case IGUAL_QUE:
		{
			AST __t487 = _t;
			AST tmp37_AST_in = (AST)_t;
			match(_t,IGUAL_QUE);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printAsign();cG.printAsign();
			expr(_t);
			_t = _retTree;
			_t = __t487;
			_t = _t.getNextSibling();
			break;
		}
		case DISTINTO_QUE:
		{
			AST __t488 = _t;
			AST tmp38_AST_in = (AST)_t;
			match(_t,DISTINTO_QUE);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printNegacion();cG.printAsign();
			expr(_t);
			_t = _retTree;
			_t = __t488;
			_t = _t.getNextSibling();
			break;
		}
		case MENOR_IGUAL:
		{
			AST __t489 = _t;
			AST tmp39_AST_in = (AST)_t;
			match(_t,MENOR_IGUAL);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printMenorEst();cG.printAsign();
			expr(_t);
			_t = _retTree;
			_t = __t489;
			_t = _t.getNextSibling();
			break;
		}
		case MAYOR_IGUAL:
		{
			AST __t490 = _t;
			AST tmp40_AST_in = (AST)_t;
			match(_t,MAYOR_IGUAL);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printMayorEst();cG.printAsign();
			expr(_t);
			_t = _retTree;
			_t = __t490;
			_t = _t.getNextSibling();
			break;
		}
		case MAYOR_ESTRICTO:
		{
			AST __t491 = _t;
			AST tmp41_AST_in = (AST)_t;
			match(_t,MAYOR_ESTRICTO);
			_t = _t.getFirstChild();
			check1 = _t==ASTNULL ? null : (AST)_t;
			expr(_t);
			_t = _retTree;
			cG.printMayorEst();
			check2 = _t==ASTNULL ? null : (AST)_t;
			expr(_t);
			_t = _retTree;
			_t = __t491;
			_t = _t.getNextSibling();
			break;
		}
		case MENOR_ESTRICTO:
		{
			AST __t492 = _t;
			AST tmp42_AST_in = (AST)_t;
			match(_t,MENOR_ESTRICTO);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printMenorEst();
			expr(_t);
			_t = _retTree;
			_t = __t492;
			_t = _t.getNextSibling();
			break;
		}
		case SUMA:
		{
			AST __t493 = _t;
			AST tmp43_AST_in = (AST)_t;
			match(_t,SUMA);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printSuma();
			expr(_t);
			_t = _retTree;
			_t = __t493;
			_t = _t.getNextSibling();
			break;
		}
		case GUION:
		{
			AST __t494 = _t;
			AST tmp44_AST_in = (AST)_t;
			match(_t,GUION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printGuion();
			expr(_t);
			_t = _retTree;
			_t = __t494;
			_t = _t.getNextSibling();
			break;
		}
		case MULTIPLICACION:
		{
			AST __t495 = _t;
			AST tmp45_AST_in = (AST)_t;
			match(_t,MULTIPLICACION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printMulti();
			expr(_t);
			_t = _retTree;
			_t = __t495;
			_t = _t.getNextSibling();
			break;
		}
		case DIVISION:
		{
			AST __t496 = _t;
			AST tmp46_AST_in = (AST)_t;
			match(_t,DIVISION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			cG.printDivision();
			expr(_t);
			_t = _retTree;
			_t = __t496;
			_t = _t.getNextSibling();
			break;
		}
		case UN_MINUS:
		{
			AST __t497 = _t;
			AST tmp47_AST_in = (AST)_t;
			match(_t,UN_MINUS);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			_t = __t497;
			_t = _t.getNextSibling();
			break;
		}
		case IDENTIFICADOR:
		{
			id = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			cG.printId(id);
			break;
		}
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			cG.printNum(l.getFirstChild(),false,false);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		_retTree = _t;
	}
	
	public final void repetir(AST _t) throws RecognitionException {
		
		AST repetir_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST l = null;
		AST id = null;
		
		AST __t501 = _t;
		AST tmp48_AST_in = (AST)_t;
		match(_t,REPETIR);
		_t = _t.getFirstChild();
		cG.println();cG.printTab(nTab);cG.printRepeat();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			cG.printNum(l.getFirstChild(),false,false);
			break;
		}
		case IDENTIFICADOR:
		{
			id = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			cG.printId(id);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		cG.printEndRepeat();
		nTab++;
		cuerpo(_t);
		_t = _retTree;
		nTab--;cG.printTab(nTab);cG.printLlaveDer();
		_t = __t501;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void mientras(AST _t) throws RecognitionException {
		
		AST mientras_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t504 = _t;
		AST tmp49_AST_in = (AST)_t;
		match(_t,MIENTRAS);
		_t = _t.getFirstChild();
		cG.println();cG.printTab(nTab);cG.printIniMientras();
		condicion(_t);
		_t = _retTree;
		cG.printRedDer();cG.printLlaveIzq();
		entonces_hacer(_t);
		_t = _retTree;
		cG.printLlaveDer();
		_t = __t504;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void iterar(AST _t) throws RecognitionException {
		
		AST iterar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t506 = _t;
		AST tmp50_AST_in = (AST)_t;
		match(_t,ITERAR);
		_t = _t.getFirstChild();
		cG.println();cG.printTab(nTab);cG.printIniFor();
		desde(_t);
		_t = _retTree;
		hasta(_t);
		_t = _retTree;
		cG.printPtoYComa();
		modificando(_t);
		_t = _retTree;
		cG.printRedDer();cG.printLlaveIzq();cG.println();
		entonces_hacer(_t);
		_t = _retTree;
		cG.printLlaveDer();
		_t = __t506;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void desde(AST _t) throws RecognitionException {
		
		AST desde_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t508 = _t;
		AST tmp51_AST_in = (AST)_t;
		match(_t,DESDE);
		_t = _t.getFirstChild();
		assign(_t);
		_t = _retTree;
		_t = __t508;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void hasta(AST _t) throws RecognitionException {
		
		AST hasta_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t510 = _t;
		AST tmp52_AST_in = (AST)_t;
		match(_t,HASTA);
		_t = _t.getFirstChild();
		condicion(_t);
		_t = _retTree;
		_t = __t510;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"entero\"",
		"\"real\"",
		"\"booleano\"",
		"\"constante\"",
		"\"inicio\"",
		"\"del\"",
		"\"programa\"",
		"\"principal\"",
		"\"avanzar\"",
		"\"mantener\"",
		"\"retroceder\"",
		"\"apagar\"",
		"\"imprimir\"",
		"\"girar\"",
		"\"segundos\"",
		"\"adelante\"",
		"\"atras\"",
		"\"sincronizados\"",
		"\"MOTOR_A\"",
		"\"MOTOR_B\"",
		"\"MOTOR_C\"",
		"\"si\"",
		"\"entonces\"",
		"\"sino\"",
		"\"fin\"",
		"\"Y\"",
		"\"O\"",
		"\"iterar\"",
		"\"veces\"",
		"\"mientras\"",
		"\"repetir\"",
		"\"desde\"",
		"\"hasta\"",
		"\"modificando\"",
		"REAL_LITERAL",
		"ENTERO_LITERAL",
		"NEGACION",
		"GUION_BAJO",
		"SUMA",
		"GUION",
		"MULTIPLICACION",
		"DIVISION",
		"INCREMENTAR",
		"DECREMENTAR",
		"IGUAL_QUE",
		"DISTINTO_QUE",
		"ASIGN",
		"MENOR_ESTRICTO",
		"MAYOR_ESTRICTO",
		"MENOR_IGUAL",
		"MAYOR_IGUAL",
		"DIGITO",
		"NUM_LITERAL",
		"LETRA",
		"IDENTIFICADOR",
		"COMILLAS",
		"CADENA_LITERAL",
		"WS",
		"COMENTARIO_LINEA",
		"COMENTARIO_BLOQUE",
		"PROGRAMA_",
		"DECLARACIONES",
		"CUERPO",
		"CONSTANTE",
		"VARIABLE",
		"TIPO_DATO",
		"ID",
		"VALOR",
		"AVANZAR",
		"MANTENER",
		"RETROCEDER",
		"APAGAR",
		"ASIGNACION",
		"IMPRIMIR",
		"SI_",
		"CONDICION",
		"SINO_",
		"UN_MINUS",
		"REPETIR_",
		"ITERAR_"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 25803616256L, 4112L, 0L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	}
	
