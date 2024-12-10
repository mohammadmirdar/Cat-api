
# Cat API App 🐾

A feature-rich Android application that showcases cats' details using **The Cat API**. The app demonstrates modern Android development practices, utilizing **Clean Architecture**, **MVI (Model-View-Intent)**, **Hilt for Dependency Injection**, **Retrofit for API communication**, **Jetpack Compose for UI**, and **MockWebServer** for testing.

---

## Features  
- Fetch and display a list of cat images.  
- View detailed information about each cat, including its breed, temperament, and more.  
- Mark cats as favorites and persist these selections locally.  
- Offline-first approach: Data is stored locally to improve stability and performance.  
- Built with modern development practices for scalability and maintainability.  

---

## Architecture  
The project uses **Clean Architecture** to ensure separation of concerns and scalability.  

### Layers  
1. **Data Layer**  
   - Handles API communication using `Retrofit`.  
   - Provides local storage using a database (e.g., `Room` or custom logic).  
2. **Domain Layer**  
   - Contains use cases such as `GetImageDetail`, `SetImageFavourite`, and `GetImageList`.  
   - Encapsulates business logic and interacts with repositories.  
3. **Presentation Layer**  
   - UI is built with **Jetpack Compose**.  
   - Uses **MVI architecture** to manage state changes and intents effectively.  

---

## Screenshots  
![App UI Design](design.png)  
*Cat Detail Screen - Example of UI design*

---

## Prerequisites  
1. **Android Studio Giraffe or later**  
2. **Kotlin 1.9.0 or later**  
3. **Gradle 8.0 or later**  

---

## Getting Started  

### 1. Clone the Repository  
```bash  
git clone https://github.com/mohammadmirdar/Cat-api.git  
cd Cat-api  
```  

### 2. API Key Setup  
This project uses **The Cat API**. Add your API key to the project:  

- **Option 1 (Keystore Method)**:  
  Store the API key in the Android keystore for enhanced security. Ensure it is injected using Hilt during initialization.  

- **Option 2 (BuildConfig Method)**:  
  Add the following to your `local.properties`:  
  ```properties  
  CAT_API_KEY=your_api_key_here  
  ```  

  Access it in the app:  
  ```kotlin  
  BuildConfig.CAT_API_KEY  
  ```  

### 3. Run the App  
1. Open the project in Android Studio.  
2. Sync Gradle to ensure dependencies are resolved.  
3. Run the app on an emulator or physical device.  

---

## Key Classes and Components  

### Data Layer  
- **`RemoteRepositoryImpl`**: Implements API calls for fetching cat images and details.  
- **`CatApiService`**: Retrofit service defining API endpoints.  

### Domain Layer  
- **`GetImageList`**: Fetches cat images from the server or local database.  
- **`SetImageFavourite`**: Updates the favorite status of a cat image.  

### Presentation Layer  
- **Jetpack Compose Screens**:  
  - `ImageListScreen` for displaying the list of cat images.  
  - `ImageDetailScreen` for viewing detailed information about a cat.  

- **MVI Components**:  
  - `ImageListState` manages the list's state, such as loading, errors, and data.  
  - Reducer functions update state in the `ViewModel`.  

---

## Testing  

### Unit Tests  
- Ensures use cases and repositories behave as expected.  
- Example test: `GetImageListTest.kt`.  

### Integration Tests  
- Validates API responses using **MockWebServer**.  

Example:  
```kotlin  
mockWebServer.enqueue(
    MockResponse()
        .setResponseCode(200)
        .setBody("[{ "id": "cat1", "url": "https://cdn2.thecatapi.com/images/cat1.jpg" }]")
)
```  

### UI Tests  
- Verifies Compose UI elements using **Jetpack Compose Testing Library**.  

---

## Dependencies  

- **Jetpack Compose**: Modern UI toolkit.  
- **Hilt**: Dependency injection.  
- **Retrofit**: Networking.  
- **MockWebServer**: For testing API calls.  
- **Kotlin Coroutines**: Asynchronous programming.  

---

## License  
This project is licensed under the MIT License. See the `LICENSE` file for details.  

---

## Author  
Developed with ❤️ by Mohammad Mirdar.  

Feel free to contribute or raise issues to improve this project!  
