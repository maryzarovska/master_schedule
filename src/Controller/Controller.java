package Controller;

import View.View;
import Model.Model;

public class Controller {
    private Model model;
    private View view; 

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        view.getBackButton().setOnAction(event -> handleBackButton());
        view.getDatePicker().setOnAction(event -> handleDatePicker());
        //view.getTextArea().setOnAction(event -> handleTextArea());
        view.getSaveButton().setOnAction(event -> handleSaveButton());
        view.getSearchField().setOnAction(event -> handleSearchField());
        view.getSearchButton().setOnAction(event -> handleSearchButton());
        //view.getResultTextArea().setOnAction(event -> handleResultTextArea());
        view.getUsernameField().setOnAction(event -> handleUsernameField());
        view.getPasswordField().setOnAction(event -> handlePasswordField());
        view.getLoginButton().setOnAction(event -> handleLoginButton());
        view.getRegisterButton().setOnAction(event -> handleRegisterButton());
        //view.getProfileImageView().setOnAction(event -> handleProfileImageView());
        //view.getOtherInfoText().setOnAction(event -> handleOtherInfoText());
        //view.getNameText().setOnAction(event -> handleNameText());
        view.getEditProfileButton().setOnAction(event -> handleEditProfileButton());
        view.getDatePickerProfile().setOnAction(event -> handleDatePickerProfile());
        view.getAddEventButton().setOnAction(event -> handleAddEventButton());
        //view.getEventTextArea().setOnAction(event -> handleEventTextArea());
        //view.getProfileTitleText().setOnAction(event -> handleProfileTitleText());
        view.getNameField().setOnAction(event -> handleNameField());
        view.getSurnameField().setOnAction(event -> handleSurnameField());
        view.getConfirmPasswordField().setOnAction(event -> handleConfirmPasswordField());
    }

    private Object handleConfirmPasswordField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleConfirmPasswordField'");
    }

    private Object handleSurnameField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSurnameField'");
    }

    private Object handleNameField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleNameField'");
    }

    private Object handleAddEventButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAddEventButton'");
    }

    private Object handleDatePickerProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleDatePickerProfile'");
    }

    private Object handleEditProfileButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleEditProfileButton'");
    }

    private Object handleRegisterButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRegisterButton'");
    }

    private Object handleLoginButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLoginButton'");
    }

    private Object handlePasswordField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePasswordField'");
    }

    private Object handleUsernameField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleUsernameField'");
    }

    private Object handleSearchButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSearchButton'");
    }

    private Object handleSearchField() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSearchField'");
    }

    private Object handleSaveButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleSaveButton'");
    }

    private Object handleDatePicker() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleDatePicker'");
    }

    private Object handleBackButton() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBackButton'");
    }
}


