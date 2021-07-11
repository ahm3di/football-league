/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.ahmed.football.league;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Ahmed
 */
public class TeamGUI extends Application {
    
    public ObservableList<Team> teams;
    public ObservableList<Result> results;
    public String homeNameIn = "";
    public String awayNameIn = "";
    public int homeScoreIn = -1;
    public int awayScoreIn = -1;
    public int homeIndex;
    public int awayIndex;
    public boolean nameExists = true;

    @Override
    public void start(Stage stage) {
        
        //Set stage title
        stage.setTitle("Football League");
        
        //Create table view, called table
        TableView<Team> table;
        table = new TableView<>();

        //Name column
        TableColumn<Team, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        //name columns will occupy 25% of the table
        nameColumn.prefWidthProperty().bind(table.widthProperty().divide(100/25));

        //Matches Played column
        TableColumn<Team, Integer> MPColumn = new TableColumn<>("MP");
        MPColumn.setCellValueFactory(new PropertyValueFactory<>("MP"));
        MPColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Wins column
        TableColumn<Team, Integer> winsColumn = new TableColumn<>("W");
        winsColumn.setCellValueFactory(new PropertyValueFactory<>("wins"));
        winsColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Draws column
        TableColumn<Team, Integer> drawsColumn = new TableColumn<>("D");
        drawsColumn.setCellValueFactory(new PropertyValueFactory<>("draws"));
        drawsColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Losses column
        TableColumn<Team, Integer> lossesColumn = new TableColumn<>("L");
        lossesColumn.setCellValueFactory(new PropertyValueFactory<>("losses"));
        lossesColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Goals For column
        TableColumn<Team, Integer> GFColumn = new TableColumn<>("GF");
        GFColumn.setCellValueFactory(new PropertyValueFactory<>("GF"));
        GFColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Goals Against column
        TableColumn<Team, Integer> GAColumn = new TableColumn<>("GA");
        GAColumn.setCellValueFactory(new PropertyValueFactory<>("GA"));
        GAColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Goal Difference column
        TableColumn<Team, Integer> GDColumn = new TableColumn<>("GD");
        GDColumn.setCellValueFactory(new PropertyValueFactory<>("GD"));
        GDColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //Points column
        TableColumn<Team, Integer> pointsColumn = new TableColumn<>("Pts");
        pointsColumn.setCellValueFactory(new PropertyValueFactory<>("points"));
        pointsColumn.prefWidthProperty().bind(table.widthProperty().divide(100/9.375));

        //assigns all columns to table
        table.getColumns().addAll(nameColumn, MPColumn, winsColumn, drawsColumn, lossesColumn, GFColumn, GAColumn, GDColumn, pointsColumn);
        
        //load objects from file into team ObservableList
        ObservableList<Team> teams = read();

        //set sable with loaded objects
        table.setItems(teams);


        //Add buttons 
        Button addButton = new Button();
        addButton.setText("Add Team");

        Button removeButton = new Button();
        removeButton.setText("Remove Team");

        Button addResultButton = new Button();
        addResultButton.setText("Add Result");
        
        Button removeResultButton = new Button();
        removeResultButton = new Button("Remove Result");



        //Create table view, called resultsTable and assign 4 columns from above
        TableView<Result> resultTable;
        
        resultTable = new TableView<>();
        
        //Home team name column
        TableColumn<Result, String> hNameColumn = new TableColumn<>("Home Team");
        hNameColumn.setCellValueFactory(new PropertyValueFactory<>("homeTeam"));
        hNameColumn.setMinWidth(310);

        //Home team score column
        TableColumn<Result, Integer> hScoreColumn = new TableColumn<>("Score");
        hScoreColumn.setCellValueFactory(new PropertyValueFactory<>("homeScore"));

        
        //Away team name column
        TableColumn<Result, String> aNameColumn = new TableColumn<>("Away Team");
        aNameColumn.setCellValueFactory(new PropertyValueFactory<>("awayTeam"));
        aNameColumn.setMinWidth(310);

        //Away team score column
        TableColumn<Result, Integer> aScoreColumn = new TableColumn<>("Score");
        aScoreColumn.setCellValueFactory(new PropertyValueFactory<>("awayScore"));

        
        //add columns to resultTable
        resultTable.getColumns().addAll(hNameColumn, hScoreColumn, aNameColumn, aScoreColumn);

        Text teamText = new Text("Teams");
        teamText.setFont(Font.font("Monospaced Bold Italic", FontWeight.EXTRA_BOLD, 32));
        teamText.setFill(Color.BLACK);
        
        Text resultText = new Text("Results");
        resultText.setFont(Font.font("Monospaced Bold Italic", FontWeight.EXTRA_BOLD, 32));
        resultText.setFill(Color.BLACK);
        
        
        //load objects from file into result ObservableList
        ObservableList<Result> results = readResult();
        //set table with loaded objects
        resultTable.setItems(results);

        //Code to be executed when addButton is clicked
        addButton.setOnAction(e -> {

            //Creat TextInputDialog called 'nameInput' to use input for the team name
            TextInputDialog nameInput = new TextInputDialog();

            //Set title,header and assign the output from the Dialog to nameEntered
            //Output is an Optional<String> which can later be converted to string
            nameInput.setTitle("Add Team");
            nameInput.setHeaderText("Enter team name:");
            Optional<String> nameEntered = nameInput.showAndWait();

            //If there is user clicks 'Ok' button the following code will run, else the dialog will close
            //as either the 'cancel' or exit button would have been clicked.
            //This is used to make sure there isn't an exception thrown when the user decides to cancel.
            if (nameEntered.isPresent()) {

                //get name entered by user, converted to string, remove trailing spaces and store in string 'nameEnteredIn'
                String nameEnteredIn = nameEntered.get().trim();

                //If teams list is empty create new ObservableList team1 and call write method
                if (teams.isEmpty()) {
                    ObservableList<Team> team1 = FXCollections.observableArrayList();
                    write(team1);
                } //bolean variable nameExists set to false sa list isn't empty
                else {
                    nameExists = false;
                }

                //Test to see if the name entered by the user isn't an empty string
                if (!nameEnteredIn.equals("")) {

                    //Create new team with with the same name as the user input
                    Team newTeam = new Team(nameEnteredIn.replace("\\s++$", ""), 0, 0, 0, 0, 0, 0, 0, 0);

                    //Test to see if the team already exists using contains method
                    //Contains method makes use of equals, which has been overriden
                    //in the Team class
                    nameExists = teams.contains(newTeam);

                    //if nameExists is false the code is run to create the team               
                    if (!nameExists) {
                        //Create new team with name entered by the user and remove any trailing spaces
                        teams.add(new Team(nameEnteredIn.replace("\\s++$", ""), 0, 0, 0, 0, 0, 0, 0, 0));

                        //Create a new allert called success with type Information
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        //Set title,header and content text
                        success.setTitle("Success");
                        success.setHeaderText(null);
                        success.setContentText("Team has been added to the table");

                        //Displays and waits for user input, focus remains on alert
                        success.showAndWait();
                        //Write the teams list to the file using write method
                        write(teams);

                    }//end of if statement !nameExists
                    else {
                        //Since name exists already the following alert with type Error is displayed
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Team already exists!");
                        alert.showAndWait();
                    }

                }//end of if statement !nameEnteredIn.equals("")
                else {
                    //Since the variable contained an empty string display the following error message
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Team name cannot be left empty!");
                    alert.showAndWait();

                }

            }//end of if statement nameEntered.isPresent()
            else {
                //As the user did not click ok and either clicked cancel or exit, close the dialog
                nameInput.close();
            }

        }); //end of addButton

        //code to be executed when remove button is clicked
        removeButton.setOnAction(e -> {

            ObservableList<Team> teamSelected, allteams;
            //set 'allteams' variable to items in the table
            allteams = table.getItems();

            //get selected team
            teamSelected = table.getSelectionModel().getSelectedItems();

            //remove selected team from table
            teamSelected.forEach(allteams::remove);

            //write new teams list to the file
            write(teams);

        });//end of removeButton

        //code to be executed when add button is clicked
        addResultButton.setOnAction(e -> {
            //set boolean variables
            boolean endLoop = true;
            boolean validResults = false;

            //Create new TextInputDialog called HomeNameDialog
            TextInputDialog HomeNameDialog = new TextInputDialog();
            //Set title and header text
            HomeNameDialog.setTitle("Home Team Details");
            HomeNameDialog.setHeaderText("Enter HOME team NAME");

            //Store output in homeName variable
            Optional<String> homeName = HomeNameDialog.showAndWait();

            //Only continue if user presses 'OK' button on the dialog
            if (homeName.isPresent()) {

                //convert optional string (homeName) to string (homeNameIn) and remove trailing spaces
                homeNameIn = homeName.get().trim();

                //create temp object newTeam with same name as user input
                Team newTeam = new Team(homeNameIn, 0, 0, 0, 0, 0, 0, 0, 0);

                //check if the team actually exists using contains method
                //Contains method makes use of equals, which has been overriden
                //in the Team class
                boolean teamExists = teams.contains(newTeam);

                //Only run code if the input isn't an empty string
                if (!homeNameIn.equals("")) {

                    //Only run the code if the team exists, as results cannot be added
                    //for teams that do not exist
                    if (teamExists) {

                        //try catch block used to catch NumberFormatException
                        try {

                            //create new TextInputDialog for home score input
                            TextInputDialog HomeScoreDialog = new TextInputDialog();
                            HomeScoreDialog.setTitle("Home Team Details");
                            HomeScoreDialog.setHeaderText("Enter HOME team SCORE");

                            Optional<String> homeScore = HomeScoreDialog.showAndWait();

                            //only continue if user selected Ok option on dialog
                            if (homeScore.isPresent()) {

                                //convert Optional String to string so we can use the equals
                                //method later on
                                String h = homeScore.get();

                                if (!h.equals("")) {
                                    //convert string 'h' to int homeScoreIn
                                    homeScoreIn = parseInt(h);

                                    endLoop = false; //set boolean to false so next section of code will run

                                } //end of !h.equals("")
                                else {
                                    //As input was empty string display alert confirming
                                    //that the score was automatically set to 0

                                    Alert info = new Alert(Alert.AlertType.INFORMATION);
                                    //Set title,header and content text
                                    info.setTitle("NOTE");
                                    info.setHeaderText(null);
                                    info.setContentText("As score input was left blank the score has automatically been set 0");
                                    homeScoreIn = 0;
                                }
                            }//end of if statement homeScore.isPresent()
                            else {
                                //as user selected exit or cancel close dialog
                                HomeScoreDialog.close();

                            }

                            //Catch NumberFormatException which occurs when user dosen't enter a number
                        } catch (NumberFormatException ne) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("ERROR");
                            alert.setHeaderText("Invalid Entry!");
                            alert.showAndWait();
                            teamExists = false;
                        }

                    }//end of is statement teamExists
                    else {
                        //as team doesn't exist display error and set teamExists to false;
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("ERROR");
                        alert.setHeaderText("Team doesn't exist!");
                        alert.showAndWait();
                        teamExists = false;

                    }

                }//end of if statement !homeName.equals("") 
                else {
                    //as the user input was an empty string display error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Team name cannot be left empty!");
                    alert.showAndWait();
                    teamExists = false;
                }
                //Only run if the homeScoreIn is greater than or equal to 0
                if (homeScoreIn >= 0) {

                    //loops while the boolean teamExists is true AND endLoop is false
                    while (teamExists && endLoop == false) {

                        //new TextInputDialog to get user input for away team name
                        TextInputDialog AwayNameDialog = new TextInputDialog();
                        AwayNameDialog.setTitle("Away Team Details");
                        AwayNameDialog.setHeaderText("Enter AWAY team NAME");
                        Optional<String> awayName = AwayNameDialog.showAndWait();

                        //only run if dialog 'ok' option is clicked
                        if (awayName.isPresent()) {

                            //convert optional (awayName) to string (awayNameIn) and remove trailing spaces
                            awayNameIn = awayName.get().trim();

                            //create temp object newTeam2 with name set to the user input
                            Team newTeam2 = new Team(awayNameIn, 0, 0, 0, 0, 0, 0, 0, 0);

                            //Test to see if the team already exists using contains method
                            //Contains method makes use of equals, which has been overriden
                            //in the Team class. If it exists the boolean will be set to true
                            boolean teamExists2 = teams.contains(newTeam2);

                            //Only run if away name input is not an empty string
                            //and away name and home name aren't equal, notice 'IgnoreCase' is used
                            //this makes sure that the case of the name is not factored in when
                            //testing for teams
                            if (!awayNameIn.equals("") && !awayNameIn.equalsIgnoreCase(homeNameIn)) {

                                //Only run code if the team exists
                                if (teamExists2) {

                                    //new TextInputDialog to get user input for awayScore
                                    TextInputDialog AwayScoreDialog = new TextInputDialog();
                                    AwayScoreDialog.setTitle("Away Team Details");
                                    AwayScoreDialog.setHeaderText("Enter AWAY team SCORE");
                                    Optional<String> awayScore = AwayScoreDialog.showAndWait();

                                    //Only run code if user clicks 'ok' button on dialog
                                    if (awayScore.isPresent()) {
                                        String a = awayScore.get();

                                        if (!a.equals("")) {

                                            try {
                                                //convert string 'a' to int awayScoreIn
                                                awayScoreIn = parseInt(a);
                                                endLoop = true;
                                                //next section of code will run as we have now got correct inputs for all fields
                                                validResults = true;
                                            } catch (NumberFormatException ne) {
                                                //if user entered anything but an integer, catch exception display error
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("ERROR");
                                                alert.setHeaderText("Invalid Value Entered!");
                                                alert.showAndWait();
                                            }
                                        }//end of if statement !a.equals("")
                                        else {

                                            //as score field was left blank set score to 0 and let the user know
                                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                                            //Set title,header and content text
                                            info.setTitle("NOTE");
                                            info.setHeaderText(null);
                                            info.setContentText("As score input was left blank the score has automatically been set 0");
                                            awayScoreIn = 0;
                                        }

                                    } // end of if statement awayScore.isPresent()
                                    else {
                                        //As user selected cancel or exit close dialog
                                        AwayScoreDialog.close();
                                        endLoop = true; //set boolean to true as pressed cancel
                                    }

                                } //end of if statement teamExists
                                else {
                                    //as team doesn't exist display error message
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText("Team doesn't exist!");
                                    alert.showAndWait();
                                }

                            }//end of if statement !awayNameIn.equals("") && !awayNameIn.equalsIgnoreCase(homeNameIn)
                            else {
                                //if the away name was an empty string display error message
                                if (awayNameIn.equals("")) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText("Team name cannot be left empty!");
                                    alert.showAndWait();
                                }

                                //if the home and team names where the same display error message
                                if (awayNameIn.equalsIgnoreCase(homeNameIn)) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setTitle("ERROR");
                                    alert.setHeaderText("A team cannot play against themselves!");
                                    alert.showAndWait();
                                }
                            }

                        } // end of if statement awayName.isPresent()
                        else {
                            //as the cancel or close button was clicked, set endLoop to true
                            //and close the dialog
                            AwayNameDialog.close();
                            endLoop = true;
                        }
                    }// end of while loop
                    

                    //only run if validResults variable is true
                    if (validResults) {

                        //loop through the list
                        for (int i = 0; i < teams.size(); i++) {
                            //find team index of team with the same name as homeNameIn
                            if (teams.get(i).getName().equalsIgnoreCase(homeNameIn)) {
                                //set homeIndex to the index of the object
                                homeIndex = i;
                                //end loop
                                break;
                            }
                        }

                        for (int i = 0; i < teams.size(); i++) {
                            //find team index of team with the same name as awayNameIn
                            if (teams.get(i).getName().equalsIgnoreCase(awayNameIn)) {
                                //set homeIndex to the index of the object
                                awayIndex = i;
                                //end loop
                                break;
                            }
                        }
                        //add new object to results list
                        results.add(new Result(homeNameIn, homeScoreIn, awayNameIn, awayScoreIn));
                        
                        //write results to file
                        writeResult(results);

                        //set goals for and against for home team
                        teams.get(homeIndex).setGF(homeScoreIn);
                        teams.get(homeIndex).setGA(awayScoreIn);

                        //set goals for and against for away team
                        teams.get(awayIndex).setGF(awayScoreIn);
                        teams.get(awayIndex).setGA(homeScoreIn);

                        //only run if home team scored more goals than away team
                        if (homeScoreIn > awayScoreIn) {
                            //use .setWins() method to add a win to the home team
                            //.setWins() accepts a value and adds it to the wins
                            teams.get(homeIndex).setWins(1);
                            
                            //use .setLosses() method to add a win to the home team
                            //.setLosses() accepts a value and adds it to the wins
                            teams.get(awayIndex).setLosses(1);
                            
                            //refresh the table with updated results
                            table.refresh();
                            
                            //write updated list to file;
                            write(teams);
                        }
                        
                        //only run if away team scored more goals than home team
                        if (awayScoreIn > homeScoreIn) {
                            
                            //use .setWins() method to add a win to the home team
                            //.setWins() accepts a value and adds it to the wins
                            teams.get(awayIndex).setWins(1);
                            
                            //use .setLosses() method to add a win to the home team
                            //.setLosses() accepts a value and adds it to the wins
                            teams.get(homeIndex).setLosses(1);
                            
                            //refresh the table with updated results
                            table.refresh();
                            
                            //write updated list to file
                            write(teams);

                        }
                        
                        //only run if both away team's score and home team's score are equal
                        if (awayScoreIn == homeScoreIn) {
                            //use .setDraws() to add 1 draw to home team
                            //set.Draws() accepts a value and adds to the draws
                            teams.get(homeIndex).setDraws(1);
                            
                            //use .setDraws() to add 1 draw to away team
                            teams.get(awayIndex).setDraws(1);
                            
                            //refresh table with updated results
                            table.refresh();
                            
                            //write updated list to file
                            write(teams);
                        }
                    }
                }//end of if statement homeScore >0
                else {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Home team goals must be greater than or equal to 0");
                    alert.showAndWait();

                }

            }//end of if statement homeName.isPresent()
            else {
                //as user selected cancel or exit, close the dialog
                HomeNameDialog.close();
            }

        });//end of addResults button
        
        //code to be executed when remove result button
        removeResultButton.setOnAction(e -> {

            ObservableList<Result> resultSelected, allresults;
            //set 'allteams' variable to items in the table
            allresults = resultTable.getItems();

            //get selected team
            resultSelected = resultTable.getSelectionModel().getSelectedItems();

            //remove selected team from table
            resultSelected.forEach(allresults::remove);

            //write new teams list to the file
            writeResult(results);

        });//end of removeButton
        
        

        //Create HBox and add items
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(addButton, removeButton, addResultButton, removeResultButton);
        buttonsBox.getStyleClass().add("buttons");

        //Create Vbox and add items
        VBox root = new VBox(20);
        root.setAlignment(Pos.BOTTOM_CENTER);
        root.getChildren().addAll(teamText,table, buttonsBox, resultText, resultTable);
        root.getStyleClass().add("root");

        //Set scene
        Scene scene = new Scene(root, 766, 600);
        stage.setScene(scene);
        scene.getStylesheets().add("stylesheet.css");

        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    public void write(ObservableList<Team> teamObservable) {
        try {
            // write objects to file
            FileOutputStream fos = new FileOutputStream("src/main/resources/teamSave.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //written as Team Objects 
            oos.writeObject(new ArrayList<Team>(teamObservable));
            //close object output stream
            oos.close();
            
        //catch Exceptions
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<Team> read() {

        try {
            //read objects from file
            FileInputStream fis = new FileInputStream("src/main/resources/teamSave.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            //read as Team objects, loaded into list
            List<Team> list = (List<Team>) ois.readObject();
            //list returned as part of observableArrayList which is a part of 
            //FXCollections
            return FXCollections.observableArrayList(list);

        //catch exceptions
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("No teamns found in file: 'teamSave.ser'");
        }
        //return empty observable list if there is a problem
        return FXCollections.emptyObservableList();
    }

    public ObservableList<Result> readResult() {

        try {
            //read Result objects from file
            FileInputStream fis2 = new FileInputStream("src/main/resources/resultSave.ser");

            ObjectInputStream ois2 = new ObjectInputStream(fis2);
            
            //read as Result objects into list
            List<Result> list2 = (List<Result>) ois2.readObject();

            //returned as observableArrayList
            return FXCollections.observableArrayList(list2);

        //catch Exceptions
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("No results found in file: 'resultSave.ser'");
        }

        //return empty observable list if there is a problem
        return FXCollections.emptyObservableList();
    }

    //write result Objects to file
    public void writeResult(ObservableList<Result> resultObservable) {
        try {
            // write objects to file
            FileOutputStream fos2 = new FileOutputStream("src/main/resources/resultSave.ser");
            ObjectOutputStream oos2 = new ObjectOutputStream(fos2);
            //written as Result objects
            oos2.writeObject(new ArrayList<Result>(resultObservable));
            //close object output stream
            oos2.close();

        //catch Exceptions    
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    
}
