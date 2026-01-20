import java.util.*;

//terminal: run "javac SortTest.java" to compile
//terminal: run "java SortTest" to execute
//straight copy: javac SortTest.java ; java SortTest

public class SortTest {

    // Functional interface for sort methods
    private interface SortMethod {
        void sort(int[] data);
    }

    // Sort methods
    public static void bubbleSort(int[] data) {
        int n = data.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (data[j] > data[j + 1]) {
                    // Swap
                    int temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
    }

    public static void insertionSort(int[] data) {
        int n = data.length;
        for (int i = 1; i < n; ++i) {
            int key = data[i];
            int j = i - 1;

            /* Move elements of arr[0..i-1], that are
               greater than key, to one position ahead
               of their current position */
            while (j >= 0 && data[j] > key) {
                data[j + 1] = data[j];
                j = j - 1;
            }
            data[j + 1] = key;
        }
    }

    public static void shellSort(int[] data) {
        int n = data.length;
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                int temp = data[i];
                int j;
                for (j = i; j >= gap && data[j - gap] > temp; j -= gap) {
                    data[j] = data[j - gap];
                }
                data[j] = temp;
            }
        }
    }

    public static void quickSort(int[] data) {
        if (data.length == 0) return;
        quickSortHelper(data, 0, data.length - 1);
    }

    private static void quickSortHelper(int[] data, int low, int high) {
        if (low < high) {
            int pi = partition(data, low, high);
            quickSortHelper(data, low, pi - 1);
            quickSortHelper(data, pi + 1, high);
        }
    }

    private static int partition(int[] data, int low, int high) {
        int pivot = data[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (data[j] < pivot) {
                i++;
                int temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
        int temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;
        return i + 1;
    }

    public static void mergeSort(int[] data) {
        if (data.length <= 1) return;
        mergeSortHelper(data, 0, data.length - 1);
    }

    private static void mergeSortHelper(int[] data, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortHelper(data, left, mid);
            mergeSortHelper(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }

    private static void merge(int[] data, int left, int mid, int right) {
        int[] leftArr = new int[mid - left + 1];
        int[] rightArr = new int[right - mid];

        System.arraycopy(data, left, leftArr, 0, leftArr.length);
        System.arraycopy(data, mid + 1, rightArr, 0, rightArr.length);

        int i = 0, j = 0, k = left;
        while (i < leftArr.length && j < rightArr.length) {
            if (leftArr[i] <= rightArr[j]) {
                data[k++] = leftArr[i++];
            } else {
                data[k++] = rightArr[j++];
            }
        }

        while (i < leftArr.length) {
            data[k++] = leftArr[i++];
        }

        while (j < rightArr.length) {
            data[k++] = rightArr[j++];
        }
    }

    // Optimized MergeSort: uses InsertionSort for small arrays
    public static void mergeSortOptimized(int[] data) {
        if (data.length <= 1) return;
        mergeSortOptimizedHelper(data, 0, data.length - 1);
    }

    private static void mergeSortOptimizedHelper(int[] data, int left, int right) {
        // Use insertion sort for small subarrays (threshold = 32)
        if (right - left < 32) {
            insertionSortRange(data, left, right);
        } else if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSortOptimizedHelper(data, left, mid);
            mergeSortOptimizedHelper(data, mid + 1, right);
            merge(data, left, mid, right);
        }
    }

    private static void insertionSortRange(int[] data, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = data[i];
            int j = i - 1;
            while (j >= left && data[j] > key) {
                data[j + 1] = data[j];
                j--;
            }
            data[j + 1] = key;
        }
    }

    public static void printData(int[] data) {
        for (int x : data) {
            System.out.print(x + " ");
        }
        System.out.println("--------------------------");
        System.out.println();
    }

    public static int[] createRandomData(int n) {
        int[] data = new int[n];

        Random gen = new Random();

        for (int i = 0; i < data.length; i++) {
            data[i] = gen.nextInt(n);
        }

        return data;
    }
    public static void main(String[] args) {
        int size = 100000;

        // create the array
        int[] data = createRandomData(size);

        // Store results as pairs of [name, time]
        java.util.List<SortResult> results = new java.util.ArrayList<>();

        // Define sorting methods using function references
        SortMethod[] methods = {
            SortTest::bubbleSort,
            SortTest::insertionSort,
            SortTest::shellSort,
            SortTest::quickSort,
            SortTest::mergeSort,
            SortTest::mergeSortOptimized,
            Arrays::sort
        };

        String[] methodNames = {
            "Bubble sort",
            "Insertion sort",
            "Shell sort",
            "Quick sort",
            "Merge sort",
            "Merge sort (Optimized)",
            "Java's sort (Arrays.sort)"
        };

        // Test each sort method
        for (int i = 0; i < methods.length; i++) {
            int[] testData = data.clone();
            long start = System.currentTimeMillis();
            methods[i].sort(testData);
            long stop = System.currentTimeMillis();
            results.add(new SortResult(methodNames[i], stop - start));
        }

        // Sort results by time
        Collections.sort(results);

        // Display results
        System.out.println("Array size: " + size);
        System.out.println("=====================================");
        System.out.println("Results (fastest to slowest):");
        System.out.println("=====================================");
        for (SortResult result : results) {
            System.out.println(result.name + ": " + result.time + " ms");
        }
    }

    static class SortResult implements Comparable<SortResult> {
        String name;
        long time;

        SortResult(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public int compareTo(SortResult other) {
            return Long.compare(this.time, other.time);
        }
    }
}
