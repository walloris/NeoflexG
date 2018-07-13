package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button save_button;

    @FXML
    private Button load_button;

    @FXML
    private Button calculate_button;

    @FXML
    private TextField formula_field;

    @FXML
    private AnchorPane graph;

    @FXML
    private Label coords_lable;

    @FXML
    void initialize() {
        calculate_button.setOnAction(event -> {
            Roots roots;
            String formula = formula_field.getText();
            if (isCorrect(formula)) {
                roots = getRoots(getValues(formula));
                coords_lable.setText(getStringRoots(roots));
            } else {
                showMessage("Error", "!!!Ошибка ввода!!!\n Пример формулы: 2x^2+2x+2=0.");
            }
        });
    }

    private String getStringRoots(Roots roots) {
        return String.format("x1 = %1$s; x2 = %2$s", roots.x1, roots.x2);
    }

    private void showMessage(String title, String mes) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(mes);
        alert.showAndWait();
    }

    private Roots getRoots(float[] arr) {
        Roots roots = new Roots();
        float a = arr[0];
        float b = arr[1];
        float c = arr[2];
        float d = (b * b) - (4 * a * c);
        if (d < 0)
            showMessage("Error", "Нет корней,епта!");
        else if (d == 0) {
            roots.x1 = (-b) / (2 * a);
            System.out.println("x = " + roots.x1);
        } else {
            roots.x1 = (float) (-b + Math.sqrt(d)) / (2 * a);
            roots.x2 = (float) (-b - Math.sqrt(d)) / (2 * a);
        }
        return roots;
    }

    private Boolean isCorrect(String str) {
        Pattern p = Pattern.compile("^-?([1-9][0-9]+[xXхХ]|[2-9][xXхХ]|[xXхХ])*\\^2[+\\-]([1-9][0-9]+[xXхХ]|[2-9][xXхХ]|[xXхХ])[+\\-]\\d+=0$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    private float[] getValues(String str) {
        float[] res = new float[3];
        Pattern p = Pattern.compile("^([-]?\\d+)*[xXхХ]\\^2([+\\-]\\d+)*[xXхХ]([+\\-]\\d+)=0$");
        Matcher m = p.matcher(str);
        if (m.matches()) {
            res[0] = Float.parseFloat(m.group(1));
            res[1] = Float.parseFloat(m.group(2));
            res[2] = Float.parseFloat(m.group(3));
        }
        return res;
    }

    class Roots {
        float x1;
        float x2;
    }


    private ObservableList<XYChart.Data> datas = FXCollections.observableArrayList();
    XYChart.Series series = new XYChart.Series();
    @FXML
    public LineChart<Number, Number> print;

    @FXML
    protected NumberAxis yAxis;

    @FXML
    protected NumberAxis xAxis;

    private void setGraph(String str) {
        Roots roots = new Roots();
        series = new XYChart.Series();
        datas.add(new XYChart.Data(roots.x1, roots.x2));
        series.setData(datas);
        print.getData().add(series);

    }

}

