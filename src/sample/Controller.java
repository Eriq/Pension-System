package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import sample.Classes.*;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources){
        populateTableParticipants();
        populateTableContributions();
        populateTableInvestments();
        populateTablePayouts();
        populateTableSchemes();
        populateTableVesting();
    }

    //create lists to hold database data
    private ObservableList<Participant> participants;
    private ObservableList<Contribution> contributions;
    private ObservableList<Investment> investments;
    private ObservableList<Payout> payouts;
    private ObservableList<Scheme> schemes;
    private ObservableList<Vesting> vestings;
    private ObservableList<Benefit> benefits;

    private DbConnection dc;

    @FXML private TabPane mainTab;
    @FXML private Tab participantsTab, schemesTab, investmentsTab, payoutsTab, vestingTab, contributionsTab, reportsTab;
    @FXML private TableView<Participant> tableParticipants;
    @FXML private TableView<Scheme> tableSchemes;
    @FXML private TableView<Investment> tableInvestments;
    @FXML private TableView<Payout> tablePayouts;
    @FXML private TableView<Vesting> tableVesting;
    @FXML private TableView<Contribution> tableContributions;
    @FXML private TableView<Benefit> tableBenefits;
    @FXML private TableColumn<Participant, String> idCol, fnameCol, lnameCol, dobCol, incomeCol, empCol, retCol, schemeCol, vestCol, investCol, detailsCol;
    @FXML private TableColumn<Benefit, String> partIdCol, fNmeCol, lNmeCol, bnfitCol, schmCol, retDateCol;
    @FXML private TableColumn<Scheme, String> schemeIdCol, nameCol, rateCol, schemeDetCol;
    @FXML private TableColumn<Investment, String> investIdCol, investDetCol, investTypeCol, investRetCol;
    @FXML private TableColumn<Payout, String> payIdCol, payNameCol, payRateCol, payDetCol;
    @FXML private TableColumn<Vesting, String> vestIdCol, vestTypCol, vestPerCol, vestRatCol, vestDetCol;
    @FXML private TableColumn<Contribution, String> contribIdCol, contribPartCol, contribDetCol, contribTimeCol, contribAmntCol;
    @FXML private TextField editFname, editLname, editSalary, editInvestment, editScheme, editVesting, editId;
    @FXML private Button clearPart, addNewParticipant, updateBtnParticipant, deleteBtnParticipant;
    @FXML private TextField editContPart, editContAmnt, editContDet, editContId;
    @FXML private Button clearContrib, updateContrib, addNewContrib, deleteContrib;
    @FXML private DatePicker editEmpDate, editRetDate, editDob;
    @FXML private MenuItem closeMenu;
    @FXML private Label reportTitle;

    public void populateTableParticipants () {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        participants = FXCollections.observableArrayList();
        String query = "SELECT * FROM participants;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                participants.add(new Participant(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getDouble(5), rs.getDate(6), rs.getDate(7),rs.getInt(8),
                        rs.getInt(9), rs.getInt(10), rs.getInt(12)));
            }
            tableParticipants.setItems(participants);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        idCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("participant_id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("first_name"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("last_name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("date_of_birth"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("annual_salary"));
        empCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("employment_date"));
        retCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("retirement_date"));
        schemeCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("scheme"));
        vestCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("vesting"));
        investCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("investment"));
        detailsCol.setCellValueFactory(new PropertyValueFactory<Participant, String>("details"));

        tableParticipants.setRowFactory(tv -> {
            TableRow<Participant> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

                    Participant clickedRow = row.getItem();
                    int participant_id = clickedRow.getParticipant_id();

                    dc = new DbConnection();
                    Connection con = dc.getConnection();
                    participants = FXCollections.observableArrayList();

                    try {
                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM participants WHERE participant_id='" + participant_id + "';");
                        while (rs.next()) {
                            editId.setText((rs.getString(1)));
                            editFname.setText(rs.getString(2));
                            editLname.setText(rs.getString(3));
                            editDob.setValue(rs.getDate(4).toLocalDate());
                            editSalary.setText(rs.getString(5));
                            editEmpDate.setValue(rs.getDate(6).toLocalDate());
                            editRetDate.setValue(rs.getDate(7).toLocalDate());
                            editScheme.setText(rs.getString(8));
                            editVesting.setText(rs.getString(9));
                            editInvestment.setText(rs.getString(10));
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    }
                }
            });
            return row ;
        });
    }

    @FXML private void updateParticipant(ActionEvent event) {

        if(editId.getText() == null || editId.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Record Selected!");
            clearParticipantFields();
            return;
        }

        if (!checkEmpty()) {
            JOptionPane.showMessageDialog(null,"Fill All Fields!");
            return;
        }

        String fname = editFname.getText(), lname = editLname.getText();
        Double salary = Double.parseDouble(editSalary.getText());
        int investment = Integer.parseInt(editInvestment.getText()), scheme = Integer.parseInt(editScheme.getText()), vesting = Integer.parseInt(editVesting.getText()), participant_id = Integer.parseInt(editId.getText());
        java.sql.Date dob = java.sql.Date.valueOf(editDob.getValue()), employment =  java.sql.Date.valueOf(editEmpDate.getValue()), retDate = java.sql.Date.valueOf(editRetDate.getValue());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String updatesql = "UPDATE participants SET first_name=?,last_name=?,date_of_birth=?,annual_salary=?,employment_date=?,retirement_date=?, scheme=?,vesting=?,investment=? WHERE participant_id=?;";

        try {
            PreparedStatement stm = conn.prepareStatement(updatesql);
            stm.setString(1, fname);
            stm.setString(2, lname);
            stm.setDate(3, dob);
            stm.setDouble(4, salary);
            stm.setDate(5, employment);
            stm.setDate(6, retDate);
            stm.setInt(7, scheme);
            stm.setInt(8, vesting);
            stm.setInt(9, investment);
            stm.setInt(10, participant_id);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Updated!");

            populateTableParticipants();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
    }

    @FXML public void populateTableBenefits (ActionEvent event) {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        benefits = FXCollections.observableArrayList();
        String query = "SELECT * FROM vw_pension_benefits;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                benefits.add(new Benefit(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getDate(6)));
            }
            tableBenefits.setItems(benefits);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        reportTitle.setText("Pension Benefits Report");
        partIdCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("participant_id"));
        fNmeCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("first_name"));
        lNmeCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("last_name"));
        bnfitCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("pension_benefits"));
        schmCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("scheme"));
        retDateCol.setCellValueFactory(new PropertyValueFactory<Benefit, String>("retirement_date"));
    }

    @FXML public void onClickedCloseBtn(ActionEvent event) {
        Stage stage = (Stage) mainTab.getScene().getWindow();
        stage.close();
    }

    @FXML public void populateTableSchemes () {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        schemes = FXCollections.observableArrayList();
        String query = "SELECT * FROM schemes;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                schemes.add(new Scheme(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4)));
            }

            tableSchemes.setItems(schemes);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        schemeIdCol.setCellValueFactory(new PropertyValueFactory<Scheme, String>("scheme_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Scheme, String>("scheme_name"));
        rateCol.setCellValueFactory(new PropertyValueFactory<Scheme, String>("contribution_rate"));
        schemeDetCol.setCellValueFactory(new PropertyValueFactory<Scheme, String>("details"));
    }

    @FXML public void populateTableInvestments () {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        investments = FXCollections.observableArrayList();
        String query = "SELECT * FROM investments;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                investments.add(new Investment(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4)));
            }

            tableInvestments.setItems(investments);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        investIdCol.setCellValueFactory(new PropertyValueFactory<Investment, String>("investment_id"));
        investTypeCol.setCellValueFactory(new PropertyValueFactory<Investment, String>("investment_type"));
        investRetCol.setCellValueFactory(new PropertyValueFactory<Investment, String>("annual_returns"));
        investDetCol.setCellValueFactory(new PropertyValueFactory<Investment, String>("details"));
    }

    @FXML public void populateTablePayouts () {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        payouts = FXCollections.observableArrayList();
        String query = "SELECT * FROM payout_options;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                payouts.add(new Payout(rs.getInt(1), rs.getString(2), rs.getFloat(3), rs.getString(4)));
            }

            tablePayouts.setItems(payouts);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        payIdCol.setCellValueFactory(new PropertyValueFactory<Payout, String>("payout_id"));
        payNameCol.setCellValueFactory(new PropertyValueFactory<Payout, String>("payout_name"));
        payRateCol.setCellValueFactory(new PropertyValueFactory<Payout, String>("payout_rate"));
        payDetCol.setCellValueFactory(new PropertyValueFactory<Payout, String>("details"));

    }

    @FXML public void populateTableVesting () {

        dc = new DbConnection();
        Connection conn = dc.getConnection();

        vestings = FXCollections.observableArrayList();
        String query = "SELECT * FROM vesting_options;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                vestings.add(new Vesting(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getFloat(4), rs.getString(5)));
            }

            tableVesting.setItems(vestings);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        vestIdCol.setCellValueFactory(new PropertyValueFactory<Vesting, String>("vesting_id"));
        vestTypCol.setCellValueFactory(new PropertyValueFactory<Vesting, String>("vesting_type"));
        vestPerCol.setCellValueFactory(new PropertyValueFactory<Vesting, String>("vesting_period"));
        vestRatCol.setCellValueFactory(new PropertyValueFactory<Vesting, String>("vesting_rate"));
        vestDetCol.setCellValueFactory(new PropertyValueFactory<Vesting, String>("details"));

    }

    @FXML public void populateTableContributions () {
        dc = new DbConnection();
        Connection conn = dc.getConnection();

        contributions = FXCollections.observableArrayList();
        String query = "SELECT * FROM contributions;";

        try {
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                contributions.add(new Contribution(rs.getInt(1), rs.getInt(2), rs.getDouble(3), rs.getString(4), rs.getString(5)));
            }

            tableContributions.setItems(contributions);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

        contribIdCol.setCellValueFactory(new PropertyValueFactory<Contribution, String>("contribution_id"));
        contribPartCol.setCellValueFactory(new PropertyValueFactory<Contribution, String>("participant"));
        contribAmntCol.setCellValueFactory(new PropertyValueFactory<Contribution, String>("amount"));
        contribTimeCol.setCellValueFactory(new PropertyValueFactory<Contribution, String>("timestamp"));
        contribDetCol.setCellValueFactory(new PropertyValueFactory<Contribution, String>("details"));

        tableContributions.setRowFactory(tv -> {
            TableRow<Contribution> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {

                    Contribution clickedRow = row.getItem();
                    int contribution_id = clickedRow.getContribution_id();

                    dc = new DbConnection();
                    Connection con = dc.getConnection();
                    contributions = FXCollections.observableArrayList();

                    try {
                        ResultSet rs = con.createStatement().executeQuery("SELECT * FROM contributions WHERE contribution_id='" + contribution_id + "';");
                        while (rs.next()) {
                            editContId.setText((rs.getString(1)));
                            editContPart.setText(rs.getString(2));
                            editContAmnt.setText(rs.getString(3));
                            editContDet.setText(rs.getString(5));
                        }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, ex);
                    } finally {
                        if (conn != null) {
                            try {
                                conn.close();
                            } catch (SQLException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage());
                            }
                        }
                    }
                }
            });
            return row ;
        });
    }

    @FXML public void populateTableContributions (ActionEvent event) {

    }

    @FXML public void populateTableParticipants (ActionEvent event) {

    }

    private  void clearParticipantFields() {
        editFname.clear(); editLname.clear(); editSalary.clear();
        editInvestment.clear(); editScheme.clear(); editVesting.clear(); editId.clear();
        editEmpDate.getEditor().clear(); editRetDate.getEditor().clear(); editDob.getEditor().clear();
    }

    @FXML public void clearParticipant (ActionEvent event) {
        clearParticipantFields();
    }

    @FXML public void addNewParticipant (ActionEvent event) {

        if (!checkEmpty()){
            JOptionPane.showMessageDialog(null,"Fill All Fields");
            return;
        }

        String fname = editFname.getText(), lname = editLname.getText();
        Double salary = Double.parseDouble(editSalary.getText());
        int investment = Integer.parseInt(editInvestment.getText()), scheme = Integer.parseInt(editScheme.getText()), vesting = Integer.parseInt(editVesting.getText());
        java.sql.Date dob = java.sql.Date.valueOf(editDob.getValue()), employment =  java.sql.Date.valueOf(editEmpDate.getValue()), retDate = java.sql.Date.valueOf(editRetDate.getValue());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String insertsql = "INSERT INTO participants (first_name, last_name, date_of_birth, annual_salary, employment_date, retirement_date, scheme, vesting, investment, payout_option) VALUES (?,?,?,?,?,?,?,?,?);";

        try {
            PreparedStatement stm = conn.prepareStatement(insertsql);
            stm.setString(1, fname);
            stm.setString(2, lname);
            stm.setDate(3, dob);
            stm.setDouble(4, salary);
            stm.setDate(5, employment);
            stm.setDate(6, retDate);
            stm.setInt(7, scheme);
            stm.setInt(8, vesting);
            stm.setInt(9, investment);
            stm.execute();

            JOptionPane.showMessageDialog(null, "Successfully Saved!");
            populateTableParticipants();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
    }

    public boolean checkEmpty() {
        if (editFname.getText() == null || editFname.getText().trim().isEmpty() ||
                editLname.getText() == null || editLname.getText().trim().isEmpty() ||
                editDob.getValue() == null ||
                editSalary.getText() == null || editSalary.getText().trim().isEmpty() ||
                editEmpDate.getValue() == null ||
                editRetDate.getValue() == null ||
                editScheme.getText() == null || editScheme.getText().trim().isEmpty() ||
                editVesting.getText() == null || editVesting.getText().trim().isEmpty() ||
                editInvestment.getText() == null || editInvestment.getText().trim().isEmpty() ) {
            return false;
        }

        return true;
    }

    @FXML public void updateContribution (ActionEvent event) {

        if(editContId.getText() == null || editContId.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Record Selected!");
            clearContribFields();
            return;
        }

        if (!checkEmptyContrib()) {
            JOptionPane.showMessageDialog(null,"Fill All Fields!");
            return;
        }

        String details = editContDet.getText();
        Double amount = Double.parseDouble(editContAmnt.getText());
        int cont_id = Integer.parseInt(editContId.getText()), part = Integer.parseInt(editContPart.getText());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String updatesql = "UPDATE contributions SET participant=?,amount=?,details=? WHERE contribution_id=?;";

        try {
            PreparedStatement stm = conn.prepareStatement(updatesql);
            stm.setInt(1, part);
            stm.setDouble(2, amount);
            stm.setString(3, details);
            stm.setInt(4, cont_id);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Updated!");

            populateTableContributions();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

    }

    @FXML public void addNewContribution (ActionEvent event) {

        if (!checkEmptyContrib()){
            JOptionPane.showMessageDialog(null,"Fill All Fields");
            return;
        }

        String details = editContDet.getText();
        Double amount = Double.parseDouble(editContAmnt.getText());
        int part = Integer.parseInt(editContPart.getText());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String insertsql = "INSERT INTO contributions (participant, amount, details) VALUES (?,?,?);";

        try {
            PreparedStatement stm = conn.prepareStatement(insertsql);
            stm.setInt(1, part);
            stm.setDouble(2, amount);
            stm.setString(3, details);
            stm.execute();

            JOptionPane.showMessageDialog(null, "Successfully Saved!");
            populateTableContributions();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

    }

    private  void clearContribFields() {
        editContDet.clear(); editContAmnt.clear();
        editContPart.clear(); editContId.clear();
    }

    @FXML public void clearContribution (ActionEvent event) {
        clearContribFields();
    }

    public boolean checkEmptyContrib() {
        if (editContDet.getText() == null || editContDet.getText().trim().isEmpty() ||
                editContAmnt.getText() == null || editContAmnt.getText().trim().isEmpty() ||
                editContPart.getText() == null || editContPart.getText().trim().isEmpty() ) {
            return false;
        }
        return true;
    }


    @FXML public void deleteContribution (ActionEvent event) {

        if(editContId.getText() == null || editContId.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Record Selected!");
            clearContribFields();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,"Delete Contribution?");
        if (confirm == 1 || confirm == 2 || confirm == -1) {
            return;
        }

        int cont_id = Integer.parseInt(editContId.getText());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String updatesql = "DELETE FROM contributions WHERE contribution_id=?;";

        try {
            PreparedStatement stm = conn.prepareStatement(updatesql);
            stm.setInt(1, cont_id);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Deleted!");

            populateTableContributions();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }
    }

    @FXML public void deleteParticipant (ActionEvent event) {

        if(editId.getText() == null || editId.getText().trim().isEmpty()){
            JOptionPane.showMessageDialog(null,"No Record Selected!");
            clearParticipantFields();
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(null,"Delete Participant?");
        if (confirm == 1 || confirm == 2 || confirm == -1) {
            return;
        }

        int participant_id = Integer.parseInt(editId.getText());

        dc = new DbConnection();
        Connection conn = dc.getConnection();
        String updatesql = "DELETE FROM participants WHERE participant_id=?;";

        try {
            PreparedStatement stm = conn.prepareStatement(updatesql);
            stm.setInt(1, participant_id);
            stm.executeUpdate();

            JOptionPane.showMessageDialog(null, "Successfully Deleted!");

            populateTableParticipants();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        }

    }

    @FXML public void openTabParticipant(ActionEvent event) {
        mainTab.getSelectionModel().select(participantsTab);
    }

    @FXML public void openTabScheme(ActionEvent event) {
        mainTab.getSelectionModel().select(schemesTab);
    }

    @FXML public void openTabInvestment(ActionEvent event) {
        mainTab.getSelectionModel().select(investmentsTab);
    }

    @FXML public void openTabPayout(ActionEvent event) {
        mainTab.getSelectionModel().select(payoutsTab);
    }

    @FXML public void openTabVesting(ActionEvent event) {
        mainTab.getSelectionModel().select(vestingTab);
    }

    @FXML public void openTabContrib(ActionEvent event) {
        mainTab.getSelectionModel().select(contributionsTab);
    }

    @FXML public void openTabReports(ActionEvent event) {
        mainTab.getSelectionModel().select(reportsTab);
    }

}
