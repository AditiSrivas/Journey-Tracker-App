# Journey-Tracker-App

##Overview
The Journey Tracker app is designed to help users track their flight journey, including multiple stops, transit visa requirements, distances, travel time, and overall journey progress. The app has two implementations:
  Jetpack Compose Version (uses declarative UI with Composables)
  XML + Kotlin Version (uses traditional View-based UI)
  
###Both versions provide functionality to:
  Display a list of journey stops.
  Show distance and time left between stops.
  Indicate whether a visa is required at each stop.
  Track journey progress with a progress bar.
  Convert distances between kilometers and miles.
  Mark stops as visited upon user action.
  
###Features Implemented
  Dynamic Stop List: Displays stops using a lazy list (Compose) or ListView (XML).
  Progress Tracking: A progress bar visually represents journey completion.
  Distance Conversion: Toggle button switches between kilometers and miles.
  Next Stop Feature: Users can progress to the next stop by tapping a button.
  Resource File Integration: Reads stops from predefined data structures.

##Jetpack Compose Version
###Implementation Details
####Composable Functions:
  MainScreen() handles UI layout, buttons, and progress tracking.
  JourneyList() uses LazyColumn to list stops dynamically.
  JourneyItem() displays stop details and highlights visited stops.
  State Management: Uses remember to track the current stop and unit conversion.
  Progress Bar: Updates dynamically as the user moves to the next stop.
  Conditional Styling: Visited stops are highlighted in light yellow (#FFFBB5).
  Unit Conversion: Distance is converted using 0.621371 factor for miles.

####Files
MainActivity.kt (Entry point and Compose UI setup)

##XML + Kotlin Version
###Implementation Details
####XML Layouts:
  activity_main.xml: Contains ListView, ProgressBar, and buttons.
  list_item_stop.xml: Defines layout for individual stops in the list.
####Adapter Pattern:
StopAdapter.kt is used to populate the ListView with journey stop data.
####Progress Tracking:
MainActivity.kt updates the progress bar and text dynamically.
####Highlighting Visited Stops:
Visited stops have a different background color (#FFFBB5).
####Button Actions:
Next Stop button advances the journey.
Distance conversion toggles between km and miles.
###Files
MainActivity.kt (Handles UI logic, progress tracking)
StopAdapter.kt (Custom adapter for ListView)
list_item_stop.xml (List item layout)
activity_main.xml (Main UI layout)

##GitHub Repository & Submission Details
Both versions are maintained in separate branches:
compose_version for Jetpack Compose
xml_version for XML + Kotlin

##Conclusion
The Journey Tracker app successfully implements all required features using both UI paradigms. The Compose version leverages modern declarative UI practices, while the XML version follows traditional imperative UI development. Both approaches ensure a seamless user experience for tracking flight journeys efficiently.
