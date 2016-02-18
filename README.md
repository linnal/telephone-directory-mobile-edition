# Telephone directory Mobile Edition
You are to build a mobile application that provides a telephone directory.

## Entries
The application handles a set of entries, that contain a first name, last name, and a telephone number.

The entries should be validated, so that it's not possible to enter an empty first or last name; and the phone number should be of the form
```
+39 02 1234567
```

That is a "+" followed by a nonempty group of digits, a space, a nonempty group of digits, a space, a group of digits with at least 6 digits.

The application consists of the following pages:
* **Home page**
  * Contains a text field that allows to search through all the entries by name or number. When I enter text in the field, the page will be reloaded with a table containing all the entries that match the text I entered.
  * The page contains a link to the "add new entry" page.
  * When an entry is displayed, it contains a link to the "edit this entry" page.
* **Add new entry page**
  * Contains a form for entering a new entry.
  * Contains an "import from contacts" button to import a single contact from the existing contacts list of the smartphone
* **Edit entry page**
  * Contains a form for modifying an existing entry.

### Extra activities (not mandatory)
* Deploy the application in a Mobile App Distribution Platform (e.g. TestFlight, HockeyApp, Beta (Fabric), etc.)
* Compatibility with Tablet devices
* Screen rotation handling


## How to compile and run it

From terminal

- Compile

     `./gradlew assembleDebug`
    
- Run on device/emulator

    `./gradlew installDebug`
    
    
or Import in Android Studio and press run


## Technologies used

[Butter Knife](http://jakewharton.github.io/butterknife/)  Field and method binding for Android views

[Realm](https://realm.io/docs/java/latest/) enables you to efficiently write your app’s model layer in a safe, persisted and fast way. 

[Realm SearchView](https://github.com/thorbenprimke/realm-searchview) A simple and powerful EditText and RecyclerView UI component. It's build on top of RealmRecyclerView and RealmBasedRecyclerViewAdapter



=========
## License

    Copyright 2016 Erinda Jaupaj

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
