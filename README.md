# Safe Heart Application

## Introduction

The Safe Heart application is designed to empower patients and clinicians with a comprehensive, user-friendly tool for recording and managing patient health information, specifically targeting cardiovascular health. By focusing on ease of use and clear presentation, Safe Heart aims to improve the communication of critical health data and risk assessments, ensuring that both patients and healthcare providers have the information they need to make informed decisions.

It is mainly developed using Java language.


## Key Features

### Patient Information Management
Safe Heart records and manages a wide range of patient information:
- **Personal Details**: Name, surname, and address.
- **Vital Signs**: Body weight, blood pressure, and temperature.
- **Laboratory Data**: Cholesterol levels (LDL and HDL).
- **Personal Characteristics**: Gender and age.
- **Social History**: Tobacco use and family history of heart attacks.
- **Core Characteristics**: Pregnancy status.

The data is retrived from [HAPI FHIR](https://hapifhir.io/) - the open source FHIR API for Java

### User Registration
- **Patients and Clinicians**: Both patients and clinicians register with their email addresses and create secure passwords.
- **Clinicians**: Clinicians can be associated with one or more hospitals and can work as general practitioners or specialists.

### Data Presentation
- **Patient Reports**: Registered patients can view their vitals, lab measurements, and the calculated Reynolds Risk Score, which predicts the 10-year risk of major cardiovascular events. Each result comes with a clear, patient-friendly explanation.
- **Clinician Notes and Recommendations**: Clinicians can add notes for each patient, viewable only by them, and provide dietary and exercise recommendations accessible to the patient.

### Risk Simulation
- **Reynolds Risk Score Simulation**: Patients and clinicians can simulate changes to the patient’s vitals or lab results to see potential improvements in the Reynolds Risk Score.

### Advanced Search and Statistics
- **Patient Search**: Clinicians can search for patients based on characteristics such as age, gender, and cholesterol levels.
- **Health Demographics**: Clinicians can view statistics on Reynolds Risk Scores, including means, averages, and distributions by neighborhood, providing insights into local health demographics.


# Instruction to Run
1. Build the Java project using Gradle.
2. Run the Java program.


# Application Implementation & Design Diagram
This application is built using Java language with help of Gradle. [JFreeChart](https://www.jfree.org/) has been utlized as Java chart library. The GUI component of this application is created using [Java Swing](https://docs.oracle.com/javase/tutorial/uiswing/).


The design implementation, including the UML, Sequence Diagram and Interaction Diagram are inside the folder [document](https://github.com/chailam/SafeHeart/tree/master/document).


# Design Rationale
This application  had implemented with some design patterns and design principles, included Model View controller (MVC), Observer, Dependency Inversion Principle (DIP), Abstract Server, Role principle and Open Closed Principle (OCP).


## Model View Controller (MVC)

We implemented the Model View Controller (MVC) pattern in our system as it is well-suited for database-driven applications. Our application fits this description, and thus MVC was a natural choice. By separating concerns, we ensured that the Model, View, and Controller components could be developed and tested independently. The Model manages the data and business logic, the View is responsible for presenting data to the user, and the Controller handles user input and updates the Model. This separation allows for ease of change and extensibility. The Model can support multiple Views, which can be added in the future without affecting the existing system.


## Observer Pattern

To adhere to the application specifications, which states that we should limit network traffic
by updating at most once per hour, and to manage the need for periodic updates without excessive network traffic, we implemented the Observer pattern. This design choice allows the system to update data stored every hour, rather than updating it immediately when a change occurs. This reduces network traffic significantly. Thus, the duty of our Observer is to get the refreshed/updated state of the subject every hour. By integrating the Observer pattern with MVC, we ensured that when the Model changes, the Views and Controller are automatically updated, maintaining consistency across the system.

## Role Principle

We applied the Role principle in the implementation of the Patient and Practitioner classes. This decision provides a flexible solution as it allows one user to have multiple roles, reflecting real-world scenarios where a Practitioner might also be a Patient. This approach enhances the extensibility of our system, making it more adaptable to various user requirements.

## Open Closed Principle (OCP)

Our system adheres to the Open Closed Principle by using abstraction, ensuring that classes are open for extension but closed for modification. For instance, we implemented abstract classes such as AbstractDataRetrieval and AbstractTableModel. These abstractions serve as hinge points in our design, allowing us to add new functionalities easily without altering existing code. This makes our system more robust and easier to maintain.

## Dependency Inversion Principle (DIP)

We followed the Dependency Inversion Principle by ensuring that high-level modules do not depend on low-level modules. Instead, both depend on abstractions. This principle is evident in our use of interfaces and abstract classes, which abstract away the concrete implementations. This approach minimizes the impact of changes in concrete classes on other parts of the system, enhancing maintainability and flexibility.

## Abstract Server

To accommodate potential future requirements for additional web services, we used an Abstract Server pattern. The AbstractDataRetrieval class serves as an interface in our server implementation. The controller depends on this server interface rather than a specific server implementation. This design allows the client to switch between different servers with similar properties without changing the controller, providing a flexible and extensible server architecture.

