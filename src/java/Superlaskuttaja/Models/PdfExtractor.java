/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Superlaskuttaja.Models;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import static com.itextpdf.text.Rectangle.BOTTOM;
import static com.itextpdf.text.Rectangle.LEFT;
import static com.itextpdf.text.Rectangle.RIGHT;
import static com.itextpdf.text.Rectangle.TOP;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Augustus58
 */
public class PdfExtractor {

    private final DateFormat dateFormat1;
    private final DateFormat dateFormat2;
    private final DateFormat dateFormat3;
    private final DateFormat dateFormat4;

    public PdfExtractor() {
        this.dateFormat1 = new SimpleDateFormat("dd.MM.yyyy");
        this.dateFormat2 = new SimpleDateFormat("dd.MM.yyyy HH.mm");
        this.dateFormat3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
    }

    public void getPdf(HttpServletResponse response, Laskuttaja laskuttaja, Lasku lasku, List<Suorite> suoritteet, Asiakas tilaaja, Asiakas vastaanottaja) throws IOException, DocumentException, SQLException, ParseException {
        Document document = new Document(PageSize.A4, 40, 20, 30, 30);
        response.setContentType("application/pdf");
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        muodostaDokumentti(document, writer, laskuttaja, lasku, suoritteet, tilaaja, vastaanottaja);
        writer.close();
    }

