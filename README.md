**Timekeeping & Payroll Management System**
A Spring Boot-based web application designed to manage employee timekeeping and payroll computation.
This project aims to simulate a real-world HR system with role-based authentication, attendance tracking, and automated payroll calculation.

**Tech Stack**
• Java 17 (LTS)
• Spring Boot 3.x
• Spring Security
• Spring Data JPA (Hibernate)
• MySQL 8
• Maven
• IntelliJ IDEA (Community Edition)

**Features (Current Progress)**
✅ Authentication & Authorization
• Database-backed login
• BCrypt password encryption
• Role-based access control:
  • ROLE_HR
  • ROLE_EMPLOYEE

✅ Attendance Module (Sprint 2 – In Progress)
• One attendance record per employee per day
• Time In functionality
• Time Out functionality
• Prevent duplicate Time In
• Prevent duplicate Time Out
• Daily status checking endpoint

**Planned Features**
• Attendance correction request
• Work hour calculation
• Overtime rules
• Late/undertime computation
• Payroll generation
• Payslip PDF export
• Dashboard UI (Light Green theme)

**Database Schema (Current)**
users
• id
• username
• password
• enabled

roles
• id
• name

users_roles
• user_id
• role_id

attendance
• id
• user_id
• work_date
• time_in
• time_out
• timeInLocked
• timeOutLocked

**Setup Instructions**
1️⃣ Clone repository
git clone https://github.com/yourusername/timekeeping-payroll-system.git
cd timekeeping-payroll

2️⃣ Configure MySQL
Create database:
CREATE DATABASE timekeeping_payroll;

Update application.properties:
spring.datasource.url=jdbc:mysql://localhost:3306/timekeeping_payroll
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD

3️⃣ Run application
mvn spring-boot:run

Application runs at:
http://localhost:8080

**Default Test Account**
HR User:
• Username: hr
• Password: Hr@12345
(Employee account will be added in next sprint.)

**Project Roadmap**
This project follows a flexible Agile approach with 2-week sprints.
• Sprint 1: Authentication & Security Setup ✅
• Sprint 2: Attendance Tracking (In Progress)
• Sprint 3: Payroll Computation
• Sprint 4: Reporting & Deployment

**Goal**
To build a production-ready HR timekeeping and payroll system that demonstrates:
• Backend architecture design
• Security implementation
• Business logic handling
• Database modeling
• Clean code practices

**Future Improvements**
• JWT-based authentication
• REST API documentation (Swagger)
• UI implementation (Light Green theme)
• Docker containerization
• Cloud deployment
