package Utils;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JTable;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
// Lưu ý: Cần thêm thư viện Apache POI để không bị lỗi các dòng dưới
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

    // Hàm Xuất Excel từ một JTable bất kỳ
    public static void exportToExcel(JTable table, String title) {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setDialogTitle("Chọn nơi lưu file Excel");
            jFileChooser.setSelectedFile(new File(title + ".xlsx"));
            jFileChooser.setFileFilter(new FileNameExtensionFilter("Excel File", "xlsx"));
            
            int userSelection = jFileChooser.showSaveDialog(null);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = jFileChooser.getSelectedFile();
                if (!fileToSave.toString().endsWith(".xlsx")) {
                    fileToSave = new File(fileToSave.toString() + ".xlsx");
                }

                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet(title);
                TableModel model = table.getModel();

                // Tạo hàng tiêu đề (Header)
                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < model.getColumnCount(); i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(model.getColumnName(i));
                    
                    // Thêm chút định dạng cho header
                    CellStyle style = workbook.createCellStyle();
                    Font font = workbook.createFont();
                    font.setBold(true);
                    style.setFont(font);
                    cell.setCellStyle(style);
                }

                // Đổ dữ liệu từ Table vào Excel
                for (int i = 0; i < model.getRowCount(); i++) {
                    Row row = sheet.createRow(i + 1);
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            row.createCell(j).setCellValue(value.toString());
                        }
                    }
                }

                // Tự động căn chỉnh độ rộng cột
                for (int i = 0; i < model.getColumnCount(); i++) {
                    sheet.autoSizeColumn(i);
                }

                FileOutputStream out = new FileOutputStream(fileToSave);
                workbook.write(out);
                workbook.close();
                out.close();
                JOptionPane.showMessageDialog(null, "Xuất file Excel thành công rực rỡ!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Lỗi xuất Excel: " + e.getMessage());
        }
    }
}