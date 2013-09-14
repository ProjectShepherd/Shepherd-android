package com.shepherd.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.shepherd.api.Person;

public class PersonUtils {
    private static final String REPORTER_ID = "reporter_id";
    private static final String FIRST_NAME = "first_name";
    private static final String MIDDLE_NAME = "middle_name";
    private static final String LAST_NAME = "last_name";
    private static final String AGE = "age";
    private static final String HEIGHT = "height";
    private static final String WEIGHT = "weight";
    private static final String SEX = "sex";
    private static final String HAIR = "hair_color";
    private static final String EYE = "eye_color";
    private static final String RACE = "race";
    private static final String DESCRIPTION = "description";
    private static final String PICTURES = "pictures";
    private static final String MOBILE = "mobile";
    private static final String THUMB = "thumb";

    public static List<Person> getMissingPersons(String jsonResponse, String fuzzyMatch) {
        List<Person> persons = new ArrayList<Person>();

        try {
            JSONArray jsonPersons = new JSONArray(jsonResponse);

            JSONObject jsonPerson;
            Person person;
            for (int i = 0; i < jsonPersons.length(); i++) {
                jsonPerson = jsonPersons.getJSONObject(i);
                person = new Person();

                person.id = jsonPerson.getLong(REPORTER_ID);

                try {
                    person.firstName = jsonPerson.getString(FIRST_NAME);
                } catch (JSONException e) {
                    person.firstName = null;
                }

                try {
                    person.middleName = jsonPerson.getString(MIDDLE_NAME);
                } catch (JSONException e) {
                    person.middleName = null;
                }

                try {
                    person.lastName = jsonPerson.getString(LAST_NAME);
                } catch (JSONException e) {
                    person.lastName = null;
                }

                try {
                    person.age = jsonPerson.getInt(AGE);
                } catch (JSONException e) {
                    person.age = 0;
                }

                try {
                    person.height = jsonPerson.getInt(HEIGHT);
                } catch (JSONException e) {
                    person.height = 0;
                }

                try {
                    person.weight = jsonPerson.getInt(WEIGHT);
                } catch (JSONException e) {
                    person.weight = 0;
                }

                try {
                    person.sex = jsonPerson.getString(SEX);
                } catch (JSONException e) {
                    person.sex = null;
                }

                try {
                    person.hair = jsonPerson.getString(HAIR);
                } catch (JSONException e) {
                    person.hair = null;
                }

                try {
                    person.eye = jsonPerson.getString(EYE);
                } catch (JSONException e) {
                    person.eye = null;
                }

                try {
                    person.race = jsonPerson.getString(RACE);
                } catch (JSONException e) {
                    person.race = null;
                }

                try {
                    person.description = jsonPerson.getString(DESCRIPTION);
                } catch (JSONException e) {
                    person.description = null;
                }

                try {
                    person.photo = jsonPerson.getJSONArray(PICTURES).getJSONObject(0).getString(MOBILE);
                } catch (JSONException e) {
                    person.photo = null;
                }

                try {
                    person.thumb = jsonPerson.getJSONArray(PICTURES).getJSONObject(0).getString(THUMB);
                } catch (JSONException e) {
                    person.thumb = null;
                }

                if (fuzzyMatch == null) {
                    persons.add(person);
                } else {
                    if (((person.firstName != null) && person.firstName.contains(fuzzyMatch))
                                    || ((person.middleName != null) && person.middleName.contains(fuzzyMatch))
                                    || ((person.lastName != null) && person.lastName.contains(fuzzyMatch))) {
                        persons.add(person);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return persons;
    }
}
