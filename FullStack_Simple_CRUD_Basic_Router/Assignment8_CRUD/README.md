# Simple Product and Student CRUD Application

Basic full-stack CRUD using Vue 3, Vue Router, Axios, Spring Boot and MongoDB.

## Collections and fields
- **products**: `name`, `price`, `quantity`
- **students**: `name`, `age`, `course`

## Backend endpoints
Products use `/api/products`; students use `/api/students`. Both provide:
- `POST /api/...` — create
- `GET /api/...` — read all
- `GET /api/.../{id}` — read one
- `PUT /api/.../{id}` — update
- `DELETE /api/.../{id}` — delete

## Frontend routes
Products: `/create`, `/read`, `/update`, `/delete`

Students: `/students/create`, `/students/read`, `/students/update`, `/students/delete`

The Read pages fetch data once when mounted. There is no polling and no separate CSS file.

## Requirements
- Java 17 or newer
- Maven
- Node.js and npm
- MongoDB running locally on port 27017

## Run backend
From the `backend` directory:
```bash
mvn spring-boot:run
```

## Run frontend
From the `frontend` directory:
```bash
npm install
npm run dev
```

Open the Vite URL, normally `http://localhost:5173`. Use each collection's Read page to copy an ID for updating or deleting a record.
