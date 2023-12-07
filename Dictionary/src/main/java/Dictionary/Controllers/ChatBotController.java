package Dictionary.Controllers;

import Dictionary.Helpers.StringUtils;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.text.SimpleDateFormat;
import java.util.Date;

import Dictionary.Models.Message;
import Dictionary.Service.OpenAIApi;
public class ChatBotController {

    @FXML
    public ScrollPane scrollPane;
    @FXML
    public TextArea messageBox;

    @FXML
    public Button sendButton;

    @FXML
    public VBox messages;

    String prompt = "";

    public void onSendButtonPressed() {
        String msg = StringUtils.normalizeUserMsg(messageBox.getText());
        if (!msg.trim().isEmpty()) {

            Thread userThread = new Thread(() -> {
                sendButton.setDisable(true);
                messageBox.setText("");
                messageBox.setDisable(true);
                addMsg(new Image(ChatBotController.class.getResource("/Image/user.png").toString()),
                        msg + "\n" + currentTime(),
                        false
                );
            });

            Thread botThread = new Thread(() -> {
                Platform.runLater(() -> {
                    String answer = StringUtils.normalizeChatResponse(response(msg)) + "\n" + currentTime();
                    this.addMsg(new Image(ChatBotController.class.getResource("/Image/chatbot.png").toString()),
                            answer,
                            true
                    );
                });
                messageBox.setDisable(false);
                sendButton.setDisable(false);
            });
            userThread.run();
            botThread.run();
        }
    }


    public void addMsg(Image img, String content, boolean second) {
        Message message = new Message(this.scrollPane);
        message.setMessage(content);
        message.setAvatar(img);
        if (second) {
//            message.setStyle("-fx-background-color: #51362A; -fx-background-radius: 20 20 20 20;");
            message.changeOrientation();
        }
        this.messages.getChildren().add(message);

    }

    public String currentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        return dateFormat.format(new Date()).toString();
    }

    public String response(String text) {
        try {
            String response = OpenAIApi.chatGPT(text);
            return response;
        } catch (Exception e) {
            System.out.println("Error in chatbot response");
            e.printStackTrace();
        }
        return null;
    }
}
