import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.Path;

public class FrameAndLogic extends JFrame{
    JPanel mainPnl;
    JPanel titlePanel;
    JPanel mid;
    JPanel buttons;
    JLabel title;
    JTextArea scroll;
    JScrollPane scrollLine;
    JButton load;
    JButton quit;

    public FrameAndLogic(){
        mainPnl = new JPanel();
        mainPnl.setLayout(new BorderLayout());

        createTitle();
        mainPnl.add(titlePanel, BorderLayout.NORTH);

        createMid();
        mainPnl.add(mid, BorderLayout.CENTER);

        createButtonArea();
        mainPnl.add(buttons, BorderLayout.SOUTH);

        add(mainPnl);
        setSize(550,550);
        setLocation(0,0);
        setTitle("Recursive Lister");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void createTitle(){
        titlePanel = new JPanel();
        titlePanel.setLayout(new GridLayout(2, 1));

        title = new JLabel("Recursive File Lister", JLabel.CENTER);
        title.setFont(new Font("Impact", Font.PLAIN, 30));

        titlePanel.add(title);
    }

    private void createMid(){
        mid = new JPanel();
        scroll = new JTextArea(18,30);
        scroll.setEditable(false);

        scrollLine = new JScrollPane(scroll);
        scrollLine.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        mid.add(scrollLine);
    }

    private void createButtonArea(){
        buttons = new JPanel(new GridLayout(1,2));
        load = new JButton("Start");
        quit = new JButton("Quit");

        load.addActionListener((ActionEvent e) -> getFile());
        quit.addActionListener((ActionEvent e) -> System.exit(0));

        buttons.add(load);
        buttons.add(quit);
    }


    private void getFile(){
        JFileChooser chooser = new JFileChooser();
        File selectedFile;

        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); //allows user to also select directories


        Path workingDirectory = new File(System.getProperty("user.dir")).toPath();

        chooser.setCurrentDirectory(workingDirectory.toFile());

        //No need for a try-catch because no errors will be thrown

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            selectedFile = chooser.getSelectedFile();

            scroll.setText(""); //resets the JTextFrame to display the new file
            if(selectedFile.exists()){
                findFile(selectedFile); //starts recursion
            }else {
                JOptionPane.showMessageDialog(null, "The file/directory does not exist.");
            }

        } else  // User closed the chooser without selecting a file
        {
            System.out.println("No file selected!!!");
            JOptionPane.showMessageDialog(null, "No file selected!");
        }

    }

    private void findFile(File selectedFile){
        if (selectedFile.isDirectory()) {
            File[] files = selectedFile.listFiles();
            if (files != null) {  //file exists?
                for (File f : files) {
                    scroll.append(f.getAbsolutePath() + "\n");
                    //System.out.println("Directory: " + f.getAbsolutePath());
                    findFile(f);

                    //scroll.append(f.toString() + "\n");
                    //findFile(f); //recursive method called again
                }
            }
        } else {
            System.out.println(selectedFile.getAbsolutePath() + " is not a file or directory.");
            //scroll.append(selectedFile.toString() + "\n");
        }
    }

    //test code because the original didn't work

    /*public static void listFilesInDirectory(File directory) {
        if (directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("Directory: " + file.getAbsolutePath());
                        listFilesInDirectory(file);
                    } else {
                        System.out.println("File: " + file.getAbsolutePath());
                    }
                }
            }
        } else {
            System.out.println(directory.getAbsolutePath() + " is not a directory.");
        }
    }

    public static void main(String[] args) {
        File directory = new File(System.getProperty("user.dir"));

        if (directory.exists()) {
            listFilesInDirectory(directory);
        } else {
            System.out.println("The directory does not exist.");
        }
    }*/
}
