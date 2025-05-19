import java.io.*;
import java.net.*;
import java.nio.channels.*;
import java.util.Properties;

public class MavenWrapperDownloader {

    private static final String WRAPPER_VERSION = "0.5.6";
    private static final String DEFAULT_DOWNLOAD_URL = "https://repo.maven.apache.org/maven2/io/takari/maven-wrapper/"
            + WRAPPER_VERSION + "/maven-wrapper-" + WRAPPER_VERSION + ".jar";
    private static final String MAVEN_WRAPPER_PROPERTIES_PATH = ".mvn/wrapper/maven-wrapper.properties";
    private static final String MAVEN_WRAPPER_JAR_PATH = ".mvn/wrapper/maven-wrapper.jar";
    private static final String PROPERTY_NAME_WRAPPER_URL = "wrapperUrl";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Base directory argument is required.");
            System.exit(1);
        }

        File baseDirectory = new File(args[0]);
        System.out.println("- Using base directory: " + baseDirectory.getAbsolutePath());

        String downloadUrl = resolveDownloadUrl(baseDirectory);
        System.out.println("- Downloading from: " + downloadUrl);

        File outputFile = new File(baseDirectory, MAVEN_WRAPPER_JAR_PATH);
        ensureOutputDirectoryExists(outputFile);

        System.out.println("- Downloading to: " + outputFile.getAbsolutePath());
        try {
            downloadFile(downloadUrl, outputFile);
            System.out.println("Download complete.");
            System.exit(0);
        } catch (Exception e) {
            System.err.println("- Error downloading:");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static String resolveDownloadUrl(File baseDirectory) {
        File propertyFile = new File(baseDirectory, MAVEN_WRAPPER_PROPERTIES_PATH);
        if (propertyFile.exists()) {
            try (FileInputStream fis = new FileInputStream(propertyFile)) {
                Properties props = new Properties();
                props.load(fis);
                return props.getProperty(PROPERTY_NAME_WRAPPER_URL, DEFAULT_DOWNLOAD_URL);
            } catch (IOException e) {
                System.err.println("- Error loading properties file: " + e.getMessage());
            }
        }
        return DEFAULT_DOWNLOAD_URL;
    }

    private static void ensureOutputDirectoryExists(File file) {
        File parentDir = file.getParentFile();
        if (!parentDir.exists() && !parentDir.mkdirs()) {
            System.err.println("- Failed to create directory: " + parentDir.getAbsolutePath());
        }
    }

    private static void downloadFile(String urlString, File destination) throws IOException {
        setAuthenticatorIfPresent();
        URL url = new URL(urlString);
        try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
             FileOutputStream fos = new FileOutputStream(destination)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        }
    }

    private static void setAuthenticatorIfPresent() {
        String username = System.getenv("MVNW_USERNAME");
        String password = System.getenv("MVNW_PASSWORD");

        if (username != null && password != null) {
            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password.toCharArray());
                }
            });
        }
    }
}
