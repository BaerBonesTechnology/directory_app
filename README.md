## Build tools & versions used
Kotlin - 1.4.32
Gradle - 8.0
Android Studio - Flamingo 2022.2.1
Jetpack Compose - 1.4.3

Dependencies
Hilt - Dependency Injection - 2.44.2
Retrofit - Network calls - 2.9.0
Gson - JSON parsing - 2.9.0
Coroutines - Asynchronous programming - 1.5.2
Landscapist Glide - Image loading - 1.3.6

Testing
JUnit - Unit testing - 4.13.2

## Steps to run the app
Open file in Android Studio and run the app on an emulator or android device.

## What areas of the app did you focus on?
Architecture - MVVM
UI - Figma and Jetpack Compose

## What was the reason for your focus? What problems were you trying to solve?
I wanted to show my knowledge of modern architecture and UI development. The image placeholder and empty list state icons are custom created in Figma.

## How long did you spend on this project?
About 5 hours of actual dev time, a little more for research, design, and documentation.

## Did you make any trade-offs for this project? What would you have done differently with more time?
I would expand use, starting with adding intents to the phone numbers and emails to allow the user to call or email the contact. I would also add a detail screen to show more information about the contact.

## What do you think is the weakest part of your project?
The UI is very basic, but I wanted to show my knowledge of Jetpack Compose and modern UI development, however it would have taken more time to create a more complex UI.

## Did you copy any code or dependencies? Please make sure to attribute them here!
The refreshing of the list was based on this article: https://medium.com/google-developer-experts/effortlessly-add-pull-to-refresh-to-your-android-app-with-jetpack-compose-4c8b218a9beb
It, admittedly gave me more trouble than I expected, but I was able to get it working. Because I was adamant about using proper MVVM architecture I referenced this documentation for help with [setting up hilt:](https://developer.android.com/training/dependency-injection/hilt-jetpack) as well as some StackOverflow posts:
[Issue I was having with viewModels](https://stackoverflow.com/questions/62471849/cannot-create-instance-of-viewmodel-after-using-hilt-in-android)
[duplicate class issue when introducing Hilt]( https://stackoverflow.com/questions/75263047/duplicate-class-in-kotlin-android)
)

## Is there any other information you’d like us to know?
Not at the moment. I'm excited to hear your feedback!