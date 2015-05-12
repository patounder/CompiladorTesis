package compilador.Componentes;

public class Operando {

	/**
	 * @uml.property name="tipo"
	 */
	String tipo;
	/**
	 * @uml.property name="id"
	 */
	String id;

	private boolean constante = false;

	/**
	 * @return
	 * @uml.property name="tipo"
	 */
	public String getTipo() {
		return tipo;
	}

	/**
	 * @param tipo
	 * @uml.property name="tipo"
	 */
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	/**
	 * @return
	 * @uml.property name="id"
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 * @uml.property name="id"
	 */
	public void setId(String id) {
		this.id = id;
	}

	// Retorna el valor de la constante
	public boolean isConstante() {
		return constante;
	}

	public void setConstante(boolean constante) {
		this.constante = constante;
	}

}
