
package com.refactor;

import java.util.ArrayList;
import java.util.List;

/**
 * EXERCÍCIO: Code Smell - Large Class
 * Objetivo:
 *  - Quebrar responsabilidades de ReportManager usando Extract Class
 *  - Sugerido: criar DataLoader, ReportFilter e ReportRenderer
 * Critério de aceite:
 *  - ReportManager deve orquestrar classes menores
 */
public class LargeClassExample {

    public static class Record {
        public final String category;
        public final double value;

        public Record(String category, double value) {
            this.category = category;
            this.value = value;
        }
    }

    // Classe responsável por carregar os dados
    public static class DataLoader {
        public List<Record> load() {
            List<Record> data = new ArrayList<>();
            data.add(new Record("A", 10));
            data.add(new Record("B", 30));
            data.add(new Record("A", 25));
            data.add(new Record("C", 7));
            return data;
        }
    }

    // Classe responsável por filtrar registros
    public static class ReportFilter {
        public List<Record> byCategory(List<Record> data, String cat) {
            List<Record> out = new ArrayList<>();
            for (Record r : data) {
                if (r.category.equals(cat)) out.add(r);
            }
            return out;
        }
    }

    // Classe responsável por renderizar relatório
    public static class ReportRenderer {
        public String render(List<Record> records) {
            StringBuilder sb = new StringBuilder("REPORT\n");
            double sum = 0;
            for (Record r : records) {
                sb.append(r.category).append(": ").append(r.value).append('\n');
                sum += r.value;
            }
            sb.append("TOTAL: ").append(sum);
            return sb.toString();
        }
    }

    // Agora a classe ReportManager apenas orquestra
    public static class ReportManager {
        private final DataLoader loader = new DataLoader();
        private final ReportFilter filter = new ReportFilter();
        private final ReportRenderer renderer = new ReportRenderer();

        public String run(String category) {
            var data = loader.load();
            List<Record> filtered = filter.byCategory(data, category);
            return renderer.render(filtered);
        }
    }
}