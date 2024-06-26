package bjp.controller;

import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

public class EnvironmentalPopup {
    private static int duration = 7;

    private static final String[] eduPopupStrings = {
            "Environmental Tip: Whenever possible, choose walking or cycling for short distances. It's a zero-emission mode of transportation that also promotes physical activity.",
            "Environmental Tip: Public transportation, such as buses, trains, and trams, emits lower emissions per passenger compared to individual car trips. Utilize public transport whenever feasible.",
            "Environmental Tip: Consider working from home if your job allows it. Telecommuting reduces the need for daily commuting, thereby lowering carbon emissions from transportation.",
            "Environmental Tip: Plan your trips efficiently to minimize distance and time spent on the road. Combine errands into a single trip to reduce fuel consumption and emissions.",
            "Environmental Tip: Spread awareness about the environmental impact of transportation and encourage others to adopt eco-friendly transportation habits." };

    public static String selectMsg() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(eduPopupStrings.length);

        String message = eduPopupStrings[randomIndex];
        return message;
    }

    public static void showEduPopup(StackPane cityMainStack, String message) {
//        Label messageLabel = new Label("");
        Label messageLabel = new Label(message); // Directly set the message during label creation
        messageLabel.setFont(new Font("Arial", 16));
        messageLabel.setAlignment(Pos.CENTER);
        messageLabel.setWrapText(true);
        // keeping the pop up style same as player popup
        messageLabel.setStyle(
            "-fx-background-color: rgba(234, 255, 208, 0.4);" +
            "-fx-background-radius: 15;" +
            "-fx-border-color: rgba(46, 139, 87, 0.7);" +
            "-fx-border-width: 3;" +
            "-fx-border-radius: 15;" +
            "-fx-padding: 10;" +
            "-fx-font-weight: bold;" +
            "-fx-font-size: 14;" +
            "-fx-text-fill: #005f00;" +
            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);"
        );

        cityMainStack.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().contains("Tip:"));

        cityMainStack.getChildren().add(messageLabel);
        StackPane.setAlignment(messageLabel, Pos.BOTTOM_CENTER);

            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(duration));
            delay.setOnFinished(delayEvent -> cityMainStack.getChildren().remove(messageLabel));
            delay.play();
    }

}
