package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.google.gson.stream.JsonReader;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class PrimaryController {

    public Label helloWorld;
    public TextField searchbar;
    public Label error_text;
    public TableView tableView;
    List<PlacesData> placesDataList = new ArrayList<>();


    public void sayHelloWorld(ActionEvent actionEvent) {
        helloWorld.setText("Hello World");
    }

    public void doSearch(ActionEvent actionEvent) throws URISyntaxException, IOException, InterruptedException, JSONException {
        System.out.println(searchbar.getText());
        if (searchbar.getText().equals(""))
        {
            error_text.setText("Please type a place to search.");
            return;
        }

        startSeaching();



    }

    private void startSeaching() throws URISyntaxException, IOException, InterruptedException, JSONException{

        String address= searchbar.getText().replace(" ", "%20");

        String url= "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?key=AIzaSyCvJepooT2EALdT4cwUin3wUvLQCzNFMfo&input="+address+"" +
                "&inputtype=textquery";
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(new
                URI(url))
                .header("accept", "application/json")
                .GET()
                .build();
        HttpResponse responseHandler =
                client.send(request, HttpResponse.BodyHandlers.ofString());
//        JsonReader reader = new JsonReader(new StringReader(responseHandler.toString()));

        String placeObject = String.valueOf(responseHandler.body());
        JSONObject jsonObject = new JSONObject(placeObject);

        placesDataList.clear();
        JSONArray candidates = jsonObject.getJSONArray("candidates");




        for (int i = 0; i < candidates.length(); i++){

            JSONObject jsonObjectPlace = candidates.getJSONObject(i);
            String placeId = jsonObjectPlace.getString("place_id");

            PlacesData placesData = getPlaceDetails(placeId);
            placesDataList.add(placesData);

        }

        final ObservableList<PlacesData> data = FXCollections.observableArrayList(placesDataList);

        TableColumn businessName = new TableColumn("Business Name");
        TableColumn businessAddress = new TableColumn("Business address");
        TableColumn city = new TableColumn("City");
        TableColumn state = new TableColumn("State");
        TableColumn zipCode = new TableColumn("Zip Code");
        tableView.getColumns().addAll(businessName, businessAddress, city, state,zipCode);

        businessName.setCellValueFactory(new PropertyValueFactory<>("businessName"));
        businessAddress.setCellValueFactory(new PropertyValueFactory<>("businessAddress"));
        city.setCellValueFactory(new PropertyValueFactory<>("cityName"));
        state.setCellValueFactory(new PropertyValueFactory<>("stateName"));
        zipCode.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

        tableView.setItems(data);

    }

    private PlacesData getPlaceDetails(String placeId) throws URISyntaxException, IOException, InterruptedException, JSONException {

        PlacesData placesData = new PlacesData();

        String url= "https://maps.googleapis.com/maps/api/place/details/json?key=AIzaSyCvJepooT2EALdT4cwUin3wUvLQCzNFMfo&place_id="+placeId;
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder(new
                URI(url))
                .header("accept", "application/json")
                .GET()
                .build();
        HttpResponse responseHandler =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        String placeObject = String.valueOf(responseHandler.body());
        JSONObject jsonObject = new JSONObject(placeObject).getJSONObject("result");

        JSONArray jsonArray = jsonObject.getJSONArray("address_components");

        for (int i = 0; i < jsonArray.length(); i++){

            JSONObject jsonObjectPlace = jsonArray.getJSONObject(i);
            JSONArray jsonArrayType = jsonObjectPlace.getJSONArray("types");
            if (jsonArrayType != null){

                boolean isTypes = jsonArrayType.toString().contains("postal_code");
                if (isTypes){

                    String zipCode = jsonObjectPlace.getString("long_name");
                    placesData.setZipCode(zipCode);

                }
                boolean isCity = jsonArrayType.toString().contains("locality");
                if (isCity){

                    String city = jsonObjectPlace.getString("long_name");
                    placesData.setCityName(city);

                }
                boolean isState = jsonArrayType.toString().contains("administrative_area_level_1");
                if (isState){

                    String state = jsonObjectPlace.getString("long_name");
                    placesData.setStateName(state);

                }


            }

        }
        String businessName = jsonObject.getString("name");
        String businessAddress = jsonObject.getString("formatted_address");
        placesData.setBusinessName(businessName);
        placesData.setBusinessAddress(businessAddress);

        return placesData;
    }

    @FXML
    private void handleKeyPressed(KeyEvent ke) throws InterruptedException, IOException, JSONException, URISyntaxException {
        if (!searchbar.getText().equals(""))
        {
            error_text.setText("");

            System.out.println("-*-*-* "+ ke.getText());
            //Start searching
            startSeaching();


        }

    }
}

