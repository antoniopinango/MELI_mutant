
# Examen Mercadolibre
----

Proyecto Meli API REST, que permite la validacion de adns tanto humano como mutante y brinda estadisticas correspondientes


----


# Tecnologías 

 * Java8 
 * Git
 * Maven
 * Spring-boot
 * Spring-Data
 * Hibernate
 * MySql
 * API Rest


-------

# Instalacion

-------

# Accesos a los servicios
	* Enponint que obtiene las estadisticas en cuanto a humanos y mutantes validados
		http://66.228.61.76/MELI/stats


		* Contenido:
			count_mutant_dna: 
				Descripcion: Numero de mutantes validados
				tipo: Double
			count_human_dna:
				Descripcion: Numero de humanos validados
				tipo: Double
			ratio:
				Descripcion: Promedio de mutantes validados
				tipo: Double


	* EndPoint que consulta las estadisticas correspondientes a las ip de consulta
		localhost:8080/api/geo/estadisticas

		* Contenido:
			distPromedio: 
				Descripcion: 	Distancia promedio de los paises donde se consulto la ip
				tipo: 			Double
			distMinima: 
				Descripcion: 	Distancia minima del pais donde se consulto la ip
				tipo:			Double
			distMaxima: 
				Descripcion: 	Distancia maxima del pais donde se consulto la ip
				tipo:			Double







