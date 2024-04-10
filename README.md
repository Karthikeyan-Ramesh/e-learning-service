# e-learning-service
This eLearning application provides APIs to manage courses, modules, sections, students, users, user progress, and user enrollments.

eLearning Application
Overview
This eLearning application provides APIs to manage courses, modules, sections, students, users, user progress, and user enrollments.

CourseController
GET /api/courses: Fetch all courses.
GET /api/courses/{courseId}: Fetch a course by ID.
POST /api/courses: Create a new course.
POST /api/courses/{courseId}/modules: Create a new module for a specific course.
DELETE /api/courses/{id}: Delete a course by ID.


ModuleController
GET /api/modules: Fetch all modules.
GET /api/modules/{id}: Fetch a module by ID.
POST /api/modules: Create a new module.
DELETE /api/modules/{id}: Delete a module by ID.


SectionController
GET /api/sections: Fetch all sections.
GET /api/sections/{id}: Fetch a section by ID.
POST /api/sections: Create a new section.
DELETE /api/sections/{id}: Delete a section by ID.


StudentController
GET /api/students: Fetch all students.
POST /api/students: Create a new student.
POST /api/students/enrollments: Enroll a student in a course.
POST /api/students/quizzes: Submit quizzes by a student.
POST /api/students/assignments: Submit assignments by a student.
GET /api/students/{studentId}/progress: Read progress of a student.


UserController
GET /api/users: Fetch all users.
GET /api/users/{id}: Fetch a user by ID.
POST /api/users: Create a new user.
DELETE /api/users/{id}: Delete a user by ID.


UserProgressController
GET /api/user-progress: Fetch all user progress records.
GET /api/user-progress/{id}: Fetch a user progress record by ID.
POST /api/user-progress: Create a new user progress record.
DELETE /api/user-progress/{id}: Delete a user progress record by ID.


Dependencies
Spring Boot
Spring Data JPA
Hibernate
Lombok
H2 Database (for testing)
Oracle Database (for production)
