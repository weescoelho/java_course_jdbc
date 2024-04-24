package application;

import db.DB;
import db.DbException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Program {
    public static void main(String[] args) {
        Connection connection = null;

        Statement st = null;
        ResultSet rs = null;

        try {
            connection = DB.getConnection();
            connection.setAutoCommit(false); // Seta para uso de transações no banco
            st = connection.createStatement();
            rs = st.executeQuery("select * from department");

            while (rs.next()) {
                System.out.println(rs.getInt("Id") + ", " + rs.getString("Name"));
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
                throw new DbException("Transaction rolledback" + e.getMessage());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
            DB.closeConnection();
        }


    }
}
