package gui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JFrame;

import board.player.Player;
import exceptions.UnexpectedFormatException;

//Creacion de servidor y cliente
public class ServerClient implements Runnable {
	//Declaracion de variables
	private String ip = "localhost";
	private int port = 22222;
	private Scanner scanner = new Scanner(System.in);
	private JFrame frame;
	private final int WIDTH = 506;
	private final int HEIGHT = 527;
	private Thread thread;

	private Socket socket;
	private DataOutputStream dos;
	private DataInputStream dis;
	private ServerSocket serverSocket;
	private boolean accepted = false;
	
	private String p1;
	private String p2;
	//Acceso al servidor y cliente con la ip y puerto
	public ServerClient() throws IOException, UnexpectedFormatException {
		System.out.println("Please input the IP: ");
		ip = scanner.nextLine();
		System.out.println("Please input the port: ");
		port = scanner.nextInt();
		while (port < 1 || port > 65535) {
			System.out.println("The port you entered was invalid, please input another port: ");
			port = scanner.nextInt();
		}
		//Apertura de juego al conectarse ambos
		if (!connect()) initializeServer();
			
		Gui gui = new Gui(new Player(p1), new Player(p2));
		
		thread = new Thread(this, "MonsterTECG");
		thread.start();
	}
	//Corrida de juego
	public void run() {
		while (true) {
			if ( !accepted) {
				listenForServerRequest();
			}
		}
	}
	//EScucha de cliente para conectarse al servidor
	private void listenForServerRequest() {
		Socket socket = null;
		try {
			socket = serverSocket.accept();
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
			System.out.println("CLIENT HAS REQUESTED TO JOIN, AND WE HAVE ACCEPTED");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//COnexion de cliente
	private boolean connect() {
		try {
			socket = new Socket(ip, port);
			dos = new DataOutputStream(socket.getOutputStream());
			dis = new DataInputStream(socket.getInputStream());
			accepted = true;
		} catch (IOException e) {
			System.out.println("Unable to connect to the address: " + ip + ":" + port + " | Starting a server, Waiting for another player");
			return false;
		}
		System.out.println("Successfully connected to the server.");
		return true;
	}
	//Inicio de servidor
	private void initializeServer() {
		try {
			serverSocket = new ServerSocket(port, 8, InetAddress.getByName(ip));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException, UnexpectedFormatException {
		ServerClient server = new ServerClient();
	}

}
