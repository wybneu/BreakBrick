import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int totalLevel;
	private int lastLevel;
	String path = "/levelInfo.txt";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Update() {
		super();
		// TODO Auto-generated constructor stub
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

	private String openLevel(HttpServletRequest request, int level) {
		String temp = "";
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
					+ "/level" + level + ".txt";
			System.out.println(strRelPath);
			file = new File(strRelPath);
			if (!file.exists()) {
				file.createNewFile();
				System.out.println("file created");
			}
			in = new FileReader(file);
			reader = new BufferedReader(in);
			String line;
			line = reader.readLine();
			if (line == null) {
				return null;
			} else {
				if (line.contains("level")) {
					while (line != null) {
						temp += line.toString().trim() + "\n";
						line = reader.readLine();
					}
				} else {
					temp = line.toString().trim();
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
		return temp;
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
			line = reader.readLine();
			if (line == null) {
				this.totalLevel = 6;
			} else {
				this.totalLevel = Integer.valueOf(line.toString().trim());
			}
		} catch (Exception e) {
			System.out.println("failed");
			e.printStackTrace();
			this.totalLevel = 6;
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

	private boolean open(HttpServletRequest request, String path, int lastLevel) {
		open(request, path);
		if (lastLevel >= this.totalLevel) {
			return false;
		}
		return true;

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("txt/plain");
		// detelefile(request,filepath);
		DataInputStream dis = new DataInputStream(request.getInputStream());
		DataOutputStream dos = new DataOutputStream(response.getOutputStream());
		if (request.getParameter("lastLevel") == null) {
			return;
		}
		lastLevel = Integer.valueOf(request.getParameter("lastLevel"));
		if (!open(request, this.path, lastLevel)) {

			dos.writeUTF("NOUPDATE");
		} else {
			dos.writeUTF(this.totalLevel + "");
			dos.flush();
			for (int i1 = lastLevel + 1; i1 <= totalLevel; i1++) {
				dos.writeUTF(this.openLevel(request, i1));
				dos.flush();
			}
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
