package com.example.tasks;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("TaskManager tests")
class TaskManagerTest {

    private TaskManager tm;

    @BeforeEach
    void setup() {
        tm = new TaskManager();
    }

    @Test
    @DisplayName("Adicionar tarefas, permitir títulos duplicados e remover por ID sem afetar outras")
    void add_duplicates_and_remove_by_id() {
        var t1 = tm.addTask("Pagar contas", "água e luz");
        var t2 = tm.addTask("Pagar contas", "internet");
        var t3 = tm.addTask("Estudar", "JUnit");

        assertEquals(3, tm.getAll().size());
        assertEquals(2, tm.filterByTitleContains("pagar").size());

        assertTrue(tm.removeTask(t1.getId()));
        assertEquals(2, tm.getAll().size());
        // O duplicado com mesmo título ainda existe
        assertEquals(List.of(t2, t3), tm.getAllSortedByCreationDate(true));
    }

    @Test
    @DisplayName("Remover todas as tarefas por título")
    void remove_all_by_title() {
        tm.addTask("Ler", "Livro A");
        tm.addTask("Ler", "Livro B");
        tm.addTask("Correr", "5km");

        int removed = tm.removeAllByTitle("Ler");
        assertEquals(2, removed);
        assertEquals(1, tm.getAll().size());
    }

    @Test
    @DisplayName("Marcar uma e todas as tarefas como concluídas")
    void mark_single_and_all_completed() {
        var t1 = tm.addTask("A", "");
        var t2 = tm.addTask("B", "");

        assertTrue(tm.markCompleted(t1.getId()));
        assertEquals(1, tm.filterByStatus(TaskStatus.COMPLETED).size());
        assertEquals(1, tm.filterByStatus(TaskStatus.PENDING).size());

        tm.markAllCompleted();
        assertEquals(2, tm.filterByStatus(TaskStatus.COMPLETED).size());
        assertEquals(0, tm.filterByStatus(TaskStatus.PENDING).size());
    }

    @Test
    @DisplayName("Filtrar por diferentes critérios (status e título)")
    void filter_by_status_and_title() {
        tm.addTask("Pagar boleto", "");
        var t2 = tm.addTask("Estudar JUnit", "");
        tm.addTask("Pagar aluguel", "");

        // marcar concluída
        tm.markCompleted(t2.getId());

        List<Task> done = tm.filterByStatus(TaskStatus.COMPLETED);
        assertEquals(1, done.size());
        assertEquals("Estudar JUnit", done.get(0).getTitle());

        List<Task> pagar = tm.filterByTitleContains("pagar");
        assertEquals(2, pagar.size());
    }

    @Test
    @DisplayName("Ordenar por data de criação (asc e desc)")
    void sort_by_creation_date() throws InterruptedException {
        var t1 = tm.addTask("T1", "");
        Thread.sleep(2); // garantir ordem diferente
        var t2 = tm.addTask("T2", "");
        Thread.sleep(2);
        var t3 = tm.addTask("T3", "");

        assertEquals(List.of(t1, t2, t3), tm.getAllSortedByCreationDate(true));
        assertEquals(List.of(t3, t2, t1), tm.getAllSortedByCreationDate(false));
    }

    @Test
    @DisplayName("Salvar em arquivo e carregar novamente (round-trip)")
    void save_and_load_roundtrip(@TempDir Path temp) throws IOException {
        var t1 = tm.addTask("P1", "descrição com vírgula, aspas " e \n quebra");
        var t2 = tm.addTask("P2", "linha2\noutra linha");
        tm.markCompleted(t2.getId());

        Path file = temp.resolve("tasks.csv");
        tm.saveToFile(file);

        assertTrue(Files.exists(file));

        TaskManager loaded = TaskManager.loadFromFile(file);

        // Mesma quantidade
        assertEquals(tm.getAll().size(), loaded.getAll().size());

        // Checar que IDs/títulos/status foram preservados
        var origSorted = tm.getAllSortedByCreationDate(true);
        var loadedSorted = loaded.getAllSortedByCreationDate(true);
        for (int i = 0; i < origSorted.size(); i++) {
            Task a = origSorted.get(i);
            Task b = loadedSorted.get(i);
            assertEquals(a.getId(), b.getId());
            assertEquals(a.getTitle(), b.getTitle());
            assertEquals(a.getStatus(), b.getStatus());
            assertEquals(a.getDescription(), b.getDescription());
        }
    }

    @Test
    @DisplayName("Remover/Marcar por ID inexistente retorna false")
    void operations_on_missing_id() {
        UUID fake = UUID.randomUUID();
        assertFalse(tm.removeTask(fake));
        assertFalse(tm.markCompleted(fake));
    }

    @Test
    @DisplayName("Adicionar tarefas com caracteres especiais e títulos longos")
    void add_special_characters_and_long_titles() {
        String longTitle = "A".repeat(10_000);
        var t1 = tm.addTask("çãõ ÁÉÍÓÚ, !@#$%", "descrição");
        var t2 = tm.addTask(longTitle, "");

        assertEquals(2, tm.getAll().size());
        assertTrue(tm.filterByTitleContains("çãõ").contains(t1));
        assertTrue(tm.filterByTitleContains("a".repeat(100)).contains(t2));
    }
}
