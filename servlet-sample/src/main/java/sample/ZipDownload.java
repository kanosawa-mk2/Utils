package sample;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

/**
 * Servlet implementation class ZipDownload
 */
@WebServlet("/ZipDownload")
public class ZipDownload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ZipDownload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//response.setCharacterEncoding("UTF-8");
		
		String path = request.getParameter("path");
		String zipNm = request.getParameter("zipNm");

		final String zipName = zipNm + ".zip";

		String sysTmpDir = System.getProperty("java.io.tmpdir");
		Path tmpDirPath = null;
		try {
			tmpDirPath = Files.createTempDirectory(Paths.get(sysTmpDir), "prefix");

			copyFile(tmpDirPath, path);

			zipDownload(response, zipName, tmpDirPath.toString());

		} finally {
			if (tmpDirPath != null) {
				FileUtils.deleteDirectory(tmpDirPath.toFile());
			}
		}

	}

	private void copyFile(Path tmpDirPath, String path) throws IOException {

		File tmpFile = new File(path);

		if (tmpFile.isDirectory()) {
			for (File file : tmpFile.listFiles()) {
				copyFile(tmpDirPath, file.toString());
			}
		} else {
			FileUtils.copyFileToDirectory(tmpFile, tmpDirPath.toFile());
		}
	}

	private void zipDownload(HttpServletResponse res, String zipName, String inputFilePath) throws IOException {

		try (ServletOutputStream sos = res.getOutputStream(); ZipOutputStream zos = new ZipOutputStream(sos,Charset.forName("MS932"))) {
			res.setContentType("application/zip");
			res.setHeader("Content-Disposition",
					String.format("attachment; filename=\"%s\"", URLEncoder.encode(zipName, "UTF-8")));
			File file = new File(inputFilePath);
			includeZip(zos, file);
		}

	}

	private void includeZip(ZipOutputStream zos, File targetFile) throws IOException {

		if (targetFile.isDirectory()) {
			File[] files = targetFile.listFiles();
			for (File file : files) {
				includeZip(zos, file);
			}
		} else {
			System.out.println(targetFile.getName());
			ZipEntry zipEntry = new ZipEntry(targetFile.getName());
			try (BufferedInputStream is = new BufferedInputStream(new FileInputStream(targetFile))) {
				zos.putNextEntry(zipEntry);
				writeStreame(is, zos);
				zos.closeEntry();
			}
		}
	}

	private void writeStreame(InputStream is, OutputStream os) throws IOException {
		int DEFAULT_BUFFER_SIZE = 1024 * 4;
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int size = -1;
		while (-1 != (size = is.read(buffer))) {
			os.write(buffer, 0, size);
		}

	}

}
