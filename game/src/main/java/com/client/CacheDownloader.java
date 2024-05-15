package com.client;

import com.client.sign.Signlink;
import com.google.common.base.Preconditions;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

public class CacheDownloader {

	private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(CacheDownloader.class.getName());

	public static int cacheVersionRemote;
	public static int cacheVersionLocal;

	private Client client;

	private static final int BUFFER = 2048;

	private Path fileLocation;

	public CacheDownloader(Client client) {
		Objects.requireNonNull(Signlink.getCacheDirectory());
		this.client = client;
		var archiveName = getArchivedName();
		fileLocation = Paths.get(Signlink.getCacheDirectory(), archiveName);
	}

	private int getLocalVersion() {
		try(BufferedReader fr = new BufferedReader(new FileReader(Signlink.getCacheDirectory() + File.separator + "version.dat"))){
			return Integer.parseInt(fr.readLine());
		} catch (Exception e) {
			return -1;
		}
	}

	public void writeVersion(int version) {
		File versionFile = new File(Signlink.getCacheDirectory() + File.separator + "version.dat");
		if(versionFile.exists())
			versionFile.delete();
		try(BufferedWriter br = new BufferedWriter(new FileWriter(versionFile))) {
			br.write(version + "");
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void deleteZip() {
		try {
			Files.deleteIfExists(fileLocation);
		} catch (IOException ex) {
			log.severe("Cannot delete cache zip file.");
		}
	}

	public CacheDownloader downloadCache() {
		if (Signlink.usingDevCache()) {
			System.out.println("Using local_cache, skipping cache update.");
			return null;
		}

		try {
			File location = new File(Signlink.getCacheDirectory());
			File version = new File(Signlink.getCacheDirectory() + "/version.dat");
			cacheVersionRemote = Configuration.CACHE_VERSION;
			if (!location.exists() || !version.exists()) {
				log.info("Cache does not exist, downloading.");
				update();
			}
			else {
				cacheVersionLocal = getLocalVersion();
				log.info("Cache version local=" + cacheVersionLocal + ", remote=" + cacheVersionRemote);
				if (cacheVersionRemote != cacheVersionLocal)
					update();
				else
					return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	private void update() throws IOException {
		downloadFile(Configuration.CACHE_LINK, getArchivedName());
	}

	private void downloadFile(String adress, String localFileName) throws IOException {
		OutputStream out = null;
		URLConnection conn;
		InputStream in = null;

		try {
			URL url = new URL(adress);
			out = new BufferedOutputStream(new FileOutputStream(Signlink.getCacheDirectory() + "/" + localFileName));

			conn = url.openConnection();
			in = conn.getInputStream();

			byte[] data = new byte[BUFFER];

			int numRead;
			long numWritten = 0;
			int fileSize = conn.getContentLength();

			while ((numRead = in.read(data)) != -1) {
				out.write(data, 0, numRead);
				numWritten += numRead;
				int percentage = (int) (((double) numWritten / (double) fileSize) * 100D);
				client.drawLoadingText(percentage, Configuration.CLIENT_TITLE + " - Downloading Cache ");
			}
		}
		finally {
			try {
				if (in != null)
					in.close();
				if (out != null)
					out.close();
				unZip();
			}
			catch (IOException ioe) {
				ioe.printStackTrace(System.err);
			}
		}
	}

	private String getArchivedName() {
		int lastSlashIndex = Configuration.CACHE_LINK.lastIndexOf('/');
		if (lastSlashIndex >= 0 && lastSlashIndex < Configuration.CACHE_LINK.length() - 1) {
			String u = Configuration.CACHE_LINK.substring(lastSlashIndex + 1);
			return u.replace("?dl=1", "");
		}
		else {
			System.err.println("error retrieving archived name.");
		}
		return "";
	}

	public void unZip() throws IOException {
		try (var sevenZFile = new SevenZFile(new File(fileLocation.toString()))) {
			SevenZArchiveEntry entry;
			int numWritten = 0;
			int files = countRegularFiles(new File(fileLocation.toString()));

			while ((entry = sevenZFile.getNextEntry()) != null) {
				String fileName = entry.getName();
				File newFile = new File(Signlink.getCacheDirectory() + File.separator + fileName);

				if (entry.isDirectory())
					new File(Signlink.getCacheDirectory() + entry.getName()).mkdir();
				else {
					int percentage = (int) (((double) numWritten++ / (double) files) * 100D);
					client.drawLoadingText(percentage, Configuration.CLIENT_TITLE + " - Installing Cache ");

					if (fileName.equals(fileLocation.getFileName().toString())) {
						try {
							unzip(sevenZFile, fileLocation.toString());
						}
						catch (IOException e) {
							e.printStackTrace(System.err);
						}
						break;
					}

					File file = new File(newFile.getParent());
					if (!file.exists())
						Preconditions.checkState(file.mkdirs(), "Cannot create file.");
					try {
						unzip(sevenZFile, Signlink.getCacheDirectory() + fileName);
					}
					catch (IOException e) {
						e.printStackTrace(System.err);
					}
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace(System.err);
		}
		finally {
			deleteZip();
			writeVersion(cacheVersionRemote);
		}
	}

	private void unzip(SevenZFile sevenZFile, String outputFilePath) throws IOException {
		try (var out = new BufferedOutputStream(new FileOutputStream(outputFilePath))) {
			byte[] buffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = sevenZFile.read(buffer)) != -1) {
				out.write(buffer, 0, bytesRead);
			}
		}
	}
	private static int countRegularFiles(File sevenZFile) throws IOException {
		int count = 0;

		File tempFile = File.createTempFile("tempSevenZFile", ".7z");
		try {
			// Create a temporary file to copy the content of the original SevenZFile
			Files.copy(sevenZFile.toPath(), tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

			// Create a new SevenZFile instance for counting
			try (SevenZFile tempSevenZFile = new SevenZFile(tempFile)) {
				SevenZArchiveEntry entry;
				while ((entry = tempSevenZFile.getNextEntry()) != null) {
					if (!entry.isDirectory()) {
						count++;
					}
				}
			}
		} finally {
			// Cleanup: delete the temporary file
			tempFile.delete();
		}

		return count;
	}




}