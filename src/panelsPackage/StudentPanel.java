package panelsPackage;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.TimerTask;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dataPackage.DataCollector;
import dataPackage.Lecturer;
import dataPackage.Student;

public class StudentPanel extends JPanel {
    private PanelsManager panelsManager;
    private DataCollector dataCollector;
    private JTextField surname;
    private JTextField name;
    private JTextField patronymic;
    private JTextField faculty;
    private JTextField groupName;
    private JLabel label_err;
    private JButton btnSafeData;
    private JButton btnGetLectorer;
    private boolean editMode;
    
    public StudentPanel(PanelsManager panelsManager, DataCollector dataCollector) {
        this.panelsManager = panelsManager;
        this.dataCollector = dataCollector;

        System.out.println("StudentPanel::StudentPanel(); -- this.getClass().getName():" + this.getClass().getName());
        setName(this.getClass().getName());

        setLayout(new FlowLayout(FlowLayout.CENTER));

        Box verticalBox = Box.createVerticalBox();

        Box horizontalBox = Box.createHorizontalBox();
        verticalBox.add(horizontalBox);

        JLabel label_surname = new JLabel("Фамилия");/** создание надписи "Фамилия" */
        horizontalBox.add(label_surname);
        label_surname.setFont(new Font("Tahoma", Font.PLAIN, 15));

        surname = new JTextField();/** создание поля ввода для фамилии */
        horizontalBox.add(surname);
        surname.setColumns(10);

        Box horizontalBox_1 = Box.createHorizontalBox();
        verticalBox.add(horizontalBox_1);

        JLabel label_name = new JLabel("Имя");/** создание надписи "Имя" */
        horizontalBox_1.add(label_name);
        label_name.setFont(new Font("Tahoma", Font.PLAIN, 15));

        name = new JTextField();/** создание надписи "Имя" */
        horizontalBox_1.add(name);
        name.setColumns(10);

        Box horizontalBox_2 = Box.createHorizontalBox();
        verticalBox.add(horizontalBox_2);

        JLabel label_patronymic = new JLabel("Отчество");/** создание надписи "Отчество" */
        horizontalBox_2.add(label_patronymic);
        label_patronymic.setFont(new Font("Tahoma", Font.PLAIN, 15));

        patronymic = new JTextField();/** создание поля ввода для отчества */
        horizontalBox_2.add(patronymic);
        patronymic.setColumns(10);

        Box horizontalBox_3 = Box.createHorizontalBox();
        verticalBox.add(horizontalBox_3);

        JLabel label_faculty = new JLabel("Факультет");/** создание надписи "Факультет" */
        horizontalBox_3.add(label_faculty);
        label_faculty.setFont(new Font("Tahoma", Font.PLAIN, 15));

        faculty = new JTextField();
        horizontalBox_3.add(faculty);
        faculty.setColumns(10);

        Box horizontalBox_4 = Box.createHorizontalBox();
        verticalBox.add(horizontalBox_4);

        JLabel label_post = new JLabel("Имя Группы");/** создание надписи "Имя Группы */
        horizontalBox_4.add(label_post);
        label_post.setFont(new Font("Tahoma", Font.PLAIN, 15));
        
        groupName = new JTextField();/** создание поля ввода для имени группы */
        horizontalBox_4.add(groupName);
        groupName.setColumns(10);

        label_err = new JLabel("");
        verticalBox.add(label_err);

        btnSafeData = new JButton("Сохранить данные");/** создание кнопки "Сохранить данные" */
        btnSafeData.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSafeData.addActionListener(new ActionListener() /** при нажатии на кнопку "Сохранить данные" заносится студент*/
       {
            public void actionPerformed(ActionEvent e) {
                System.out.println("StudentPanel::btnSafeData::actionPerformed(); -- ");/** прошло успешное добавление */
                if (validateEnterAllData()) /** проверка на заполненность всех полей */
                {
                    changeEditMode();
                }
            }
        });
        verticalBox.add(btnSafeData);
        add(verticalBox);

        btnGetLectorer = new JButton("Получить дипломного руководителя");/** создание кнопки "Получить дипломного руководителя" */
        btnGetLectorer.setVisible(false);/** кнопка не видима*/
        btnGetLectorer.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnGetLectorer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("StudentPanel::btnGetLectorer::actionPerformed(); -- ");
                Student newStudent = new Student(surname.getText(), name.getText(), patronymic.getText(),
                        faculty.getText(), groupName.getText());
                Lecturer lecturer = dataCollector.addStudent(newStudent);
                if(lecturer != null) {
                    JOptionPane.showMessageDialog(null, "Ваш дипломный руководитель:" + lecturer.getFIO() + " информация о нем: " + lecturer.cathedra, "???Warning???", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Дипломный руководитель не назначен", "???Warning2???", JOptionPane.INFORMATION_MESSAGE);
                }
                System.out.println("StudentPanel::btnGetLectorer::actionPerformed(); -- newStudent:" + newStudent);
                panelsManager.setInfoListPanel();
            }
        });
        verticalBox.add(btnGetLectorer);

        editMode = true;
        System.out.println("StudentPanel::StudentPanel(); -- getPreferredSize():" + getPreferredSize());
    }

    private boolean validateEnterAllData() {
        if (surname.getText().isEmpty() || name.getText().isEmpty() || patronymic.getText().isEmpty()
                || faculty.getText().isEmpty() || groupName.getText().isEmpty()) {
            label_err.setText("Заполните все поля!!!");
            new java.util.Timer().schedule(new TimerTask() {
                public void run() {
                    label_err.setText("");
                }
            }, 2000);/**через 2 секунда надпись "заполните все поля" пропадает */
            return false;
        } else {
            return true;
        }
    }

    private void changeEditMode() {/** функция для изменение данных студента */
        System.out.println("StudentPanel::changeEditMode(); -- editMode:" + editMode);
        editMode = !editMode;/** панель в режиме редактирования */
        surname.setEnabled(editMode);/** установка новых данных, панель не редактируется */
        name.setEnabled(editMode);
        patronymic.setEnabled(editMode);
        faculty.setEnabled(editMode);
        groupName.setEnabled(editMode);
        if (editMode) {
            btnSafeData.setText("Сохранить данные");
        } else {
            btnSafeData.setText("Изменить информацию");
        }
        btnGetLectorer.setVisible(!editMode);
    }
}
