# Secure-Messenger

A graduation project designed to create a highly secure messaging application with innovative features for protecting user privacy and detecting intrusions. Developed in Java, the app leverages Firebase Realtime Database and Room Database to ensure data security and offline functionality.

## Overview
This app was intended to provide users with a secure communication platform without compromising their privacy. It focused on anonymous account creation, secure message storage, and advanced behavior analysis for intrusion detection. While some features were not fully implemented due to the need for advanced AI models, the project demonstrates a strong foundation in secure app development.

## Key Features
- **Anonymous Account Creation**:
  - Users can create accounts with randomly generated IDs, without providing personal information.
  - Option to delete accounts at any time.
- **Secure Messaging**:
  - Messages are primarily stored in users' offline Room Databases.
  - Once a message is opened, it is deleted from the server and remains only on the sender's and receiver's devices.
- **Usage Pattern Detection**:
  - Users can define app usage patterns.
  - The app detects unusual patterns and prompts for a password or closes for security.
- **Typing Behavior Analysis**:
  - Tracks typing behavior to detect potential intruders based on anomalies.
- **Offline Support**:
  - Room Database ensures messages are accessible offline.

## Technical Details
- **Language**: Java
- **Databases**:
  - Firebase Realtime Database for real-time updates and message exchange.
  - Room Database for offline message storage and data security.
- **Security Features**:
  - Pattern-based app usage detection.
  - Typing behavior analysis for intrusion detection.

## Notes
This project showcases a deep understanding of secure messaging and database management. While incomplete due to the need for advanced AI models, it serves as a strong example of innovative Android development and a focus on user privacy.



![imgonline-com-ua-twotoone-Aml9ZdzJ2zAqjwf](https://user-images.githubusercontent.com/128988435/227807964-24433471-c30d-4219-85ce-17a921eda0b4.jpg)
