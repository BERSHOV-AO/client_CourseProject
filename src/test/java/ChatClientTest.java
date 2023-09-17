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

//    @Test
//    public void testStartClient() {
//        ChatClient client = new ChatClient("TestClient");
//        client.setPort(12345);
//        client.setServerAddress("localhost");
//
//        client.loadSettings();
//
//
//        // Redirect console output to a file
////        File logFile = new File("test.log");
////        client.setLogFile(logFile);
//
//
////        Thread clientThread = new Thread(client::startClient);
////        clientThread.start();
//        Thread clientThread = new Thread(() -> client.startClient());
//        clientThread.start();
//
//
//        // ждем когда клиент стартует
//        try {
//            Thread.sleep(1000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
//
//        System.setIn(new ByteArrayInputStream("\nAlex\n".getBytes()));
//
//
//        Assert.assertEquals(true, client.getTsRunning());
//       // assertTrue(client.getTsRunning());
//
//        // симулируем ввод пользователя команды exit
//        System.setIn(new ByteArrayInputStream("/exit\n".getBytes()));
//
// //        ждем остановки клиента
//        try {
//            clientThread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        // Check if the log file is created
//       // assertEquals(true, );
//
//       // assertFalse(client.getTsRunning());
//    }
}
