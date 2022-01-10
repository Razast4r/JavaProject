package mainPack;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

//Δημιουργία κυρίως παραθύρου εφαρμογής File->New->Swing UI Designer GUI Form
public class MainFrame extends JFrame {

    private JLabel plotLabel;
    private JButton plotBtn;
    private JButton newRecipeBtn;
    private JButton recipeViewBtn;
    private JRadioButton hardRadioBtn;
    private JRadioButton mediumRadioBtn;
    private JRadioButton easyRadioBtn;
    private JPanel mainPanel;
    private JPanel syntagesPanel;
    private JScrollPane syntagesScrollPanel;
    private JButton recipeModBtn;
    private JLabel categoryLabel;
    private JLabel difficultyLabel;
    private JLabel exeTimeLabel;
    private JLabel nameOfCategoryLabel;
    private JLabel valueOfExeTimeLabel;
    private JButton recipeDeleteBtn;
    private JComboBox categoriesComboBox;
    private JLabel categorySelLabel;
    private JList listOfRecipes;
    private JPanel optionPanel;
    private JPanel categoryPanel;
    private ButtonGroup difficultyGroup;
    private int thesi;

    //Για να αποθηκεύουμε τα δεδομένα αντί της ΒΔ
    private ArrayList<String> arrSyntages;
    private ArrayList<String> arrCategories;
    private ArrayList<String> arrDifficulties;
    private ArrayList<String> arrSyntagesTime;
    private DefaultListModel defaultListModel;

