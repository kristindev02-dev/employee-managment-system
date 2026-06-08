# Employee Management System - Simple Project Documentation

This document explains the full project in a simple way so a new developer can run and understand it quickly.

## 1. What This Project Is

This is a full-stack Employee Management System with:

- A Spring Boot backend (REST APIs + database)
- A plain HTML/CSS/JavaScript frontend (Bootstrap UI)

Main features:

- Manage Employees (create, list, update, delete)
- Manage Departments
- Manage Positions
- Dashboard with summary metrics

## 2. Project Structure

- `employee-management/` -> Spring Boot backend
- `Frontend/` -> Static frontend pages and JS logic
- `run-backend.ps1` -> Starts backend in dev mode (H2 in-memory DB)
- `run-frontend.ps1` -> Starts frontend at `http://localhost:5500`

## 3. Tech Stack

Backend:

- Java 23
- Spring Boot 4.0.6
- Spring Data JPA
- MySQL (default profile)
- H2 in-memory DB (dev profile)

Frontend:

- HTML + CSS + vanilla JavaScript
- Bootstrap 5
- Bootstrap Icons

## 4. Backend Details

Base URL:

- `http://localhost:8080`

### API Endpoints

Employees:

- `GET /employees` -> list all employees
- `GET /employees/{id}` -> get one employee
- `POST /employees` -> create employee
- `PUT /employees/{id}` -> update employee
- `DELETE /employees/{id}` -> delete employee

Departments:

- `GET /departments` -> list all departments
- `GET /departments/{id}` -> get one department
- `POST /departments` -> create department
- `PUT /departments/{id}` -> update department
- `DELETE /departments/{id}` -> delete department
- `DELETE /departments` -> delete all departments

Positions:

- `GET /positions` -> list all positions
- `GET /positions/{id}` -> get one position
- `POST /positions` -> create position
- `PUT /positions/{id}` -> update position
- `DELETE /positions/{id}` -> delete position
- `DELETE /positions` -> delete all positions

### Data Models

- Employee has: id, name, email, phoneNo, salary, joinedDate, department, position
- Department has: department_id, department_name, employees
- Position has: position_id, position_name

Relationships:

- Many employees belong to one department
- Many employees belong to one position

### Profiles and Databases

Default profile (`application.properties`):

- Uses MySQL `jdbc:mysql://localhost:3306/nkh`
- Username: `root`
- Password: `root`

Dev profile (`application-dev.properties`):

- Uses H2 in-memory DB
- Auto-creates schema each run
- Seeds sample data (20 employees + sample departments/positions)

CORS:

- Allowed for all origins/methods/headers (frontend can call backend directly)

## 5. Frontend Details

Frontend runs as static pages via Python HTTP server.

Main pages:

- `dashboard.html` -> overview metrics and recent hires
- `employee.html` -> employee CRUD + filters + pagination
- `department.html` -> department CRUD + search + pagination
- `position.html` -> position CRUD + search + pagination
- `sidebar.html` -> shared navigation layout

JS helper files:

- `js/layout.js` -> shared sidebar/offcanvas behavior
- `js/pagination.js` -> table pagination helper
- `js/validation.js` -> form validation helpers
- `js/flash.js` -> reusable toast notification utility

## 6. How To Run The Project

### Prerequisites

Install these first:

- Java 23 (required by `pom.xml`)
- Python 3 (for frontend static server)
- Internet connection (for Bootstrap CDN assets)

### Option A: Recommended (PowerShell scripts)

From project root:

1. Run backend:
   - `./run-backend.ps1`
2. Open another terminal and run frontend:
   - `./run-frontend.ps1`
3. Open browser:
   - `http://localhost:5500/dashboard.html`

### Option B: Manual start

Backend:

1. `cd employee-management`
2. `./mvnw.cmd spring-boot:run "-Dspring-boot.run.profiles=dev"`

Frontend:

1. `cd Frontend`
2. `python -m http.server 5500 --bind 127.0.0.1`

## 7. Typical User Flow

1. Open dashboard to see totals
2. Open Department page and create departments (if needed)
3. Open Position page and create positions (if needed)
4. Open Employee page and create employees
5. Use search/filter/pagination to manage records

## 8. Important Notes

- In dev mode, data is in-memory and resets after backend restart.
- Some delete actions are blocked in UI when related employees exist.
- Frontend currently has inline `showFlashMessage` in multiple pages and also has shared `js/flash.js`.

## 9. Quick API Test Examples

Create a department:

```bash
curl -X POST http://localhost:8080/departments \
  -H "Content-Type: application/json" \
  -d "{\"department_name\":\"Operations\"}"
```

Create a position:

```bash
curl -X POST http://localhost:8080/positions \
  -H "Content-Type: application/json" \
  -d "{\"position_name\":\"Analyst\"}"
```

Create an employee:

```bash
curl -X POST http://localhost:8080/employees \
  -H "Content-Type: application/json" \
  -d "{\"name\":\"John Doe\",\"email\":\"john@example.com\",\"phoneNo\":\"09123456789\",\"salary\":45000,\"joinedDate\":\"2026-06-08\",\"department\":{\"department_id\":1},\"position\":{\"position_id\":1}}"
```

## 10. Troubleshooting

Backend does not start:

- Check Java version (`java -version`) is 23
- Check if port 8080 is already used
- If using default profile, make sure MySQL is running and credentials are correct

Frontend cannot load data:

- Make sure backend is running at `http://localhost:8080`
- Open browser dev tools and check failed API calls
- Confirm CORS is enabled (it is configured in backend)

Styles/icons not loading:

- Check internet access because Bootstrap and icons are from CDN

## 11. Where To Extend Next

Good next improvements:

- Add centralized error handling on backend
- Add DTOs and validation annotations for request payloads
- Replace inline page scripts with separate JS modules
- Add unit/integration tests for controllers and services
- Add authentication and role-based access
