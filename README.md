Documentation

1. Configuration options and how to customize them.

To use the app, you need to enable the internet and grant permissions for camera usage and access to the gallery within the app.

2. Dependencies and any external libraries used.

I used all standard modern libraries:

- Hilt for dependency injection, as it's the go-to solution for streamlined DI in Android development.
- Retrofit and OkHttpClient for seamless communication with the backend, ensuring reliable and efficient network requests.
- Glide to handle image loading, offering smooth performance.
- Coroutines to manage concurrency, enabling responsive app behavior by simplifying asynchronous tasks.
- SharedPreferences for storing small amounts of local data, providing quick and easy access to persistent information.
- Paging Runtime to implement pagination, ensuring smooth and efficient data loading for larger datasets.
- Navigation Fragment with Single Activity architecture to streamline navigation between pages, creating a fluid and cohesive user experience.


3. Provide a brief overview of the code structure and important modules.

Since the project is relatively small, I didn’t break it into modules.
Additionally, the task didn’t require saving backend data to a database.
I believe this functionality should be added to the app.
My project structure is designed in a way that adding or modifying features can be done effortlessly, making the code flexible and easy to maintain.

Of course, the code can be further improved; this is not the final version. 
It can be made more flexible, divided into modules, and the functions for working with the gallery and camera can be moved to extension functions.
