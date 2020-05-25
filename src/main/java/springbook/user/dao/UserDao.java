package springbook.user.dao;

import java.sql.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import springbook.user.domain.User;

import javax.sql.DataSource;

public class UserDao {
    private ConnectionMaker connectionMaker;
    private Connection c;
    private User user;
    private DataSource dataSource;

    public UserDao(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker",ConnectionMaker.class);
    }
    public UserDao(ConnectionMaker connectionMaker) {

        this.connectionMaker = connectionMaker;
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        this.c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "insert into users(id, name, password) values(?,?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        ps.close();
        c.close();
    }

    public User get(String id) throws ClassNotFoundException,SQLException {
        this.c = dataSource.getConnection();

        PreparedStatement ps = c.prepareStatement(
                "select * from users where id = ?");
        ps.setString(1,id);

        ResultSet rs = ps.executeQuery();
        rs.next();
        this.user = new User();
        this.user.setId(rs.getString("id"));
        this.user.setName(rs.getString("name"));
        this.user.setPassword(rs.getString("password"));

        rs.close();
        ps.close();
        c.close();

        return this.user;
    }


    public void setConnectionMaker(ConnectionMaker connectionMaker){

        this.connectionMaker = connectionMaker;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

}
