import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;

import org.junit.After;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.*;

public class ChatClientTest {
    private final ByteArrayOutputStream outCounter = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errConnect = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.out;

    private static final String LOG_FILE = "C:\\JavaCourseProject\\client_CourseProject\\file.log";


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outCounter));
        System.setErr(new PrintStream(errConnect));
    }

    @After
    public void restoreStream() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void testLoadSettings() {
        ChatClient client = new ChatClient("TestClient");
        client.loadSettings();
        assertEquals(12345, client.getPort());
        assertEquals("localhost", client.getServerAddress());
    }
}
