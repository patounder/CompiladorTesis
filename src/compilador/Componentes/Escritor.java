package compilador.Componentes;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import compilador.Compilador.Compilador;

import antlr.collections.AST;

/**
 * Performs the code generation for the Mile language.
 * 
 * 
 * @author Ricardo Soto<br>
 * @since 1.5<br>
 */

public class Escritor {
    
	
	
	/**
	 * @uml.property  name="pW"
	 */
	private PrintWriter pW = null;
    private String rutaUnder = Compilador.getFileName();
    private String rutaNXC ;
    
    //========================================METODOS REVISADOS==================================================================
    public Escritor() {
    	this.buildFile();
    }
    
    public void buildFile() {
       
       rutaNXC=rutaUnder.replace(".under", ".nxc");
       this.createFile(rutaNXC);
    }
 
    /**
     * Associates the print writer to an output file, the file name is given.
     *
     * @param st
     *            the name of the file
     */
    public void createFile(String st) {
        try {  
            FileWriter fW = new FileWriter(st);
            BufferedWriter bW = new BufferedWriter(fW);
            this.pW = new PrintWriter(bW);
        } catch (IOException ioe) {
            System.err.println("Ruta no existe: '" + st + "'");
        }
    }
    
    public void addVar(AST type, AST id) {
    	String st = "";
    	if(type.toString().equals("entero")) {
    		st = "int " + id.toString();
    	} else {
    		
    		if(type.toString().equals("real")){
    			st = "float " + id.toString();
        	}else{
        		if(type.toString().equals("carac")){
        			st = "char " + id.toString();
            	}else{
            		st = "int " + id.toString();            		
            	}
        		
        	}
    		
    	}
    	
    	
    	pW.print(st);
    }
    
    public void addConst(AST td,AST id,AST lit) {
    	
    	String st = "#define "+id.toString() + " "+ lit.toString();
    	
//    	if(td.toString().equals("entero")) {
//    		st += "int " +id.toString() + " "+ lit.toString();
//    	} else {
//    		
//    		if(td.toString().equals("real")){
//    			st += "float " + id.toString()+ " "+ lit.toString();
//        	}else{
//        		if(td.toString().equals("carac")){
//        			st += "char " + id.toString()+ " "+ lit.toString();
//            	}else{
//            		st += "int " + id.toString()+ " "+ lit.toString();            		
//            	}
//        		
//        	}
//    		
//    	}
    	
       	pW.print(st);
    }
    
    public void addMain() {
    	pW.println("task main(){");
    	pW.println("");

    }
    
    public void end() {
    	pW.println("");
    	pW.print("}");
       	this.closeFile();
    	
    }
    
    public void addAvanzar(boolean sincronizado) {
   	
    	//Con el atributo sincronizado se define la isntrucciona a utilizar
    	if(sincronizado)pW.print("OnFwdReg(");
        else pW.print("OnFwd(");
   
	}
    
    public void printOut(ArrayList<AST> listOuts) {
    	
    	String st = "OUT_";
    	
    	for(AST auxOut: listOuts){
    		if (auxOut.getText().contains("A")) st = st+"A";
    		if (auxOut.getText().contains("B")) st = st+"B";
    		if (auxOut.getText().contains("C")) st = st+"C";
    	}

    	pW.print(st);
    	
	}
    
    
//    En esta funcion se determinara si se agrega o no la 
    public void printVelocidad(boolean sincronizado){
    	
    	if(sincronizado)	pW.print(",75,OUT_REGMODE_SYNC");
    	else	pW.print(",75");
    	 
    }
    
    public void printIzqDer(){
    	pW.println("(");
    }
    
    public void printRedDer(){
    	pW.print(")");
    }
    
    public void printLlaveDer(){
    	pW.println("}");
    }
    
    public void printLlaveIzq(){
    	pW.println("{");
    }
    
    public void addMantener() {
    	String st = "Wait(";
       	pW.print(st);
	}
    
