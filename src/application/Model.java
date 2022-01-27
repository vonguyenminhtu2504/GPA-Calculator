package application;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Model {

  private XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
  private ArrayList<Integer> listOfCredits = new ArrayList<Integer>();
  private ArrayList<String> listOfGrades = new ArrayList<String>();
  private int numberOfRows = 3;
  private int numberOfSemesters = 1;
  private boolean isSaved = false;
  private double totalCreditPoints = 0.0;
  private double totalGradePoints = 0.0;
  private int[] pieData = new int[5];
  private int totalGrades = 0;
  private double currGpa = 0.0;
  private double cumGpa = 0.0;

  public void calculateGPA() {
    double numberSemesterHours = 0.0;
    double totalClassPoints = 0.0;
    double gradeScaled = 0.0;
    int classHours = 0;
    String grade = "";

    for (int i = 0; i <= numberOfRows; i++) {
      classHours = listOfCredits.get(i);
      numberSemesterHours += classHours;
      grade = listOfGrades.get(i);

      if (grade.equals("A+") || grade.equals("A")) {
        gradeScaled = 4.0;
        pieData[0] = pieData[0] + 1;
      } else if (grade.equals("A-")) {
        gradeScaled = 3.7;
        pieData[0] = pieData[0] + 1;
      } else if (grade.equals("B+")) {
        gradeScaled = 3.3;
        pieData[1] = pieData[1] + 1;
      } else if (grade.equals("B")) {
        gradeScaled = 3.0;
        pieData[1] = pieData[1] + 1;
      } else if (grade.equals("B-")) {
        gradeScaled = 2.7;
        pieData[1] = pieData[1] + 1;
      } else if (grade.equals("C+")) {
        gradeScaled = 2.3;
        pieData[2] = pieData[2] + 1;
      } else if (grade.equals("C")) {
        gradeScaled = 2.0;
        pieData[2] = pieData[2] + 1;
      } else if (grade.equals("C-")) {
        gradeScaled = 1.7;
        pieData[2] = pieData[2] + 1;
      } else if (grade.equals("D+")) {
        gradeScaled = 1.3;
        pieData[3] = pieData[3] + 1;
      } else if (grade.equals("D")) {
        gradeScaled = 1.0;
        pieData[3] = pieData[3] + 1;
      } else if (grade.equals("D-")) {
        gradeScaled = 0.7;
        pieData[3] = pieData[3] + 1;
      } else if (grade.equals("F")) {
        gradeScaled = 0.0;
        pieData[4] = pieData[4] + 1;
      }

      totalClassPoints += (classHours * gradeScaled);
    }

    totalGradePoints += totalClassPoints;
    totalCreditPoints += numberSemesterHours;

    setCumGpa(Math.round((totalGradePoints / totalCreditPoints) * 100.0) / 100.0);
    setCurrGPA(Math.round(totalClassPoints / numberSemesterHours * 100.0) / 100.0);
  }

  public void setSeries(double gpaType) {
    series
        .getData()
        .add(new XYChart.Data<String, Double>("Semester " + numberOfSemesters, gpaType));
  }

  public Series<String, Double> getSeries() {
    return series;
  }

  public ObservableList<Data> createPieData() {
    isSaved = true;

    for (int i = 0; i < pieData.length; i++) {
      totalGrades = totalGrades + pieData[i];
    }

    double[] percents = new double[5];
    for (int k = 0; k < percents.length; k++) {
      percents[k] = (100.0 * pieData[k] / totalGrades);
    }

    NumberFormat formatter = new DecimalFormat("#0.00");

    return FXCollections.observableArrayList(
        new PieChart.Data("A  " + formatter.format(percents[0]) + "%", pieData[0]),
        new PieChart.Data("B  " + formatter.format(percents[1]) + "%", pieData[1]),
        new PieChart.Data("C  " + formatter.format(percents[2]) + "%", pieData[2]),
        new PieChart.Data("D  " + formatter.format(percents[3]) + "%", pieData[3]),
        new PieChart.Data("F  " + formatter.format(percents[4]) + "%", pieData[4]));
  }

  public void setCredits(ArrayList<TextField> listOfCredits) {
    this.listOfCredits.clear();
    if (!listOfCredits.isEmpty()) {
      for (int i = 0; i < listOfCredits.size(); i++) {
        if (!(listOfCredits.get(i).getText().equals(""))
            && (listOfCredits.get(i).getText() != null)) {
          this.listOfCredits.add(Integer.parseInt(listOfCredits.get(i).getText()));
        }
      }
    }
  }

  public void setGradeList(ArrayList<ComboBox<String>> listOfGrades) {
    this.listOfGrades.clear();
    for (int i = 0; i < listOfGrades.size(); i++) {
      this.listOfGrades.add(listOfGrades.get(i).getValue());
    }
  }

  public void incrementSemesters() {
    this.numberOfSemesters++;
  }

  public void removeRowData(int index) {
    listOfCredits.remove(listOfCredits.get(index));
    listOfGrades.remove(listOfGrades.get(index));
  }

  public void clearData() {
    listOfCredits.clear();
    listOfGrades.clear();
  }

  public void setCumGpa(double cumGpa) {
    this.cumGpa = cumGpa;
  }

  public void setCurrGPA(double currGpa) {
    this.currGpa = currGpa;
  }

  public void setNumOfRows(int numOfRows) {
    numberOfRows = numOfRows;
  }

  public void setSaved(boolean flag) {
    this.isSaved = flag;
  }

  public boolean isSaved() {
    return this.isSaved;
  }

  public double getCurrGpa() {
    return currGpa;
  }

  public int getNumOfRows() {
    return numberOfRows;
  }

  public double getCumGpa() {
    return cumGpa;
  }
}
