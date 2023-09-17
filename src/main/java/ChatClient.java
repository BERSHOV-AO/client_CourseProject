import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ChatClient {
    private static final String SETTINGS_FILE = "C:\\JavaCourseProject\\client_CourseProject\\settings.txt";
    private static final String LOG_FILE = "C:\\JavaCourseProject\\client_CourseProject\\file.log";
    private int port;
    private String serverAddress;
    private String clientName;
    private PrintWriter logWriter;
    private boolean isRunning = false;

    public ChatClient(String clientName) {
        this.clientName = clientName;
        // loadSettings();
        //  startClient();
    }

    public void loadSettings() {
        try (Scanner scanner = new Scanner(new File(SETTINGS_FILE))) {
            port = Integer.parseInt(scanner.nextLine());
            serverAddress = scanner.nextLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public int getPort() {
        return port;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public boolean getTsRunning() {
        return isRunning;
    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public void startClient() {
        try (Socket socket = new Socket(serverAddress, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            isRunning = true;

            logWriter = new PrintWriter(new FileWriter(LOG_FILE, true), true);

            out.println(clientName);
            System.out.println("Сетевой чат: ");

            Thread messageThread = new Thread(() -> {
                String serverMessage;
                try {
                    if (isRunning == true) {
                        while (!socket.isClosed() && isRunning == true && (serverMessage = in.readLine()) != null) {
                            System.out.println(serverMessage);
                            logWriter.println(serverMessage); // запись в файл логирования
                        }
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
            isRunning = false;
            // Выход из цикла, если получена команда "/exit"
            out.println(clientMessage); // Отправляем команду "/exit" серверу
            logWriter.println("[" + getCurrentTime() + "] " + clientName + ": " + clientMessage);

            // Закрываем  сокет и другие ресурсы
            messageThread.interrupt();
            socket.close();
            logWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.print("Enter your name: ");
        Scanner scanner = new Scanner(System.in);
        String clientName = scanner.nextLine();
        //new ChatClient(clientName);
        ChatClient client = new ChatClient(clientName);
        client.loadSettings();
        client.startClient();
    }
}