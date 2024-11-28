package src.primeirob.projetotres;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run(){
        try {
            client = new Socket("localhost", 8080);
            out = new PrintWriter(client.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));

            InputHanlder inHandler = new InputHanlder();
            Thread t = new Thread(inHandler);
            t.start();

            String inMessage;
            while ((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown(){
        done = true;
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

    class InputHanlder implements Runnable {

        @Override
        public void run(){
            try{
                BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
                while (!done) {
                    String message = inReader.readLine();
                    if(message.equals("/quit")){
                        out.println("/quit");
                        inReader.close();
                        shutdown();
                    }else{
                        out.println(message);
                    }
                }
            }catch(IOException e){
                shutdown();
            }
        }
    }

    public static void main(String[] args) {
        clearConsole();
        Client client = new Client();
        client.run();
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