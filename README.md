💬 ChatRoom App (AI-Powered)

ChatRoom App is a cutting-edge, real-time messaging application that integrates Generative AI to enhance user interaction. Built with a modern Android stack (Jetpack Compose) and a robust Python Flask backend, it features seamless voice input and intelligent AI responses.

✨ Key Features

🤖 AI-Powered Chat: Integrated Gemini API for intelligent, context-aware responses with prompt orchestration.

🎙️ Voice-to-Text: Built-in Speech-to-Text capabilities allowing users to chat hands-free.

⚡ Real-Time Sync: Instant message delivery and persistence using Firebase Firestore and Realtime DB.

🔐 Secure Authentication: Robust user login and registration flows via Firebase Auth.

🚀 High Performance:

<400ms average response latency for AI queries (hosted on Render).

60% reduced UI latency utilizing StateFlow and optimized Recomposition.

🛠️ Tech Stack

Android (Frontend)

Language: Kotlin

UI Framework: Jetpack Compose (Material 3)

Architecture: MVVM (Model-View-ViewModel)

State Management: StateFlow, LiveData, ViewModel

Speech: Android SpeechRecognizer

Backend & Cloud

API Service: Python Flask (deployed on Render)

AI Model: Gemini API

Database & Auth: Firebase Firestore, Firebase Authentication

Networking: Retrofit, Coroutines

📱 Getting Started

Follow these steps to set up the project locally for development.

Prerequisites

Android Studio (Koala or newer recommended)

JDK 17 or higher

Firebase Project: You will need a google-services.json file.

Gemini API Key: Required for AI features.

Installation

Clone the Repository

git clone [https://github.com/TusharGarg12/ChatRoomApp.git](https://github.com/TusharGarg12/ChatRoomApp.git)


Firebase Setup

Go to the Firebase Console.

Create a new project and enable Authentication (Email/Password) and Firestore.

Download the google-services.json file and place it in the app/ directory.

API Configuration

Open local.properties (or your secrets configuration file).

Add your Gemini API key:

GEMINI_API_KEY=your_api_key_here


Build and Run

Open the project in Android Studio.

Sync Gradle files.

Select your emulator or physical device and click Run.

🔮 Future Improvements

[ ] Multi-modal support (Image uploads to AI).

[ ] Group chat partitioning.

[ ] Enhanced offline caching with Room DB.

🤝 Contributing

Contributions are welcome! Please fork the repository and submit a pull request for any enhancements.

📞 Contact

Tushar Garg

LinkedIn

GitHub
