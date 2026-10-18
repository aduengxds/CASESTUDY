package com.codegym.controller;

import com.codegym.model.Note;
import com.codegym.service.NoteManagement;
import com.codegym.strategy.NoteDBStrategy;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/notes/view")
public class ViewNoteServlet extends HttpServlet {

    private NoteManagement management;

    @Override
    public void init() {
        management = new NoteManagement(new NoteDBStrategy());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        Note note = management.getNoteById(id);

        if (note == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Không tìm thấy ghi chú"
            );
            return;
        }

        request.setAttribute("note", note);

        request.getRequestDispatcher("/view-note.jsp")
                .forward(request, response);
    }
}
