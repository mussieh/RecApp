![alt text](https://github.com/mussieh/Recapp/blob/master/recapp_temporary_logo.png "Recapp Logo")
# Recapp
Recapp is an Android application designed to recommend well-reviewed resources including books, videos, and websites on selected subjects in Computer Science. The app is a work in progress and will be demonstrated in May of 2018. 
The goal of the application is to help students find excellent academic resources quickly and conveniently.

**Currently, the following have been implemented in the app:**

* Sample subject recommendation
* Extensive ConstraintLayout use for creating a responsive UI
* Maximal Fragment usage for better application modularity
* Full-text search through Algolia
* Firebase Realtime Database support
* Use of the Glide image loading library for fast and efficient image loading
* Personal list with draggable (dismissable) cardview items
* Sample subject settings
* Master/detail navigation flow for sample book data

**Working on:**

* Adding support for YoutubePlayerFragment to play Youtube videos inside the app
* Adding support for Chrome Custom tabs to display websites inside the app
* Hooking up the Fragments with real data
* Polishing the UI and performing Espresso tests

**Stretch goals:**

* Adding user accounts with authentication
* Adding more subjects
* More custom themes, icons and fluid animations

Sample Book Data Handset View   |  Sample Book Data Tablet View
:------------------------------:|:-------------------------:
![](https://github.com/mussieh/Recapp/blob/master/droid_handset.png)       |  ![](https://github.com/mussieh/Recapp/blob/master/droid_tablet.png)

**Note: In order for the app to run properly, make sure you include your own API keys in the required classes. In addition, know that
the development of this application has not yet been completed.**

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

What you need to install the program:

```
Android Studio (Latest version preferred)

```

### Running

In order to run the program, download the source code into a source folder and install Android studio,
if it is not already installed. Then, import this project from within Android Studio and press on the 'Run' button from
the toolbar.

### Further References

[Firebase Support Library](https://firebase.google.com/docs/android/setup)
[Algolia Android API Client](https://www.algolia.com/doc/api-client/android/getting-started) (optional if full-text search is not needed)


