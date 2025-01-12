import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class UserApp {

    public static class User {
        private int id;
        private String imie;
        private String nazwisko;
        private int wiek;
        private int wzrost;
        private String nrTelefonu;
        private boolean statusStudenta;
        private boolean zatrudnienie;

        public User(int id, String imie, String nazwisko, int wiek, int wzrost, String nrTelefonu, boolean statusStudenta, boolean zatrudnienie) {
            this.id = id;
            this.imie = imie;
            this.nazwisko = nazwisko;
            this.wiek = wiek;
            this.wzrost = wzrost;
            this.nrTelefonu = nrTelefonu;
            this.statusStudenta = statusStudenta;
            this.zatrudnienie = zatrudnienie;
        }

        public int getId() { return id; }
        public String getImie() { return imie; }
        public String getNazwisko() { return nazwisko; }
        public int getWiek() { return wiek; }
        public int getWzrost() { return wzrost; }
        public String getNrTelefonu() { return nrTelefonu; }
        public boolean isStatusStudenta() { return statusStudenta; }
        public boolean isZatrudnienie() { return zatrudnienie; }

        @Override
        public String toString() {
            return id + "," + imie + "," + nazwisko + "," + wiek + "," + wzrost + "," + nrTelefonu + "," + statusStudenta + "," + zatrudnienie;
        }
    }

    public static class UserManager {
        private List<User> users = new ArrayList<>();
        private int nextId = 1;

        private String[] imiona = {"Jan", "Anna", "Piotr", "Michael", "Katarzyna", "Magdalena"};
        private String[] nazwiska = {"Kowalski", "Nowak", "Winiary", "Wojtowicz", "Kaczmarek", "Jankowski"};
        private String[] numeryTelefonow = {"123456789", "987654321", "456789123", "321654987", "654987321"};

        public void addUser(String imie, String nazwisko, int wiek, int wzrost, String nrTelefonu, boolean statusStudenta, boolean zatrudnienie) {
            User user = new User(nextId++, imie, nazwisko, wiek, wzrost, nrTelefonu, statusStudenta, zatrudnienie);
            users.add(user);
        }

        public List<User> getAllUsers() {
            return users;
        }

        public void removeUser(int id) {
            users.removeIf(user -> user.getId() == id);
            hashMap.clear(); // Clear the hashmap when a user is removed
        }

        public void clearUsers() {
            users.clear();
            nextId = 1;
            hashMap.clear(); // Clear the hashmap when all users are removed
        }

        // Losowanie u?ytkowników
        public void generateRandomUsers(int count) {
            Random rand = new Random();
            for (int i = 0; i < count; i++) {
                String imie = imiona[rand.nextInt(imiona.length)];
                String nazwisko = nazwiska[rand.nextInt(nazwiska.length)];
                int wiek = rand.nextInt(50) + 18;
                int wzrost = rand.nextInt(40) + 150;
                String nrTelefonu = numeryTelefonow[rand.nextInt(numeryTelefonow.length)];
                boolean statusStudenta = rand.nextBoolean();
                boolean zatrudnienie = rand.nextBoolean();
                
                addUser(imie, nazwisko, wiek, wzrost, nrTelefonu, statusStudenta, zatrudnienie);
            }
        }

        // Eksport do CSV
        public void exportToCSV(String filename) throws IOException {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
            writer.close();
        }

        // Import z CSV
        public void importFromCSV(String filename) throws IOException {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                User user = new User(nextId++, data[1], data[2], Integer.parseInt(data[3]), Integer.parseInt(data[4]), data[5], Boolean.parseBoolean(data[6]), Boolean.parseBoolean(data[7]));
                users.add(user);
            }
            reader.close();
        }
    }
    public class GlobalVariables {
        // Zmienna statyczna globalna
        public static boolean IsSort =  false;
    }
    public static void main(String[] args) {
      
        JFrame frame = new JFrame("User Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        UserManager userManager = new UserManager();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Tabela
        String[] columnNames = {"ID", "Imie", "Nazwisko", "Wiek", "Wzrost", "Nr Telefonu", "Status Studenta", "Zatrudnienie"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane);

        // Panel do dodawania nowych u?ytkowników
        JPanel addUserPanel = new JPanel();
        addUserPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField imieField = new JTextField(10);
        JTextField nazwiskoField = new JTextField(10);
        JTextField wiekField = new JTextField(5);
        JTextField wzrostField = new JTextField(5);
        JTextField telefonField = new JTextField(10);
        JCheckBox studentCheckBox = new JCheckBox("Student");
        JCheckBox zatrudnienieCheckBox = new JCheckBox("Zatrudniony");

        gbc.gridx = 0;
        gbc.gridy = 0;
        addUserPanel.add(new JLabel("Imie"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(imieField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        addUserPanel.add(new JLabel("Nazwisko"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(nazwiskoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        addUserPanel.add(new JLabel("Wiek"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(wiekField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        addUserPanel.add(new JLabel("Wzrost"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(wzrostField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        addUserPanel.add(new JLabel("Nr Telefonu"), gbc);
        gbc.gridx = 1;
        addUserPanel.add(telefonField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        addUserPanel.add(studentCheckBox, gbc);
        gbc.gridx = 1;
        addUserPanel.add(zatrudnienieCheckBox, gbc);

        JButton addUserButton = new JButton("Dodaj Uzytkownika");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        addUserPanel.add(addUserButton, gbc);

        JButton showAllUsers = new JButton("Pokaz wszystkich uzytkownikow");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        addUserPanel.add(showAllUsers, gbc);

        JButton showTabelBegin = new JButton("Pokaz tablice pocz¹tków");
        gbc.gridx = 1;
        gbc.gridy = 8;
        gbc.gridwidth = 1;
        addUserPanel.add(showTabelBegin, gbc);

        panel.add(addUserPanel);

        showAllUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            tableModel.setRowCount(0); // Clear earlier search results
            List<User> allUsers = userManager.getAllUsers();
            allUsers.sort(Comparator.comparingInt(User::getId)); // Sort users by ID
            for (User user : allUsers) {
                tableModel.addRow(new Object[]{
                user.getId(), user.getImie(), user.getNazwisko(), user.getWiek(),
                user.getWzrost(), user.getNrTelefonu(), user.isStatusStudenta(), user.isZatrudnienie()
                });
            }
            }
        });


        showTabelBegin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            DefaultTableModel hashMapTableModel = new DefaultTableModel(new String[]{"Column", "Value", "Users"}, 0);
            JTable hashMapTable = new JTable(hashMapTableModel);
            JScrollPane hashMapScrollPane = new JScrollPane(hashMapTable);
            JFrame hashMapFrame = new JFrame("Begins Table");
            hashMapFrame.setSize(600, 400);
            hashMapFrame.add(hashMapScrollPane);
            hashMapFrame.setVisible(true);

            hashMapTableModel.setRowCount(0); // Clear previous data
            for (Map.Entry<String, Map<String, List<User>>> columnEntry : hashMap.entrySet()) {
                String column = columnEntry.getKey();
                for (Map.Entry<String, List<User>> valueEntry : columnEntry.getValue().entrySet()) {
                    String value = valueEntry.getKey();
                    List<User> users = valueEntry.getValue();
                    StringBuilder usersString = new StringBuilder();
                    for (User user : users) {
                        usersString.append(user.getId()).append(", ");
                    }
                    hashMapTableModel.addRow(new Object[]{column, value, usersString.toString()});
                }
            }
            }
        });
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                int wiek = Integer.parseInt(wiekField.getText());
                int wzrost = Integer.parseInt(wzrostField.getText());
                String nrTelefonu = telefonField.getText();
                boolean statusStudenta = studentCheckBox.isSelected();
                boolean zatrudnienie = zatrudnienieCheckBox.isSelected();

                userManager.addUser(imie, nazwisko, wiek, wzrost, nrTelefonu, statusStudenta, zatrudnienie);
                tableModel.addRow(new Object[]{userManager.getAllUsers().size(), imie, nazwisko, wiek, wzrost, nrTelefonu, statusStudenta, zatrudnienie});
            }
        });

        // Panel do przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 10, 10));

        // Przycisk generowania u?ytkowników
        JButton generateButton = new JButton("Generuj Uzytkownikow");
        buttonPanel.add(generateButton);
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] options = {"10", "100", "100 000", "200 000", "300 000", "500 000", "1 000 000", "2 000 000", "5 000 000", "10 000 000", "15 000 000", "20 000 000"};
                String input = (String) JOptionPane.showInputDialog(frame, "Wybierz liczbe uzytkownikow do wygenerowania:", "Generowanie Uzytkownikow", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                int count = Integer.parseInt(input.replace(" ", ""));
                userManager.generateRandomUsers(count);
                tableModel.setRowCount(0);
         
                for (User user : userManager.getAllUsers()) {
                    tableModel.addRow(new Object[]{
                        user.getId(), user.getImie(), user.getNazwisko(), user.getWiek(),
                        user.getWzrost(), user.getNrTelefonu(), user.isStatusStudenta(), user.isZatrudnienie()
                    });
                }
            }
        });

        // Eksport do CSV
        JButton exportButton = new JButton("Eksportuj do CSV");
        buttonPanel.add(exportButton);
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        userManager.exportToCSV(file.getAbsolutePath());
                        JOptionPane.showMessageDialog(frame, "Eksport zakonczony.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Import z CSV
        JButton importButton = new JButton("Importuj z CSV");
        buttonPanel.add(importButton);
        importButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        userManager.importFromCSV(file.getAbsolutePath());
                        tableModel.setRowCount(0);
                        for (User user : userManager.getAllUsers()) {
                            tableModel.addRow(new Object[]{
                                user.getId(), user.getImie(), user.getNazwisko(), user.getWiek(),
                                user.getWzrost(), user.getNrTelefonu(), user.isStatusStudenta(), user.isZatrudnienie()
                            });
                        }
                        JOptionPane.showMessageDialog(frame, "Import zakonczony.");
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Przycisk usuwania wszystkich u?ytkowników
        JButton clearButton = new JButton("Usun wszystkich uzytkownikow");
        buttonPanel.add(clearButton);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userManager.clearUsers();
                tableModel.setRowCount(0); // Usu? wszystkie wiersze z tabeli
            }
        });

        // Przycisk usuwania pojedynczego u?ytkownika
        JButton removeUserButton = new JButton("Usun uzytkownika");
        buttonPanel.add(removeUserButton);
        removeUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    int userId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    userManager.removeUser(userId);
                    tableModel.removeRow(selectedRow); // Usu? wiersz z tabeli
                } else {
                    JOptionPane.showMessageDialog(frame, "Wybierz uzytkownika do usuniecia.");
                }
            }
        });

        // Przycisk wyszukiwania binarnego
        JButton searchButton = new JButton("Wyszukaj uzytkownika");
        buttonPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Podaj ID uzytkownika do wyszukania:");
                int id = Integer.parseInt(input);
                long startTime = System.currentTimeMillis();
                String column = "ID";
                String value = String.valueOf(id);
                List<User> results = binarySearch(userManager.getAllUsers(), column, value,GlobalVariables.IsSort);
                long endTime = System.currentTimeMillis();
                updateTableWithResults(table, results);
                displaySearchResults(results, endTime - startTime);
            
            }
        });

        // Przycisk wyszukiwania liniowego
        JButton linearSearchButton = new JButton("Liniowe wyszukiwanie");
        buttonPanel.add(linearSearchButton);
        linearSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columns = {"ID", "Imie", "Nazwisko", "Wiek", "Wzrost", "Nr Telefonu", "Status Studenta", "Zatrudnienie"};
                String column = (String) JOptionPane.showInputDialog(frame, "Wybierz kolumne do wyszukania:", "Wyszukiwanie", JOptionPane.QUESTION_MESSAGE, null, columns, columns[0]);
                String value = JOptionPane.showInputDialog(frame, "Podaj wartosc do wyszukania:");
                long startTime = System.currentTimeMillis();
                List<User> results = linearSearch(userManager.getAllUsers(), column, value);
                long endTime = System.currentTimeMillis();
                updateTableWithResults(table, results);
                displaySearchResults(results, endTime - startTime);
            }
        });

        // Przycisk wyszukiwania binarnego
        JButton binarySearchButton = new JButton("Binarne wyszukiwanie");
        buttonPanel.add(binarySearchButton);
        binarySearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            String[] columns = {"ID", "Imie", "Nazwisko", "Wiek", "Wzrost", "Nr Telefonu", "Status Studenta", "Zatrudnienie"};
            String column = (String) JOptionPane.showInputDialog(frame, "Wybierz kolumne do wyszukania:", "Wyszukiwanie", JOptionPane.QUESTION_MESSAGE, null, columns, columns[0]);
            String value = JOptionPane.showInputDialog(frame, "Podaj wartosc do wyszukania:");
            List<User> results = binarySearch(userManager.getAllUsers(), column, value, GlobalVariables.IsSort);
            updateTableWithResults(table, results);
            displaySearchResults(results, 0); // Pass 0 as time is displayed in binarySearch method
            }
        });

        // Przycisk wyszukiwania ?a?cuchowego
        JButton chainSearchButton = new JButton("Lancuchowe wyszukiwanie");
        buttonPanel.add(chainSearchButton);
        chainSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] columns = {"ID", "Imie", "Nazwisko", "Wiek", "Wzrost", "Nr Telefonu", "Status Studenta", "Zatrudnienie"};
                String column = (String) JOptionPane.showInputDialog(frame, "Wybierz kolumne do wyszukania:", "Wyszukiwanie", JOptionPane.QUESTION_MESSAGE, null, columns, columns[0]);
                String value = JOptionPane.showInputDialog(frame, "Podaj wartosc do wyszukania:");
                long startTime = System.currentTimeMillis();
                List<User> results = chainSearch(userManager.getAllUsers(), column, value);
                long endTime = System.currentTimeMillis();
                updateTableWithResults(table, results);
                displaySearchResults(results, endTime - startTime);

            }
        });

        panel.add(buttonPanel);

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    // Metoda wyszukiwania binarnego
    public static User binarySearch(List<User> users, int id) {
        int left = 0;
        int right = users.size() - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (users.get(mid).getId() == id) {
                return users.get(mid);
            }
            if (users.get(mid).getId() < id) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return null;
    }

    // Metoda wyszukiwania liniowego
    public static List<User> linearSearch(List<User> users, String column, String value) {
        List<User> results = new ArrayList<>();
        for (User user : users) {
            if (matches(user, column, value)) {
                results.add(user);
            }
        }
        return results;
    }
        boolean IsSort = false;

        // Metoda wyszukiwania binarnego
        public static List<User> binarySearch(List<User> users, String column, String value, boolean IsSort) {
            List<User> results = new ArrayList<>();
            if (!isSorted(users, column) && IsSort == false) {
                IsSort = true;
                users.sort(Comparator.comparing(user -> getFieldValue(user, column)));
            }

            long startTime = System.currentTimeMillis(); // Start measuring time for binary search
            int left = 0;
            int right = users.size() - 1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                String midValue = getFieldValue(users.get(mid), column);
                if (midValue.equals(value)) {
                    // Find the first occurrence
                    int first = mid;
                    while (first >= left && getFieldValue(users.get(first), column).equals(value)) {
                        first--;
                    }
                    first++;
                    int last = mid;
                    while (last <= right && getFieldValue(users.get(last), column).equals(value)) {
                        last++;
                    }
                    last--;
                    for (int i = first; i <= last; i++) {
                        results.add(users.get(i));
                    }
                    break;
                }
                if (midValue.compareTo(value) < 0) {
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            long endTime = System.currentTimeMillis(); // End measuring time for binary search
            System.out.println("Binary search time: " + (endTime - startTime) + " ms");

            return results;
        }
    // Metoda pomocnicza do sprawdzania, czy lista jest posortowana wed³ug kolumny
    private static boolean isSorted(List<User> users, String column) {
        for (int i = 1; i < users.size(); i++) {
            if (getFieldValue(users.get(i - 1), column).compareTo(getFieldValue(users.get(i), column)) > 0) {
                return false;
            }
        }
        return true;
    }

    // HashMap for chain search
    private static Map<String, Map<String, List<User>>> hashMap = new HashMap<>();

    // Metoda wyszukiwania ³añcuchowegoW
    public static List<User> chainSearch(List<User> users, String column, String value) {
        if (!hashMap.containsKey(column)) {
            buildHashMap(users, column);
        }
        return hashMap.getOrDefault(column, new HashMap<>()).getOrDefault(value, new ArrayList<>());
    }

    // Metoda budowania HashMap w tle
    public static void buildHashMap(List<User> users, String column) {
        Map<String, List<User>> columnMap = new HashMap<>();
        for (User user : users) {
            String key = getFieldValue(user, column);
            columnMap.computeIfAbsent(key, k -> new ArrayList<>()).add(user);
        }
        hashMap.put(column, columnMap);
    }

    // Metoda pomocnicza do porównywania wartoœci pola
    public static boolean matches(User user, String column, String value) {
        return getFieldValue(user, column).equals(value);
    }

    // Metoda pomocnicza do pobierania warto?ci pola
    public static String getFieldValue(User user, String column) {
        switch (column.toLowerCase()) {
            case "id": return String.valueOf(user.getId());
            case "imie": return user.getImie();
            case "nazwisko": return user.getNazwisko();
            case "wiek": return String.valueOf(user.getWiek());
            case "wzrost": return String.valueOf(user.getWzrost());
            case "nr telefonu": return user.getNrTelefonu();
            case "status studenta": return String.valueOf(user.isStatusStudenta());
            case "zatrudnienie": return String.valueOf(user.isZatrudnienie());
            default: throw new IllegalArgumentException("Nieznana kolumna: " + column);
        }
    }

    // Metoda pomocnicza do wy?wietlania wyników wyszukiwania
    public static void displaySearchResults(List<User> results, long searchTime) {
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nie znaleziono wynikow. Czas wyszukiwania: " + searchTime + " ms");
        } else {
            StringBuilder sb = new StringBuilder("Znaleziono uzytkownikow (czas wyszukiwania: " + searchTime + " ms):\n");
            
            JOptionPane.showMessageDialog(null, sb.toString());
        }
    }

    // Metoda pomocnicza do aktualizacji tabeli z wynikami wyszukiwania
    public static void updateTableWithResults(JTable table, List<User> results) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        tableModel.setRowCount(0); // Usu? wszystkie wiersze z tabeli
        for (User user : results) {
            tableModel.addRow(new Object[]{
                user.getId(), user.getImie(), user.getNazwisko(), user.getWiek(),
                user.getWzrost(), user.getNrTelefonu(), user.isStatusStudenta(), user.isZatrudnienie()
            });
        }
    }
}