    //constructor
    public MainFrame() {
        setContentPane(mainPanel);
        setTitle("Ο Συνταγούλης");
        setSize(650,400);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //ορίζουμε τι θα είναι αρχικά κρυφό
        displayRecipeDetails(false);
        optionPanel.setVisible(false);
        categoryPanel.setVisible(false);
        syntagesPanel.setVisible(false);
        //το συνολικό παράθυρο είναι ορατό
        setVisible(true);

        //αρχικοποίηση combobox με τιμές που κανονικά τις τραβάμε από τη Βάση Δεδομένων που έχουμε
        ArrayList<String> categoriesOfRecipes = new ArrayList<>();
        categoriesOfRecipes.add("Όλες");
        categoriesOfRecipes.add("Σαλάτες");
        categoriesOfRecipes.add("Ορεκτικά");
        categoriesOfRecipes.add("Κρέας - Κοτόπουλο");
        categoriesOfRecipes.add("Ψάρι");
        categoriesOfRecipes.add("Σούπες");
        categoriesOfRecipes.add("Ζυμαρικά - Ρύζι");

        //εισάγουμε τις τιμές που αντιστοιχούν στις διαθέσιμες κατηγορίες
        for (String cat: categoriesOfRecipes){
            categoriesComboBox.addItem(cat);
        }

        //τι συμβαίνει όταν κάνουμε κλικ σε κάποια κατηγορία
        categoriesComboBox.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //αδειάζουμε τη λίστα από τις προηγούμενες τιμές
                defaultListModel.clear();

                //η κατηγορία που έχουμε επιλέξει αποθηκεύεται στη μεταβλητή x
                String x = String.valueOf(categoriesComboBox.getSelectedItem());
                //την εκτυπώνουμε στην κονσόλα
                System.out.println("Ο χρήστης επίλεξε: "+x);
                //εντοπίζουμε τις συνταγές της κατηγορίας, τις κρατάμε στην προσωρινή λίστα και τις εκτυπώνουμε στην κονσόλα
                for (int i=0; i<arrSyntages.size(); i++){
                    if((x == arrCategories.get(i))||(x == "Όλες")){
                        defaultListModel.addElement(arrSyntages.get(i));
                        System.out.println("\t"+arrSyntages.get(i));
                    }
                }
                //τα τμήματα αυτά του παραθύρου παραμένουν κρυφά μέχρι να επιλεγεί μια συνταγή
                optionPanel.setVisible(false);
                displayRecipeDetails(false);

            }
        });

        //Δημιουργία Listener για να εντοπίζουμε ποια συνταγή για από τις διαθέσιμες στη λίστα επιλέγει ο χρήστης
        ListSelectionListener listSelectionListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent listSelectionEvent) {
                JList list = (JList) listSelectionEvent.getSource();
                int selections[] = list.getSelectedIndices();
                Object selectionValues[] = list.getSelectedValues();
                for (int i = 0, n = selections.length; i < n; i++) {
                    if (i == 0) {
                        System.out.println(" Selections: ");
                    }
                    System.out.println(selectionValues[i] + " ");
                    System.out.println("Index:"+arrSyntages.indexOf(selectionValues[i]));
                    thesi = arrSyntages.indexOf(selectionValues[i]);
                    //System.out.println("category:"+arrCategories.get(thesi));
                    nameOfCategoryLabel.setText(arrCategories.get(thesi));
                    //int recipeDifficulty = Integer.valueOf(arrCategories.get(thesi));
                    System.out.println("Βαθμός Δυσκολίας:"+arrDifficulties.get(thesi));
                    String recipeDifficulty = arrDifficulties.get(thesi);
                    //ανάλογα με τον βαθμό δυσκολίας ενεργοποιείται το κατάλληλο radiobutton
                    switch (recipeDifficulty){
                        case "1":
                            easyRadioBtn.setSelected(true);
                            break;
                        case "2":
                            mediumRadioBtn.setSelected(true);
                            break;
                        case "3":
                            hardRadioBtn.setSelected(true);
                            break;
                    }
                    //παρουσιάζουμε τον χρόνο εκτέλεσης
                    valueOfExeTimeLabel.setText(arrSyntagesTime.get(thesi));

                }
                //κάνουμε ορατά τα κατάλληλα πεδία
                displayRecipeDetails(true);
                optionPanel.setVisible(true);
            }
        };

        //ορίζουμε τον Listener στη λίστα των συνταγών
        listOfRecipes.addListSelectionListener(listSelectionListener);

        //ορίζουμε τον Listener στο κουμπί "Προβολή διαθέσιμων συνταγών"
        plotBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                categoryPanel.setVisible(true);
                syntagesPanel.setVisible(true);
            }
        });

        recipeDeleteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                /**
                 * Δημιουργία παραθύρου επιβεβαίωσης για την διαγραφή των δεδομένων της
                 * βάσης. Το παράθυρο εμφανίζει δύο επιλογές. Ανάλογα την επιλογή του
                 * χρήστη, προχωρά ή όχι στην διαδραφή των δεδομένων.
                 * Πηγή: https://mkyong.com/swing/java-swing-how-to-make-a-confirmation-dialog/
                 */
                int input = JOptionPane.showConfirmDialog(null, "Πρόκειται να διαγράψεις την " +
                                "συνταγή \""+arrSyntages.get(thesi)+"\". Είσαι σίγουρος;;;", "WARNING!!!",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.ERROR_MESSAGE);
                //επιλογή ΟΚ
                if (input==0) {
                    System.out.println("Θα διαγραφεί η συνταγή: "+arrSyntages.get(thesi));
                    //διαγράφω τις σχετικές εγγραφές
                    arrSyntages.remove(thesi);
                    arrCategories.remove(thesi);
                    arrDifficulties.remove(thesi);
                    arrSyntagesTime.remove(thesi);
                    //ανανεώνω τη λίστα των συνταγών που παρουσιάζονται στον χρήστη
                    categoriesComboBox.actionPerformed(e);
                    //εμφανίζω μήνυμα επιτυχούς διαγραφής της συνταγής
                    JOptionPane.showMessageDialog(null,"H συνταγή διαγράφηκε!", "Information Panel",  JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    private void displayRecipeDetails(boolean x){
        categoryLabel.setVisible(x);
        nameOfCategoryLabel.setVisible(x);
        difficultyLabel.setVisible(x);
        easyRadioBtn.setVisible(x);
        mediumRadioBtn.setVisible(x);
        hardRadioBtn.setVisible(x);
        exeTimeLabel.setVisible(x);
        valueOfExeTimeLabel.setVisible(x);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here

        String syntages[] = { "Στριφτή τυρόπιτα με ελιές", "Πλιγούρι με λαχανικά και ρεβίθια", "Πατάτες Ογκρατέν με " +
                "λιαστή τομάτα και μυρωδικά", "Πίτα με λαχανικά, πουρέ πατάτας και μπέϊκον", "Σουφλέ μπρόκολο με blue" +
                " cheese και κρεμμύδια", "Ογκρατέν με καλοκαιρινά λαχανικά και μπεσαμέλ", "Πιπεριές Φλωρίνης γεμιστές" +
                " με πλιγούρι και ελιές", "Ψάρι γλώσσα στο φούρνο με πατάτες", "Μελιτζανοκεφτέδες με φέτα",
                "Αφράτη τυρόπιτα με φύλλο σφολιάτας και 3 τυριά","Νηστίσιμα Πιταρούδια με πατάτα",
                "Αλμυρά κριτσίνια ολικής με ελαιόλαδο","Εύκολη Αλμυρή Τάρτα με μανιτάρια και πιπεριές",
                "Κοτόπουλο Κοκκινιστό στην κατσαρόλα με μακαρόνια","Συνταγή για γεμιστά μόνο με ρύζι \"ορφανά\"",
                "Πένες με κόκκινη σάλτσα μελιτζάνας","Μοσχαράκι γιουβέτσι με κριθαράκι ολικής","Ψωμάκια με προζύμι",
                "Εύκολη Σκορδαλιά με πατάτα","Συνταγή για σπιτικές βάφλες","Λεμονάτα μπιφτέκια λαχανικών στο φούρνο"};

        //now we are converting list into arraylist
         arrSyntages = new ArrayList<String>(Arrays.asList(syntages));

        String syntagesCategory[] = { "Ορεκτικά", "Σαλάτες", "Σούπες", "Ορεκτικά", "Ορεκτικά",
                "Κρέας - Κοτόπουλο", "Ζυμαρικά - Ρύζι", "Ψάρι", "Σαλάτες",
                "Ορεκτικά","Ορεκτικά",
                "Ορεκτικά","Ορεκτικά",
                "Κρέας-Κοτόπουλο","Ζυμαρικά - Ρύζι",
                "Ζυμαρικά - Ρύζι","Κρέας - Κοτόπουλο","Σαλάτες",
                "Σαλάτες","Ζυμαρικά - Ρύζι","Κρέας-Κοτόπουλο"};

        //now we are converting list into arraylist
        arrCategories = new ArrayList<String>(Arrays.asList(syntagesCategory));

        String syntagesDiff[] = { "1", "2", "1", "3", "1", "1", "2", "2", "2", "3","3", "3","1", "2","2", "1","1","1",
                "2","2","3"};

        //now we are converting list into arraylist
        arrDifficulties = new ArrayList<String>(Arrays.asList(syntagesDiff));

        String syntagesTime[] = { "1 ώρα", "2 ώρες", "Μισή ώρα", "3 ώρες", "45 λεπτά", "2 ώρες", "1 ώρα", "40 λεπτά",
                "Μισή ώρα", "2 ώρες","2 μισή ώρες", "3 ώρες","1 και μισή", "2 μισή ώρες","Μισή ώρα", "1 ώρα","3 ώρες",
                "1 ώρα","45 λεπτά","1 και μισή","3 ώρες"};
        //now we are converting list into arraylist
        arrSyntagesTime = new ArrayList<String>(Arrays.asList(syntagesTime));

        System.out.println("Οι συνταγές τις βάσης");
        for (int i=0; i<arrSyntages.size(); i++){
            System.out.println(arrSyntages.get(i)+"\t\t\t Cat:"+arrCategories.get(i)+"\tΧρόνος εκτέλεσης:"+arrSyntagesTime.get(i));
        }
        System.out.println("/---------------------/");

        defaultListModel = new DefaultListModel();

        //περνάμε τις συνταγές στο μοντέλο της λίστας
        for(int i = 0; i < syntages.length; i++)
        {
            defaultListModel.addElement(syntages[i]);
        }
        //περνάμε το μοντέλο της λίστας στη Jlist του παραθύρου της εφαρμογής
        listOfRecipes = new JList(defaultListModel);
    }
}
