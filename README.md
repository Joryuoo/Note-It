# NoteIt - Android Application

## Project Context

Developed as the final project for **CSIT284 - Platform Based Development (Mobile)**.

## Download

You can download the compiled APK for installation here:
[Download NoteIt APK](https://drive.google.com/drive/folders/1zmZpCQJWjJcpj_i9i58Xp0SBab2nY4f7?usp=sharing)

## App Description

NoteIt is an Android application designed for users to capture, organize, and manage their thoughts, ideas, and tasks efficiently directly on their device. It provides integrated functionalities for note-taking and task management. The app **supports multiple user accounts**, with each user's data kept separate and stored locally. It offers a reliable, offline-first experience for users to keep track of important information and to-dos.

## Features

NoteIt includes the following features:

* **Note Management:**
    * Create new notes.
    * View and edit existing notes.
    * Display notes efficiently using a **`RecyclerView`**.
    * Organize notes per user.
* **Task / To-Do List Management:**
    * Create new tasks or to-do lists.
    * View and manage tasks.
    * Display tasks efficiently using a **`RecyclerView`** with checkboxes.
* **Multi-User Authentication & Profiles (Local):**
    * Supports **multiple user accounts** on a single device.
    * User sign-up for new accounts.
    * User sign-in to existing accounts.
    * User profile management for the logged-in user.
    * Logout functionality to switch users or secure the app.
    * *Note: Each user's data is stored separately and locally on the device.*
* **Archiving:**
    * Archive notes (per user).
    * Archive tasks (per user).
    * Visual indicator for archiving.
* **Data Handling (Local Storage):**
    * Uses standard Kotlin **classes** (NoteModel, TodoListModel, UserModel) to structure the data.
    * Saves all data (notes, tasks, user information) for **all users** locally to the **app's internal storage**.
    * The `UserManager` handles the separation and management of data for different user accounts within the local storage.
    * Utilizes **Serialization (with the GSON library)** to convert data objects (to JSON format) for persistent storage on the device, enabling full offline functionality.

## UI and UX (User Interface and User Experience)

The UI/UX is structured around standard Android patterns with specific flows:

* **Initial Screens:** The app presents a Landing Page with a "Get Started" option. If an active user session for the *last logged-in user* is detected, the app automatically directs them to the Homepage with their saved session data loaded, bypassing the Sign-Up and Sign-In screens. Otherwise, users can Sign-Up for a new account or Sign-In to an existing one.
* **Main Interface:** The central Homepage serves as the main hub, displaying content for the currently logged-in user. Navigation is handled through **three distinct Fragments**, allowing users to switch between different sections (e.g., notes, tasks, archive/settings) for their account. Users can also access their Profile screen.
* **Adding Content:** Creating new notes or tasks **starts a new activity** presenting a user-friendly and visually appealing interface for content input, associated with the current user.
* **Visual Design:** The app utilizes custom drawables, shapes, button styles, edit text styles, and themes/colors to create a tailored visual appearance. Custom dialogs are used for confirmations or alerts.
* **Navigation:** Standard Android navigation components provide access to Profile, Archive sections, and Logout (essential for multi-user switching). Informational screens for "About App" and "About Developer" are also accessible.

## Innovation

Key aspects of the app include:

* **Multi-User Local Storage:** Allows multiple distinct user accounts and their associated data to exist within a single app installation on one device, all managed locally without requiring cloud services.
* **Offline-First Design:** By storing all user data locally, the app provides a robust offline experience for every user.
* **Combined Note/Task Management:** Integrating both notes and tasks within a single interface streamlines productivity.
* **Data Persistence with GSON:** Leveraging GSON for serialization ensures efficient and reliable saving and loading of different users' data within the app's storage.
* **Session Management:** Smart handling of the last user's session allows returning users quick access to their specific homepage.
* **Efficient UI:** Uses `RecyclerView` for smooth scrolling and performance when displaying lists of notes and tasks for each user.

## Credits

* Illustrations by **Ivan Haidutski**.
