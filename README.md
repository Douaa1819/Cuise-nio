# Cuisénio - Culinary Management Platform 🍰🍽️

## Project Overview
Cuisénio is a web platform built with **Spring Boot (REST API)** for the backend and **Angular (modular architecture)** for the frontend. It simplifies recipe management, meal planning, and ingredient organization while fostering a community-driven environment for culinary enthusiasts.

## Features
### User Features
- **User Authentication**: Register, login, update profile, and reset password.
- **Meal Planning**: Schedule meals based on dietary preferences.
- **Recipe Management**: Create, edit, and search recipes with advanced filtering.
- **Favorites & Ratings**: Save recipes and rate them based on experience.
- **Community Interaction**: Comment on and share recipes.
- **Email Notifications**: Receive updates on new recipes and blog posts.
- **Mobile-Friendly UI**: Fully responsive design for all devices.

### Admin Features
- **User Management**: Admins can moderate users and content.
- **Recipe Categorization**: Manage recipe categories and ingredients.
- **Analytics & Reports**: View platform usage statistics.

## Backend Architecture (Spring Boot)
```
backend/
├── auth/        # Authentication & Authorization
├── recipe/      # Recipe Management (includes ingredients, categories, images)
├── mealplan/    # Meal Planning
├── common/      # Shared utilities and configurations
```

## Frontend Architecture (Angular)
```
frontend/
├── core/        # Services, guards, interceptors
├── features/
│   ├── auth/    # Login, register, profile
│   ├── recipe/  # Recipe list, details, creation
│   ├── mealplan/ # Meal planning UI
│   ├── shared/  # Reusable components, pipes, directives
```

## Installation & Setup
### Backend (Spring Boot)
1. Clone the repository:
   ```sh
   git clone https://github.com/Douaa1819/Cuise-nio.git
   cd cuisenio/backend
   ```
2. Configure the database in `application.yaml`:
3. Run the backend application:
   ```sh
   mvn spring-boot:run
   ```

### Frontend (Angular)
1. Navigate to the frontend directory:
   ```sh
   cd ../frontend
   ```
2. Install dependencies:
   ```sh
   npm install
   ```
3. Run the Angular application:
   ```sh
   ng serve
   ```

## API Documentation
The backend exposes a REST API documented with **Swagger**.
- Access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## Technologies Used
- **Backend**: Spring Boot, Spring Security, JPA, MapStruct, JWT, PostgreSQL
- **Frontend**: Angular, NgRx (state management), Bootstrap/Tailwind
- **Tools**: Docker, Postman, GitHub Actions (CI/CD)



## License
This project is licensed under the MIT License.

## Contact
For any inquiries, feel free to contact **[Douaa1819]** at **douaachemnane@gmail.com**.
