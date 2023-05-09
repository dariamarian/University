package proiectServerClientJava.persistence.repository.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import proiectServerClientJava.domain.Angajat;
import proiectServerClientJava.persistence.repository.IRepoAngajat;
import proiectServerClientJava.service.MyException;

public class RepoAngajatORM implements IRepoAngajat {
    private static SessionFactory sessionFactory;
    private void initialize(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Exceptie " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
    public RepoAngajatORM() {
        initialize();
    }

    @Override
    public Angajat add(Angajat entity) {
        return null;
    }

    @Override
    public Angajat remove(Integer id) {
        return null;
    }

    @Override
    public Angajat findElement(Integer id) {
        return null;
    }
    @Override
    public Iterable<Angajat> getAll() {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            Iterable<Angajat> angajati = session.createQuery("from Angajat", Angajat.class).list();
            session.getTransaction().commit();
            return angajati;
        }
    }

    public Angajat authenticateAngajat(String username, String password) throws MyException {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Angajat angajat = session.createQuery("from Angajat where username = :username and password = :password",Angajat.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            if (angajat == null) {
                throw new MyException("Username sau parola gresita!");
            }
            return angajat;
        }
    }
}
