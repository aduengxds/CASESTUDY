package com.codegym.controller;

import com.codegym.service.NoteManagement;
import com.codegym.strategy.NoteDBStrategy;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/notes/delete")
public class DeleteNoteServlet extends HttpServlet {

    private NoteManagement management;

    @Override
    public void init() {
        management = new NoteManagement(new NoteDBStrategy());
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        management.deleteNote(id);

        response.sendRedirect(
                request.getContextPath() + "/notes"
        );
    }
}
