
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
 * PostMan

-------

# Instalacion
 

-------

# Accesos a los servicios
	
	* EndPoint que obtiene las estadisticas en cuanto a humanos y mutantes validados
			URL API: GET	http://66.228.61.76/MELI/stats


		* Contenido:
			count_mutant_dna: 
				Descripcion: Numero de mutantes validados
				tipo: Long
			count_human_dna:
				Descripcion: Numero de humanos validados
				tipo: Long
			ratio:
				Descripcion: Promedio de mutantes validados
				tipo: Float

	* EndPoint que valida un ADN como Humano o mutante
			URL API:POST	http://66.228.61.76/MELI/stats

			Mandando como cuerpo de la peticion un JSON, en la pestaña RAW

			{
			    "dna":[
			        "ATGCGA",
			        "AAGTGC",
			        "GTACGT",
			        "AAAAGG",
			        "GGCCTA",
			        "TCACTG"
			        ]
			}




