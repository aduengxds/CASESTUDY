package com.codegym.strategy;

import com.codegym.model.Note;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class NoteFileStrategy implements NoteStrategy {

    private static final String FILE_PATH = "data/notes.txt";

    public NoteFileStrategy() {
        createFileIfNotExists();
    }

    private void createFileIfNotExists() {

        try {

            Path path = Paths.get(FILE_PATH);

            if (path.getParent() != null) {
                Files.createDirectories(path.getParent());
            }

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

        } catch (IOException e) {
            throw new RuntimeException("Không thể tạo file notes.txt", e);
        }
    }

    @Override
    public void save(Note note) {

        List<Note> notes = findAll();

        if (note.getId() == 0) {

            int newId = notes.stream()
                    .mapToInt(Note::getId)
                    .max()
                    .orElse(0) + 1;

            note.setId(newId);
            notes.add(note);

        } else {

            boolean found = false;

            for (int i = 0; i < notes.size(); i++) {

                if (notes.get(i).getId() == note.getId()) {
                    notes.set(i, note);
                    found = true;
                    break;
                }
            }

            if (!found) {
                notes.add(note);
            }
        }

        writeAll(notes);
    }

    @Override
    public void delete(int id) {

        List<Note> notes = findAll();

        notes.removeIf(note -> note.getId() == id);

        writeAll(notes);
    }

    @Override
    public List<Note> findAll() {

        List<Note> notes = new ArrayList<>();

        Path path = Paths.get(FILE_PATH);

        if (!Files.exists(path)) {
            return notes;
        }

        try (BufferedReader reader =
                     Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {

                Note note = parseLine(line);

                if (note != null) {
                    notes.add(note);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file notes.txt", e);
        }

        notes.sort((a, b) -> Integer.compare(b.getId(), a.getId()));

        return notes;
    }

    @Override
    public List<Note> search(String keyword) {

        String lowerKeyword = keyword.toLowerCase();

        List<Note> result = new ArrayList<>();

        for (Note note : findAll()) {

            String title = note.getTitle() == null
                    ? ""
                    : note.getTitle().toLowerCase();

            String content = note.getContent() == null
                    ? ""
                    : note.getContent().toLowerCase();

            if (title.contains(lowerKeyword)
                    || content.contains(lowerKeyword)) {

                result.add(note);
            }
        }

        return result;
    }

    @Override
    public Note findById(int id) {

        for (Note note : findAll()) {

            if (note.getId() == id) {
                return note;
            }
        }

        return null;
    }

    private Note parseLine(String line) {

        String[] parts = line.split("\\|", -1);

        if (parts.length < 4) {
            return null;
        }

        try {

            int id = Integer.parseInt(parts[0]);
            String title = parts[1];
            String content = parts[2];
            int typeId = Integer.parseInt(parts[3]);

            return new Note(id, title, content, typeId);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private void writeAll(List<Note> notes) {

        try (BufferedWriter writer =
                     Files.newBufferedWriter(
                             Paths.get(FILE_PATH),
                             StandardCharsets.UTF_8,
                             StandardOpenOption.CREATE,
                             StandardOpenOption.TRUNCATE_EXISTING)) {

            for (Note note : notes) {

                String title = clean(note.getTitle());
                String content = clean(note.getContent());

                writer.write(
                        note.getId()
                                + "|" + title
                                + "|" + content
                                + "|" + note.getTypeId()
                );

                writer.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException("Không thể ghi file notes.txt", e);
        }
    }

    private String clean(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("|", "/")
                .replace("\r", " ")
                .replace("\n", " ");
    }
}
