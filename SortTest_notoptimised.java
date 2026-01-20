import java.util.*;

//terminal: run "javac SortTest.java" to compile
//terminal: run "java SortTest" to execute
//straight copy: javac SortTest_notoptimised.java ; java SortTest_notoptimised

public class SortTest_notoptimised {

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
   

        public static void printFirst20(int[] data) {
        int limit = Math.min(20, data.length);
        for (int i = 0; i < limit; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println();
    }

        public static void printLast20Descending(int[] data) {
    int count = Math.min(20, data.length);
    for (int i = data.length - 1; i >= data.length - count; i--) {
        System.out.print(data[i] + " ");
    }
    System.out.println();
}

public static void main(String[] args) {
    int size = 100000;

    int[] data = createRandomData(size);

    java.util.List<SortResult> results = new java.util.ArrayList<>();

    // Insertion Sort
    int[] dataInsertion = data.clone();
    long startInsertion = System.currentTimeMillis();
    insertionSort(dataInsertion);
    long stopInsertion = System.currentTimeMillis();
    results.add(new SortResult("Insertion sort", stopInsertion - startInsertion));
    System.out.print("Insertion sort (first 20): ");
    printFirst20(dataInsertion);
    System.out.print("Insertion sort (last 20, descending): ");
    printLast20Descending(dataInsertion);

    // Bubble Sort
    int[] dataBubble = data.clone();
    long startBubble = System.currentTimeMillis();
    bubbleSort(dataBubble);
    long stopBubble = System.currentTimeMillis();
    results.add(new SortResult("Bubble sort", stopBubble - startBubble));
    System.out.print("Bubble sort (first 20): ");
    printFirst20(dataBubble);
    System.out.print("Bubble sort (last 20, descending): ");
    printLast20Descending(dataBubble);

    // Shell Sort
    int[] dataShell = data.clone();
    long startShell = System.currentTimeMillis();
    shellSort(dataShell);
    long stopShell = System.currentTimeMillis();
    results.add(new SortResult("Shell sort", stopShell - startShell));
    System.out.print("Shell sort (first 20): ");
    printFirst20(dataShell);
    System.out.print("Shell sort (last 20, descending): ");
    printLast20Descending(dataShell);

    // Quick Sort
    int[] dataQuick = data.clone();
    long startQuick = System.currentTimeMillis();
    quickSort(dataQuick);
    long stopQuick = System.currentTimeMillis();
    results.add(new SortResult("Quick sort", stopQuick - startQuick));
    System.out.print("Quick sort (first 20): ");
    printFirst20(dataQuick);
    System.out.print("Quick sort (last 20, descending): ");
    printLast20Descending(dataQuick);

    // Merge Sort
    int[] dataMerge = data.clone();
    long startMerge = System.currentTimeMillis();
    mergeSort(dataMerge);
    long stopMerge = System.currentTimeMillis();
    results.add(new SortResult("Merge sort", stopMerge - startMerge));
    System.out.print("Merge sort (first 20): ");
    printFirst20(dataMerge);
    System.out.print("Merge sort (last 20, descending): ");
    printLast20Descending(dataMerge);

    // Java's built-in sort
    int[] dataJava = data.clone();
    long startJava = System.currentTimeMillis();
    Arrays.sort(dataJava);
    long stopJava = System.currentTimeMillis();
    results.add(new SortResult("Java's sort (Arrays.sort)", stopJava - startJava));
    System.out.print("Java sort (first 20): ");
    printFirst20(dataJava);
    System.out.print("Java sort (last 20, descending): ");
    printLast20Descending(dataJava);


    // Sort results by time
    Collections.sort(results);

    System.out.println();
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
