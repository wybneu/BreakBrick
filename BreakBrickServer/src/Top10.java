import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Url;

/**
 * Servlet implementation class Lab5
 */
@WebServlet("/Top10")
public class Top10 extends HttpServlet {
	// Statements
	private String names[];
	private String score[];
	private static int length = 10;
	private String filepath = "/score.txt";
	private boolean isNewRecord = false;
	private int rank = length + 1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public Top10() {
		super();
	}

	public void detelefile(HttpServletRequest request, String path) {
		String strRelPath = request.getSession().getServletContext()
				.getRealPath("")
				+ path;
		File file = new File(strRelPath);
		if (!file.exists()) {
			file.delete();
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("file created");
		}
	}

	private void open(HttpServletRequest request, String path) {
		File file = null;
		FileReader in = null;
		BufferedReader reader = null;
		String strRelPath = "";
		try {
			// URL url = new URL(path);
			// BufferedReader reader = new BufferedReader(new
			// InputStreamReader(url.openStream()));

			strRelPath = request.getSession().getServletContext()
					.getRealPath("")
					+ path;
			System.out.println(strRelPath);
			file = new File(strRelPath);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("file created");
			}
			in = new FileReader(file);
			reader = new BufferedReader(in);
			String line;
			for (int i = 0; i < length; i++) {
				line = reader.readLine();
				if (line == null) {
					names[i] = "unkown";
				} else {
					names[i] = line.toString().trim();
				}
				line = reader.readLine();
				if (line == null) {
					score[i] = "0";
				} else {
					score[i] = line.toString().trim();
				}
			}

		} catch (Exception e) {
			System.out.println("failed");
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (in != null)
					in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public void print() {
		for (int i = 0; i < 10; i++) {
			System.out.println(names[i] + " " + this.score[i]);

		}
	};

	private void open(HttpServletRequest request, String path, String name,
			String score) {
		open(request, path);
		File file = null;
		FileWriter out = null;
		BufferedWriter writer = null;
		String strRelPath = "";
		try {
			for (int i = length - 1; i >= 0; i--) {
				if (Integer.valueOf(score) > Integer.valueOf(this.score[i])) {
					rank = i;
				}
			}
			if (rank < length) {

				for (int i = length - 1; i >= 0; i--) {
					if (i == rank) {
						this.names[i] = name;
						this.score[i] = score;
						break;
					} else {
						this.names[i] = this.names[i - 1];
						this.score[i] = this.score[i - 1];
					}
				}
//				String temp;
//				String tempname;
//
//				for (int i = 0; i < length; i++) {
//					for (int j = i + 1; j < length; j++) {
//						if (Integer.valueOf(this.score[j]) > Integer
//								.valueOf(this.score[i])) {
//							temp=this.score[j];
//							tempname=this.names[j];
//							this.score[j]=this.score[i];
//							this.names[j]=this.names[i];
//							this.score[i]=temp;
//							this.names[i]=tempname;
//						}
//					}
//				}

				strRelPath = request.getSession().getServletContext()
						.getRealPath("")
						+ path;
				file = new File(strRelPath);
				if (!file.exists()) {
					file.createNewFile();
					System.out.println("file created");
				} else {
					file.delete();
					file.createNewFile();
				}
				out = new FileWriter(file);
				writer = new BufferedWriter(out);
				for (int i = 0; i < length; i++) {
					writer.write(names[i]);
					writer.newLine();
					writer.flush();
					writer.write(this.score[i]);
					writer.newLine();
					writer.flush();
				}
			}

		} catch (Exception e) {
			System.out.println("failed");
			e.printStackTrace();
		} finally {
			try {
				if (writer != null)
					writer.close();
				if (out != null)
					out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("txt/plain");
		// detelefile(request,filepath);
		names = new String[length];
		score = new String[length];
		for (int i = 0; i < length; i++) {
			names[i] = "Unkown";
			score[i] = "0";
		}
		DataInputStream dis = new DataInputStream(request.getInputStream());
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());

		if (request.getParameter("name") != null) {
			open(request, filepath, request.getParameter("name"),
					request.getParameter("score"));
		} else {
			open(request, filepath);
		}
		if (request.getParameter("name") != null) {
			dos.writeUTF(rank + "");
		}
		for (int i = 0; i < length; i++) {
			dos.writeUTF(names[i]);
			dos.flush();
			dos.writeUTF(score[i]);
			dos.flush();

		}
		dos.close();
		dis.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
