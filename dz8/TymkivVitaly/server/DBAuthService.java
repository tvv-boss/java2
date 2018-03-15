package ru.geekbrains.java2.dz.dz8.TymkivVitaly.server;

import java.sql.*;

public class DBAuthService implements AuthService {
    private Connection connection;
    private PreparedStatement ps;

    @Override
    public void start(MyServer server) {
        connectToDB();
        try {
            ps = connection.prepareStatement("SELECT Nick FROM entries WHERE Login = ? AND Pass = ?;"); // подготавливаем запрос на выборку данных из базы
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void connectToDB() {
        try {
            try {
                Class.forName("org.sqlite.JDBC");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            connection = DriverManager.getConnection("jdbc:sqlite:chat.db"); // открываем соединение с базой
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNickByLoginPass(String login, String pass) { // реализуем метод получения ника по логину и паролю
        try {
            ps.setString(1, login);
            ps.setInt(2, pass.hashCode());
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return rs.getString(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void stop() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
