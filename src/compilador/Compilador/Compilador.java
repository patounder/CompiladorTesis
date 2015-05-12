package compilador.Compilador;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import compilador.Componentes.GeneradorCodigo;
import compilador.Componentes.AnalizadorSintactico;
import compilador.Componentes.AnalizadorSemantico;
import compilador.Componentes.AnalizadorLexico;

import antlr.RecognitionException;
import antlr.SemanticException;
import antlr.Token;
import antlr.TokenStreamException;
import antlr.collections.AST;
import antlr.debug.misc.ASTFrame;

/**
 * The main class of the Mile Language
 * 
 * @author Ricardo Soto
 * @since 1.5
 */

public class Compilador {

	private static String fileName = "";
	private static FileInputStream fis = null;
	private static ArrayList<SemanticException> excepSem;

	public static void main(String args[]) {
		try {
			setSourceFile(args);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Set source file and arguments.
	 * 
	 * @param args
	 */
	public static void setSourceFile(String args[]) {
		int i = args.length - 1;
		try {
			setFileName(args);
			setFis(new FileInputStream(args[i]));
			
//			 testLexico();
//			 testSintactico();
//			 testSemantico();
			 generacionCodigo();
			
			
		} catch (FileNotFoundException ex) {//Excepciones al abrir el c.fuente
			 System.err.println(ex.getMessage()
           			 +".\nEvite utilizar espacio en nombre de directorios.");
			
			//			ex.printStackTrace();
		}
	}

	/**
	 * Setea el archivo de entrada
	 * 
	 * @param fis
	 */
	public static void setFis(FileInputStream fisIn) {
		fis = fisIn;
	}

	/**
	 * Setea el nombre del archivo a leer
	 * 
	 * @param fileName
	 */
	public static void setFileName(String arg[]) {
		fileName = arg[0];
	}

	/**
	 * Metodo para probar el analisis lexico
	 */
	public static void testLexico() {
		try {

			AnalizadorLexico lexer = new AnalizadorLexico(fis);
			lexer.setFilename(fileName);
			lexer.setTokenObjectClass("antlraux.util.LexInfoToken");
			Token tok = lexer.nextToken();

			while (tok.getType() != Token.EOF_TYPE) {
//				System.out.println("Linea" + tok.getLine() + " = "
//						+ tok.getText());
				tok = lexer.nextToken();
			}

			System.out.println("Tokens Ok");
		} catch (TokenStreamException tse) {
			
			String str;

			str = tse.getMessage();
			str = str.replace("expecting", "Esperando");
			str = str.replace("found", "Encuentra");
			str = str.replace("unexpected", "Inesperado");
			System.err.println("Error leyendo token. " + str);
		
		
		}
	}

	/**
	 * Metodo para la prueba del analisis sintactico
	 * 
	 */
	public static void testSintactico() {
		try {
			AnalizadorLexico lexer = new AnalizadorLexico(fis);
			lexer.setFilename(fileName);
			lexer.setTokenObjectClass("antlraux.util.LexInfoToken");

			AnalizadorSintactico parser = new AnalizadorSintactico(lexer);
			parser.setFilename(fileName);
			parser.programa();

			AST ast = parser.getAST();
			final ASTFrame frame1 = new ASTFrame("", ast);
			frame1.setVisible(true);
		
		} catch (RecognitionException ex) {
			
			String str;
			int linea = (ex.getLine() / 2)+1;
			
			str = ex.getMessage();
			str = str.replace("expecting", "esperando");
			str = str.replace("found", "encuentra");
			str = str.replace("unexpected", "inesperado");
			System.err.println("Error Linea: " + linea + " " + str);
		
		} catch (TokenStreamException te) {

			String str;

			str = te.getMessage();
			str = str.replace("expecting", "Esperando");
			str = str.replace("found", "Encuentra");
			str = str.replace("unexpected", "Inesperado");
			System.err.println("Error leyendo token. " + str);
		}
	}

	/**
	 * Metodo para probar el analisis semantico
	 * 
	 */
	public static void testSemantico() {

		try {
			AnalizadorLexico lexer = new AnalizadorLexico(fis);
			lexer.setFilename(fileName);
			lexer.setTokenObjectClass("antlraux.util.LexInfoToken");

			AnalizadorSintactico parser = new AnalizadorSintactico(lexer);
			parser.setFilename(fileName);
			parser.programa();
			AST ast = parser.getAST();

			// Comienzo del analisis semantico, se le pasa como parametro el AST
			// generado
			AnalizadorSemantico treeParser = new AnalizadorSemantico();
			treeParser.programa(ast);
			
			Compilador.excepSem = compilador.Componentes.InspectorSemantico
					.getExcepSemantic();

			if (!Compilador.excepSem.isEmpty()) {
				throw new SemanticException("");
			} else {

				final ASTFrame frame1 = new ASTFrame("", ast);
				frame1.setVisible(true);
			}
		}	catch (SemanticException ex) {
			// ex.printStackTrace();
			// System.err.println("Error leyendo tokens: " + ex.getMessage());

			for (SemanticException se : Compilador.excepSem) {
				System.err.println(se.getMessage());
			}
		}	catch (RecognitionException ex) {
			
			String str;
			int linea = (ex.getLine() / 2)+1;
			
			str = ex.getMessage();
			str = str.replace("expecting", "esperando");
			str = str.replace("found", "encuentra");
			str = str.replace("unexpected", "inesperado");
			System.err.println("Error Linea: " + linea + " " + str);
		
		} 	catch (TokenStreamException te) {

			String str;

			str = te.getMessage();
			str = str.replace("expecting", "Esperando");
			str = str.replace("found", "Encuentra");
			str = str.replace("unexpected", "Inesperado");
			System.err.println("Error leyendo token. " + str);
		}
	}

	/**
	 * Metodo en donde efectivamente se realizan todas las etapas de un
	 * compilador y se genera el codigo .nxc
	 */
	public static void generacionCodigo() {

		try {

			// System.out.println("Analisis Lexico...");
			AnalizadorLexico lexer = new AnalizadorLexico(fis);
			lexer.setFilename(fileName);
			lexer.setTokenObjectClass("antlraux.util.LexInfoToken");

			// System.out.println("Analisis Sintactico...");
			AnalizadorSintactico parser = new AnalizadorSintactico(lexer);
			parser.setFilename(fileName);
			parser.programa();

			AST ast = parser.getAST();
			
			AnalizadorSemantico treeParser = new AnalizadorSemantico();
			treeParser.programa(ast);

			Compilador.excepSem = compilador.Componentes.InspectorSemantico
					.getExcepSemantic();

			if (!Compilador.excepSem.isEmpty()) {
				throw new SemanticException("");
			} else {//Pregunta solo por la excepciones semanticas, porque los demas no estan en una lista

				GeneradorCodigo codeGen = new GeneradorCodigo();
				codeGen.programa(ast);
				// final ASTFrame frame1 = new ASTFrame("", ast);
				// frame1.setVisible(true);
				System.out.println("Compilado con Exito !");
			}

		} catch (SemanticException st) {

			for (SemanticException se : Compilador.excepSem) {
				System.err.println(se.getMessage());
			}

		} catch (TokenStreamException tE) {
			String str;

			str = tE.getMessage();
			str = str.replace("expecting", "esperando");
			str = str.replace("found", "encuentra");
			str = str.replace("unexpected", "inesperado");
			System.err.println("Error Lexico, " + str);
		} catch (RecognitionException rE) {

			String str;

			int linea = (rE.getLine() / 2) + 1;

			str = rE.getMessage();
			str = str.replace("expecting", "esperando");
			str = str.replace("found", "encuentra");
			str = str.replace("unexpected", "inesperado");
			System.err.println("Error linea " + linea + " : " + str);
		}
	}
	
	public static String getFileName() {
		return fileName;
	} 

}
