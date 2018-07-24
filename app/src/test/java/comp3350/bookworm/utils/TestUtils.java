package comp3350.bookworm.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import comp3350.bookworm.Application.Main;

public class TestUtils {
    private static final File DB_SRC = new File("src/main/assets/db/Bookworm.script");
    private static final File DB_DST = new File("src/main/assets/tempdb/Bookworm.script");
    private static final File DB_PROPERTY = new File("src/main/assets/tempdb/Bookworm.properties");

//    public static File copyDB() throws IOException {
//        final File target = File.createTempFile("temp-db", ".script");
//        Files.copy(DB_SRC, target);
//        Main.setDBPathName(target.getAbsolutePath().replace(".script", ""));
//        return target;
//    }

    public static File copyDB() throws IOException {
        FileInputStream inStream = new FileInputStream(DB_SRC);
        FileOutputStream outStream = new FileOutputStream(DB_DST);
        FileChannel inChannel = inStream.getChannel();
        FileChannel outChannel = outStream.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        inStream.close();
        outStream.close();
        Main.setDBPathName("src/main/assets/tempdb/Bookworm");
        return DB_DST;
    }

    public static void delete() {
        DB_DST.delete();
        DB_PROPERTY.delete();
    }
}
