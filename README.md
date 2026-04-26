# C.Y.L.D Booking

C.Y.L.D Booking is a full-stack booking site for a DJ collective. The repository is split into a Vue/Vite frontend and a Spring Boot backend.

## Structure

- `frontend/` - Vue 3 and Vite landing page.
- `backend/` - Spring Boot booking API.
- `docs/` - project planning notes.

## Requirements

- Node.js for frontend development.
- Java 21 and Maven for backend development.

## Frontend

```powershell
cd frontend
npm install
npm run dev
```

## Backend

```powershell
cd backend
mvn spring-boot:run
```

The backend exposes `POST /api/bookings`.

## Checks

```powershell
cd frontend
npm run build
```

```powershell
cd backend
mvn test
```

Mail settings are read from environment variables. Copy `.env.example` to a local `.env` when mail delivery is implemented.
