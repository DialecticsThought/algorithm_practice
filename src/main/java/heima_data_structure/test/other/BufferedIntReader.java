package heima_data_structure.test.other;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

class BufferedIntReader implements Closeable {
    private final BufferedReader reader;
    private final File file;

    public BufferedIntReader(File file) {
        try {
            this.file = file;
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Integer current = null;

    public boolean hasNext() {
        if (current != null) {
            return true;
        } else {
            try {
                String nextLine = reader.readLine();
                if (nextLine != null) {
                    current = Integer.parseInt(nextLine);
                }
                return (nextLine != null);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

    public Integer next() {
        if (current != null || hasNext()) {
            Integer line = current;
            current = null;
            return line;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void close() throws IOException {
        reader.close();
        file.delete();
    }
}
