package meu.edu.jo.common;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import meu.edu.jo.entities.PersonalInfo;
import meu.edu.jo.repositories.PersonalInfoRepository;
import meu.edu.jo.services.PersonalInfoService;

@Component("pdfGenerator")
public class PDFGenerator {

	@Value("${code}")
	private String code;

	@Value("${reportFileNameDateFormat}")
	private String reportFileNameDateFormat;

	@Value("${localDateFormat}")
	private String localDateFormat;

	@Value("${logoImgScale}")
	private Float[] logoImgScale;

	@Value("${currencySymbol:}")
	private String currencySymbol;

	@Autowired
	PersonalInfoRepository userProfileRepository;

	@Autowired
	PersonalInfoService userProfileService;

	public void generatePdfReport() {

		Document document = new Document();

		try {
			PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getPdfNameWithDate()));
			PdfFooterEvent footerEvent = new PdfFooterEvent();
			PdfHeaderEvent headerEvent = new PdfHeaderEvent();

			writer.setPageEvent(footerEvent);
			writer.setPageEvent(headerEvent);

			document.open();
			createHeader(document);
			createTable(document, 4);
			document.close();

		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}
	}

	private void createHeader(Document document) throws DocumentException {
		Paragraph headerParagraph = new Paragraph();
		leaveEmptyLine(headerParagraph, 1);
		headerParagraph.setAlignment(Element.ALIGN_CENTER);
		leaveEmptyLine(headerParagraph, 1);
		document.add(headerParagraph);
	}

	private void createTable(Document document, int noOfColumns) throws DocumentException, IOException {
		BaseColor customColor = new BaseColor(147, 28, 31);
		BaseColor whiteColor = BaseColor.WHITE;
		List<String> columnNames = new ArrayList<>();
		columnNames.add("الرمز");
		columnNames.add("الاسم بالعربي");
		columnNames.add("الاسم بالإنجليزي");
		columnNames.add("القسم");

		PdfPTable table = new PdfPTable(noOfColumns);

		try {
			// Get the custom font from the FontLoader
			BaseFont customBaseFont = FontLoader.getCustomFont();
			Font headerFont = new Font(customBaseFont, 12, Font.BOLD, whiteColor);

			table.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			for (int i = 0; i < noOfColumns; i++) {
				PdfPCell cell = new PdfPCell(new Phrase(columnNames.get(i), headerFont));
				cell.setArabicOptions(i);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setBackgroundColor(customColor);
				table.addCell(cell);
			}

			table.setHeaderRows(1);

			// Create an empty paragraph with spacing as margin
			Paragraph tableMargin = new Paragraph();
			tableMargin.setSpacingBefore(60); // Adjust the spacing as needed

			// Add the margin and then the table
			document.add(tableMargin);
			getDbData(table);
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private void getDbData(PdfPTable table) throws DocumentException, IOException {
		List<PersonalInfo> list = userProfileService.getAllUserProfile();

		// Get the path to the custom font file from the FontLoader
		String fontPath = FontLoader.getCustomFontPath();

		try {
			// Create the BaseFont using the custom font path
			BaseFont bf = BaseFont.createFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

			for (PersonalInfo userProfile : list) {
				table.setWidthPercentage(100);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
				table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
				table.addCell(userProfile.getId().toString());
				table.addCell(extractedCellValue(bf, userProfile.getFullName()));
				table.addCell(extractedCellValue(bf, userProfile.getDepartment().toString()));
			}
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	private PdfPCell extractedCellValue(BaseFont bf, String value) throws UnsupportedEncodingException {

		byte[] valueInBytes;
		try {
			valueInBytes = value.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			valueInBytes = new byte[0]; // Handle encoding error gracefully
		}
		Font valueTextFont = new Font(bf, 12);
		PdfPCell valueNameCell = new PdfPCell(new Phrase(new String(valueInBytes, "UTF-8"), valueTextFont));
		return valueNameCell;
	}

	private static void leaveEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	public String getPdfNameWithDate() {
		String localDateString = LocalDateTime.now().format(DateTimeFormatter.ofPattern(reportFileNameDateFormat));
		return "" + localDateString + ".pdf";
	}

	public class PdfHeaderEvent extends PdfPageEventHelper {
		private Image logoImage;

		public PdfHeaderEvent() {
			try {
				logoImage = Image.getInstance("classpath:static/header.png");
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
		}

		public void onStartPage(PdfWriter writer, Document document) {
			float pageWidth = document.right() - document.left();
			logoImage.scaleAbsolute(pageWidth, (pageWidth / 1299) * 217);
			float imageY = document.top(60);
			logoImage.setAbsolutePosition(document.left(), imageY);
			try {
				document.add(logoImage);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public class PdfFooterEvent extends PdfPageEventHelper {
		public void onEndPage(PdfWriter writer, Document document) {
			float pageWidth = document.right() - document.left();
			Image logoImage = null;
			try {
				logoImage = Image.getInstance("classpath:static/footer.png");
			} catch (DocumentException | IOException e) {
				e.printStackTrace();
			}
			logoImage.scaleAbsolute(pageWidth, (pageWidth / 1299) * 217);
			float imageY = document.bottom();
			logoImage.setAbsolutePosition(document.left(), imageY);
			try {
				document.add(logoImage);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
	}
}
