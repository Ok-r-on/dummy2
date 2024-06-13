package com.example.tsesy;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button generatePDFbtn;
    private static final Font FONT_EVENT     = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
    private static final Font FONT_DATE      = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);

    private static final Font FONT_CELL      = new Font(Font.FontFamily.TIMES_ROMAN,  12, Font.NORMAL);
    private static final Font FONT_COLUMN    = new Font(Font.FontFamily.TIMES_ROMAN,  14, Font.BOLDITALIC);

    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generatePDFbtn = findViewById(R.id.pdfbtn);

        // below code is used for
        // checking our permissions.
        if (!checkPermission()) {
            requestPermission();
        }
        generatePDFbtn.setOnClickListener(v -> {
            try {
                addingheadertopdf();

            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        });

        PieChart pieChart = findViewById(R.id.pie_chart);

        List<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(30f, "Category 1"));
        entries.add(new PieEntry(20f, "Category 2"));
        entries.add(new PieEntry(50f, "Category 3"));
        entries.add(new PieEntry(50f, "Category 3"));

        PieDataSet dataSet = new PieDataSet(entries, "Ring Chart");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);

        pieChart.setHoleRadius(40f);
        pieChart.animateXY(500,500);
        pieChart.setCenterText("Out of 120 hours");
        pieChart.setTransparentCircleRadius(45f);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.invalidate(); // refresh

        PieChart pieChart2 = findViewById(R.id.pie_chart2);

        List<PieEntry> entries2 = new ArrayList<>();
        entries2.add(new PieEntry(30f, "Category 1"));
        entries2.add(new PieEntry(20f, "Category 2"));
        entries2.add(new PieEntry(50f, "Category 3"));
        entries2.add(new PieEntry(50f, ""));

        PieDataSet dataSet2 = new PieDataSet(entries2, "Ring Chart");
        int[] customColors = new int[entries2.size()];
        customColors[0] = Color.argb(45,204,112,255); // Semi-transparent red
        customColors[1] = Color.argb(232,76,61,255); // Semi-transparent green
        customColors[2] = Color.argb(241,196,15,255);
        customColors[2] = Color.argb(53,152,219,255);
        customColors[3] = Color.TRANSPARENT;
        dataSet2.setColors(customColors);
        dataSet2.setValueTextColor(Color.WHITE);
        dataSet2.setValueTextSize(12f);



        PieData data2 = new PieData(dataSet2);
        pieChart2.setData(data2);

        pieChart2.setHoleRadius(40f);
        pieChart2.animateXY(500,500);
        pieChart2.setCenterText("Out of 120 hours");
        pieChart2.setDrawRoundedSlices(true);
        pieChart2.setTransparentCircleRadius(45f);
        pieChart2.getDescription().setEnabled(false);
        pieChart2.getLegend().setEnabled(false);
        pieChart2.invalidate();
    }

    private void addingheadertopdf() throws DocumentException {
        String[] userNames = {"Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Henry", "Ivy", "Jack", "Emily",
                "Michael", "Sophia", "Jacob", "Olivia", "William", "Isabella", "Ethan", "Emma", "Daniel", "Ava", "Alexander",
                "Mia", "Matthew", "Abigail", "Andrew", "Harper", "Joshua", "Avery", "Christopher", "Ella", "Joseph",
                "Scarlett", "Samuel", "Amelia", "John", "Chloe", "Ryan", "Victoria", "Liam", "Madison", "Noah", "Elizabeth",
                "Mason", "Natalie", "Lucas", "Aubrey", "JamesDavidDavidDavidDavidDavidDavid", "Lillian", "Benjamin"};
        Document document=new Document();
        document.setMargins(24f,24f,32f,32f);
        document.setPageSize(PageSize.A4);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{7,2});
        table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell cell;

        {
            /*LEFT TEXT*/
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPadding(8f);
            cell.setUseAscender(true);

            Paragraph temp = new Paragraph("Event Name" ,FONT_EVENT);
            temp.setAlignment(Element.ALIGN_LEFT);
            cell.addElement(temp);

            table.addCell(cell);
        }
        {
            /*MIDDLE TEXT*/
            cell = new PdfPCell();
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(PdfPCell.NO_BORDER);
            cell.setPadding(8f);
            cell.setUseAscender(true);

            Paragraph temp = new Paragraph("23/12/2023" ,FONT_DATE);
            temp.setAlignment(Element.ALIGN_CENTER);
            cell.addElement(temp);

            table.addCell(cell);
        }

        Paragraph paragraph=new Paragraph("",new Font(Font.FontFamily.TIMES_ROMAN, 2.0f, Font.NORMAL, BaseColor.BLACK));
        paragraph.add(table);

        PdfPTable table1 = new PdfPTable(2);
        table1.setWidths(new float[]{1f,2f});
        table1.setHeaderRows(1);
        table1.setHorizontalAlignment(Element.ALIGN_LEFT);
        {
            cell = new PdfPCell(new Phrase("Serial Number", FONT_COLUMN));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(4f);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase("User Names", FONT_COLUMN));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(4f);
            table1.addCell(cell);
        }

        boolean alternate = false;

        BaseColor lt_gray = new BaseColor(221,221,221); //#DDDDDD
        BaseColor cell_color;

        int size;
        size = userNames.length;

        for (int i = 0; i < size; i++)
        {
            cell_color = alternate ? lt_gray : BaseColor.WHITE;
            String temp = userNames[i];

            cell = new PdfPCell(new Phrase(String.valueOf((i+1)), FONT_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(cell_color);
            cell.setPadding(4f);
            table1.addCell(cell);

            cell = new PdfPCell(new Phrase(temp, FONT_CELL));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setBackgroundColor(cell_color);
            cell.setPadding(4f);
            table1.addCell(cell);

            alternate = !alternate;
        }

        try {
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(getFilePath()));
            writer.setFullCompression();
            document.open();
            document.add(paragraph);
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(table1);
            document.close();
            writer.close();
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFilePath() {
        String folder = "NSS";
        File downloadsDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), folder);
        if (!downloadsDir.exists()) {
            downloadsDir.mkdirs();
        }

        // Create the file path
        String fileName = "GFG1.pdf";
        File file = new File(downloadsDir, fileName);
        return file.getAbsolutePath();
    }

    private boolean checkPermission() {
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}