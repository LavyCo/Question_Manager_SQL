
# Question Manager SQL

This project is an extension of the original **Exam Manager - Question Wizard and Exam Generator** application. It has been enhanced and refactored to utilize SQL for better data management and efficiency.

## Project Overview

The original project focused on creating an innovative Exam Manager application using Java and Object-Oriented Programming (OOP) principles. The application was designed to optimize the exam generation process through the use of advanced data structures, resulting in improved performance and scalability.

In this extended version, all user data has been transitioned to SQL, leveraging MySQL to create more efficient database tables. This shift was done to enhance data management, making the system more scalable and reliable.

## Key Features

- **Exam Generation:** The application efficiently generates exams by pulling questions from a structured database.
- **OOP Principles:** The core application is built using Object-Oriented Programming, ensuring a modular and maintainable codebase.
- **SQL Integration:** All user and exam data are stored in MySQL tables, enabling more efficient data querying and management.
- **Data Structures:** Utilized advanced data structures to optimize the performance of exam generation.
  
## Development Details

- **Original Development:** Java, OOP principles
- **Current Version:** Java with SQL integration
- **Database:** MySQL

## How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/LavyCo/Question_Manager_SQL.git
   ```
2. Import the project into your preferred Java IDE.
3. Ensure that MySQL is installed and running on your system.
4. Set up the MySQL database:
   - Create a new database for the project.
   - Run the SQL scripts provided in the `database/` directory to create the necessary tables.
5. Configure the database connection in the application:
   - Update the database connection settings in the `src/main/resources/db.properties` file with your MySQL credentials.
6. Build and run the application.

