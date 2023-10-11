import javax.swing.*;


    public class Editor extends JFrame{
        public Editor(){

            super("Работа с файлами"); 
            setDefaultCloseOperation(EXIT_ON_CLOSE); 
            JPanel panel = new JPanel(); 
            panel.setLayout(null); 
            setSize(915, 600); 
            setContentPane(panel);

            JMenuBar menuBar = new JMenuBar();

            JMenu file_create = new JMenu("Создание файлов"); 
            file_create.add(new JMenuItem("TXT"));
            file_create.add(new JMenuItem("Word")); 
            file_create.add(new JMenuItem("Excel"));
            file_create.add(new JMenuItem("CSV"));


            JMenu file_edit = new JMenu("Работа с файлами");
            ///
            JMenu func_txt = new JMenu("TXT");
            file_edit.add(func_txt);
            func_txt.add(new JMenuItem("Записать файл"));
            func_txt.add(new JMenuItem("Дописать в файл")); 
            func_txt.add(new JMenuItem("Чтение из файла"));
            ///
            JMenu func_word = new JMenu("Word");
            file_edit.add(func_word);
            func_word.add(new JMenuItem("Записать файл"));
            func_word.add(new JMenuItem("Дописать в файл")); 
            func_word.add(new JMenuItem("Чтение из файла"));
            ///
            JMenu func_excel = new JMenu("Excel");
            file_edit.add(func_excel);
            func_excel.add(new JMenuItem("Записать файл"));
            func_excel.add(new JMenuItem("Дописать в файл")); 
            func_excel.add(new JMenuItem("Чтение из файла"));
            ///
            JMenu func_csv = new JMenu("CSV");
            file_edit.add(func_csv);
            func_csv.add(new JMenuItem("Записать файл"));
            func_csv.add(new JMenuItem("Дописать в файл")); 
            func_csv.add(new JMenuItem("Чтение из файла"));
            ///

            menuBar.add(file_create);
            menuBar.add(file_edit);
            menuBar.add(Box.createHorizontalGlue());
            setJMenuBar(menuBar);
        }
    }
