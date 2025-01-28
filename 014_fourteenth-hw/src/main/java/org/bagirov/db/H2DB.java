package org.bagirov.db;

import org.bagirov.cache.CacheStore;

import java.sql.*;
import java.util.Optional;

public class H2DB implements CacheStore {
    private static final String DB_URL = "jdbc:h2:./cacheDB";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    public H2DB() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS cache (\"key\" VARCHAR PRIMARY KEY, \"value\" VARCHAR)");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to initialize H2 cache table", e);
        }
    }

    @Override
    public void save(String key, String value) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("MERGE INTO cache (\"key\", \"value\") VALUES (?, ?)")) {
            stmt.setString(1, key);
            stmt.setString(2, value);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save data to cache", e);
        }
    }

    @Override
    public Optional<String> get(String key) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement("SELECT \"value\" FROM cache WHERE \"key\" = ?")) {
            stmt.setString(1, key);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getString("value"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get data from cache", e);
        }
        return Optional.empty();
    }

    @Override
    public void clear() {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM cache");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to clear cache", e);
        }
    }
}
