 Acceso a la BD
- Host:relational.fel.cvut.cz
- Puerto: 3306
- Usuario: guest
- Clave: ctu-relational
 Instrucciones de ejecución
1. Clonar o descargar este repositorio.
2. Abrir el proyecto en NetBeans(Java).
3. Asegurarse de tener el conector MySQL JDBC Driver en el classpath (Maven lo gestiona desde el `pom.xml`).
4. Ejecutar la clase principal:
5. El programa mostrará:
- Conexión exitosa a la BD.
- Listado de tablas disponibles.
- Columnas de las primeras 2 tablas.
- Resultados de las consultas SQL.
 Consultas implementadas
1. Filtro de texto: categorías cuyo código empieza por "A".
sql
SELECT category_no, category_desc, category_code
FROM category
WHERE category_code LIKE 'A%'
LIMIT 10;
SELECT charge_no, member_no, charge_amt
FROM charge
WHERE charge_amt > 1000
LIMIT 10;
SELECT member_no, lastname, firstname
FROM member
LIMIT 5;

