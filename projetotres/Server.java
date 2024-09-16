import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable{

    private ArrayList<ConnectionHandler> connections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;

    public Server(){
        connections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run(){
        try {
            server = new ServerSocket(8080);
            pool = Executors.newCachedThreadPool();
            System.out.println("Server running on port: " + 8080);

            while (!done) {
                Socket client = server.accept();
                ConnectionHandler handler = new ConnectionHandler(client);
                connections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {
            shutdown();
        }
    }

    public void broadCast(String message){
        for(ConnectionHandler ch : connections){
            if(ch != null){
                ch.sendMessage(message);
            }
        }
    }

    public void shutdown(){
        try {
            done = true;
            pool.shutdown();
            if(!server.isClosed()){
                server.close();
            }

            for(ConnectionHandler ch : connections){
                ch.shutdown();
            }
        } catch (IOException e) {
            // ignore
        }
    }

    class ConnectionHandler implements Runnable{
        
        private Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler(Socket client){
            this.client = client;
        }

        @Override
        public void run(){
            try {
                out = new PrintWriter(client.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                out.println("(System): Please enter a nickname: ");
                nickname = in.readLine();
                System.out.println(nickname + " connected!");
                broadCast(nickname + " joined the chat!");
                out.println("(System): Write your message:");
                String message;
                while ((message = in.readLine()) != null) {
                    if(message.startsWith("/nick")){
                        String[] messageSplit = message.split(" ", 2);
                        if(messageSplit.length == 2){
                            broadCast(nickname + " rename themselves to " + messageSplit[1]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[1]);
                            nickname = messageSplit[1];
                            out.println("(System): Successfully changed nickname to " + nickname);
                        }else{
                            out.println("(System): No nickname provided!");
                        }
                    }else if(message.startsWith("/quit")){
                        broadCast(nickname + " left the chat!");
                        shutdown();
                    }else{
                        broadCast(nickname + ": " + message);
                        out.println("(System): Write your message:");
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }


        public void sendMessage(String message){
            out.println(message);
        }

        public void shutdown(){
            try {
                in.close();
                out.close();
                if(!client.isClosed()){
                    client.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }
    public static void main(String[] args) {
        clearConsole();
        Server server = new Server();

        server.run();
    }

    private static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}