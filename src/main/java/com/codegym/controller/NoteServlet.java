package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.service.NoteManagement;
import com.codegym.strategy.NoteDBStrategy;
import com.codegym.strategy.NoteFileStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/notes")
public class NoteServlet extends HttpServlet {

    private NoteManagement management;

    @Override
    public void init() {

        /*
         * Mặc định sử dụng MySQL.
         * Có thể chuyển sang File bằng cách:
         *
         * management.setStrategy(new NoteFileStrategy());
         */

        management = new NoteManagement(new NoteDBStrategy());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String keyword = request.getParameter("keyword");

        List<Note> notes;

        if (keyword == null || keyword.trim().isEmpty()) {
            notes = management.getAllNotes();
        } else {
            notes = management.searchNotes(keyword);
        }

        request.setAttribute("notes", notes);
        request.setAttribute("keyword", keyword);

        request.getRequestDispatcher("/index.jsp")
                .forward(request, response);
    }
}
