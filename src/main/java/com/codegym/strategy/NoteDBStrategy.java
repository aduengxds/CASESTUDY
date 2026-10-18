package com.codegym.strategy;

import com.codegym.database.DBConnection;
import com.codegym.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDBStrategy implements NoteStrategy {

    @Override
    public void save(Note note) {

        String insertSql =
                "INSERT INTO notes(title, content, type_id) VALUES (?, ?, ?)";

        String updateSql =
                "UPDATE notes SET title = ?, content = ?, type_id = ? WHERE id = ?";

        try (Connection connection = DBConnection.getConnection()) {

            if (note.getId() == 0) {

                try (PreparedStatement statement =
                             connection.prepareStatement(
                                     insertSql,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    statement.setString(1, note.getTitle());
                    statement.setString(2, note.getContent());
                    statement.setInt(3, note.getTypeId());

                    statement.executeUpdate();

                    try (ResultSet rs = statement.getGeneratedKeys()) {
                        if (rs.next()) {
                            note.setId(rs.getInt(1));
                        }
                    }
                }

            } else {

                try (PreparedStatement statement =
                             connection.prepareStatement(updateSql)) {

                    statement.setString(1, note.getTitle());
                    statement.setString(2, note.getContent());
                    statement.setInt(3, note.getTypeId());
                    statement.setInt(4, note.getId());

                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi lưu Note vào MySQL", e);
        }
    }

    @Override
    public void delete(int id) {

        String sql = "DELETE FROM notes WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi xóa Note", e);
        }
    }

    @Override
    public List<Note> findAll() {

        String sql =
                "SELECT n.id, n.title, n.content, n.type_id, t.name AS type_name " +
                "FROM notes n " +
                "JOIN note_type t ON n.type_id = t.id " +
                "ORDER BY n.id DESC";

        return executeQuery(sql);
    }

    @Override
    public List<Note> search(String keyword) {

        String sql =
                "SELECT n.id, n.title, n.content, n.type_id, t.name AS type_name " +
                "FROM notes n " +
                "JOIN note_type t ON n.type_id = t.id " +
                "WHERE n.title LIKE ? OR n.content LIKE ? " +
                "ORDER BY n.id DESC";

        List<Note> notes = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";

            statement.setString(1, searchKeyword);
            statement.setString(2, searchKeyword);

            try (ResultSet rs = statement.executeQuery()) {

                while (rs.next()) {
                    notes.add(mapResultSet(rs));
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm kiếm Note", e);
        }

        return notes;
    }

    @Override
    public Note findById(int id) {

        String sql =
                "SELECT n.id, n.title, n.content, n.type_id, t.name AS type_name " +
                "FROM notes n " +
                "JOIN note_type t ON n.type_id = t.id " +
                "WHERE n.id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi tìm Note theo ID", e);
        }

        return null;
    }

    private List<Note> executeQuery(String sql) {

        List<Note> notes = new ArrayList<>();

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                notes.add(mapResultSet(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn Note", e);
        }

        return notes;
    }

    private Note mapResultSet(ResultSet rs) throws SQLException {

        return new Note(
                rs.getInt("id"),
                rs.getString("title"),
                rs.getString("content"),
                rs.getInt("type_id"),
                rs.getString("type_name")
        );
    }
}
