import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FlipkartCustomerSearch {

    public static void main(String[] args) throws Exception {

        if (args.length < 2) {
            System.out.println("Usage:");
            System.out.println("java FlipkartCustomerSearch <pdf-folder> <customer-name>");
            System.exit(1);
        }

        String folderPath = args[0];
        String customerName = args[1];

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("Invalid folder: " + folderPath);
            System.exit(1);
        }

        File[] pdfFiles = folder.listFiles(
            (dir, name) -> name.toLowerCase().endsWith(".pdf"));

        if (pdfFiles == null) {
            System.out.println("No PDF files found.");
            return;
        }

        int matchCount = 0;

        for (File pdf : pdfFiles) {

            try (PDDocument doc = PDDocument.load(pdf)) {

                PDFTextStripper stripper = new PDFTextStripper();
                String text = stripper.getText(doc);

                if (text.toLowerCase()
                        .contains(customerName.toLowerCase())) {

                    matchCount++;

                    System.out.println(
                        "MATCH: " + pdf.getAbsolutePath());
                }
            } catch (Exception e) {
                System.err.println(
                    "Error reading " + pdf.getName() +
                    " : " + e.getMessage());
            }
        }

        System.out.println("\nTotal Matches: " + matchCount);
    }
}
