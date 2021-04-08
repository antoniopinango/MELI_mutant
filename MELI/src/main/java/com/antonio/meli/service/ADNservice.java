package com.antonio.meli.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.meli.entity.ADN;
import com.antonio.meli.exception.ADNexception;
import com.antonio.meli.exception.DBexception;
import com.antonio.meli.interfaxe.IADNinterfac;
import com.antonio.meli.repository.ADNRepository;

@Service
public class ADNservice implements IADNinterfac {

	private final char[] letrasPermitidas = { 'A', 'T', 'C', 'G' };
	private final int tamanoMinimo=4;

	/**
	 * Inyeccion de dependencia del repository
	 */
	@Autowired
	ADNRepository repo;

	/**
	 * Recorre lista. metodo principal donde se recorre la lista y se hace las
	 * validaciones correspondientes
	 * 
	 * @param lista the lista
	 * @return true, if successful
	 */
	@Override
	public boolean isMutant(List<String> lista){
		boolean charValido = false;
		int patronesIguales = 0;
		
		validarTamagno(lista);
		for (String elemento : lista) {
			charValido = recorreElemento(elemento.toUpperCase(), lista.size());
			if (!charValido) return charValido;
			if (validaHorizontal(elemento))
				patronesIguales++;
		}
		List<String> vertical = bidimensionalVertical(lista);
		patronesIguales += validaVertical(vertical);
		patronesIguales += validaDiagonal(lista);
		patronesIguales += validaDiagonalInverso(lista);
		
		if (patronesIguales >= 2) {
			save(lista, true);
			return true;
		}else {
			save(lista,false);
			return false;
		}
	}

	private void validarTamagno(List<String> lista) throws ADNexception{
		int tamagnoList= lista.size();
		if (tamagnoList< tamanoMinimo)
			throw new ADNexception("La lista no cuenta con el tamaño correcto para la validacion");
	}
	
	/**
	 * Recorre elemento. se recorre cada elemento del string pasado por parametros
	 * 
	 * @param elemento the elemento
	 * @return true, if successful
	 */
	private boolean recorreElemento(String elemento,int tamanoLista) {
		char[] adn = elemento.toCharArray();
		boolean flag = false;
		if (validaNxN(adn.length, tamanoLista)) {
			for (int i = 0; i < adn.length; i++) {
				flag = validaCarater(adn[i]);
				if (!flag)
					return flag;
			}
		}return flag;
		
	}
	
	/**
	 * Valida si la lista tiene igual tamaño
	 * Horizontal y verticalmente
	 * @param tamanoElemento
	 * @param tamanoLista
	 * @return
	 * @throws ADNexception
	 */
	private boolean validaNxN(int tamanoElemento, int tamanoLista) throws ADNexception {
		if (tamanoElemento==tamanoLista) {
			return true;
		}else {
			throw new ADNexception("La lista no cuenta con el tamaño correcto para la validacion");
		}
	}
	
	/**
	 * Valida carater. se valida si cada Base Nitrogenada es permitida
	 * 
	 * @param caracter the caracter
	 * @return true, if successful
	 */
	private boolean validaCarater(char caracter) throws ADNexception{
		boolean flag = false;
		for (int j = 0; j < letrasPermitidas.length; j++) {
			if (caracter == letrasPermitidas[j]) {
				flag = true;
				return flag;
			}
		}
		if (!flag) {
			throw new ADNexception("Tiene uno o mas caracteres invalidos");
		}
		return flag;
	}
	
	/**
	 * Valida HVO. Se valida si la cadena de string mandada por params contiene
	 * bases nitrogenadas iguales
	 * 
	 * @param elemento the elemento
	 * @return true, if successful
	 */
	private boolean validaHorizontal(String elemento) {
		boolean flag = false;
		String[] combinacion = { "CCCC", "TTTT", "AAAA", "GGGG" };
		for (int i = 0; i < combinacion.length; i++) {
			if (elemento.contains(combinacion[i])) {
				flag = true;
				return flag;
			}
		}
		return flag;
	}
	
	/**
	 * Valida vertical. recorre la lista que llega por parametros y valida
	 * 
	 * @param verticalList the vertical list
	 * @return the int
	 */
	private int validaVertical(List<String> verticalList) {
		boolean flag = false;
		int contador = 0;
		for (String elemento : verticalList) {
			flag = validaHorizontal(elemento);
			if (flag)
				contador++;
		}
		return contador;
	}
	
