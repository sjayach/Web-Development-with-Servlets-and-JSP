package cs540.telnetClient;

import java.net.*;
import java.io.*;

public class TelnetClient
{
	
	public static void pwd (String command, PrintWriter pw ) {
		pw.println("PWD : " +System.getProperty("user.dir"));
	}
	
	public static void list_files (PrintWriter pw) {
		File folder = new File(System.getProperty("user.dir"));
		File[] listOfFiles = folder.listFiles();

		    for (File files :listOfFiles) {
		      if (files.isFile()) {
		       pw.println(files.getName());
		      } else if (files.isDirectory()) {
		        pw.println(files.getName() + "/");
		      }
		    }
	}
	
	public static void help_usage(PrintWriter pw) {
		pw.println("Usage:");
		pw.println(" ");
		pw.println("pwd : Displays present working directory");
		pw.println("ls : List all files and directories in the current directory");
		pw.println("bye: Close the connection and reuse the same port later ");
		pw.println("quit: Terminate the server connection");
	}
	
    public static void main(java.lang.String[] args) throws UnknownHostException, IOException {
        //Create object of Socket
    	
    	int portNumber = 4557;
		System.out.println("Creating server socket on port " + portNumber);
		System.setProperty("java.net.useSystemProxies", "true");
		ServerSocket serverSocket = new ServerSocket(portNumber);
		serverSocket.getReuseAddress();
		
		Socket socket; 
		socket =  serverSocket.accept();
		OutputStream os ;
		os = socket.getOutputStream();
		PrintWriter pw;
		pw = new PrintWriter(os, true);
		pw.print(">");

		while (true) {
			boolean is_quit = false;
			
			os.flush();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw.flush();
			String str = br.readLine();
			
			System.out.println("Just said hello to:" + str);
			
			switch(str) {
				case "pwd":
					pwd(str, pw);
					break;
				case "ls":
					list_files(pw);
					break;
				case "bye":
					pw.close();
					socket.close();
					os.close();
					System.out.println("Server Closed");
					socket = serverSocket.accept();
					os = socket.getOutputStream();
					pw = new PrintWriter(os, true);
					break;
				case "quit":
					is_quit = true;
					break;
				case "help":
					help_usage(pw);
					break;
				default:
					if (str.trim().length() != 0)
						pw.println(str+ " : Command not found. Please use \"help\" to find the available commands");
					break;
			}
			
			if (is_quit)
				break;
			
			pw.print(">");
			os.flush();
			pw.flush();
			
		}
		
		pw.close();
		socket.close();
		os.close();
		serverSocket.setReuseAddress(true);
		serverSocket.close();

    }
}