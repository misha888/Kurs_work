package src.sample.classes.GUI;

import src.sample.Main;
import src.sample.classes.utilmodules.Timer;
import src.sample.classes.taskmodules.memory.MemoryScheduler;
import src.sample.classes.taskmodules.processing.Process;
import src.sample.classes.taskmodules.processing.Queue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;


public class Controller {
    @FXML
    private void initialize()
    {
        queueTable.getColumns().setAll(TableColumnGenerator(false));
        rejectedTable.getColumns().setAll(TableColumnGenerator(false));
        doneTable.getColumns().setAll(TableColumnGenerator(false));
        CoresTable.getColumns().setAll(TableColumnGenerator(true));
    }

    @FXML
    TableView queueTable;
    @FXML
    TableView rejectedTable;
    @FXML
    TableView doneTable;
    @FXML
    TableView CoresTable;

    ObservableList<Process> QueueTableList = FXCollections.observableArrayList();
    ObservableList<Process> RejectedTableList = FXCollections.observableArrayList();
    ObservableList<Process> DoneTableList = FXCollections.observableArrayList();
    ObservableList<CoreInformation> CoreITableList = FXCollections.observableArrayList();

    private ArrayList<TableColumn> TableColumnGenerator( boolean coreTable) {

        ArrayList<TableColumn> temp = new ArrayList<>();
        if(!coreTable) {
            TableColumn<Process, String> idColumn = new TableColumn<>("ID");
            TableColumn<Process, String> nameColumn = new TableColumn<>("Название процесса");
            TableColumn<Process, String> statusColumn = new TableColumn<>("Состояние процесса");
            TableColumn<Process, String> tickColumn = new TableColumn<>("Такты процесса");
            TableColumn<Process, String> memColumn = new TableColumn<>("Память процесса");
            TableColumn<Process, String> timeInColumn = new TableColumn<>("Время входа процесса");
            TableColumn<Process, String> burstColumn = new TableColumn<>("Время наработки процесса");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
            tickColumn.setCellValueFactory(new PropertyValueFactory<>("tickWorks"));
            memColumn.setCellValueFactory(new PropertyValueFactory<>("memory"));
            timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
            burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

            tickColumn.setVisible(false);
            memColumn.setVisible(false);
            burstColumn.setVisible(false);

            temp.add(idColumn);
            temp.add(nameColumn);
            temp.add(statusColumn);
            temp.add(tickColumn);
            temp.add(memColumn);
            temp.add(timeInColumn);
            temp.add(burstColumn);
            return temp;
        }
        else
        {
            TableColumn<CoreInformation, String> coreNumb = new TableColumn<>("№ ядра");
            TableColumn<CoreInformation, String> coreID = new TableColumn<>("Последний ID");
            TableColumn<CoreInformation, String> statusColumn = new TableColumn<>("Состояние");
            TableColumn<CoreInformation, String> timeInColumn = new TableColumn<>("Время входа");
            TableColumn<CoreInformation, String> burstColumn = new TableColumn<>("Время отработки");

            coreNumb.setCellValueFactory(new PropertyValueFactory<>("name"));
            coreID.setCellValueFactory(new PropertyValueFactory<>("id"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
            timeInColumn.setCellValueFactory(new PropertyValueFactory<>("timeIn"));
            burstColumn.setCellValueFactory(new PropertyValueFactory<>("burstTime"));

            temp.add(coreNumb);
            temp.add(coreID);
            temp.add(statusColumn);
            temp.add(timeInColumn);
            temp.add(burstColumn);
            return temp;
        }
    }

    public void TableRowSetter(Queue queue, ArrayList<CoreInformation> coreInformations)
    {
        QueueTableList.setAll(queue.getReadyQueue());
        queueTable.setItems(QueueTableList);
        queueTable.refresh();

        RejectedTableList.setAll(queue.getRejectedQueue());
        rejectedTable.setItems(RejectedTableList);
        rejectedTable.refresh();

        DoneTableList.setAll(queue.getDoneProcesses());
        doneTable.setItems(DoneTableList);
        doneTable.refresh();

        CoreITableList.setAll(coreInformations);
        CoresTable.setItems(CoreITableList);
        CoresTable.refresh();
    }

    @FXML
    Button runBTN;
    @FXML
    Button pauseBTN;
    @FXML
    Button resetBTN;

    @FXML
    protected void runBTN_Click()
    {
        if(!Main.emuThread.isAlive())
            Main.emuThread.start();
        else
            Main.emuThread.resume();

        runBTN.setDisable(true);
        resetBTN.setDisable(true);
        pauseBTN.setDisable(false);
    }

    @FXML
    protected void pauseBTN_Click()
    {
        if(Main.emuThread.isAlive())
            Main.emuThread.suspend();
        pauseBTN.setDisable(true);
        runBTN.setDisable(false);
        resetBTN.setDisable(false);
    }

    @FXML
    protected void resetBTN_Click()
    {
        if(Main.emuThread.isAlive()) {
            Main.emuThread.stop();
        }
        MemoryScheduler.clearMemory();
        Timer.clearTime();
        Main.emuThread = new Thread(new ThreadStart());

        queueTable.setItems(null);
        rejectedTable.setItems(null);
        doneTable.setItems(null);

        resetBTN.setDisable(true);
        pauseBTN.setDisable(true);
        runBTN.setDisable(false);
    }
}
