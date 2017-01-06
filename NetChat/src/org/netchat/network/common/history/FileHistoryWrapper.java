package org.netchat.network.common.history;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class FileHistoryWrapper implements HistoryWrapper {
    private File file;
    private FileWriter writer;

    public FileHistoryWrapper() {
    }

    public void init(String filePath) throws IllegalArgumentException {
        if (filePath == null || filePath.isEmpty())
            throw new IllegalArgumentException("empty file path");
        init(new File(filePath));
    }

    public void init(File file) throws IllegalArgumentException {
        if (file == null)
            throw new IllegalArgumentException("empty file");
        this.file = file;
        try {
            this.writer = new FileWriter(this.file, this.file.exists());
        } catch (IOException e) {
            throw new IllegalArgumentException(String.format("can't init wrapper by path=[%s]", this.file.getPath()), e);
        }
    }

    @Override
    public void write(String s) {
        try {
            this.writer.write(String.format("[%1$td.%1$tm.%1$ty %1$tH:%1$tM:%1$tS.%1$tL] %2$s", new Date(), s));
            this.writer.flush();
        } catch (IOException e) {
            System.err.println(String.format("problem write history to file=[%s]", e.getMessage()));
            e.printStackTrace();
        }
    }
}
