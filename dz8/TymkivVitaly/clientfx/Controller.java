package ru.geekbrains.java2.dz.dz8.TymkivVitaly.clientfx;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public TextField msgField;
    public TextField authLogin;
    public TextArea textArea;
    public PasswordField authPass;
    public ListView clientsList;

    public HBox bottomPanel;
    public HBox upperPanel;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private boolean authorized;
    private String nick;

    public void setAuthorized(boolean authorized) { // переключаем режим авторизации
        this.authorized = authorized;
        if (!this.authorized) { // если пользователь не авторизован
            upperPanel.setVisible(true); // включаем панель авторизации
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false); // выключаем панель отправки сообщений
            bottomPanel.setManaged(false);
            clientsList.setVisible(false);
            clientsList.setManaged(false);
        } else { // если пользователь авторизован
            upperPanel.setVisible(false); // выключаем панель авторизации
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true); // включаем панель отправки сообщений
            bottomPanel.setManaged(true);
            clientsList.setVisible(true);
            clientsList.setManaged(true);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setAuthorized(false);
        clientsList.setItems(FXCollections.observableArrayList());
    }

    public void start() {
        try {
            socket = new Socket("localhost", 8189);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            Thread t = new Thread(new Runnable() { // поток чтения сообщений от сервера
                @Override
                public void run() {
                    try {
                        while (true) { // цикл авторизации
                            String str = in.readUTF(); // читаем сообщения от сервера
                            if (str.startsWith("/authok ")) { // ждем сообщения об удачной авторизации вида "/authok nick1"
                                nick = str.split("\\s")[1]; // вытаскиваем свой ник
                                setAuthorized(true); // устанавливаем флаг авторизации в true
                                textArea.clear(); // очищаем текстовое поле
                                break; // выходим из цикла авторизации
                            }
                            textArea.appendText(str + "\n"); // если авторизация не прошла, печатаем причину, пришедшую с сервера
                        }
                        while (true) { // заходим в цикл общения с сервером
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.startsWith("/clients ")) {
                                    String[] names = str.split("\\s");
                                    Platform.runLater(() -> {
                                        clientsList.getItems().clear();
                                        for (int i = 1; i < names.length; i++) {
                                            clientsList.getItems().add(names[i]);
                                        }
                                    });
                                }
                            } else {
                                textArea.appendText(str + "\n");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        setAuthorized(false);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            t.setDaemon(true);
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAuth() {
        if (socket == null || socket.isClosed())
            start();
        try {
            out.writeUTF("/auth " + authLogin.getText() + " " + authPass.getText());
            authLogin.clear();
            authPass.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(msgField.getText());
            msgField.clear();
            msgField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clientsListClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            msgField.setText("/w " + (String) clientsList.getSelectionModel().getSelectedItem() + " ");
            msgField.requestFocus();
            msgField.selectEnd();
        }
    }
}