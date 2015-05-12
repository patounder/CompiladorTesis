// $ANTLR : "GeneradorCodigo.g" -> "GeneradorCodigo.java"$

/**
 * Semantic analysis for the Mile Language.
 * 
 * @author Ricardo Soto
 * @since 1.5
 */

package compilador.Componentes;
import java.util.ArrayList;


public interface GeneradorCodigoTokenTypes {
	int EOF = 1;
	int NULL_TREE_LOOKAHEAD = 3;
	int TIPO_ENTERO_PR = 4;
	int TIPO_REAL_PR = 5;
	int TIPO_BOOLEANO = 6;
	int CONST_PR = 7;
	int INICIO = 8;
	int DEL = 9;
	int PROGRAMA = 10;
	int PRINCIPAL = 11;
	int AVANZAR_PR = 12;
	int MANTENER_PR = 13;
	int RETROCEDER_PR = 14;
	int APAGAR_PR = 15;
	int IMPRIMIR_PR = 16;
	int GIRAR_PR = 17;
	int SEGUNDOS_PR = 18;
	int ADELANTE_PR = 19;
	int ATRAS_PR = 20;
	int SINCRONIZADOS_PR = 21;
	int MOTOR_APR = 22;
	int MOTOR_BPR = 23;
	int MOTOR_CPR = 24;
	int SI = 25;
	int ENTONCES = 26;
	int SINO = 27;
	int FIN = 28;
	int Y_RW = 29;
	int O_RW = 30;
	int ITERAR = 31;
	int VECES = 32;
	int MIENTRAS = 33;
	int REPETIR = 34;
	int DESDE = 35;
	int HASTA = 36;
	int MODIFICANDO = 37;
	int REAL_LITERAL = 38;
	int ENTERO_LITERAL = 39;
	int NEGACION = 40;
	int GUION_BAJO = 41;
	int SUMA = 42;
	int GUION = 43;
	int MULTIPLICACION = 44;
	int DIVISION = 45;
	int INCREMENTAR = 46;
	int DECREMENTAR = 47;
	int IGUAL_QUE = 48;
	int DISTINTO_QUE = 49;
	int ASIGN = 50;
	int MENOR_ESTRICTO = 51;
	int MAYOR_ESTRICTO = 52;
	int MENOR_IGUAL = 53;
	int MAYOR_IGUAL = 54;
	int DIGITO = 55;
	int NUM_LITERAL = 56;
	int LETRA = 57;
	int IDENTIFICADOR = 58;
	int COMILLAS = 59;
	int CADENA_LITERAL = 60;
	int WS = 61;
	int COMENTARIO_LINEA = 62;
	int COMENTARIO_BLOQUE = 63;
	int PROGRAMA_ = 64;
	int DECLARACIONES = 65;
	int CUERPO = 66;
	int CONSTANTE = 67;
	int VARIABLE = 68;
	int TIPO_DATO = 69;
	int ID = 70;
	int VALOR = 71;
	int AVANZAR = 72;
	int MANTENER = 73;
	int RETROCEDER = 74;
	int APAGAR = 75;
	int ASIGNACION = 76;
	int IMPRIMIR = 77;
	int SI_ = 78;
	int CONDICION = 79;
	int SINO_ = 80;
	int UN_MINUS = 81;
	int REPETIR_ = 82;
	int ITERAR_ = 83;
}
