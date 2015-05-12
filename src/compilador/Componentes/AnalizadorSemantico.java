// $ANTLR : "AnalizadorSemantico.g" -> "AnalizadorSemantico.java"$

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


public class AnalizadorSemantico extends antlr.TreeParser       implements AnalizadorSemanticoTokenTypes
 {

/**
*	Objeto que se utiliza un objeto que ayudara a realizar el analisis semantico
*/
private InspectorSemantico   sI = new InspectorSemantico();


/**
*	Lista que se utiliza para almacenar temporalmente a variables de una asignacion
*/
private ArrayList<AST> listTempOperandos = new ArrayList<AST>();
public AnalizadorSemantico() {
	tokenNames = _tokenNames;
}

	public final void programa(AST _t) throws RecognitionException {
		
		AST programa_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t315 = _t;
		AST tmp1_AST_in = (AST)_t;
		match(_t,PROGRAMA_);
		_t = _t.getFirstChild();
		declaraciones(_t);
		_t = _retTree;
		cuerpo(_t);
		_t = _retTree;
		_t = __t315;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void declaraciones(AST _t) throws RecognitionException {
		
		AST declaraciones_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t317 = _t;
		AST tmp2_AST_in = (AST)_t;
		match(_t,DECLARACIONES);
		_t = _t.getFirstChild();
		{
		_loop319:
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
				break _loop319;
			}
			}
		} while (true);
		}
		_t = __t317;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void cuerpo(AST _t) throws RecognitionException {
		
		AST cuerpo_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t331 = _t;
		AST tmp3_AST_in = (AST)_t;
		match(_t,CUERPO);
		_t = _t.getFirstChild();
		{
		_loop333:
		do {
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
				break _loop333;
			}
			}
		} while (true);
		}
		_t = __t331;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void dec_var(AST _t) throws RecognitionException {
		
		AST dec_var_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST td = null;
		AST i = null;
		
		AST __t321 = _t;
		AST tmp4_AST_in = (AST)_t;
		match(_t,VARIABLE);
		_t = _t.getFirstChild();
		td = _t==ASTNULL ? null : (AST)_t;
		tipo_dato(_t);
		_t = _retTree;
		i = (AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getNextSibling();
		sI.addOperando(td.getFirstChild(),i,false,"");
		_t = __t321;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void dec_const(AST _t) throws RecognitionException {
		
		AST dec_const_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST td = null;
		AST i = null;
		AST l = null;
		
		AST __t323 = _t;
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
		sI.addOperando(td.getFirstChild(),i,true,l.getFirstChild().toString());
		_t = __t323;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void tipo_dato(AST _t) throws RecognitionException {
		
		AST tipo_dato_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t325 = _t;
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
		_t = __t325;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void literal(AST _t) throws RecognitionException {
		
		AST literal_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t328 = _t;
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
		_t = __t328;
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
		case RETROCEDER_PR:
		{
			retroceder(_t);
			_t = _retTree;
			break;
		}
		case APAGAR_PR:
		{
			apagar(_t);
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
		
		AST __t361 = _t;
		AST tmp12_AST_in = (AST)_t;
		match(_t,ASIGNACION);
		_t = _t.getFirstChild();
		listTempOperandos.clear();
		i = (AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getNextSibling();
		sI.checkOperando(i);
													listTempOperandos.add(i);
													sI.constanteNoIzquerda(i);
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case IDENTIFICADOR:
		{
			i2 = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(i2);listTempOperandos.add(i2);
															sI.checkTipos(listTempOperandos);
			break;
		}
		case VALOR:
		{
			l = _t==ASTNULL ? null : (AST)_t;
			literal(_t);
			_t = _retTree;
			sI.checkLiteralEnAsign(listTempOperandos,l.getFirstChild());
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		{
		_loop365:
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
					sI.checkOperando(i3);listTempOperandos.add(i3);
																sI.checkTipos(listTempOperandos);
					break;
				}
				case VALOR:
				{
					l1 = _t==ASTNULL ? null : (AST)_t;
					literal(_t);
					_t = _retTree;
					sI.checkLiteralEnAsign(listTempOperandos,l1.getFirstChild());
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
				break _loop365;
			}
			
		} while (true);
		}
		_t = __t361;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void imprimir(AST _t) throws RecognitionException {
		
		AST imprimir_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		
		AST __t369 = _t;
		AST tmp13_AST_in = (AST)_t;
		match(_t,IMPRIMIR_PR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case CADENA_LITERAL:
		{
			AST tmp14_AST_in = (AST)_t;
			match(_t,CADENA_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(i);
			break;
		}
		case VALOR:
		{
			literal(_t);
			_t = _retTree;
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t369;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void si(AST _t) throws RecognitionException {
		
		AST si_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t372 = _t;
		AST tmp15_AST_in = (AST)_t;
		match(_t,SI);
		_t = _t.getFirstChild();
		condicion(_t);
		_t = _retTree;
		entonces_hacer(_t);
		_t = _retTree;
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
		_t = __t372;
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
		AST i = null;
		
		AST __t337 = _t;
		AST tmp16_AST_in = (AST)_t;
		match(_t,AVANZAR_PR);
		_t = _t.getFirstChild();
		sI.clearListaNombreSalidas();
		{
		int _cnt339=0;
		_loop339:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				sI.addAndCheckSalidas(out1);
			}
			else {
				if ( _cnt339>=1 ) { break _loop339; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt339++;
		} while (true);
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SINCRONIZADOS_PR:
		{
			AST tmp17_AST_in = (AST)_t;
			match(_t,SINCRONIZADOS_PR);
			_t = _t.getNextSibling();
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
			literal(_t);
			_t = _retTree;
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(i);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t337;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void mantener(AST _t) throws RecognitionException {
		
		AST mantener_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST i = null;
		
		AST __t343 = _t;
		AST tmp18_AST_in = (AST)_t;
		match(_t,MANTENER_PR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ENTERO_LITERAL:
		{
			AST tmp19_AST_in = (AST)_t;
			match(_t,ENTERO_LITERAL);
			_t = _t.getNextSibling();
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(i);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t343;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void retroceder(AST _t) throws RecognitionException {
		
		AST retroceder_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST out1 = null;
		AST i = null;
		
		AST __t346 = _t;
		AST tmp20_AST_in = (AST)_t;
		match(_t,RETROCEDER_PR);
		_t = _t.getFirstChild();
		sI.clearListaNombreSalidas();
		{
		int _cnt348=0;
		_loop348:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				sI.addAndCheckSalidas(out1);
			}
			else {
				if ( _cnt348>=1 ) { break _loop348; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt348++;
		} while (true);
		}
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case SINCRONIZADOS_PR:
		{
			AST tmp21_AST_in = (AST)_t;
			match(_t,SINCRONIZADOS_PR);
			_t = _t.getNextSibling();
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
			literal(_t);
			_t = _retTree;
			break;
		}
		case IDENTIFICADOR:
		{
			i = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(i);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t346;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void apagar(AST _t) throws RecognitionException {
		
		AST apagar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST out1 = null;
		
		AST __t352 = _t;
		AST tmp22_AST_in = (AST)_t;
		match(_t,APAGAR_PR);
		_t = _t.getFirstChild();
		sI.clearListaNombreSalidas();
		{
		int _cnt354=0;
		_loop354:
		do {
			if (_t==null) _t=ASTNULL;
			if (((_t.getType() >= MOTOR_APR && _t.getType() <= MOTOR_CPR))) {
				out1 = _t==ASTNULL ? null : (AST)_t;
				salida(_t);
				_t = _retTree;
				sI.addAndCheckSalidas(out1);
			}
			else {
				if ( _cnt354>=1 ) { break _loop354; } else {throw new NoViableAltException(_t);}
			}
			
			_cnt354++;
		} while (true);
		}
		_t = __t352;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void girar(AST _t) throws RecognitionException {
		
		AST girar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t356 = _t;
		AST tmp23_AST_in = (AST)_t;
		match(_t,GIRAR_PR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case ADELANTE_PR:
		{
			AST tmp24_AST_in = (AST)_t;
			match(_t,ADELANTE_PR);
			_t = _t.getNextSibling();
			break;
		}
		case ATRAS_PR:
		{
			AST tmp25_AST_in = (AST)_t;
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
		salida(_t);
		_t = _retTree;
		_t = __t356;
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
			AST tmp26_AST_in = (AST)_t;
			match(_t,MOTOR_APR);
			_t = _t.getNextSibling();
			break;
		}
		case MOTOR_BPR:
		{
			AST tmp27_AST_in = (AST)_t;
			match(_t,MOTOR_BPR);
			_t = _t.getNextSibling();
			break;
		}
		case MOTOR_CPR:
		{
			AST tmp28_AST_in = (AST)_t;
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
			AST tmp29_AST_in = (AST)_t;
			match(_t,SUMA);
			_t = _t.getNextSibling();
			break;
		}
		case GUION:
		{
			AST tmp30_AST_in = (AST)_t;
			match(_t,GUION);
			_t = _t.getNextSibling();
			break;
		}
		case MULTIPLICACION:
		{
			AST tmp31_AST_in = (AST)_t;
			match(_t,MULTIPLICACION);
			_t = _t.getNextSibling();
			break;
		}
		case DIVISION:
		{
			AST tmp32_AST_in = (AST)_t;
			match(_t,DIVISION);
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
	
	public final void condicion(AST _t) throws RecognitionException {
		
		AST condicion_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t379 = _t;
		AST tmp33_AST_in = (AST)_t;
		match(_t,CONDICION);
		_t = _t.getFirstChild();
		sI.clearOperatorTable();
		expr(_t);
		_t = _retTree;
		_t = __t379;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void entonces_hacer(AST _t) throws RecognitionException {
		
		AST entonces_hacer_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t375 = _t;
		AST tmp34_AST_in = (AST)_t;
		match(_t,ENTONCES);
		_t = _t.getFirstChild();
		cuerpo(_t);
		_t = _retTree;
		_t = __t375;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void sino(AST _t) throws RecognitionException {
		
		AST sino_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t377 = _t;
		AST tmp35_AST_in = (AST)_t;
		match(_t,SINO);
		_t = _t.getFirstChild();
		cuerpo(_t);
		_t = _retTree;
		_t = __t377;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void expr(AST _t) throws RecognitionException {
		
		AST expr_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST op = null;
		AST op1 = null;
		AST op2 = null;
		AST op4 = null;
		AST op5 = null;
		AST op6 = null;
		AST check1 = null;
		AST check2 = null;
		AST op7 = null;
		AST op8 = null;
		AST op9 = null;
		AST op10 = null;
		AST op11 = null;
		AST op12 = null;
		AST op13 = null;
		AST id = null;
		
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case O_RW:
		{
			AST __t381 = _t;
			op = _t==ASTNULL ? null :(AST)_t;
			match(_t,O_RW);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op);
			_t = __t381;
			_t = _t.getNextSibling();
			break;
		}
		case Y_RW:
		{
			AST __t382 = _t;
			op1 = _t==ASTNULL ? null :(AST)_t;
			match(_t,Y_RW);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op1);
			_t = __t382;
			_t = _t.getNextSibling();
			break;
		}
		case IGUAL_QUE:
		{
			AST __t383 = _t;
			op2 = _t==ASTNULL ? null :(AST)_t;
			match(_t,IGUAL_QUE);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op2);
			_t = __t383;
			_t = _t.getNextSibling();
			break;
		}
		case DISTINTO_QUE:
		{
			AST __t384 = _t;
			op4 = _t==ASTNULL ? null :(AST)_t;
			match(_t,DISTINTO_QUE);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op4);
			_t = __t384;
			_t = _t.getNextSibling();
			break;
		}
		case MENOR_IGUAL:
		{
			AST __t385 = _t;
			op5 = _t==ASTNULL ? null :(AST)_t;
			match(_t,MENOR_IGUAL);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op5);
			_t = __t385;
			_t = _t.getNextSibling();
			break;
		}
		case MAYOR_IGUAL:
		{
			AST __t386 = _t;
			op6 = _t==ASTNULL ? null :(AST)_t;
			match(_t,MAYOR_IGUAL);
			_t = _t.getFirstChild();
			check1 = _t==ASTNULL ? null : (AST)_t;
			expr(_t);
			_t = _retTree;
			check2 = _t==ASTNULL ? null : (AST)_t;
			expr(_t);
			_t = _retTree;
			listTempOperandos.clear();sI.addOperator(op6);
			_t = __t386;
			_t = _t.getNextSibling();
			break;
		}
		case MAYOR_ESTRICTO:
		{
			AST __t387 = _t;
			op7 = _t==ASTNULL ? null :(AST)_t;
			match(_t,MAYOR_ESTRICTO);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op7);
			_t = __t387;
			_t = _t.getNextSibling();
			break;
		}
		case MENOR_ESTRICTO:
		{
			AST __t388 = _t;
			op8 = _t==ASTNULL ? null :(AST)_t;
			match(_t,MENOR_ESTRICTO);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op8);
			_t = __t388;
			_t = _t.getNextSibling();
			break;
		}
		case SUMA:
		{
			AST __t389 = _t;
			op9 = _t==ASTNULL ? null :(AST)_t;
			match(_t,SUMA);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op9);
			_t = __t389;
			_t = _t.getNextSibling();
			break;
		}
		case GUION:
		{
			AST __t390 = _t;
			op10 = _t==ASTNULL ? null :(AST)_t;
			match(_t,GUION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op10);
			_t = __t390;
			_t = _t.getNextSibling();
			break;
		}
		case MULTIPLICACION:
		{
			AST __t391 = _t;
			op11 = _t==ASTNULL ? null :(AST)_t;
			match(_t,MULTIPLICACION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op11);
			_t = __t391;
			_t = _t.getNextSibling();
			break;
		}
		case DIVISION:
		{
			AST __t392 = _t;
			op12 = _t==ASTNULL ? null :(AST)_t;
			match(_t,DIVISION);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			expr(_t);
			_t = _retTree;
			sI.addOperator(op12);
			_t = __t392;
			_t = _t.getNextSibling();
			break;
		}
		case UN_MINUS:
		{
			AST __t393 = _t;
			op13 = _t==ASTNULL ? null :(AST)_t;
			match(_t,UN_MINUS);
			_t = _t.getFirstChild();
			expr(_t);
			_t = _retTree;
			sI.addOperator(op13);
			_t = __t393;
			_t = _t.getNextSibling();
			break;
		}
		case IDENTIFICADOR:
		{
			id = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(id);
			break;
		}
		case VALOR:
		{
			literal(_t);
			_t = _retTree;
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
		AST id = null;
		
		AST __t397 = _t;
		AST tmp36_AST_in = (AST)_t;
		match(_t,REPETIR);
		_t = _t.getFirstChild();
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case VALOR:
		{
			literal(_t);
			_t = _retTree;
			break;
		}
		case IDENTIFICADOR:
		{
			id = (AST)_t;
			match(_t,IDENTIFICADOR);
			_t = _t.getNextSibling();
			sI.checkOperando(id);
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		cuerpo(_t);
		_t = _retTree;
		_t = __t397;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void mientras(AST _t) throws RecognitionException {
		
		AST mientras_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t400 = _t;
		AST tmp37_AST_in = (AST)_t;
		match(_t,MIENTRAS);
		_t = _t.getFirstChild();
		condicion(_t);
		_t = _retTree;
		entonces_hacer(_t);
		_t = _retTree;
		_t = __t400;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void iterar(AST _t) throws RecognitionException {
		
		AST iterar_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t402 = _t;
		AST tmp38_AST_in = (AST)_t;
		match(_t,ITERAR);
		_t = _t.getFirstChild();
		desde(_t);
		_t = _retTree;
		hasta(_t);
		_t = _retTree;
		modificando(_t);
		_t = _retTree;
		entonces_hacer(_t);
		_t = _retTree;
		_t = __t402;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void desde(AST _t) throws RecognitionException {
		
		AST desde_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t404 = _t;
		AST tmp39_AST_in = (AST)_t;
		match(_t,DESDE);
		_t = _t.getFirstChild();
		assign(_t);
		_t = _retTree;
		;
		_t = __t404;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void hasta(AST _t) throws RecognitionException {
		
		AST hasta_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t406 = _t;
		AST tmp40_AST_in = (AST)_t;
		match(_t,HASTA);
		_t = _t.getFirstChild();
		condicion(_t);
		_t = _retTree;
		_t = __t406;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void modificando(AST _t) throws RecognitionException {
		
		AST modificando_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		
		AST __t408 = _t;
		AST tmp41_AST_in = (AST)_t;
		match(_t,MODIFICANDO);
		_t = _t.getFirstChild();
		incremento(_t);
		_t = _retTree;
		_t = __t408;
		_t = _t.getNextSibling();
		_retTree = _t;
	}
	
	public final void incremento(AST _t) throws RecognitionException {
		
		AST incremento_AST_in = (_t == ASTNULL) ? null : (AST)_t;
		AST id = null;
		
		AST __t410 = _t;
		id = _t==ASTNULL ? null :(AST)_t;
		match(_t,IDENTIFICADOR);
		_t = _t.getFirstChild();
		sI.checkOperando(id);
		{
		if (_t==null) _t=ASTNULL;
		switch ( _t.getType()) {
		case INCREMENTAR:
		{
			AST tmp42_AST_in = (AST)_t;
			match(_t,INCREMENTAR);
			_t = _t.getNextSibling();
			break;
		}
		case DECREMENTAR:
		{
			AST tmp43_AST_in = (AST)_t;
			match(_t,DECREMENTAR);
			_t = _t.getNextSibling();
			break;
		}
		default:
		{
			throw new NoViableAltException(_t);
		}
		}
		}
		_t = __t410;
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
	
	}
	
