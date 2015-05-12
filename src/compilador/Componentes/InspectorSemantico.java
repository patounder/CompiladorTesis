package compilador.Componentes;

import java.util.ArrayList;
import antlr.collections.AST;
import antlr.SemanticException;

/**
 * Performs the semantic checking for the Mile language.
 * 
 * 
 * @author Patricio Quiroz Vargas<br>
 * @since 1.5<br>
 */

public class InspectorSemantico {

	// Lista de todos los que actualmente en el programa
	private ArrayList<String> listaNombreId = new ArrayList<String>();
	
	// Lista de los operadores en una expresión
	private ArrayList<String> listaOperadores = new ArrayList<String>();
	/**
	 *  Lista de las operandos totales del programa.
	 * */
	private ArrayList<Operando> listaOperandos = new ArrayList<Operando>();
	private ArrayList<String> listaSalidas = new ArrayList<String>();

	private static ArrayList<SemanticException> excep = new ArrayList<SemanticException>();

	// ============================COMIENZO METODOS============================================================================
	/**
	 * Método que se encarga de agregar un operando de la asignación y chequear
	 * si es variable o constante que no se este redefiniendo, de ser constante
	 * sea asignado su valor correcto
	 */
	public void addOperando(AST tipo, AST id, boolean constnt, String valor)
			throws SemanticException {

		if (listaNombreId.contains(id.toString())) {

			int linea = (id.getLine()/2)+1;
			try {
				throw new SemanticException("Error Linea "
						+ linea + ": re-definicion de variable '" + id
						+ "'");
			} catch (SemanticException se) {
				getExcepSemantic().add(se);
			}
			
			//this.semanticError(id, "re-definicion de variable '" + id + "'");

		} else {

			if (!constnt) { // Es una variable
				Operando auxOperando = new Operando();

				auxOperando.setTipo(tipo.toString());
				auxOperando.setId(id.toString());
				auxOperando.setConstante(false);

				getListaOperandos().add(auxOperando);// Se agrega a la lista de
														// variables
				listaNombreId.add(id.toString());// Y a la lista de nombres

				// System.out.println("se agrega variable "+
				// auxOperando.getId());
			} else {// Es una constante
					// Se comprueba que el valor asignado a la constante sea
					// correcto

				if ((valor.contains(".") && tipo.toString().equals("Entero"))
						|| (!valor.contains(".") && tipo.toString().equals(
								"Real"))) {
					
					int linea = (tipo.getLine()/2)+1;
					try {
						throw new SemanticException("Error Linea "
								+ linea + ": Constante '" + id
								+ "' y valor asignado '" + valor
								+ "' no son compatibles");
					} catch (SemanticException se) {
						getExcepSemantic().add(se);
					}

				} else {
					
					Operando auxOperando = new Operando();
					auxOperando.setTipo(tipo.toString());
					auxOperando.setId(id.toString());
					auxOperando.setConstante(true); // Se almacena su condicion
													// de constante pero no el
													// valor
					this.getListaOperandos().add(auxOperando);
					listaNombreId.add(id.toString());
				}

			}

		}
	}

	/**
	 * Método que se encarga chequear que identificador este definido
	 * */
	public void checkOperando(AST var) throws SemanticException {

		if (!listaNombreId.contains(var.toString())) {

			int linea = (var.getLine()/2)+1;
			try {
				throw new SemanticException("Error Linea " + linea
						+ ": identificador '" + var.toString()
						+ "' no esta definido");
			} catch (SemanticException se) {
				getExcepSemantic().add(se);
			}
		}
	}
	
	/**
	 * Método encargado de chequear que una constante no este al lado izquierdo
	 * de una asignación
	 * */
	public void constanteNoIzquerda(AST id) {

		for (Operando oper : getListaOperandos()) {
			if (oper.getId().equals(id.toString()) && oper.isConstante()) {
				int linea = (id.getLine()/2)+1;
				try {
					throw new SemanticException(
							"Error Linea "
									+ linea
									+ ": No es posible cambiar el valor de una constante.");
				} catch (SemanticException se) {
					getExcepSemantic().add(se);
				}
				// this.semanticError(id.get,
				// " No es posible cambiarle el valor de una constante.");

			}

		}
	}
	
	/**
	 * Método que chequea que operandos en una asignación sean del mismo tipo
	 * */
	public void checkTipos(ArrayList<AST> listVar) {

		ArrayList<Operando> listaEncon = new ArrayList<Operando>();

		int i = 0;
		int j = 0;

		while (j < listVar.size()) {

			while (i < getListaOperandos().size()) {

				if (getListaOperandos().get(i).getId()
						.equals(listVar.get(j).toString())) {
					listaEncon.add(getListaOperandos().get(i));
				}

				i++;

			}

			i = 0;
			j++;
		}

		i = listaEncon.size() - 1;

		while (i >= 1) {
			if (!(listaEncon.get(i).getTipo().equals(listaEncon.get(i - 1)
					.getTipo()))) {

				try {
					throw new SemanticException(
							"Incompatibilidad de tipos en las variables '"
									+ listaEncon.get(i).getId() + "' y '"
									+ listaEncon.get(i - 1).getId()+"'");
				} catch (SemanticException se) {
					getExcepSemantic().add(se);
				}
				i = 1;// para salir del bucle
			}

			i--;
		}

		listaEncon.clear();
	}
	
