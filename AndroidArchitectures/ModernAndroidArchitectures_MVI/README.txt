IN this Project, we have implemented MVI architecture. 

Model-View-Intent (MVI) is a reactive architectural pattern for Android development that emphasizes unidirectional data flow and a single source of truth for UI state. It is particularly popular for modern applications using Jetpack Compose because it makes UI state predictable and easier to test. 


Core Components
MVI consists of three main parts that form a continuous cycle: 

Intent: Represents a user's intention to perform an action (e.g., clicking a "Login" button or swiping to refresh). In Android, these are often defined as a sealed class or sealed interface of events.

Model: Represents the State of the UI at any given point in time. Unlike other patterns where models are just data objects, MVI models are usually single, immutable data structures (e.g., Loading, Success(data), or Error).

View: The UI layer (Activity, Fragment, or Composable) that observes the Model and renders the current state. It is also responsible for capturing user actions and sending them as Intents. 


How the MVI Cycle Works

User Action: The user interacts with the View.

Intent Sent: The View emits an Intent (e.g., AddNoteIntent) to the ViewModel.

State Transformation: The business logic (often inside a Reducer) takes the current state and the intent to calculate a new state.
UI Update: The View observes this new immutable state and re-renders the UI to match it. 

Reference taken from : 
https://www.snow.dog/blog/jetpackcompose-mvi-architecture 
https://github.com/SnowdogApps/compose-mvi-architecture 
