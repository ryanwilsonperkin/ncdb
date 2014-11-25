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
public class Query3 {
    final List<Record> records;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private Collision results;
    
    Query3(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
    }
    
    public String result() {
        return String.format("$Q3,%d,%d,%02d,%d", results.vehicles, results.year, results.month, results.weekday);
    }
    
    private class Computation extends RecursiveTask<Collision> {
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
        protected Collision compute() {
            if ((end - start) <= threshold) {
                return calculate();
            } else {
                Computation c1 = new Computation(records, start, (start + end) / 2, threshold);
                c1.fork();
                Computation c2 = new Computation(records, (start + end) / 2, end, threshold);
                return reduce(c2.invoke(), c1.join());
            }
        }

        private Collision calculate() {
            Collision result = records.get(start).collision;
            for (int i = start; i < end; i++) {
                Collision c = records.get(i).collision;
                if (c.vehicles > result.vehicles) result = c;
            }
            return result;
        }

        private Collision reduce(Collision a, Collision b) {
            return (a.vehicles > b.vehicles) ? a : b;
        }
    }
}
