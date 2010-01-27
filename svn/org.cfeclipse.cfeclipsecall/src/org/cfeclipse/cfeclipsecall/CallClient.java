package org.cfeclipse.cfeclipsecall;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.util.regex.Pattern;

public class CallClient {
	private static boolean firstCall = true;

	public static final String ENV_SOCKET = "eclipsecall.socket";
	public static final String ENV_CALL = "eclipsecall.call";
	public static final int DEFAULT_SOCKET = 2341;
	private static final Pattern p_arg = Pattern.compile("\\-[SG][0-9,\\-]+");

	public static void doOpen(String socketNumber, String eclipse, String call) {
		Socket server = null;
		int sockno = DEFAULT_SOCKET;
		if (socketNumber != null) {
			sockno = Integer.parseInt(socketNumber);
		}
		System.out.println(call);

		try {
			// try to establish the socket connection
			server = new Socket("localhost", sockno);
			OutputStream out = server.getOutputStream();
			out.write(call.getBytes());
			out.flush();
			server.close();
		} catch (ConnectException ex) {
			// on connection exception: try to start the program given by the
			// "-E" parameter
			if (firstCall) {
				firstCall = false;
				if (eclipse != null) {
					try {
						Runtime.getRuntime().exec(eclipse);
						synchronized (CallClient.class) {
							CallClient.class.wait(3000);
						}
						for (int i = 0; i < 120; i++) {
							try {
								server = new Socket("localhost", sockno);
								synchronized (CallClient.class) {
									CallClient.class.wait(1000);
								}
								server.close();
								server = new Socket("localhost", sockno);
								OutputStream out = server.getOutputStream();
								out.write(call.getBytes());
								out.flush();
								server.close();
								new Thread(new Runnable() {
									public void run() {
										synchronized (CallClient.class) {
											try {
												CallClient.class.wait(200);
											} catch (Throwable T) {
												// nothing to do
											}
										}
										System.exit(0);
									}
								}).start();
							} catch (ConnectException e) {
								// nothing to do - wait a little longer
							}
							synchronized (CallClient.class) {
								CallClient.class.wait(500);
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (server != null)
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
	}

	public static String[] parseArgs(String[] args) {
		String[] retStrings = { "server", "eclipse", "call" };
		String eclipse, call;
		int sockno = DEFAULT_SOCKET;
		int i = 0;
		call = args[0];
		eclipse = "";
		try {
			String env_sock = System.getProperty(ENV_SOCKET);
			String env_call = System.getProperty(ENV_CALL);
			System.out.println("sys properties: " + env_call + " sock: " + env_sock);
			if (env_call != null) {
				eclipse = env_call;
			}
			if (env_sock != null) {
				sockno = Integer.parseInt(env_sock);
			}
		} catch (Throwable T) {
			// nothing to do
		}
		while (i < args.length - 1 && !args[i + 1].startsWith("-")) {
			call += " " + args[i + 1];
			i++;
		}
		File f = new File(call);
		if (!f.isAbsolute()) {
			call = new File(System.getProperty("user.dir"), call).getAbsolutePath();
		}
		for (; i < args.length; i++) {
			if (args[i].startsWith("-G") && args[i].length() > 2) {
				String add = args[i].substring(2);
				add = add.replace(',', '|');
				call += "|" + add;
			} else if (args[i].startsWith("-S")) {
				sockno = Integer.parseInt(args[i].substring(2));
			} else if (args[i].startsWith("-E")) {
				eclipse = args[i].substring(2);
				while (i < args.length - 1) {
					if (p_arg.matcher(args[i + 1]).matches()) {
						break;
					}
					eclipse += " ";
					String add = args[i + 1];
					if (args[i + 1].indexOf(' ') != -1 || args[i + 1].indexOf("(") != -1) {
						add = "\"" + add + "\"";
					}
					eclipse += " " + add;
					i++;
				}
			}
		}
		retStrings[0] = Integer.toString(sockno);
		retStrings[0] = eclipse;
		retStrings[0] = call;
		return retStrings;

	}

	public static void main(String[] args) {

		if (System.getProperty("os.name").indexOf("Mac") != -1) {
			MacOsHandler.initializeMacOsHandler();
		}

		if (args.length == 0) {
			System.out.println("use -usage argument for cfeclipsecall options");
		} else if (args.length == 1 && args[0].equals("-usage")) {
			System.out.println("usage: ");
			System.out.println("eclipsecall <filename> [-G<lineno>[,<col>-<col>]] [-S<socketno>] [-E<eclipse.exe>]");
			System.out.println("example:");
			System.out.println("mark column 10-20 in line 100 of myfile: ");
			System.out.println("> eclipsecall D:\\mydir\\myfile -G100,10-20");
			System.out.println("only show myfile without marking: ");
			System.out.println("> eclipsecall D:\\mydir\\myfile");
		} else {
			String[] parsedArgs = parseArgs(args);
			doOpen(parsedArgs[0], parsedArgs[1], parsedArgs[2]);
		}
	}
}
