package com.rinitec.algerieoffice.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

public class FilesUtil {
	public static final String UPLOAD_DIR = "upload";
	public static final String TEMP_DIR = "temp";
	public static final String MAPSITE_DIR = "mapsite";
	
	public static final boolean isValideImage(final MultipartFile file) {
		final String mimeType = file.getContentType().split("/")[0];
		return mimeType.equalsIgnoreCase("image");
	}
	
	public static final boolean isValidePDF(final MultipartFile file) {
		final String mimeType = file.getContentType().split("/")[1];
		return mimeType.equalsIgnoreCase("pdf");
	}
	
	public static final String getOriginalFilename(final MultipartFile file) {
		final String filename = file.getOriginalFilename();
		final String original = filename.lastIndexOf(".") != -1 ? filename.split("\\.")[0] : filename;
		return original.length() > 30 ? original.substring(0, 30) : original;
	}
	
	public static final void downloadFile(final byte[] content, final HttpServletResponse response) throws IOException {
		final OutputStream outStream = response.getOutputStream();
		response.setContentLength(content.length);
		outStream.write(content, 0, content.length);
		outStream.flush();
		outStream.close();
	}
	
	public static final void downloadImage(final byte[] content, final String format, final Integer width, final Integer height, final HttpServletResponse response) throws IOException {
		final InputStream inStream = new ByteArrayInputStream(content);
		final BufferedImage bufferedIconImage = Thumbnails.of(inStream).forceSize(width, height).allowOverwrite(true).outputFormat(format).asBufferedImage();
		final BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
		ImageIO.write(bufferedIconImage, format, outStream);
		outStream.flush();
		outStream.close();
		inStream.close();
	}
	
	public static final void downloadFile(final File file, final HttpServletResponse response) throws IOException {
		final BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(file));
		final BufferedOutputStream outStream = new BufferedOutputStream(response.getOutputStream());
		int bytesRead = 0;
		byte[] buffer = new byte[1024];
		while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
        outStream.flush();
        outStream.close();
        inStream.close();
	}
	
}