    public void printNum(AST num, boolean segundos, boolean idWait){
    	
    	//Variable segundos se utiliza para parsear numeros de formato String
    	//Variable idWait es true si la variable fue mandada desde un avanzar
    	
	    if (segundos){	
	    	if(num.toString().contains(".")){
	    		pW.print(Float.valueOf(num.toString()).floatValue()*1000);
	    	}else{
	    		pW.print(Integer.parseInt(num.toString())*1000);
	    	}
	    }else{
	    	if(idWait) pW.print(num.toString()+"*1000");
	    	else pW.print(num.toString());
	    }	
	    	
    }
    
    public void printId(AST id){
    	pW.print(id.toString());
    }
    
    public void addRetrocede(boolean sincronizado) {
    	
    	if(sincronizado)pW.print("OnRevReg(");
    	else pW.print("OnRev(");
	}
    
    public void addApagar() {
    	String st = "Off(";
       	pW.print(st);
	}
    
    public void printAsign(){
    	pW.print("=");
    }
    
    public void printPtoYComa(){
    	pW.print(";");
    }

    public void println(){
    	pW.println();
    }
    
    public void printImprimir(AST text) {
    	String st = "NumOut(10,10,"+text.toString();
       	pW.print(st);
       	printRedDer();
	}
    
    public void printImprimir(AST text, String comilla) {
    	String st = "TextOut(10,10,"+comilla+text.toString()+comilla;
       	pW.print(st);
       	printRedDer();
	}

    public void printSuma(){
    	pW.print("+");
    }
    
    public void printGuion(){
    	pW.print("-");
    }
    
    public void printMulti(){
    	pW.print("*");
    }
    
    public void printDivision(){
    	pW.print("/");
    }
    
    public void printIncrementar(){
    	pW.print("++ ");
    }
    
    public void printDecrementar(){
    	pW.print("-- ");
    }

    public void printOr(){
    	pW.print(" || ");
    }
    
    public void printAnd(){
    	pW.print(" && ");
    }
    
    public void printMenorEst(){
    	pW.print(" < ");
    }
    
    public void printMayorEst(){
    	pW.print(" > ");
    }
    
    public void printIniSi(){
    	pW.print("if(");
    }
    
    public void printIniSino(){
    	pW.print("else{\n");
    	
    }
    
    public void printRepeat() {
		pW.print("repeat(");
	}
    
    public void printEndRepeat(){
    	pW.println("){");
    }
    
    public void printIniMientras(){
    	pW.print("while(");
    }
    
    public void printIniFor(){
    	pW.println();
    	pW.print("	for(");
    }
    
    public void printGiro(AST fwd, AST rev, AST salida){
    	
    	String out="OUT_";
    	
       		if (salida.toString().contains("A")) out = out+"A";
       		else if (salida.toString().contains("B")) out = out+"B";
       			else  out = out+"C";
    	
    	String str="RotateMotor( "+out+", 50,";
    	
    	if(fwd != null){
    		str+= " 375);";
    	}else{
    		str+=" -375);";
    	}
    	
    	pW.print(str);
    }
    
    public void printTab(int veces){
    	String str="";
    	
    	for(int i = 0; i<veces ; i++){
    		str+="\t";
    	}
    	
    	pW.print(str);	
    }
    //====================================FIN METODOS REVISADOS==================================================================

    public void inicioCuerpo(){
    	pW.println("");
    	pW.println("");
    } 
          
    public void printComa(){
    	pW.print(",");
    }

    public void printEspacio(){
    	pW.print(" ");
    }
    
    public void printLit(Object num){
    	pW.print(num);
    }
   
    public void printMenorIgual(){
    	pW.print("<=");
    }
    
    public void printMayorIgual(){
    	pW.print(">=");
    }
    
    public void printNegacion(){
    	pW.print("!");
    }
    
    
    

    /**
     * Closes the output file.
     */
    public void closeFile() {
    	pW.close();
    }

    
}


 