package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.Main;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {


    /* Создание Connection ссылки */
    private static Connection connection = Util.getConnection();

    /* Создание ссылки Statement*/
    private static PreparedStatement preparedStatement;

    /* SQL Query CREATE TABLE*/
    private final String CREATE  = "create table if not exists User " +
            "(id int not null AUTO_INCREMENT," +
            " name varchar(40)," +
            " lastname varchar(40)," +
            " age int, primary key(id));";

    /* SQL Query DROP TABLE*/
    private final String DROP = "drop table if exists user";

    /* SQL Query INSERT User*/
    private final String INSERT_INTO = "insert into user(firstName,lastName,age) values(?,?,?);";

    /* SQL Query DELETE User*/
    private final String DELETE = "delete from User where id=?;";

    /* SQL Query CLEAN_TABLE User*/
    private final String CLEAN_TABLE = "delete from User;";

    /* SQL Query SELECT User*/
    private final String SELECT = "select * from  User";



    public UserDaoJDBCImpl() {

    }


    public void createUsersTable() {
        try( PreparedStatement preparedStatement = connection.prepareStatement(CREATE)) {
            preparedStatement.executeUpdate();
            System.out.println("Table was created");
        }catch (SQLSyntaxErrorException e){
            System.out.println("Ошибка в оформлении команды");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DROP)) {
            preparedStatement.executeUpdate();
            System.out.println("Table was droped");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTO)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setByte(3,age);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try(PreparedStatement preparedStatement = connection.prepareStatement(DELETE);) {
            preparedStatement.setInt(1, (int) id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try(PreparedStatement preparedStatement = connection.prepareStatement(SELECT)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User(resultSet.getString("firstName"),resultSet.getString("lastName"), (byte) resultSet.getInt("age"));
                user.setId((long) resultSet.getInt("id"));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try(PreparedStatement preparedStatement = connection.prepareStatement(CLEAN_TABLE)) {
            preparedStatement.executeUpdate();
            System.out.println("Table User is cleaned");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}