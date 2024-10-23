import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author veritas
 * @Data 2024/10/21 17:27
 */
public class NetworkModel {

    class client {
        Socket socket;

        public client(String host, Integer port) {
            try {
                this.socket = new Socket(host, port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        public void sendText(String text) {
            try {
                OutputStream outputStream = socket.getOutputStream();

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");

                new BufferedWriter(outputStreamWriter).write(text);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String receiveText() {
            try {
                InputStream inputStream = socket.getInputStream();

                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String str = null;

                String wholeText = "";

                while ((str = bufferedReader.readLine()) != null) {
                    wholeText += str + "\n";
                }

                return wholeText;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void sendFile(String filePath) {
            try {
                File file = new File(filePath);

                FileInputStream fileInputStream = new FileInputStream(file);

                OutputStream outputStream = socket.getOutputStream();

                byte[] buffer = new byte[4096 * 4096];

                int bytesRead = 0;

                while ((bytesRead = fileInputStream.read()) != -1) {

                    outputStream.write(buffer, 0, bytesRead);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        public void receiveFile(String filePath) {
            try {
                File file = new File(filePath);

                FileOutputStream fileOutputStream = new FileOutputStream(file);

                InputStream inputStream = socket.getInputStream();

                byte[] buffer = new byte[4096 * 4096];

                int bytesRead = 0;

                while ((bytesRead = inputStream.read()) != -1) {

                    fileOutputStream.write(buffer, 0, bytesRead);
                }

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }


        public void close() {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Server {
        ServerSocket serverSocket;
        // 存储每个客户端的Socket和标记
        private Map<String, Socket> clientSockets = new HashMap<>();

        public Server(Integer port) {
            try {
                this.serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * TCP 连接的独立性：
         *      每次 Socket 连接建立时，TCP 会生成一个唯一的连接标识，基于 四元组：客户端 IP 地址、客户端端口号、服务器 IP 地址、服务器端口号。
         *      这意味着即使同一个客户端 IP 地址和端口号连接，服务器仍然会为其创建一个新的 Socket 实例来处理该连接。
         * 每个连接都是独立的：
         *      无论客户端是同一台机器还是多个不同的机器，每次连接都会生成一个新的 Socket，每个 Socket 实例代表一个独立的 TCP 连接。
         *      即使客户端断开后重新连接，它仍然会有一个新的 Socket 对象。
         * 不会遇到同一个 Socket：
         *      在服务器端，accept() 每次返回的 Socket 都是一个新的实例，即使来自同一个客户端，也不会返回之前已经使用过的 Socket。
         */
        public void accept() {

        }

        public void close() {
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
