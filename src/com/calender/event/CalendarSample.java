package com.calender.event;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

/**
 * Main class for the Calendar API command line sample.
 * Demonstrates how to make an authenticated API call using OAuth 2 helper classes.
 */
public class CalendarSample {

  /**
   * Be sure to specify the name of your application. If the application name is {@code null} or
   * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
   */
  private static final String APPLICATION_NAME = "MeetingApplication/1.0";

  /** Directory to store user credentials. */
  private static final java.io.File DATA_STORE_DIR =
      new java.io.File(System.getProperty("user.home"), ".store/calendar_sample");

  /**
   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
   * globally shared instance across your application.
   */
  private static FileDataStoreFactory dataStoreFactory;

  /** Global instance of the JSON factory. */
  private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

  /** Global instance of the HTTP transport. */
  private static HttpTransport httpTransport;

  private static Calendar client;

  /** Authorizes the installed application to access user's protected data. */
  private static Credential authorize() throws Exception {
    // load client secrets
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
        new InputStreamReader(CalendarSample.class.getResourceAsStream("client_secret.json")));
    if (clientSecrets.getDetails().getClientId().startsWith("Enter") ||
        clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
      System.out.println(
          "Overwrite the src/main/resources/client_secrets.json file with the client secrets file "
          + "you downloaded from the Quickstart tool or manually enter your Client ID and Secret "
          + "from https://code.google.com/apis/console/?api=calendar#project:380094479037 "
          + "into src/main/resources/client_secrets.json");
      System.exit(1);
    }
    System.out.println("There..");

    // Set up authorization code flow.
    // Ask for only the permissions you need. Asking for more permissions will
    // reduce the number of users who finish the process for giving you access
    // to their accounts. It will also increase the amount of effort you will
    // have to spend explaining to users what you are doing with their data.
    // Here we are listing all of the available scopes. You should remove scopes
    // that you are not actually using.
    Set<String> scopes = new HashSet<String>();
    scopes.add(CalendarScopes.CALENDAR);
    scopes.add(CalendarScopes.CALENDAR_READONLY);
System.out.println("There..");
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets, scopes)
        .setDataStoreFactory(dataStoreFactory)
        .build();
    // authorize
    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver.Builder().setPort(1111).build()).authorize("user");
  }

  public String getDetails()
  {
	  LocalServerReceiver lr=new LocalServerReceiver.Builder().build();
      try
      {
		return "Redirect Uri: "+lr.getRedirectUri()+"---host: "+lr.getHost()+"---Port: "+lr.getPort();
	  } 
      catch (IOException e) 
      {
		return e.getMessage();
	  }
      
  }
  public  String register() {
    try {
      // initialize the transport
    	httpTransport = GoogleNetHttpTransport.newTrustedTransport();
System.out.println(httpTransport.toString());
      // initialize the data store factory
      dataStoreFactory = new FileDataStoreFactory(DATA_STORE_DIR);
       
      // authorization
      Credential credential = authorize();
System.out.println("Authorization Successful");
      // set up global Calendar instance
      client = new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
          .setApplicationName(APPLICATION_NAME).build();

      return "Success! Now add code here.";
    } catch (Exception e) {
      return e.getMessage();
    }
    
  }
 public String addEvent()
 {
	 try {
			 
     Event event = new Event();

     event.setSummary("Appointment");
     event.setLocation("Somewhere");

     ArrayList<EventAttendee> attendees = new ArrayList<EventAttendee>();
     attendees.add(new EventAttendee().setEmail("syed.faraz.ahmed.pk@gmail.com"));
     // ...
     event.setAttendees(attendees);

     Date startDate = new Date();
     Date endDate = new Date(startDate.getTime() + 3600000);
     DateTime start = new DateTime(startDate, TimeZone.getTimeZone("UTC"));
     event.setStart(new EventDateTime().setDateTime(start));
     DateTime end = new DateTime(endDate, TimeZone.getTimeZone("UTC"));
     event.setEnd(new EventDateTime().setDateTime(end));

     Event createdEvent;
		createdEvent = client.events().insert("primary", event).execute();
	
     return createdEvent.getId();
	 } catch (IOException e) {
return e.getMessage();
}

	 
 }
}