	/**
	 * Valida diagonal. se recorre una matriz bidimensional se valida si la letra es
	 * igual a la de abajo a la izquierda y se suma al contador si hay 4 iguales
	 * 
	 * @param dna the dna
	 * @return the int
	 */
	private int validaDiagonal(List<String> dna) throws ADNexception{
		int contador = 0;
		char[][] arregloFinal = crearMatrizB(dna);
		for (int i = 0; i < arregloFinal.length - 1; i++) {
			for (int j = 0; j < arregloFinal.length - 1; j++) {
				int columna = i;
				int fila = j;
//				valida si la posicion de j e i sea menor que la longitud del arreglo
				if (j < arregloFinal.length - 1 && i < arregloFinal.length - 1){
					if (arregloFinal[columna][fila] == arregloFinal[i + 1][j + 1]){
//						valida si la posicion de j e i sea menor que la longitud del arreglo
						if (j + 2 < arregloFinal.length - 1 && i + 2 < arregloFinal.length - 1){
							if (arregloFinal[columna][fila] == arregloFinal[i + 2][j + 2]){
//								valida si la posicion de j e i +3 sea menor que la longitud del arreglo
								if (j < arregloFinal.length - 1 && i < arregloFinal.length - 1){
									if (arregloFinal[columna][fila] == arregloFinal[i + 3][j + 3]){
										contador++;
									}
								}
							}
						}

					}
				}
			}
		}
		return contador;
	}
	
	/**
	 * Valida diagonal inverso.
	 * Se recorre el arreglo para validar la diagonal inversa
	 * o secundaria, 
	 * se valida si la posicion actual es igual a la de la
	 * siguiente fila y la posicion de la columna anterior
	 *
	 * @param dna the dna
	 * @return true, if successful
	 */
	public int validaDiagonalInverso(List<String> dna) {
		char[][] arregloFinal = crearMatrizB(dna);
		bidimensional(dna);
		int contador = 0;
		for (int i = 0; i < arregloFinal.length - 1; i++) {
			for (int j = arregloFinal.length - 1; j >= 0; j--) {
				int fila = i;
				int columna = j;
				if (i + 1 < arregloFinal.length - 1 && j - 1 <= arregloFinal.length - 1 && j - 1 > 0) {
					if (arregloFinal[fila][columna] == arregloFinal[i + 1][j - 1]) {
						if (i + 2 < arregloFinal.length - 1 && j - 2 < arregloFinal.length - 1 && j - 2 > 0) {
							if (arregloFinal[fila][columna] == arregloFinal[i + 2][j - 2]) {
								if (i + 3 <= arregloFinal.length - 1 && j - 3 < arregloFinal.length - 1 && j - 3 >= 0) {
									if (arregloFinal[fila][columna] == arregloFinal[i + 3][j - 3]) {
										contador++;
									}
								}
							}
						}
					}
				}
			}
		}
		return contador;
	}
	
	/**
	 * Crear matriz B. crea una matriz bidimensional pasandole una lista de string
	 * por parametros
	 * 
	 * @param dna the dna
	 * @return the char[][]
	 */
	public char[][] crearMatrizB(List<String> dna) {
		char[][] arregloFinal = new char[dna.size()][dna.size()];
		for (int i = 0; i <= arregloFinal.length - 1; i++) {
			char[] caracteres = dna.get(i).toCharArray();
			for (int j = 0; j <= arregloFinal.length - 1; j++) {
				arregloFinal[i][j] = caracteres[j];
			}
		}
		return arregloFinal;
	}

	/**
	 * Metodo para guardar el ADN y si es mutante o humano en BD
	 * @param adn
	 * @param mutante
	 * @throws DBexception
	 */
	private void save(List<String> adn, boolean mutante) throws DBexception{
		ADN modelo= new ADN(adn.toString(), mutante);
		repo.save(modelo);					
	}

	/**
	 * Bidimensional vertical. metodo que arma matriz bidimensional, la recorre, y
	 * arma una lista por cada elemento, y luego devuelve la lista
	 * 
	 * @param dna the dna
	 * @return the list
	 */
	private List<String> bidimensionalVertical(List<String> dna) {
		String cadenaVertical = "";
		char[][] arreglo = crearMatrizB(dna);
		int contador = 0;
		List<String> arregloVertical = new ArrayList<String>();
		for (int j = 0; j <= arreglo.length - 1; j++) {
			for (int i = 0; i <= arreglo.length - 1; i++) {
				contador++;
				cadenaVertical += arreglo[i][j];
				if (contador == arreglo.length) {
					contador = 0;
					arregloVertical.add(cadenaVertical);
					cadenaVertical = "";
				}
			}
		}
		return arregloVertical;
	}
	
	/**
	 * Bidimensional. imprime ordenadamente el arreglo bidimensional
	 * 
	 * @param dna the dna
	 */
	public void bidimensional(List<String> dna) {
		char[][] arreglo = crearMatrizB(dna);
		for (int i = 0; i <= arreglo.length - 1; i++) {
			for (int j = 0; j <= arreglo.length - 1; j++) {
				System.out.print(arreglo[i][j]);
				System.out.print(" ");
			}
			System.out.println("");
		}
	}

}