	/**
	 * Método encargado de chequear que en una asignación donde se considere un
	 * literal, este sea del mismo tipo de dato que de los restantes operandos.
	 * Se necesita solo un miembro de la lista listVar, ya que todos los demás
	 * son del mismo tipo (Esto se comprueba con el método checkTipos()
	 * */
	public void checkLiteralEnAsign(ArrayList<AST> listVarAct, AST literal) {

		Operando auxOperando=null;

		for(Operando op : getListaOperandos()){
			//Solo se busca el primer elemento de listVar
			//en la listaOperandos
			if (op.getId().equals(listVarAct.get(0).toString())){
				auxOperando = op;
			}
		}
		
		if (auxOperando != null) {
			if ((literal.toString().contains(".") && auxOperando.getTipo()
					.equals("Entero"))
					|| (!literal.toString().contains(".") && auxOperando
							.getTipo().equals("Real"))) {

				int linea = (literal.getLine()/2)+1; //*****Por alguna razon no asocia de manera correcta la linea
				
				try {
					throw new SemanticException("Error Linea "
							+ linea + ": Valor '"
							+ literal.toString()
							+ "' no compatible con la asignacion.");
				} catch (SemanticException se) {
					getExcepSemantic().add(se);
				}

			}

		}
		
	}
	
	/**
	 * Método que se encarga de que se mencione una salida (MOTOR_A, B o C) en
	 * una instrucción de movimiento
	 * */
	public void addAndCheckSalidas(AST var) throws SemanticException {

		if (this.listaSalidas.contains(var.toString())) {
			
			int linea = (var.getLine()/2)+1;
			
			try {
				throw new SemanticException("Error Linea "+ linea+" : '" + var.toString()
						+ "' ya se ha senalado");
			} catch (SemanticException e) {
				getExcepSemantic().add(e);
			}
		} else {
			this.listaSalidas.add(var.toString());
		}
	}
	
	public void clearListaNombreSalidas() {
		this.listaSalidas.clear();
	}
	

	// ============================FIN	METODOS============================================================================

	/**
	 * Método que se ocupa para señalar mensajes de tipo semántico
	 */
	public void semanticError(AST ast, String message) {
		System.err.println("ERROR LINEA: " + ast.getLine() + " " + message);
	}

	public void recorrerOperandos() {
		System.out.println(getListaOperandos().size());

		if (!getListaOperandos().isEmpty()) {

			int i = 0;

			while (getListaOperandos().size() > i) {

				System.out.println("Operando "
						+ getListaOperandos().get(i).getId() + " de tipo "
						+ getListaOperandos().get(i).getTipo()
						+ " y constante "
						+ getListaOperandos().get(i).isConstante());

				i++;
			}
		}

	}

	

	/**
	 * Método se encarga de analizar que los valores mantenidos actualmente en
	 * listVar sean del mismo tipo
	 */
	public void checkListVar(ArrayList<AST> listVar, String tipo)
			throws SemanticException {

		ArrayList<Operando> listaEncon = new ArrayList<Operando>();
		int i = 0;
		int j = 0;

		while (i < listVar.size()) {
			System.out.println(listVar.get(i));
			i++;

		}
		i = 0;
		// En este while se buscan las variables en listaVariables
		while (j < listVar.size()) {

			while (i < getListaOperandos().size()) {

				if (getListaOperandos().get(i).getId()
						.equals(listVar.get(j).toString())) {
					listaEncon.add(getListaOperandos().get(i));
				}
				i++;
			}
			// Para empezar desde el principio de listaVariables pero ahora
			// buscando el siguiente valor de listVar
			i = 0;
			j++;
		}

		// Se recorre la lista de encontrados para comparar los tipos
		i = 0;
		while (i < listaEncon.size()) {

			if (!(listaEncon.get(i).getTipo().equals(tipo))) {
				try {
					throw new SemanticException("Tipo de dato \""
							+ listVar.get(i).toString()
							+ "\" y valor asignado incorrecto.");
				} catch (SemanticException e) {
					getExcepSemantic().add(e);
				}
				// this.semanticError(listVar.get(i), ". Tipo de dato '"
				// + listVar.get(i).toString()
				// + "' y valor asignado incorrecto");
			}
			i++;
		}
		listaEncon.clear();
	}

	

	

	public void clearOperatorTable() {
		listaOperadores.clear();
	}

	public void addOperator(AST op) throws SemanticException {

		if (listaOperadores.contains(op.toString())) {

			try {
				int linea = (op.getLine()/2)+1;
				throw new SemanticException("Error Linea " + linea
						+ ": Simbolo repetido '" + op + "'");
			} catch (SemanticException se) {
				getExcepSemantic().add(se);
			}

		} else {
			listaOperadores.add(op.toString());
		}
	}

	public static ArrayList<SemanticException> getExcepSemantic() {
		return excep;
	}

	private ArrayList<Operando> getListaOperandos() {
		return listaOperandos;
	}

}
