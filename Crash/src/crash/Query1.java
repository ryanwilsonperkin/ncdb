/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
    private int[][][] results;
    private int[] worst_month_collisions;
    private int[] worst_month_fatalities;
    
    Query1(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
        reduceCollisions();
        reduceFatalities();
    }
    
    public String result() {
        String years = "";
        int year;
        for (int i = 0; i <= 10; i++) {
            year = (i == 10) ? 9999 : i + 1999;
            years += String.format("$Q1,%d,%d,%d\n", year, worst_month_collisions[i], worst_month_fatalities[i]);
        }
        return years;
    }
    
    private void reduceCollisions() {
        int[] worst_month_names = new int[11];
        int[] worst_month_counts = new int[11];
        int[] total_month_counts = new int[12];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                if (results[i][j][0] > worst_month_counts[i]) {
                    worst_month_counts[i] = results[i][j][0];
                    worst_month_names[i] = j+1;
                }
                total_month_counts[j] += results[i][j][0];
            }
        }
        for (int j = 0; j < 12; j++) {
            if (total_month_counts[j] > worst_month_counts[10]) {
                worst_month_counts[10] = total_month_counts[j];
                worst_month_names[10] = j+1;
            }
        }
        this.worst_month_collisions = worst_month_names;
    }
    
    private void reduceFatalities() {
        int[] worst_month_names = new int[11];
        int[] worst_month_counts = new int[11];
        int[] total_month_counts = new int[12];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 12; j++) {
                if (results[i][j][1] > worst_month_counts[i]) {
                    worst_month_counts[i] = results[i][j][1];
                    worst_month_names[i] = j+1;
                }
                total_month_counts[j] += results[i][j][1];
            }
        }
        for (int j = 0; j < 12; j++) {
            if (total_month_counts[j] > worst_month_counts[10]) {
                worst_month_counts[10] = total_month_counts[j];
                worst_month_names[10] = j+1;
            }
        }
        this.worst_month_fatalities = worst_month_names;
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
                Collision c = records.get(i).collision;
                int year = c.year - 1999;
                int month = c.month - 1;
                if (month < 0) {
                    continue;
                }
                ++result[year][month][0];
                if (c.severity == 1) {
                    ++result[year][month][1];
                }
            }
            return result;
        }

        private int[][][] reduce(int[][][] a, int[][][] b) {
            int[][][] c = new int[10][12][2];
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    c[i][j][0] = a[i][j][0] + b[i][j][0];
                    c[i][j][1] = a[i][j][1] + b[i][j][1];
                }
            }
            return c;
        }
    }
}
