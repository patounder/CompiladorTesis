// $ANTLR : "AnalizadorSintactico.g" -> "AnalizadorSintactico.java"$

/**
 * Parser for the Mile language.
 * 
 * @author Ricardo Soto
 * @since 1.5
 */

package compilador.Componentes;


import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;
import antlr.collections.AST;
import java.util.Hashtable;
import antlr.ASTFactory;
import antlr.ASTPair;
import antlr.collections.impl.ASTArray;

public class AnalizadorSintactico extends antlr.LLkParser       implements AnalizadorSintacticoVocabTokenTypes
 {

protected AnalizadorSintactico(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public AnalizadorSintactico(TokenBuffer tokenBuf) {
  this(tokenBuf,2);
}

protected AnalizadorSintactico(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

public AnalizadorSintactico(TokenStream lexer) {
  this(lexer,2);
}

public AnalizadorSintactico(ParserSharedInputState state) {
  super(state,2);
  tokenNames = _tokenNames;
  buildTokenTypeASTClassMap();
  astFactory = new ASTFactory(getTokenTypeToASTClassMap());
}

	public final void programa() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST programa_AST = null;
		
		declaraciones();
		astFactory.addASTChild(currentAST, returnAST);
		match(INICIO);
		match(DEL);
		match(PROGRAMA);
		cuerpo();
		astFactory.addASTChild(currentAST, returnAST);
		match(FIN);
		match(DEL);
		match(PROGRAMA);
		programa_AST = (AST)currentAST.root;
		programa_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(PROGRAMA_,"programa")).add(programa_AST));
		currentAST.root = programa_AST;
		currentAST.child = programa_AST!=null &&programa_AST.getFirstChild()!=null ?
			programa_AST.getFirstChild() : programa_AST;
		currentAST.advanceChildToEnd();
		programa_AST = (AST)currentAST.root;
		returnAST = programa_AST;
	}
	
	public final void declaraciones() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST declaraciones_AST = null;
		
		{
		_loop233:
		do {
			switch ( LA(1)) {
			case TIPO_ENTERO_PR:
			case TIPO_REAL_PR:
			{
				dec_var();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case CONST_PR:
			{
				dec_const();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				break _loop233;
			}
			}
		} while (true);
		}
		declaraciones_AST = (AST)currentAST.root;
		declaraciones_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(DECLARACIONES,"declaraciones")).add(declaraciones_AST));
		currentAST.root = declaraciones_AST;
		currentAST.child = declaraciones_AST!=null &&declaraciones_AST.getFirstChild()!=null ?
			declaraciones_AST.getFirstChild() : declaraciones_AST;
		currentAST.advanceChildToEnd();
		declaraciones_AST = (AST)currentAST.root;
		returnAST = declaraciones_AST;
	}
	
	public final void cuerpo() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST cuerpo_AST = null;
		
		{
		_loop242:
		do {
			switch ( LA(1)) {
			case AVANZAR_PR:
			case MANTENER_PR:
			case RETROCEDER_PR:
			case APAGAR_PR:
			case GIRAR_PR:
			{
				movimientos();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IDENTIFICADOR:
			{
				assign();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case IMPRIMIR_PR:
			{
				imprimir();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case TIPO_ENTERO_PR:
			case TIPO_REAL_PR:
			{
				dec_var();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case SI:
			{
				si();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			case MIENTRAS:
			case REPETIR:
			{
				repetitivas();
				astFactory.addASTChild(currentAST, returnAST);
				break;
			}
			default:
			{
				break _loop242;
			}
			}
		} while (true);
		}
		cuerpo_AST = (AST)currentAST.root;
		cuerpo_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CUERPO,"cuerpo")).add(cuerpo_AST));
		currentAST.root = cuerpo_AST;
		currentAST.child = cuerpo_AST!=null &&cuerpo_AST.getFirstChild()!=null ?
			cuerpo_AST.getFirstChild() : cuerpo_AST;
		currentAST.advanceChildToEnd();
		cuerpo_AST = (AST)currentAST.root;
		returnAST = cuerpo_AST;
	}
	
	public final void dec_var() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dec_var_AST = null;
		
		tipo_dato();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp7_AST = null;
		tmp7_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp7_AST);
		match(IDENTIFICADOR);
		dec_var_AST = (AST)currentAST.root;
		dec_var_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VARIABLE,"variable")).add(dec_var_AST));
		currentAST.root = dec_var_AST;
		currentAST.child = dec_var_AST!=null &&dec_var_AST.getFirstChild()!=null ?
			dec_var_AST.getFirstChild() : dec_var_AST;
		currentAST.advanceChildToEnd();
		dec_var_AST = (AST)currentAST.root;
		returnAST = dec_var_AST;
	}
	
	public final void dec_const() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST dec_const_AST = null;
		
		match(CONST_PR);
		tipo_dato();
		astFactory.addASTChild(currentAST, returnAST);
		AST tmp9_AST = null;
		tmp9_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp9_AST);
		match(IDENTIFICADOR);
		literal();
		astFactory.addASTChild(currentAST, returnAST);
		dec_const_AST = (AST)currentAST.root;
		dec_const_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONSTANTE,"constante")).add(dec_const_AST));
		currentAST.root = dec_const_AST;
		currentAST.child = dec_const_AST!=null &&dec_const_AST.getFirstChild()!=null ?
			dec_const_AST.getFirstChild() : dec_const_AST;
		currentAST.advanceChildToEnd();
		dec_const_AST = (AST)currentAST.root;
		returnAST = dec_const_AST;
	}
	
	public final void tipo_dato() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST tipo_dato_AST = null;
		
		{
		switch ( LA(1)) {
		case TIPO_ENTERO_PR:
		{
			AST tmp10_AST = null;
			tmp10_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp10_AST);
			match(TIPO_ENTERO_PR);
			break;
		}
		case TIPO_REAL_PR:
		{
			AST tmp11_AST = null;
			tmp11_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp11_AST);
			match(TIPO_REAL_PR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		tipo_dato_AST = (AST)currentAST.root;
		tipo_dato_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(TIPO_DATO,"tipo_dato")).add(tipo_dato_AST));
		currentAST.root = tipo_dato_AST;
		currentAST.child = tipo_dato_AST!=null &&tipo_dato_AST.getFirstChild()!=null ?
			tipo_dato_AST.getFirstChild() : tipo_dato_AST;
		currentAST.advanceChildToEnd();
		tipo_dato_AST = (AST)currentAST.root;
		returnAST = tipo_dato_AST;
	}
	
	public final void literal() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST literal_AST = null;
		
		{
		switch ( LA(1)) {
		case REAL_LITERAL:
		{
			AST tmp12_AST = null;
			tmp12_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp12_AST);
			match(REAL_LITERAL);
			break;
		}
		case ENTERO_LITERAL:
		{
			AST tmp13_AST = null;
			tmp13_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp13_AST);
			match(ENTERO_LITERAL);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		literal_AST = (AST)currentAST.root;
		literal_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(VALOR,"valor")).add(literal_AST));
		currentAST.root = literal_AST;
		currentAST.child = literal_AST!=null &&literal_AST.getFirstChild()!=null ?
			literal_AST.getFirstChild() : literal_AST;
		currentAST.advanceChildToEnd();
		literal_AST = (AST)currentAST.root;
		returnAST = literal_AST;
	}
	
	public final void movimientos() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST movimientos_AST = null;
		
		{
		switch ( LA(1)) {
		case AVANZAR_PR:
		{
			avanzar();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case MANTENER_PR:
		{
			mantener();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case RETROCEDER_PR:
		{
			retroceder();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case APAGAR_PR:
		{
			apagar();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case GIRAR_PR:
		{
			girar();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		movimientos_AST = (AST)currentAST.root;
		returnAST = movimientos_AST;
	}
	
	public final void assign() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST assign_AST = null;
		
		AST tmp14_AST = null;
		tmp14_AST = astFactory.create(LT(1));
		astFactory.addASTChild(currentAST, tmp14_AST);
		match(IDENTIFICADOR);
		match(ASIGN);
		{
		switch ( LA(1)) {
		case IDENTIFICADOR:
		{
			AST tmp16_AST = null;
			tmp16_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp16_AST);
			match(IDENTIFICADOR);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		_loop268:
		do {
			if (((LA(1) >= SUMA && LA(1) <= DIVISION))) {
				op_mat();
				astFactory.addASTChild(currentAST, returnAST);
				{
				switch ( LA(1)) {
				case IDENTIFICADOR:
				{
					AST tmp17_AST = null;
					tmp17_AST = astFactory.create(LT(1));
					astFactory.addASTChild(currentAST, tmp17_AST);
					match(IDENTIFICADOR);
					break;
				}
				case REAL_LITERAL:
				case ENTERO_LITERAL:
				{
					literal();
					astFactory.addASTChild(currentAST, returnAST);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
			}
			else {
				break _loop268;
			}
			
		} while (true);
		}
		assign_AST = (AST)currentAST.root;
		assign_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(ASIGNACION,"asignacion")).add(assign_AST));
		currentAST.root = assign_AST;
		currentAST.child = assign_AST!=null &&assign_AST.getFirstChild()!=null ?
			assign_AST.getFirstChild() : assign_AST;
		currentAST.advanceChildToEnd();
		assign_AST = (AST)currentAST.root;
		returnAST = assign_AST;
	}
	
	public final void imprimir() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST imprimir_AST = null;
		
		AST tmp18_AST = null;
		tmp18_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp18_AST);
		match(IMPRIMIR_PR);
		{
		switch ( LA(1)) {
		case CADENA_LITERAL:
		{
			AST tmp19_AST = null;
			tmp19_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp19_AST);
			match(CADENA_LITERAL);
			break;
		}
		case IDENTIFICADOR:
		{
			AST tmp20_AST = null;
			tmp20_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp20_AST);
			match(IDENTIFICADOR);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		imprimir_AST = (AST)currentAST.root;
		returnAST = imprimir_AST;
	}
	
	public final void si() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST si_AST = null;
		
		AST tmp21_AST = null;
		tmp21_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp21_AST);
		match(SI);
		condicion();
		astFactory.addASTChild(currentAST, returnAST);
		entonces_hacer();
		astFactory.addASTChild(currentAST, returnAST);
		{
		switch ( LA(1)) {
		case SINO:
		{
			sino();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case FIN:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(FIN);
		match(SI);
		si_AST = (AST)currentAST.root;
		returnAST = si_AST;
	}
	
	public final void repetitivas() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST repetitivas_AST = null;
		
		{
		switch ( LA(1)) {
		case REPETIR:
		{
			repetir();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case MIENTRAS:
		{
			mientras();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		repetitivas_AST = (AST)currentAST.root;
		returnAST = repetitivas_AST;
	}
	
	public final void avanzar() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST avanzar_AST = null;
		
		AST tmp24_AST = null;
		tmp24_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp24_AST);
		match(AVANZAR_PR);
		{
		int _cnt247=0;
		_loop247:
		do {
			if (((LA(1) >= MOTOR_APR && LA(1) <= MOTOR_CPR))) {
				salida();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt247>=1 ) { break _loop247; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt247++;
		} while (true);
		}
		{
		switch ( LA(1)) {
		case SINCRONIZADOS_PR:
		{
			AST tmp25_AST = null;
			tmp25_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp25_AST);
			match(SINCRONIZADOS_PR);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		case IDENTIFICADOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case IDENTIFICADOR:
		{
			AST tmp26_AST = null;
			tmp26_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp26_AST);
			match(IDENTIFICADOR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEGUNDOS_PR);
		avanzar_AST = (AST)currentAST.root;
		returnAST = avanzar_AST;
	}
	
	public final void mantener() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mantener_AST = null;
		
		AST tmp28_AST = null;
		tmp28_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp28_AST);
		match(MANTENER_PR);
		{
		switch ( LA(1)) {
		case ENTERO_LITERAL:
		{
			AST tmp29_AST = null;
			tmp29_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp29_AST);
			match(ENTERO_LITERAL);
			break;
		}
		case IDENTIFICADOR:
		{
			AST tmp30_AST = null;
			tmp30_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp30_AST);
			match(IDENTIFICADOR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEGUNDOS_PR);
		mantener_AST = (AST)currentAST.root;
		returnAST = mantener_AST;
	}
	
	public final void retroceder() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST retroceder_AST = null;
		
		AST tmp32_AST = null;
		tmp32_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp32_AST);
		match(RETROCEDER_PR);
		{
		int _cnt254=0;
		_loop254:
		do {
			if (((LA(1) >= MOTOR_APR && LA(1) <= MOTOR_CPR))) {
				salida();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt254>=1 ) { break _loop254; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt254++;
		} while (true);
		}
		{
		switch ( LA(1)) {
		case SINCRONIZADOS_PR:
		{
			AST tmp33_AST = null;
			tmp33_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp33_AST);
			match(SINCRONIZADOS_PR);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		case IDENTIFICADOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case IDENTIFICADOR:
		{
			AST tmp34_AST = null;
			tmp34_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp34_AST);
			match(IDENTIFICADOR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(SEGUNDOS_PR);
		retroceder_AST = (AST)currentAST.root;
		returnAST = retroceder_AST;
	}
	
	public final void apagar() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST apagar_AST = null;
		
		AST tmp36_AST = null;
		tmp36_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp36_AST);
		match(APAGAR_PR);
		{
		int _cnt259=0;
		_loop259:
		do {
			if (((LA(1) >= MOTOR_APR && LA(1) <= MOTOR_CPR))) {
				salida();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				if ( _cnt259>=1 ) { break _loop259; } else {throw new NoViableAltException(LT(1), getFilename());}
			}
			
			_cnt259++;
		} while (true);
		}
		apagar_AST = (AST)currentAST.root;
		returnAST = apagar_AST;
	}
	
	public final void girar() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST girar_AST = null;
		
		AST tmp37_AST = null;
		tmp37_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp37_AST);
		match(GIRAR_PR);
		{
		switch ( LA(1)) {
		case ADELANTE_PR:
		{
			AST tmp38_AST = null;
			tmp38_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp38_AST);
			match(ADELANTE_PR);
			break;
		}
		case ATRAS_PR:
		{
			AST tmp39_AST = null;
			tmp39_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp39_AST);
			match(ATRAS_PR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		salida();
		astFactory.addASTChild(currentAST, returnAST);
		girar_AST = (AST)currentAST.root;
		returnAST = girar_AST;
	}
	
	public final void salida() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST salida_AST = null;
		
		{
		switch ( LA(1)) {
		case MOTOR_APR:
		{
			AST tmp40_AST = null;
			tmp40_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp40_AST);
			match(MOTOR_APR);
			break;
		}
		case MOTOR_BPR:
		{
			AST tmp41_AST = null;
			tmp41_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp41_AST);
			match(MOTOR_BPR);
			break;
		}
		case MOTOR_CPR:
		{
			AST tmp42_AST = null;
			tmp42_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp42_AST);
			match(MOTOR_CPR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		salida_AST = (AST)currentAST.root;
		returnAST = salida_AST;
	}
	
	public final void op_mat() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST op_mat_AST = null;
		
		{
		switch ( LA(1)) {
		case SUMA:
		{
			AST tmp43_AST = null;
			tmp43_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp43_AST);
			match(SUMA);
			break;
		}
		case GUION:
		{
			AST tmp44_AST = null;
			tmp44_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp44_AST);
			match(GUION);
			break;
		}
		case MULTIPLICACION:
		{
			AST tmp45_AST = null;
			tmp45_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp45_AST);
			match(MULTIPLICACION);
			break;
		}
		case DIVISION:
		{
			AST tmp46_AST = null;
			tmp46_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp46_AST);
			match(DIVISION);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		op_mat_AST = (AST)currentAST.root;
		returnAST = op_mat_AST;
	}
	
	public final void condicion() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST condicion_AST = null;
		
		expr();
		astFactory.addASTChild(currentAST, returnAST);
		condicion_AST = (AST)currentAST.root;
		condicion_AST = (AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(CONDICION,"condicion")).add(condicion_AST));
		currentAST.root = condicion_AST;
		currentAST.child = condicion_AST!=null &&condicion_AST.getFirstChild()!=null ?
			condicion_AST.getFirstChild() : condicion_AST;
		currentAST.advanceChildToEnd();
		condicion_AST = (AST)currentAST.root;
		returnAST = condicion_AST;
	}
	
	public final void entonces_hacer() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST entonces_hacer_AST = null;
		
		AST tmp47_AST = null;
		tmp47_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp47_AST);
		match(ENTONCES);
		cuerpo();
		astFactory.addASTChild(currentAST, returnAST);
		entonces_hacer_AST = (AST)currentAST.root;
		returnAST = entonces_hacer_AST;
	}
	
	public final void sino() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST sino_AST = null;
		
		AST tmp48_AST = null;
		tmp48_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp48_AST);
		match(SINO);
		cuerpo();
		astFactory.addASTChild(currentAST, returnAST);
		sino_AST = (AST)currentAST.root;
		returnAST = sino_AST;
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expr_AST = null;
		
		expAND();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop280:
		do {
			if ((LA(1)==O_RW)) {
				AST tmp49_AST = null;
				tmp49_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp49_AST);
				match(O_RW);
				expAND();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop280;
			}
			
		} while (true);
		}
		expr_AST = (AST)currentAST.root;
		returnAST = expr_AST;
	}
	
	public final void expAND() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expAND_AST = null;
		
		expCons();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop283:
		do {
			if ((LA(1)==Y_RW)) {
				AST tmp50_AST = null;
				tmp50_AST = astFactory.create(LT(1));
				astFactory.makeASTRoot(currentAST, tmp50_AST);
				match(Y_RW);
				expCons();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop283;
			}
			
		} while (true);
		}
		expAND_AST = (AST)currentAST.root;
		returnAST = expAND_AST;
	}
	
	public final void expCons() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expCons_AST = null;
		
		expSum();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop287:
		do {
			if ((_tokenSet_0.member(LA(1)))) {
				{
				switch ( LA(1)) {
				case IGUAL_QUE:
				{
					AST tmp51_AST = null;
					tmp51_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp51_AST);
					match(IGUAL_QUE);
					break;
				}
				case DISTINTO_QUE:
				{
					AST tmp52_AST = null;
					tmp52_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp52_AST);
					match(DISTINTO_QUE);
					break;
				}
				case MENOR_IGUAL:
				{
					AST tmp53_AST = null;
					tmp53_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp53_AST);
					match(MENOR_IGUAL);
					break;
				}
				case MAYOR_IGUAL:
				{
					AST tmp54_AST = null;
					tmp54_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp54_AST);
					match(MAYOR_IGUAL);
					break;
				}
				case MENOR_ESTRICTO:
				{
					AST tmp55_AST = null;
					tmp55_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp55_AST);
					match(MENOR_ESTRICTO);
					break;
				}
				case MAYOR_ESTRICTO:
				{
					AST tmp56_AST = null;
					tmp56_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp56_AST);
					match(MAYOR_ESTRICTO);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expSum();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop287;
			}
			
		} while (true);
		}
		expCons_AST = (AST)currentAST.root;
		returnAST = expCons_AST;
	}
	
	public final void expSum() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expSum_AST = null;
		
		expProduct();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop291:
		do {
			if ((LA(1)==SUMA||LA(1)==GUION)) {
				{
				switch ( LA(1)) {
				case SUMA:
				{
					AST tmp57_AST = null;
					tmp57_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp57_AST);
					match(SUMA);
					break;
				}
				case GUION:
				{
					AST tmp58_AST = null;
					tmp58_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp58_AST);
					match(GUION);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expProduct();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop291;
			}
			
		} while (true);
		}
		expSum_AST = (AST)currentAST.root;
		returnAST = expSum_AST;
	}
	
	public final void expProduct() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expProduct_AST = null;
		
		expMinus();
		astFactory.addASTChild(currentAST, returnAST);
		{
		_loop295:
		do {
			if ((LA(1)==MULTIPLICACION||LA(1)==DIVISION)) {
				{
				switch ( LA(1)) {
				case MULTIPLICACION:
				{
					AST tmp59_AST = null;
					tmp59_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp59_AST);
					match(MULTIPLICACION);
					break;
				}
				case DIVISION:
				{
					AST tmp60_AST = null;
					tmp60_AST = astFactory.create(LT(1));
					astFactory.makeASTRoot(currentAST, tmp60_AST);
					match(DIVISION);
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				expMinus();
				astFactory.addASTChild(currentAST, returnAST);
			}
			else {
				break _loop295;
			}
			
		} while (true);
		}
		expProduct_AST = (AST)currentAST.root;
		returnAST = expProduct_AST;
	}
	
	public final void expMinus() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expMinus_AST = null;
		
		switch ( LA(1)) {
		case GUION:
		{
			{
			match(GUION);
			expNot();
			astFactory.addASTChild(currentAST, returnAST);
			}
			expMinus_AST = (AST)currentAST.root;
			expMinus_AST=(AST)astFactory.make( (new ASTArray(2)).add(astFactory.create(UN_MINUS,"UN_MINUS")).add(expMinus_AST)) ;
			currentAST.root = expMinus_AST;
			currentAST.child = expMinus_AST!=null &&expMinus_AST.getFirstChild()!=null ?
				expMinus_AST.getFirstChild() : expMinus_AST;
			currentAST.advanceChildToEnd();
			expMinus_AST = (AST)currentAST.root;
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		case NEGACION:
		case SUMA:
		case IDENTIFICADOR:
		{
			{
			{
			switch ( LA(1)) {
			case SUMA:
			{
				match(SUMA);
				break;
			}
			case REAL_LITERAL:
			case ENTERO_LITERAL:
			case NEGACION:
			case IDENTIFICADOR:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			expNot();
			astFactory.addASTChild(currentAST, returnAST);
			}
			expMinus_AST = (AST)currentAST.root;
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		returnAST = expMinus_AST;
	}
	
	public final void expNot() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST expNot_AST = null;
		
		{
		switch ( LA(1)) {
		case NEGACION:
		{
			AST tmp63_AST = null;
			tmp63_AST = astFactory.create(LT(1));
			astFactory.makeASTRoot(currentAST, tmp63_AST);
			match(NEGACION);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		case IDENTIFICADOR:
		{
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		{
		switch ( LA(1)) {
		case IDENTIFICADOR:
		{
			AST tmp64_AST = null;
			tmp64_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp64_AST);
			match(IDENTIFICADOR);
			break;
		}
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		expNot_AST = (AST)currentAST.root;
		returnAST = expNot_AST;
	}
	
	public final void repetir() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST repetir_AST = null;
		
		AST tmp65_AST = null;
		tmp65_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp65_AST);
		match(REPETIR);
		{
		switch ( LA(1)) {
		case REAL_LITERAL:
		case ENTERO_LITERAL:
		{
			literal();
			astFactory.addASTChild(currentAST, returnAST);
			break;
		}
		case IDENTIFICADOR:
		{
			AST tmp66_AST = null;
			tmp66_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp66_AST);
			match(IDENTIFICADOR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		match(VECES);
		cuerpo();
		astFactory.addASTChild(currentAST, returnAST);
		match(FIN);
		match(REPETIR);
		repetir_AST = (AST)currentAST.root;
		returnAST = repetir_AST;
	}
	
	public final void mientras() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST mientras_AST = null;
		
		AST tmp70_AST = null;
		tmp70_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp70_AST);
		match(MIENTRAS);
		condicion();
		astFactory.addASTChild(currentAST, returnAST);
		entonces_hacer();
		astFactory.addASTChild(currentAST, returnAST);
		match(FIN);
		match(MIENTRAS);
		mientras_AST = (AST)currentAST.root;
		returnAST = mientras_AST;
	}
	
	public final void iterar() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST iterar_AST = null;
		
		AST tmp73_AST = null;
		tmp73_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp73_AST);
		match(ITERAR);
		desde();
		astFactory.addASTChild(currentAST, returnAST);
		hasta();
		astFactory.addASTChild(currentAST, returnAST);
		modificando();
		astFactory.addASTChild(currentAST, returnAST);
		entonces_hacer();
		astFactory.addASTChild(currentAST, returnAST);
		match(FIN);
		match(ITERAR);
		iterar_AST = (AST)currentAST.root;
		returnAST = iterar_AST;
	}
	
	public final void desde() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST desde_AST = null;
		
		AST tmp76_AST = null;
		tmp76_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp76_AST);
		match(DESDE);
		assign();
		astFactory.addASTChild(currentAST, returnAST);
		desde_AST = (AST)currentAST.root;
		returnAST = desde_AST;
	}
	
	public final void hasta() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST hasta_AST = null;
		
		AST tmp77_AST = null;
		tmp77_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp77_AST);
		match(HASTA);
		condicion();
		astFactory.addASTChild(currentAST, returnAST);
		hasta_AST = (AST)currentAST.root;
		returnAST = hasta_AST;
	}
	
	public final void modificando() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST modificando_AST = null;
		
		AST tmp78_AST = null;
		tmp78_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp78_AST);
		match(MODIFICANDO);
		incremento();
		astFactory.addASTChild(currentAST, returnAST);
		modificando_AST = (AST)currentAST.root;
		returnAST = modificando_AST;
	}
	
	public final void incremento() throws RecognitionException, TokenStreamException {
		
		returnAST = null;
		ASTPair currentAST = new ASTPair();
		AST incremento_AST = null;
		
		AST tmp79_AST = null;
		tmp79_AST = astFactory.create(LT(1));
		astFactory.makeASTRoot(currentAST, tmp79_AST);
		match(IDENTIFICADOR);
		{
		switch ( LA(1)) {
		case INCREMENTAR:
		{
			AST tmp80_AST = null;
			tmp80_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp80_AST);
			match(INCREMENTAR);
			break;
		}
		case DECREMENTAR:
		{
			AST tmp81_AST = null;
			tmp81_AST = astFactory.create(LT(1));
			astFactory.addASTChild(currentAST, tmp81_AST);
			match(DECREMENTAR);
			break;
		}
		default:
		{
			throw new NoViableAltException(LT(1), getFilename());
		}
		}
		}
		incremento_AST = (AST)currentAST.root;
		returnAST = incremento_AST;
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
	
	protected void buildTokenTypeASTClassMap() {
		tokenTypeToASTClassMap=null;
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 34621422135410688L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	
	}
