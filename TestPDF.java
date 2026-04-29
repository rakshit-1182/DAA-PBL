import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.FileOutputStream;

public class TestPDF {
    public static void main(String[] args) throws Exception {

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("output.pdf"));

        document.open();

        document.add(new Paragraph("Hello PDF!"));

        PdfPTable table = new PdfPTable(2);
        table.addCell("Room");
        table.addCell("Capacity");

        table.addCell("A1");
        table.addCell("40");

        document.add(table);

        document.close();
    }
}