    private void muodostaDokumentti(Document document, PdfWriter writer, Laskuttaja laskuttaja, Lasku lasku, List<Suorite> suoritteet, Asiakas tilaaja, Asiakas vastaanottaja) throws DocumentException, SQLException, ParseException {
        document.open();
        //----------------------------------------------------------------------
        PdfPTable table1 = new PdfPTable(7);
        Font f1 = new Font(Font.FontFamily.HELVETICA, 10);
        Font f2 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
        Font f3 = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font f4 = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

        table1.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
        table1.setLockedWidth(true);
        table1.setWidths(new int[]{370, 100, 73, 100, 73, 100, 179});
        PdfPCell cell;
        Paragraph p;

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph(laskuttaja.getYrityksenNimi(), f4);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("LASKU", f4);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        table1.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        p = new Paragraph(laskuttaja.getKatuosoite(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        table1.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph(laskuttaja.getPostinumero() + " " + laskuttaja.getKaupunki(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("Päiväys", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph(dateFormat1.format(dateFormat4.parse(lasku.getPaivays().toString())), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        table1.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("Laskun numero", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph(lasku.getLaskunNumero().toString(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        table1.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph("Alv-tunniste: " + laskuttaja.getAlvTunniste(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("Eräpäivä", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph(dateFormat1.format(dateFormat4.parse(lasku.getErapaiva().toString())), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        table1.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("Viivästyskorko", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph(Integer.toString(lasku.getViivastyskorko()) + ".0%", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.LIGHT_GRAY);
        table1.addCell(cell);

        table1.completeRow();

        //Jos vastaanottaja on sama kuin tilaaja ei laiteta erikseen vastaanottajan tietoja näkyville.
        if (tilaaja.getAsiakasnumero().equals(vastaanottaja.getAsiakasnumero())) {
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getNimi(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Viitenumero", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(lasku.getViite(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getKatuosoite(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Maksuehto", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(lasku.getMaksuehto(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getPostinumero() + " " + tilaaja.getKaupunki(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Pankki", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumeronPankki(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getEmail(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Tilinumero", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumero(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Swift/BIC", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumeronSwiftBic(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(" ", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph("Lisätiedot", f2);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(lasku.getLisatiedot(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(" ", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            document.add(table1);
        } else {
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph("Palvelun tilaaja", f2);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Viitenumero", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(lasku.getViite(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getNimi(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Maksuehto", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(lasku.getMaksuehto(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getKatuosoite(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Pankki", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumeronPankki(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getPostinumero() + " " + tilaaja.getKaupunki(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Tilinumero", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumero(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(3);
            p = new Paragraph(tilaaja.getEmail(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph("Swift/BIC", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(2);
            p = new Paragraph(laskuttaja.getTilinumeronSwiftBic(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
            cell.setBorderColor(BaseColor.LIGHT_GRAY);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph("Palvelun vastaanottaja", f2);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(vastaanottaja.getNimi(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(vastaanottaja.getKatuosoite(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(vastaanottaja.getPostinumero() + " " + vastaanottaja.getKaupunki(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(vastaanottaja.getEmail(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph("Lisätiedot", f2);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(lasku.getLisatiedot(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(7);
            p = new Paragraph(" ", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table1.addCell(cell);

            table1.completeRow();

            document.add(table1);
        }

        //Muodostetaan suoritteiden taulukko.
        PdfPTable table2 = new PdfPTable(7);
        table2.setWidths(new int[]{370, 100, 73, 100, 73, 100, 179});
        table2.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
        table2.setLockedWidth(true);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Kuvaus", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Määrä", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Yks.", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("à hinta", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Alv %", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Alv €", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph("Yhteensä", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(LEFT + BOTTOM + RIGHT + TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        table2.completeRow();

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(0);
        df.setGroupingUsed(false);
        
        for (Suorite suorite : suoritteet) {
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(suorite.getKuvaus() + " " + dateFormat1.format(dateFormat3.parse(suorite.getAloitusaika().toString())), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(df.format(suorite.getMaara()), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(suorite.getMaaranYksikot(), f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(df.format(suorite.getaHintaVeroton()) + "€", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(suorite.getAlvProsentti() + "%", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(df.format(((new BigDecimal(suorite.getAlvProsentti()).multiply(new BigDecimal(0.01))).multiply(suorite.getaHintaVeroton())).multiply(suorite.getMaara())) + "€", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);
            cell.setColspan(1);
            p = new Paragraph(df.format(suorite.getYht()) + "€", f1);
            p.setLeading(0, 1);
            p.setAlignment(Element.ALIGN_RIGHT);
            cell.addElement(p);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBorder(LEFT + RIGHT);
            cell.setBorderColor(BaseColor.BLACK);
            cell.setBorderWidth(0.2f);
            table2.addCell(cell);
            table2.completeRow();
        }

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        p = new Paragraph(" ", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(TOP);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table2.addCell(cell);

        table2.completeRow();

        BigDecimal verotonYht = new BigDecimal(0.0);
        BigDecimal alvYht = new BigDecimal(0.0);
        for (Suorite suorite : suoritteet) {
            verotonYht = verotonYht.add(suorite.getaHintaVeroton().multiply(suorite.getMaara()));
            alvYht = alvYht.add(((new BigDecimal(0.01).multiply(new BigDecimal(suorite.getAlvProsentti()))).multiply(suorite.getaHintaVeroton())).multiply(suorite.getMaara()));
        }

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph("Veroton hinta yhteensä", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(df.format(verotonYht) + "€", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        table2.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph("Arvonlisävero yhteensä", f2);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(df.format(alvYht) + "€", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        table2.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(7);
        p = new Paragraph(" ", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        table2.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(4);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(2);
        p = new Paragraph("Yhteensä", f3);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(df.format(lasku.getSumma()) + "€", f3);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table2.addCell(cell);

        table2.completeRow();

        document.add(table2);

        PdfPTable table3 = new PdfPTable(3);
        table3.setWidths(new int[]{1, 1, 1});
        table3.setTotalWidth(document.right(document.rightMargin()) - document.left(document.leftMargin()));
        table3.setLockedWidth(true);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph("Pyydämme käyttämään maksaessanne viitenumeroa: " + lasku.getViite(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph(" ", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setBorder(BOTTOM);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBorderWidth(0.2f);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getYrityksenNimi(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getNimi(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getTilinumeronPankki(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getKatuosoite(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getPuhelinnumero(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getTilinumero(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getPostinumero() + " " + laskuttaja.getKaupunki(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getEmail(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(1);
        p = new Paragraph(laskuttaja.getTilinumeronPankki(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph(" ", f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_RIGHT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setColspan(3);
        p = new Paragraph("Virtuaaliviivakoodi: " + lasku.getPankkiviivakoodi(), f1);
        p.setLeading(0, 1);
        p.setAlignment(Element.ALIGN_CENTER);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table3.addCell(cell);

        table3.completeRow();

        table3.writeSelectedRows(0, -1,
                document.left(document.leftMargin()),
                table3.getTotalHeight() + document.bottom(document.bottomMargin()),
                writer.getDirectContent());
        //----------------------------------------------------------------------
        document.close();

    }
}
