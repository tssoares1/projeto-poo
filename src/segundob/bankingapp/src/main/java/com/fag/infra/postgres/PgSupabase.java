package com.fag.infra.postgres;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import com.fag.domain.dto.UserAccountDTO;
import com.fag.domain.repositories.IUserRepository;

public class PgSupabase implements IUserRepository {
    public PgSupabase() {
        PostgresConnection.getInstance();
    }

    @Override
    public UserAccountDTO createUser(UserAccountDTO dto) {
        Connection connection = PostgresConnection.getInstance().getConnection();

        String insertQuery = "INSERT INTO users (id, document, name, email, password, account_number, created_at, disabled_at) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, dto.getId());
            preparedStatement.setString(2, dto.getDocument());
            preparedStatement.setString(3, dto.getName());
            preparedStatement.setString(4, dto.getEmail());
            preparedStatement.setString(5, dto.getPassword());
            preparedStatement.setInt(6, dto.getAccountNumber());
            preparedStatement.setObject(7, dto.getCreatedAt());
            preparedStatement.setObject(8, dto.getDisabledAt());

            preparedStatement.executeUpdate();

            return dto;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public UserAccountDTO findUserBy(String document) {
        Connection connection = PostgresConnection.getInstance().getConnection();

        String query = "SELECT id, document, name, email, password, account_number, created_at, disabled_at FROM users WHERE document = '"
                + document + "';";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    UserAccountDTO user = new UserAccountDTO();

                    user.setId(resultSet.getString("id"));
                    user.setDocument(resultSet.getString("document"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAccountNumber(resultSet.getInt("account_number"));
                    user.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                    user.setDisabledAt(resultSet.getObject("disabled_at", LocalDateTime.class));

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erro ao conectar ou consultar o banco de dados!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao executar query!");
        }

        return null;
    }

    @Override
    public UserAccountDTO getLastData() {
        Connection connection = PostgresConnection.getInstance().getConnection();

        String query = "SELECT id, document, name, email, password, account_number, created_at, disabled_at " +
                "FROM users ORDER BY created_at DESC LIMIT 1;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    UserAccountDTO user = new UserAccountDTO();

                    user.setId(resultSet.getString("id"));
                    user.setDocument(resultSet.getString("document"));
                    user.setName(resultSet.getString("name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPassword(resultSet.getString("password"));
                    user.setAccountNumber(resultSet.getInt("account_number"));
                    user.setCreatedAt(resultSet.getObject("created_at", LocalDateTime.class));
                    user.setDisabledAt(resultSet.getObject("disabled_at", LocalDateTime.class));

                    return user;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Erro ao conectar ou consultar o banco de dados!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao executar query!");
            e.printStackTrace();
        }

        return null;
    }
}
