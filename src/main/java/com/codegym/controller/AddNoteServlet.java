package com.codegym.controller;

import com.codegym.model.NoteType;
import com.codegym.service.NoteManagement;
import com.codegym.strategy.NoteDBStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.codegym.database.DBConnection;

@WebServlet("/notes/add")
public class AddNoteServlet extends HttpServlet {

    private NoteManagement management;

    @Override
    public void init() {
        management = new NoteManagement(new NoteDBStrategy());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("types", getNoteTypes());

        request.getRequestDispatcher("/add-note.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int typeId = Integer.parseInt(request.getParameter("typeId"));

        management.addNote(title, content, typeId);

        response.sendRedirect(
                request.getContextPath() + "/notes"
        );
    }

    private List<NoteType> getNoteTypes() {

        List<NoteType> types = new ArrayList<>();

        String sql = "SELECT id, name, description FROM note_type ORDER BY id";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {

                types.add(new NoteType(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Không thể lấy danh sách loại Note", e);
        }

        return types;
    }
}
