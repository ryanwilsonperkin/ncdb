/*
 * Query4.java
 *  Determine how many people typically wreck their new car in a year on average
 *  and how old the average vehicle in a collision was over the 10 years.
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
public class Query4 {
    final List<Record> records;
    final ForkJoinPool thread_pool;
    final int threshold;
    
    private int[] results;
    
    Query4(List<Record> records, ForkJoinPool thread_pool, int threshold) {
        this.records = records;
        this.thread_pool = thread_pool;
        this.threshold = threshold;
        this.results = thread_pool.invoke(
                new Computation(records, 0, records.size(), threshold));
    }
    
    public String result() {
        int annual_new_wrecks = results[0] / 10;
        float average_vehicle_age =  (float) results[1] / records.size();
        return String.format("$Q4,%d,%.1f", annual_new_wrecks, average_vehicle_age);
    }

    
    private class Computation extends RecursiveTask<int[]> {
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
        protected int[] compute() {
            if ((end - start) <= threshold) {
                return calculate();
            } else {
                Computation c1 = new Computation(records, start, (start + end) / 2, threshold);
                c1.fork();
                Computation c2 = new Computation(records, (start + end) / 2, end, threshold);
                return reduce(c2.invoke(), c1.join());
            }
        }

        private int[] calculate() {
            int[] result = new int[2];
            for (int i = start; i < end; i++) {
                Record r = records.get(i);
                if (r.vehicle.year >= r.collision.year) ++result[0];
                result[1] += r.collision.year - r.vehicle.year + 1;
            }
            return result;
        }

        private int[] reduce(int[] a, int[] b) {
            int[] c = new int[2];
            c[0] = a[0] + b[0];
            c[1] = a[1] + b[1];
            return c;
        }
    }
}
