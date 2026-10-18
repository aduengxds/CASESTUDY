package com.codegym.service;

import com.codegym.model.Note;
import com.codegym.strategy.NoteStrategy;

import java.util.List;

public class NoteManagement {

    private NoteStrategy strategy;

    public NoteManagement(NoteStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(NoteStrategy strategy) {
        this.strategy = strategy;
    }

    public void addNote(String title, String content, int typeId) {

        Note note = new Note(title, content, typeId);

        strategy.save(note);
    }

    public void updateNote(int id,
                           String title,
                           String content,
                           int typeId) {

        Note note = new Note(id, title, content, typeId);

        strategy.save(note);
    }

    public void deleteNote(int id) {
        strategy.delete(id);
    }

    public List<Note> searchNotes(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return strategy.findAll();
        }

        return strategy.search(keyword.trim());
    }

    public List<Note> getAllNotes() {
        return strategy.findAll();
    }

    public Note getNoteById(int id) {
        return strategy.findById(id);
    }

    public NoteStrategy getStrategy() {
        return strategy;
    }
}
