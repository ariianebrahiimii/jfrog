package net.r4p3;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws InterruptedException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("What do you want to do?");
            System.out.println("1 - generate License String");
            System.out.println("2 - inject artifactory");
            System.out.println("exit - exit");
            String line = scanner.nextLine();
            
            if (line.equalsIgnoreCase("exit")) {
                break;
            }
            if (line.equalsIgnoreCase("1")) {
                System.out.println(new String(new Injector(null).generateCrackedLicense()));
                scanner.nextLine();
                continue;
            }
            while (line.equalsIgnoreCase("2")) {
                System.out.println("where is artifactory home? (\"back\" for back)");
                String home = scanner.nextLine();
                if (home.equals("back")) {
                    break;
                }
                if (!new File(home, "webapps/artifactory.war").exists()) {
                    System.err.println("make sure webapps/artifactory.war exists");
                    System.out.println("type back for back");
                    continue;
                }
                System.out.println("artifactory detected. continue? (yes/no)");
                if (scanner.nextLine().equals("no")) {
                    break;
                }
                new Injector(home + "/webapps").inject();
                System.out.println("DONE!");
                break;
            }
        } while (true);
        scanner.close();
    }
}
