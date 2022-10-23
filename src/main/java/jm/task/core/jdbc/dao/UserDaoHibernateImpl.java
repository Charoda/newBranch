package jm.task.core.jdbc.dao;

import jdk.dynalink.linker.LinkerServices;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private static SessionFactory factory = Util.getSessionFactory();

    private Session session;


    /* SQL Query CREATE TABLE*/
    private final String CREATE  = "create table if not exists User " +
            "(id int not null AUTO_INCREMENT," +
            " name varchar(40)," +
            " lastname varchar(40)," +
            " age int, primary key(id));";


    /* SQL Query DROP TABLE*/
    private final String DROP = "drop table if exists user";

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {

        try {
           session = factory.getCurrentSession();
           session.beginTransaction();
           session.createSQLQuery(CREATE).executeUpdate();
           session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void dropUsersTable() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createSQLQuery(DROP).executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = factory.getCurrentSession();
            User user = new User(name,lastName,age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }

    }

    @Override
    public void removeUserById(long id) {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class,id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            users = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = factory.getCurrentSession();
            session.beginTransaction();
            session.createQuery("delete User").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
        }
    }
}
