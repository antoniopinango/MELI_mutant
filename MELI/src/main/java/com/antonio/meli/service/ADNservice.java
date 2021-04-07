package com.antonio.meli.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.antonio.meli.entity.ADN;
import com.antonio.meli.exception.ADNexception;
import com.antonio.meli.exception.DBexception;
import com.antonio.meli.interfaxe.IADNinterfac;
import com.antonio.meli.repository.ADNRepository;

@Service
public class ADNservice implements IADNinterfac {

	private static char[] letrasPermitidas = { 'A', 'T', 'C', 'G' };
	private static int tamagnoArray=4;

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
	public boolean isMutant(List<String> lista) throws DBexception, ADNexception {
		boolean charInvalido = false;
		int patronesIguales = 0;
		
		validarTamagno(lista);
		for (String elemento : lista) {
			ADN adn = new ADN(elemento);
			save(adn);
			charInvalido = recorreElemento(elemento.toUpperCase());
			if (!charInvalido) return charInvalido;
			if (validaHorizontal(elemento))
				patronesIguales++;
		}
		List<String> vertical = bidimensionalVertical(lista);
		patronesIguales += validaVertical(vertical);
		patronesIguales += validaDiagonal(lista);
		patronesIguales += validaDiagonalInverso(lista);
		System.out.println(patronesIguales);
		if (patronesIguales >= 2)
			return true;
		
		return null != null;
	}

	private void validarTamagno(List<String> lista) throws ADNexception{
		int tamagnoList= lista.size();
		if (tamagnoList< tamagnoArray)
			throw new ADNexception("La lista no cuenta con el tamaño correcto para la validacion");
	}

	private void save(ADN adn) throws DBexception{
		try {
			Optional<String> opt=repo.findByAdn(adn.getAdn());
			if (opt.isEmpty()||opt == null) {
				repo.save(adn);
			}
				
		} catch (Exception e) {
			throw new DBexception("Error en la persistencia de la base de datos.");
		}	
	}

	/**
	 * Recorre elemento. se recorre cada elemento del string pasado por parametros
	 * 
	 * @param elemento the elemento
	 * @return true, if successful
	 */
	public boolean recorreElemento(String elemento) {
		char[] adn = elemento.toCharArray();
		boolean flag = false;
		for (int i = 0; i < adn.length; i++) {
			flag = validaCarater(adn[i]);
			if (!flag)
				return flag;
		}
		return flag;
	}

	/**
	 * Valida carater. se valida si cada Base Nitrogenada es permitida
	 * 
	 * @param caracter the caracter
	 * @return true, if successful
	 */
	public boolean validaCarater(char caracter) throws ADNexception{
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
	public boolean validaHorizontal(String elemento) {
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
	 * Bidimensional vertical. metodo que arma matriz bidimensional, la recorre, y
	 * arma una lista por cada elemento, y luego devuelve la lista
	 * 
	 * @param dna the dna
	 * @return the list
	 */
	public List<String> bidimensionalVertical(List<String> dna) {
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
	 * igual a la de abajo a la izquierda y se suma al contador
	 * 
	 * @param dna the dna
	 * @return the int
	 */
	public int validaDiagonal(List<String> dna) throws ADNexception{
		int contador = 0;
		try {
			char[][] arregloFinal = crearMatrizB(dna);
			for (int i = 0; i < arregloFinal.length - 1; i++) {
				for (int j = 0; j < arregloFinal.length - 1; j++) {
					int columna = i;
					int fila = j;
					if (j < arregloFinal.length - 1 && i < arregloFinal.length - 1) {
						if (arregloFinal[columna][fila] == arregloFinal[i + 1][j + 1]) {
							if (j + 2 < arregloFinal.length - 1 && i + 2 < arregloFinal.length - 1) {
								if (arregloFinal[columna][fila] == arregloFinal[i + 2][j + 2]) {
									if (j < arregloFinal.length - 1 && i < arregloFinal.length - 1) {
										if (arregloFinal[columna][fila] == arregloFinal[i + 3][j + 3]) {
											contador++;
										}
									}
								}
							}

						}
					}
				}
			}
		} catch (ADNexception e) {
			
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

	/**
	 * Valida diagonal inverso.
	 *
	 * @param dna the dna
	 * @return true, if successful
	 */
	public int validaDiagonalInverso(List<String> dna) {
		char[][] arregloFinal = crearMatrizInversa(dna);
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
		System.out.println(contador);
		return contador;
	}

	public char[][] crearMatrizInversa(List<String> dna) {
		char[][] arregloFinal = new char[dna.size()][dna.size()];
		for (int i = 0; i < arregloFinal.length - 1; i++) {
			char[] caracteres = dna.get(i).toCharArray();
			for (int j = arregloFinal.length - 1; j >= 0; j--) {
				arregloFinal[i][j] = caracteres[j];
			}
		}
		return arregloFinal;
	}

}
