package sample;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.*;

/**
 * Controller class.
 */
public class Controller {
    @FXML
    protected void buttonAction(MouseEvent event) {
        if (!event.getEventType().equals(MouseEvent.MOUSE_PRESSED)) {
            // The following method delays the changing of color until it is finished
            startThread();
        }
    }

    private void startThread() {

        //didn't work, here or in thread
//        buttonUp.setStyle("-fx-background-color: black;");
        //
//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//                buttonUp.setStyle("-fx-background-color: black;");
//            }
//        });
        final String url = "http://192.168.8.42/doesnotexist";
        //start up a single thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        try {
            //didn't work, crashes app
//            FutureTask<Void> updateUITask = new FutureTask<>(() ->
//                    buttonUp.setStyle("-fx-background-color: black;"),null);
//            Platform.runLater(updateUITask);
//            updateUITask.get();

            // todo this seems to be blocking the UI thread (e.g. can't move window) but
            // it shouldn't since it's a separate thread?
            executor.submit(() -> {
                String responseBody = "";
                System.out.println("sending request to " + url);
                try {
                    InputStream response = new URL(url).openStream();
                    try (Scanner scanner = new Scanner(response)) {
                        responseBody = scanner.useDelimiter("\\A").next();
                        System.out.println(responseBody);
                    }
                } catch (IOException e) {
                    //the thread will finally exit after executor.shutdownNow() has tried to stop it
                }
                return responseBody;
            }).get(2, TimeUnit.SECONDS); //timeout of x seconds
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("Request timed out.");
            executor.shutdownNow();
        }
    }
}
