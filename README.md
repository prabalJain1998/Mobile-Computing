# Mobile-Computing
Solutions to Android Coding Assignment ( In java ) 

## Assignment - 1 : Covid Precautions App.
Create COVID-19 Precautions Android app with the first activity having a form containing TextViews and EditText boxes asking user to enter his/her Name, and checkboxes showing 5 pre-cutionary measures against the virus spread. They are 1) Wearing a mask when outside 2) Washing hands frequently, 3) Maintaining 2 feet distnce 4) Covering nose and mouth while sneezing and coughing 5) Taking health diets. The user should be able select the precautions he is taking. It should will also contain Submit and Clear buttons, with Submit Button it will show the users the entered information in another activity. In that activity, there will be be a button to check whether the user should be safe if he follows all 5 precautions. This state should be returned to the first activity with remebering the details that was selected earlier and showcase if the user is safe. Now, with Clear Button it will allow the user to clear the form in the first activity. Make sure that the app is robust across rotation.

For each change in the state of the Activities, it should LOG in type INFO and provide a Toast showing the change in the state. For example, if the state of first activity the changes from Resumed to Paused, the log should contain INFO statement saying “State of activity <name_of_the_activity> changed from Resumed to Paused”, similarly for the Toast. The app should LOG and provide Toast for all possible state transitions for each activity.

## Assignment - 2 : Music Player
Develop a music player app
The app will have control to
(1) to start a foreground service to start playing music (store some music files in appropriate
resource directory)
(2) stop playing the music
(3) start another activity that checks if the Internet connection is available, if so it talks a music player
server and downloads the music files and save in appropriate files (we have hosted a sample audio
file at http://faculty.iiitd.ac.in/~mukulika/s1.mp3 , download and save this audio file) that are private to
the app
(4) It will have broadcast receivers for the following actions BATTERY_LOW,
POWER_DISCONNECTED, BATTERY_OKAY
Design the UI of the app using a fragment.
In the demo, we will evaluate your assignment on the following aspects:
(1) start the service to play a song: 2 marks
(2) stop the service: 2 marks
(3) check for network and download song: 3 marks (1 mark for checking network availability, 1 mark
for connecting to the webserver, 1 mark for downloading)
(4) saving the downloaded song into appropriate file: 1 mark
(5) correctly implement all broadcast receivers: 2 marks
(6) UI design using fragments: 2 marks
(7) viva (all related concepts): 2 marks
(8) app functions properly (doesn't crash): 1 mark
Bonus mark: If your app is able to play the downloaded file (1 mark)

## Assignment - 3 : Student Application
Develop MC-Student Details app
The app will have control to:
1. Use fragment to show the list of students who have registered in MC course (use
RecyclerView to show the list of students)
2. The list should contain the name and roll number of students.
3. On clicking a row one can show the details of that student e.g. Name, department, and email
id. Also there should be an option to change the details in that fragment.
4. The list and the details both should use fragments.
5. Populate the list with 30 students.

In the demo, we will evaluate your assignment on the following aspects:
1. List of students who have registered for the course(RecyclerView) (3 marks)
2. Navigation from one fragment to another on clicking the roll number (3 marks)
3. Able to change the student detail (changes details should get reflected in the fragment)
(3 marks)
4. UI design using fragments. (2 marks)
5. Viva (all related concepts) (2 marks)
6. App functions properly (doesn’t crash) (2 marks)

## Assignment - 4 : Play with Sensors
● Collect sensor data from the following sensors: a) accelerometer, b) linear-acceleration,
c) temperature, d) light, e) GPS, and f) proximity.
● Collect these data and store into a database using Room. Use separate tables for each
sensor. The UI has a toggle switch for each of the sensors to start and stop collecting
data any number of times.
● The UI also has provisions (buttons) to get the past 1 hour average value of the
accelerometer (for all three axis separately) and temperature sensor.
● If the user wants to see the average values, it should be showcased as part of the UI
using a text view.
● Now using only the accelerometer sensor data identify if the device is stationary or in
motion. Hint: look for patterns in the accelerometer data to determine whether stationary/
in motion.
Rubric:
1. Access to individual sensors i.e. ability to read/write data from the individual sensor is:
Accelerometer (0.5 mark)
Linear Acceleration (0.5 mark)
Temperature (0.5 mark)
Light (0.5 mark)
GPS (0.5 marks)
Proximity (0.5 mark)
2. Store data in database: 3 marks
3. Show the average value for accelerometer (2) and for temperature (1)
4. Motion detection: 3 marks

## Assignment - 5 : Indoor Localization using WIFi
● Implement an indoor localization app that will provide room level accuracy.
● Get the WiFi scan results to know the list of access points nearby and their RSSI values
(Hint: https://developer.android.com/reference/android/net/wifi/WifiManager
https://developer.android.com/reference/android/net/wifi/WifiInfo#getRssi()
https://developer.android.com/reference/android/net/wifi/ScanResult
).
● Showcase the list of APs that you can hear with their RSSI values (think of a nice UI) to
show this. For example using bars as can be seen from this Fig. You can think of other
alternatives as well.

● Wardive inside your home and get RSSI measurements of the APs from different rooms
of your home using WiFi scan results. Store this information. Design this wardriving
using appropriate functionality in the UI. For example, you have one UI control like
button that says now start your wardriving.
● Given a test RSSI measurements of these APs, return your location. Think of a nice UI
to develop this. For example, again having one UI control says a button that lets you
test. During testing it will get RSSI of the APs nearby by getting the WiFi scan results.
Then, it will match it to stored information with a single point that is most similar to the
test data.
● Optional1: implementing the matching with KNN
● Optional2: remove wardriving by utilizing IMU sensors
Rubric:
1. Get the list of WiFi APs: 2
2. Showcasing this appropriate UI: 3 marks
3. Collect training data: UI+ getting the scan data with RSSI+ saving: 5 marks
4. Testing of localization app: UI+ returning the correct location 5
5. Bonus marks optional 1: 3
6. Bonus marks optional 2: 3
Note: You are free to design the UI as per your choice. You are encouraged to design a clean
and pleasing UI.
