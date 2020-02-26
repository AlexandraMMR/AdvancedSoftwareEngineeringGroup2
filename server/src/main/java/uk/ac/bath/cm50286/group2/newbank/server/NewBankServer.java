package uk.ac.bath.cm50286.group2.newbank.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class NewBankServer extends Thread{

	private static final Logger LOGGER = LogManager.getLogger(NewBankServer.class);
	private ServerSocket server;

	public NewBankServer(int port) throws IOException {
		server = new ServerSocket(port);
	}

	public void run() {
		// starts up a new client handler thread to receive incoming connections and process requests
		System.out.println("New Bank Server listening on " + server.getLocalPort());
		try {
			while(true) {
				Socket s = server.accept();
				NewBankClientHandler clientHandler = new NewBankClientHandler(s);
				clientHandler.start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) throws IOException, SQLException {
		// starts a new NewBankServer thread on a specified port number
		LOGGER.info("Starting NewBankServer on port 14002");
		new NewBankServer(14002).start();
	}
}
