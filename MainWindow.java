import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFCell;


public class MainWindow {
    private static final String TXT_FILE = "output.txt";
    private static final String EXCEL_FILE = "output.xlsx";
    private static final String WORD_FILE = "output.docx";
    private static final String CSV_FILE = "output.csv";


    public static void main(String[] args) {
        JFrame frame = new JFrame("File Editor App");
        JMenuBar menuBar = new JMenuBar();

        JMenu createMenu = createFileCreationMenu();
        JMenu editMenu = createFileEditingMenu();
        JMenu adminMenu = admiMenu_clear();

        menuBar.add(createMenu);
        menuBar.add(editMenu);
        menuBar.add(adminMenu);

        JMenuItem exitItem = new JMenuItem("Выход");
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        menuBar.add(exitItem);
        frame.setJMenuBar(menuBar);
        frame.setSize(750, 550);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JMenu createFileCreationMenu() {
        JMenu fileMenu = new JMenu("Создать файл");
        addMenuItem(fileMenu, "Создать TXT", TXT_FILE);
        addMenuItem(fileMenu, "Создать Excel", EXCEL_FILE);
        addMenuItem(fileMenu, "Создать Word", WORD_FILE);
        addMenuItem(fileMenu, "Создать CSV", CSV_FILE);
        return fileMenu;
    }

    private static void addMenuItem(JMenu parentMenu, String title, String filePath) {
        JMenuItem menuItem = new JMenuItem(title);
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = JOptionPane.showInputDialog("Что записать в " + title + "?");
                switch (filePath) {
                    case TXT_FILE:
                    case CSV_FILE:
                        writeToFile(filePath, content);
                        break;
                    case EXCEL_FILE:
                        // Simplified example: writing to the first cell of an Excel file
                        XSSFWorkbook workbook = new XSSFWorkbook();
                        workbook.createSheet("Sheet1").createRow(0).createCell(0).setCellValue(content);
                        try (FileOutputStream fos = new FileOutputStream(filePath)) {
                            workbook.write(fos);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case WORD_FILE:
                        XWPFDocument document = new XWPFDocument();
                        document.createParagraph().createRun().setText(content);
                        try (FileOutputStream fos = new FileOutputStream(filePath)) {
                            document.write(fos);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
            }
        });
        parentMenu.add(menuItem);
    }

    private static JMenu createFileEditingMenu() {
        JMenu editMenu = new JMenu("Редактировать файл");

        addEditSubmenu(editMenu, "TXT", TXT_FILE);
        addEditSubmenu(editMenu, "Word", WORD_FILE);
        addEditSubmenu(editMenu, "Excel", EXCEL_FILE);
        addEditSubmenu(editMenu, "CSV", CSV_FILE);

        return editMenu;
    }

    private static JMenu admiMenu_clear() {
        JMenu adminMenu = new JMenu("Администрирование");
        return adminMenu;
    }

    private static void addEditSubmenu(JMenu parentMenu, String title, String filePath) {
        JMenu submenu = new JMenu(title);

        JMenuItem writeItem = new JMenuItem("Записать в файл");
        JMenuItem appendItem = new JMenuItem("Дописать в файл");
        JMenuItem readItem = new JMenuItem("Чтение из файла");

        writeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = JOptionPane.showInputDialog("Что перезаписать в " + title + "?");
                switch (filePath) {
                    case TXT_FILE:
                    case CSV_FILE:
                        writeToFile(filePath, content);
                        break;
                    // Similar actions can be implemented for Word and Excel
                }
            }
        });

        appendItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = JOptionPane.showInputDialog("Что дописать в " + title + "?");
                appendToFile2(filePath, content);
            }
        });

        readItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = readFromFile(filePath);
                JOptionPane.showMessageDialog(null, content, "Содержимое " + title, JOptionPane.INFORMATION_MESSAGE);
            }
        });

        submenu.add(writeItem);
        submenu.add(appendItem);
        submenu.add(readItem);
        parentMenu.add(submenu);
    }

    private static void writeToFile(String filename, String content) {
        if (filename.endsWith(".xlsx")) {
            try {
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet sheet = workbook.createSheet("Sheet1");
                XSSFRow row = sheet.createRow(0);
                XSSFCell cell = row.createCell(0);
                cell.setCellValue(content);
                try (FileOutputStream fos = new FileOutputStream(filename)) {
                    workbook.write(fos);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;  // завершаем метод после записи в Excel
        }
    
        // Logic for txt and csv
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    


    private static void appendToFile2(String filename, String content) {
        if (filename.endsWith(".txt")) {
            try (FileWriter writer = new FileWriter(filename, true)) {
                writer.write(content + "\n");  // добавляем содержимое и новую строку
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;  // завершаем метод после дописывания в TXT
        }

        if (filename.endsWith(".csv")) {
            try (FileWriter writer = new FileWriter(filename, true)) {
                writer.write(content + "\n");  // добавляем содержимое и новую строку
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;  // завершаем метод после дописывания в CSV
        }

        if (filename.endsWith(".docx")) {
            try {
                XWPFDocument doc;
                File file = new File(filename);
    
                if (file.exists() && file.length() > 0) {
                    doc = new XWPFDocument(new FileInputStream(filename));
                } else {
                    doc = new XWPFDocument();
                }
    
                // Получаем последний абзац или создаем новый, если документ пуст
                XWPFParagraph paragraph = (doc.getParagraphs().size() > 0) ? 
                                           doc.getParagraphs().get(doc.getParagraphs().size() - 1) : 
                                           doc.createParagraph();
                XWPFRun run = paragraph.createRun();
                run.setText(content);
    
                try (FileOutputStream fos = new FileOutputStream(filename)) {
                    doc.write(fos);
                }
    
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
        if (filename.endsWith(".xlsx")) {
            try {
                XSSFWorkbook workbook;
                File file = new File(filename);
    
                if (file.exists() && file.length() > 0) {
                    workbook = new XSSFWorkbook(new FileInputStream(filename));
                } else {
                    workbook = new XSSFWorkbook();
                }
    
                XSSFSheet sheet = workbook.getSheetAt(0);
                int lastRowNum = sheet.getLastRowNum();
                XSSFRow currentRow = sheet.getRow(lastRowNum) != null ? 
                                     sheet.getRow(lastRowNum) : 
                                     sheet.createRow(lastRowNum);
    
                XSSFCell newCell = currentRow.createCell(currentRow.getLastCellNum() == -1 ? 0 : currentRow.getLastCellNum());
                newCell.setCellValue(content);
    
                try (FileOutputStream fos = new FileOutputStream(filename)) {
                    workbook.write(fos);
                }
    
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    


    private static String readFromFile(String filename) {
        StringBuilder content = new StringBuilder();
    
        if (filename.endsWith(".txt") || filename.endsWith(".csv")) {
            // Чтение текстовых файлов
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (filename.endsWith(".docx")) {
            // Чтение файлов Word
            try (XWPFDocument doc = new XWPFDocument(new FileInputStream(filename))) {
                for (XWPFParagraph paragraph : doc.getParagraphs()) {
                    content.append(paragraph.getText()).append("\n");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else if (filename.endsWith(".xlsx")) {
            // Чтение файлов Excel
            try (XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(filename))) {
                XSSFSheet sheet = workbook.getSheetAt(0);
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                    XSSFRow row = sheet.getRow(i);
                    if (row != null) {
                        XSSFCell cell = row.getCell(0);
                        if (cell != null) {
                            content.append(cell.toString()).append("\n");
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    
        return content.toString();
    }
}

















