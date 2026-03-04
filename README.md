# Habit Tracker

Welcome to my Habit Tracker app. 

I built this Android application to help people build good habits and actually stick to them. While building this, my main goal was to focus on modern Android development practices and write clean, maintainable code.

## Tech Stack
I wanted to keep the app robust and up-to-date with current Android standards, so here are the core technologies I used:
* **Language:** Kotlin
* **Architecture:** MVI (Model-View-Intent) along with the Repository Pattern
* **Dependency Injection:** Dagger Hilt
* **Local Database:** Room
* **Async & Reactive:** Coroutines & Flow

## What the App Does
* Lets you easily add, edit, or delete your daily habits.
* You can check off habits as you complete them throughout the day.
* It works completely offline.

## A Bit About the Architecture
I decided to go strictly with the **MVI architecture** for this project. It really helped me keep the data flow predictable and made managing the UI states much easier. 
For the data layer, I used **Room Database** so everything is saved locally on the device. To tie everything together without writing a ton of boilerplate code, I implemented **Hilt** for dependency injection. 
