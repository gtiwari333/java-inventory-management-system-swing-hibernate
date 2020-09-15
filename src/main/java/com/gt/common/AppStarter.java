package com.gt.common;

import com.ca.ui.Main;
import org.apache.log4j.Logger;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used in code <br/>
 * com.gt.common-AppStarter.java<br/>
 *
 * @author Ganesh Tiwari @@ gtiwari333@gmail.com <br/>
 * Created on : Mar 19, 2012<br/>
 * Copyright : <a
 * href="http://ganeshtiwaridotcomdotnp.blogspot.com">Ganesh Tiwari </a>
 */
public class AppStarter {
    private final int PORT = 45433;
    public boolean notFindExisting = true;
    Logger logger = Logger.getLogger(AppStarter.class);

    public AppStarter() {

        if (notFindExisting()) {
            // if not found existing ... will be killed
            // start detecting server thread
            new Thread(new DetectForNew()).start();
        }

    }

    private synchronized boolean notFindExisting() {
        // try to connect to server
        Socket client;
        try {
            String HOST = "localhost";
            client = new Socket(HOST, PORT);
            logger.info("Connection accepted by already running app");
            notFindExisting = false;
        } catch (Exception e) {
            notFindExisting = true;

        }
        return notFindExisting;
    }

    class DetectForNew implements Runnable {
        ServerSocket serverSocket;

        public final void run() {
            try {
                serverSocket = new ServerSocket(PORT);
                while (true) {
                    serverSocket.accept();
                    Main.showMaximized();
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("detect thread terminated " + e.getMessage());

            }
        }
    }

}
