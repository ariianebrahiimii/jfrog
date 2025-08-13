package net.r4p3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.commons.codec.binary.Base64;
import org.jfrog.license.api.Product;
import org.jfrog.license.api.Product.Type;
import org.jfrog.license.legacy.JsonLicenseSerializer;

public class Injector {

	private String home;

	public byte[] generateCrackedLicense() {
		Map<String, Product> products = new HashMap<String, Product>();
		Product p = new Product();
		p.setType(Type.ENTERPRISE_PLUS);
		p.setExpires(Date.from(LocalDateTime.now().plus(1, ChronoUnit.YEARS).toInstant(ZoneOffset.ofHours(0))));
		p.setValidFrom(Date.from(Instant.now()));
		p.setTrial(false);
		p.setId("");
		p.setOwner("r4p3");
		products.put("artifactory", p);
		JsonLicenseSerializer serializer = new JsonLicenseSerializer();
		
		return Base64.encodeBase64(serializer.generate(products));
	}
	

	public Injector(String home) {
		this.home = home;
	}

	public void inject() throws URISyntaxException {
		try {
			File file = new File(home, "artifactory.war");
			File temp = new File(home, "artifactorytemp.war");
			File lic = new File(home, "aam-new");
			File licInjected = new File(home, "aam-injecting");
			File licOrig = new File(home, "aam-original");
			ZipOutputStream in = new ZipOutputStream(new FileOutputStream(lic));

			JarFile jarFile = new JarFile(getJarFile());
			for (Enumeration<?> e = jarFile.entries(); e.hasMoreElements();) {
				ZipEntry en = (ZipEntry) e.nextElement();
				InputStream zis = jarFile.getInputStream(en);
				if (!en.getName().startsWith("org/jfrog/license"))
					continue;
				in.putNextEntry(en);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = zis.read(bytes)) >= 0) {
					in.write(bytes, 0, length);
				}
				in.closeEntry();
			}
			jarFile.close();
			in.close();

			FileOutputStream out = new FileOutputStream(temp);
			ZipFile zf = new ZipFile(file);
			replaceConfigurationFile(zf, new ZipOutputStream(out), lic, licInjected, licOrig,
					Pattern.compile(".*artifactory-addons-manager-(.+\\.)*jar"));
			out.close();
			zf.close();
			Files.copy(temp.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			temp.delete();
			lic.delete();
			licInjected.delete();
			licOrig.delete();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private File getJarFile() throws FileNotFoundException, URISyntaxException {
		return new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
	}

	public static void replaceConfigurationFile(ZipFile zipFile, ZipOutputStream zos, File lic, File licInjected,
			File licOrig, Pattern configFileToReplaced) throws IOException {
		String zipEntryName;
		byte[] buf = new byte[1024];
		int len;
		for (Enumeration<?> e = zipFile.entries(); e.hasMoreElements();) {

			ZipEntry entryIn = (ZipEntry) e.nextElement();
			zipEntryName = entryIn.getName();
            entryIn = new ZipEntry(zipEntryName);

			if (configFileToReplaced.matcher(zipEntryName).matches()) {
				System.out.println("putting another " + zipEntryName);
				zos.putNextEntry(entryIn);

				InputStream is = zipFile.getInputStream(entryIn);
				FileOutputStream ozos = new FileOutputStream(licOrig);

				while ((len = (is.read(buf))) > 0) {
					ozos.write(buf, 0, len);
				}
				ozos.close();

				// Original
				ZipFile orig = new ZipFile(licOrig);
				ZipOutputStream ozis = new ZipOutputStream(new FileOutputStream(licInjected));

				for (Enumeration<?> ea = orig.entries(); ea.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) ea.nextElement();
					if (entry.getName().startsWith("org/jfrog/license/")) {
						continue;
					}
					System.out.println(entry.getName());
					InputStream stream = orig.getInputStream(entry);
					ozis.putNextEntry(new ZipEntry(entry.getName()));

					while ((len = (stream.read(buf))) > 0) {
						ozis.write(buf, 0, len);
					}
					ozis.closeEntry();
					stream.close();
				}
				ZipFile licZip = new ZipFile(lic);
				for (Enumeration<?> ea = licZip.entries(); ea.hasMoreElements();) {
					ZipEntry entry = (ZipEntry) ea.nextElement();

					System.out.println("Injecting " + entry.getName());
					InputStream stream = licZip.getInputStream(entry);
					ozis.putNextEntry(new ZipEntry(entry.getName()));

					while ((len = (stream.read(buf))) > 0) {
						ozis.write(buf, 0, len);
					}
					ozis.closeEntry();
					stream.close();
				}
				licZip.close();
				ozis.close();
				orig.close();
				FileInputStream iss = new FileInputStream(licInjected);

				while ((len = (iss.read(buf))) > 0) {
					zos.write(buf, 0, len);
				}
				iss.close();
				zos.closeEntry();
			} else {
				zos.putNextEntry(entryIn);
				InputStream is = zipFile.getInputStream(entryIn);
				while ((len = (is.read(buf))) > 0) {
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				is.close();
			}

		} // enf of for
		zos.close();
	}
}
