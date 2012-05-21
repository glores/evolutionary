package aGeneticos.controlador;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

public class Mapa {
	private boolean casillas[][];
	private int filas, cols;
	public Mapa(){
		
	}
	
	public void cargarMapa(String ruta){
		try {
			BufferedReader lector=new BufferedReader(new FileReader(ruta));
			//Filas
			String linea=lector.readLine();
			filas=Integer.parseInt(linea);
			//Columnas
			linea=lector.readLine();
			cols=Integer.parseInt(linea);
			//Mapa
			casillas=new boolean[filas][cols];	
			String []lineaPartida;
			for(int i=0;i<filas;i++){
				linea=lector.readLine();
				lineaPartida=linea.split(" ");
				for(int j=0;j<cols;j++){
					casillas[i][j]=parsear(lineaPartida[j]);
				}
			}
			
		} catch (FileNotFoundException e) {
			Logger.getLogger("CP").severe("Problema al abrir el archivo del mapa.");			
		} catch (IOException e) {
			Logger.getLogger("CP").severe("Problema al leer el archivo del mapa.");			
		}
	}
	
	private boolean parsear(String cadena){		
			return cadena.equals("1");		
	}
	
	public boolean getCasilla(int x, int y){
		return casillas[x][y];
	}
	
	public int getNumFilas(){
		return filas;
	}
	
	public int getNumCols(){
		return cols;
	}
	
	public Object clone(){
		Mapa clon= new Mapa();
		clon.casillas=new boolean[filas][cols];
		for(int i=0;i<filas;i++){			
			for(int j=0;j<cols;j++){
				clon.casillas[i][j]=casillas[i][j];
			}
		}
		clon.cols=this.cols;
		clon.filas=this.filas;
		return clon;
	}
	
	public void comer(int x, int y){
		casillas[x][y]=false;
	}
}
