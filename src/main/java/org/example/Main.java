package org.example;

import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        ComentarioDAO gestor = new ComentarioDAO();

        try {
            Usuario emisor = gestor.obtenerUsuario(1L);
            Usuario receptor = gestor.obtenerUsuario(2L);

            if (emisor == null || receptor == null) {
                System.out.println("Los usuarios 1 y 2 no existen. ¿Está arrancada la base de datos? "
                        + "(docker compose up -d --wait)");
                return;
            }

            gestor.enviarComentario(emisor, receptor, "¡Hola! Este comentario se ha enviado desde Main.");
            System.out.println("Comentario enviado correctamente.\n");

            // Actividad 2: comentarios emitidos por el usuario en los últimos 7 días
            LocalDateTime fin = LocalDateTime.now();
            LocalDateTime inicio = fin.minusDays(7);
            System.out.println("Comentarios de " + emisor.getEmail() + " en los últimos 7 días:");
            gestor.mostrarComentarios(emisor, inicio, fin);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            HibernateUtil.cerrar();
        }
    }
}