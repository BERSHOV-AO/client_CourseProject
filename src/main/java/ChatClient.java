import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost"; // Адрес сервера
    private static final String SETTINGS_FILE = "C:\\СourseChat\\client_CourseProject\\settings.txt";
    private static final String LOG_FILE = "C:\\СourseChat\\client_CourseProject\\file.log";
    private int port;
    private String clientName;
    private PrintWriter logWriter;

    public ChatClient(String clientName) {
        this.clientName = clientName;
        loadSettings();
        startClient();
    }

    private void loadSettings() {
        try (Scanner scanner = new Scanner(new File(SETTINGS_FILE))) {
            port = Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    private void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);

            out.println(clientName);
            System.out.println("Сетевой чат: ");

            Thread messageThread = new Thread(() -> {
                String serverMessage;
                try {

                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println(serverMessage);
                        logWriter.println(serverMessage); // запись в файл логирования
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            messageThread.start();
            String clientMessage;
            while (!(clientMessage = scanner.nextLine()).equalsIgnoreCase("/exit")) {
                out.println(clientMessage);
                // Запись сообщения в файл логирования
                logWriter.println("[" + getCurrentTime() + "] " + clientName + ": " + clientMessage);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String clientName = scanner.nextLine();
        new ChatClient(clientName);
    }
}