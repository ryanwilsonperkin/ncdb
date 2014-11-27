/*
 * Query1.java
 *  Determine the worst month of the year for collisions by quantity and by 
 *  fatalities.
 *
 *  Ryan Wilson-Perkin (0719644)
 */
package crash;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author ryan
 */
public class Query1 {
    final List<Record> records;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private final int[][][] results;
    private final int[] worst_month_collisions;
    private final int[] worst_month_fatalities;
    private final int worst_overall_month_collisions;
    private final int worst_overall_month_fatalities;
    
    Query1(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
        this.worst_month_collisions = getWorstMonthCollisions();
        this.worst_month_fatalities = getWorstMonthFatalities();
        this.worst_overall_month_collisions = getWorstMonthOverallCollisions();
        this.worst_overall_month_fatalities = getWorstMonthOverallFatalities();
    }
    
    public String result() {
        String years = "";
        int year;
        for (int i = 0; i < 10; i++) {
            year = i + 1999;
            years += String.format("$Q1,%d,%d,%d\n", year, worst_month_collisions[i], worst_month_fatalities[i]);
        }
        years += String.format("$Q1,%d,%d,%d\n", 9999, worst_overall_month_collisions, worst_overall_month_fatalities);
        return years;
    }
    
    private int getWorstMonthOverallCollisions() {
        int[] month_counts = new int[12];
        int worst_month = 1;
        int max = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                month_counts[j] += results[i][j][0];
            }
        }
        for (int i = 0; i < 12; i++) {
            if (month_counts[i] > max) {
                max = month_counts[i];
                worst_month = i + 1;
            }
        }
        return worst_month;
    }
    
    private int getWorstMonthOverallFatalities() {
        int[] month_counts = new int[12];
        int worst_month = 1;
        int max = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                month_counts[j] += results[i][j][1];
            }
        }
        for (int i = 0; i < 12; i++) {
            if (month_counts[i] > max) {
                max = month_counts[i];
                worst_month = i + 1;
            }
        }
        return worst_month;
    }
    
    private int[] getWorstMonthCollisions() {
        int worst_months[] = new int[10];
        for (int i = 0; i < 10; i++) {
            worst_months[i] = getWorstMonthCollisions(i);
        }
        return worst_months;
    }
    
    private int getWorstMonthCollisions(int year) {
        int worst_month = 1;
        int max = 0;
        for (int i = 0; i < 12; i++) {
            if (results[year][i][0] > max) {
                max = results[year][i][0];
                worst_month = i + 1;
            }
        }
        return worst_month;
    }
    
    private int[] getWorstMonthFatalities() {
        int worst_months[] = new int[10];
        for (int i = 0; i < 10; i++) {
            worst_months[i] = getWorstMonthFatalities(i);
        }
        return worst_months;
    }
    
    private int getWorstMonthFatalities(int year) {
        int worst_month = 1;
        int max = 0;
        for (int i = 0; i < 12; i++) {
            if (results[year][i][1] > max) {
                max = results[year][i][1];
                worst_month = i + 1;
            }
        }
        return worst_month;
    }

    private class Computation extends RecursiveTask<int[][][]> {
        final List<Record> records;
        final int start;
        final int end;
        final int threshold;

        Computation(List<Record> records, int start, int end, int threshold) {
            this.records = records;
            this.start = start;
            this.end = end;
            this.threshold = threshold;
        }

        @Override
        protected int[][][] compute() {
            if ((end - start) <= threshold) {
                return calculate();
            } else {
                Computation c1 = new Computation(records, start, (start + end) / 2, threshold);
                c1.fork();
                Computation c2 = new Computation(records, (start + end) / 2, end, threshold);
                return reduce(c2.invoke(), c1.join());
            }
        }

        private int[][][] calculate() {
            int[][][] result = new int[10][12][2];
            for (int i = start; i < end; i++) {
                Record r = records.get(i);
                int year = r.collision.year - 1999;
                int month = r.collision.month - 1;
                if (month < 0) {
                    continue;
                }
                if (r.vehicle.id == 1 && r.person.id == 1) {
                    ++result[year][month][0];
                }
                if (r.person.treatment == 3) {
                    ++result[year][month][1];
                }
            }
            return result;
        }

        private int[][][] reduce(int[][][] a, int[][][] b) {
            int[][][] c = new int[10][12][2];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 12; j++) {
                    c[i][j][0] = a[i][j][0] + b[i][j][0];
                    c[i][j][1] = a[i][j][1] + b[i][j][1];
                }
            }
            return c;
        }
    }
}
