# Chat Hibernate

Práctica de consola que simular una aplicación de Chat, en la que unos usuarios se envían comentarios y se pueden hacer consultas sobre la BD. Usa Hibernate para guardar los datos en una base de datos MySQL.

## 1. Tecnologías Empleadas

- **Lenguaje:** Java 21.
- **Librerías:** Hibernate 6.
- **Base de Datos:** MySQL.
- **Conectividad:** Conector JDBC de MySQL.
- **Docker:** Opcional, sirve para tener la base de datos lista sin instalar nada.

## 2. Estructura

- **Modelo:** Clases Usuario y Comentario. Cada comentario tiene un emisor y un receptor, que son dos usuarios.
- **Acceso a datos:** Clases ComentarioDAO y HibernateUtil. Guardan y consultan los comentarios, de forma que el programa principal no habla directamente con la base de datos.
- **Programa principal:** Clase Main. Envía un comentario y después muestra los que ha enviado un usuario en los últimos 7 días.
- **Base de datos:** El archivo `sql/init.sql` crea las tablas y unos datos de ejemplo.

## 3. Despliegue y Pruebas
##  Cómo ejecutarlo

Requisitos: **Java 21**, **Maven** (o IntelliJ IDEA) y **Docker Desktop**.

### Opción A: con Docker (recomendada, no hace falta instalar MySQL)

```bash
# 1. Levantar MySQL con la base de datos y los datos de prueba ya cargados
docker compose up -d --wait

# 2. Ejecutar la clase Main (desde IntelliJ)

# 3. Al terminar, apagar la base de datos
docker compose down
```
Cada vez que se levanta, la base de datos parte de los mismos datos de prueba. Los cambios que hagas no se conservan tras `docker compose down`.

> Si ya tienes un MySQL instalado en tu ordenador usando el puerto 3306, páralo antes de ejecutar Docker.

### Opción B: con MySQL instalado (MySQL Workbench)

1. Abrir MySQL Workbench y ejecutar el script `sql/init.sql`.
2. Comprobar que el usuario y la contraseña del archivo `hibernate.cfg.xml` coinciden con los de tu MySQL.
3. Ejecutar la clase `Main`.

### Ejemplo de salida

```
Comentario enviado correctamente.

Comentarios de ana@example.com en los últimos 7 días:
17/10/2026 05:39 | De: ana@example.com | Para: luis@example.com | Buen trabajo con el proyecto de Hibernate.
19/10/2026 03:44 | De: ana@example.com | Para: luis@example.com | ¡Hola! Este comentario se ha enviado desde Main.
```
