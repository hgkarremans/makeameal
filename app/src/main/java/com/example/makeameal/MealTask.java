package com.example.makeameal;

import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;

import com.example.makeameal.Domain.Cook;
import com.example.makeameal.Domain.Meal;
import com.example.makeameal.Domain.Person;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealTask
        extends AsyncTask<String, Void, ArrayList<Meal>> {

    private final static String TAG = MealTask.class.getSimpleName();
    private ArrayList<Meal> meals = new ArrayList<>();

    private RandomMealListenener listener;
    private String mealUrl = "https://shareameal-api.herokuapp.com/api/meal/";

    private final static String JSON_RESULT = "result";
    private final static String JSON_MEAL_ID = "id";
    private final static String JSON_MEAL_NAME = "name";

    private final static String JSON_MEAL_DESCRIPTION = "description";
    private final static String JSON_MEAL_ISACTIVE = "isActive";
    private final static String JSON_MEAL_ISVEGA = "isVega";
    private final static String JSON_MEAL_ISVEGAN = "isVegan";
    private final static String JSON_MEAL_ISTOTAKEHOME = "isToTakeHome";
    private final static String JSON_MEAL_DATETIME = "dateTime";
    private final static String JSON_MEAL_CREATEDATE = "createDate";
    private final static String JSON_MEAL_UPDATEDATE = "updateDate";
    private final static String JSON_MEAL_MAXAMOUNTOFPARTICIPANTS = "maxAmountOfParticipants";
    private final static String JSON_MEAL_PRICE = "price";
    private final static String JSON_MEAL_IMAGEURL = "imageUrl";
    private final static String JSON_MEAL_ALLERGENES = "allergenes";
    private final static String JSON_MEAL_COOK = "cook";
    private final static String JSON_MEAL_PARTICIPANTS = "participants";

    // Constructor
    public MealTask(RandomMealListenener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Meal> doInBackground(String... strings) {
        // Url uitlezen
        String mealUrl = strings[0];
        Log.d(TAG, "doInBackground aangeroepen - url = " + mealUrl);

        // Request naar API sturen, via de URL
        // Response als JSON String ontvangen
        try {
            Log.d(TAG, String.valueOf(1));
            String jsonResponse = downloadUrl(mealUrl);
            Log.d(TAG, String.valueOf(2));
            Log.d(TAG, "jsonResponse: " +  jsonResponse);

            meals = convertJsonToMeals(jsonResponse);
            Log.d(TAG, "The array" + meals);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }

    @Override
    protected void onPostExecute(ArrayList<Meal> people) {
        Log.d(TAG, "onPostExecute");
        listener.onMealAvailable(meals);
    }

    private String downloadUrl(String myurl) throws IOException {
        InputStream inputStream = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 10000;

        HttpURLConnection conn = null;
        try {
            URL url = new URL(myurl);
            conn =
                    (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            // Start the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
            inputStream = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = getStringFromInputStream(inputStream);
            return contentAsString;

            // Close the InputStream and connection
        } finally {
            conn.disconnect();
            if (inputStream != null) {
                inputStream.close();
            }
        }

    }

    // Reads an InputStream and converts it to a String.
    private static String getStringFromInputStream(InputStream inputStream){
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    private ArrayList<Meal> convertJsonToMeals(String jsonResponse){
        // Parsen (uitpluizen)
        // Response omzetten van JSON naar ArrayList<Person> people.
        JSONObject response = null;

        try {
            response = new JSONObject(jsonResponse);
            JSONArray results = response.getJSONArray(JSON_RESULT);
            for(int i = 0; i < results.length(); i++){
                try {
                    JSONObject meal = results.getJSONObject(i);
//                    JSONObject id = meal.getJSONObject(JSON_MEAL_ID);
//                    JSONObject name = meal.getJSONObject(JSON_MEAL_NAME);
//                    JSONObject desciption = meal.getJSONObject(JSON_MEAL_DESCRIPTION);
//                    JSONObject isActive = meal.getJSONObject((JSON_MEAL_ISACTIVE));
//                    JSONObject isVega = meal.getJSONObject(JSON_MEAL_ISVEGA);
//                    JSONObject isVegan = meal.getJSONObject(JSON_MEAL_ISVEGAN);
//                    JSONObject isToTakeHome = meal.getJSONObject(JSON_MEAL_ISTOTAKEHOME);
//                    JSONObject dateTime = meal.getJSONObject(JSON_MEAL_DATETIME);
//                    JSONObject createDate = meal.getJSONObject(JSON_MEAL_CREATEDATE);
//                    JSONObject updateDate = meal.getJSONObject(JSON_MEAL_UPDATEDATE);
//                    JSONObject maxAmountOfParticipants = meal.getJSONObject(JSON_MEAL_MAXAMOUNTOFPARTICIPANTS
//                    JSONObject price = meal.getJSONObject(JSON_MEAL_PRICE);
//                    JSONObject imageURl = meal.getJSONObject(JSON_MEAL_IMAGEURL);

                    int id = meal.getInt(JSON_MEAL_ID);
                    String name = meal.getString(JSON_MEAL_NAME);
                    String description = meal.getString(JSON_MEAL_DESCRIPTION);
                    boolean isActive = meal.getBoolean(JSON_MEAL_ISACTIVE);
                    boolean isVega = meal.getBoolean(JSON_MEAL_ISVEGA);
                    boolean isVegan = meal.getBoolean(JSON_MEAL_ISVEGAN);
                    boolean isToTakeHome = meal.getBoolean(JSON_MEAL_ISTOTAKEHOME);
                    String dateTime = meal.getString(JSON_MEAL_DATETIME);
                    String createDate = meal.getString(JSON_MEAL_CREATEDATE);
                    String updateDate = meal.getString(JSON_MEAL_UPDATEDATE);
                    int maxAmountOfParticipants = meal.getInt(JSON_MEAL_MAXAMOUNTOFPARTICIPANTS);
                    double price = meal.getDouble(JSON_MEAL_PRICE);
                    String imageUrl = meal.getString(JSON_MEAL_IMAGEURL);

                    JSONArray allergenes = meal.getJSONArray(JSON_MEAL_ALLERGENES);
                    List<String> allergenesList = new ArrayList<>();
                    for(int j = 0; j < allergenes.length(); j++){
                        allergenesList.add(allergenes.getString(j));
                    }
                    JSONObject cook = meal.getJSONObject(JSON_MEAL_COOK);
                    Log.d(TAG, "cook: " + cook);
                    Gson gson = new Gson();
                    Log.d(TAG, "cook: " + cook);
                    Cook cookObject = gson.fromJson(meal.getString("cook"), Cook.class);
                    Log.d(TAG, "cookObject: " + cookObject);
                    JSONArray participants = meal.getJSONArray(JSON_MEAL_PARTICIPANTS);

                    Person[] personArray = new Person[participants.length()];

                    for (int h = 0; h < participants.length(); h++) {
                        JSONObject personJson = participants.getJSONObject(i);

                        String firstName = personJson.getString("firstName");
                        String lastName = personJson.getString("lastName");
                        String emailPerson = personJson.getString("email");
                        String phonePerson = personJson.getString("phone");
                        String passwordPerson = personJson.getString("password");
                        String cityPerson = personJson.getString("city");
                        String streetPerson = personJson.getString("street");


                        Person person = new Person(
                                firstName,
                                lastName,
                                emailPerson,
                                phonePerson,
                                passwordPerson,
                                cityPerson,
                                streetPerson
                        );


                        personArray[i] = person;
                    }
                    List<Person> personList = Arrays.asList(personArray);
                    Meal p = new Meal(
                            id,
                            name,
                            description,
                            isActive,
                            isVega,
                            isVegan,
                            isToTakeHome,
                            dateTime,
                            createDate,
                            updateDate,
                            maxAmountOfParticipants,
                            price,
                            imageUrl,
                            allergenesList,
                            cookObject,
                            personList
                    );

                    meals.add(p);
                    Log.d(TAG, "convertJsonToMeals: " + meals);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return meals;
    }

    //
    public interface RandomMealListenener {
        public void onMealAvailable(ArrayList<Meal> meals);
    }
}
