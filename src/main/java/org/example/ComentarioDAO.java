package org.example;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ComentarioDAO {

    public Usuario obtenerUsuario(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Usuario.class, id);
        }
    }

    public void enviarComentario(Usuario emisor, Usuario receptor, String texto) {
        if (emisor == null || receptor == null) {
            throw new IllegalArgumentException("El emisor y el receptor son obligatorios.");
        }
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El comentario no puede estar vacío.");
        }

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            try {
                Comentario comentario = new Comentario(texto, LocalDateTime.now(), emisor, receptor);
                session.persist(comentario);
                transaction.commit();
            } catch (RuntimeException e) {
                if (transaction.isActive()) {
                    transaction.rollback();
                }
                throw e; // se vuelve a lanzar para que Main sepa que ha fallado
            }
        }
    }

    public List<Comentario> obtenerComentariosEntreFechas(Usuario emisor, LocalDateTime inicio, LocalDateTime fin) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la de fin.");
        }

        String hql = "SELECT c FROM Comentario c "
                + "JOIN FETCH c.emisor "
                + "JOIN FETCH c.receptor "
                + "WHERE c.emisor = :emisor "
                + "AND c.dataComentario BETWEEN :inicio AND :fin "
                + "ORDER BY c.dataComentario";

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Comentario> query = session.createQuery(hql, Comentario.class);
            query.setParameter("emisor", emisor);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            return query.list();
        }
    }

    public void mostrarComentarios(Usuario emisor, LocalDateTime inicio, LocalDateTime fin) {
        List<Comentario> lista = obtenerComentariosEntreFechas(emisor, inicio, fin);
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        if (lista.isEmpty()) {
            System.out.println("No hay comentarios de " + emisor.getEmail() + " en ese periodo.");
            return;
        }
        for (Comentario c : lista) {
            System.out.println(c.getDataComentario().format(formato)
                    + " | De: " + c.getEmisor().getEmail()
                    + " | Para: " + c.getReceptor().getEmail()
                    + " | " + c.getTexto());
        }
    }
}