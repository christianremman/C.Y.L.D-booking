# C.Y.L.D Booking

C.Y.L.D Booking is a Vue and Spring Boot booking site for the C.Y.L.D DJ collective. The frontend presents the collective and booking form. The backend accepts booking requests at `POST /api/bookings` and sends the inquiry by SMTP email.

## Project Structure

```text
C.Y.L.D-booking/
  frontend/        Vue and Vite app
  backend/         Spring Boot booking API and email delivery
  docs/            Project plans and verification notes
  .env.example     SMTP environment variable template
```

## Requirements

- Java 21
- Maven 3.9+
- Node.js 20+ or 22+
- npm 10+

## Frontend Development

```powershell
cd frontend
npm install
npm run dev
```

## Backend Development

```powershell
cd backend
mvn spring-boot:run
```

The backend exposes:

```text
POST /api/bookings
Content-Type: application/json
```

Expected request fields:

```json
{
  "name": "string",
  "email": "string",
  "phone": "string",
  "eventDate": "YYYY-MM-DD",
  "eventLocation": "string",
  "eventType": "string",
  "budget": "string optional",
  "message": "string"
}
```

## Mail Setup

Copy `.env.example` to `.env` and fill in the SMTP values:

```powershell
Copy-Item .env.example .env
```

When the backend is started from the `backend/` directory, Spring Boot imports the repo-root `.env` file with `spring.config.import=optional:file:../.env[.properties]`.

Required variables:

- `MAIL_HOST`
- `MAIL_PORT`
- `MAIL_USERNAME`
- `MAIL_PASSWORD`
- `MAIL_FROM`
- `BOOKING_RECIPIENT`

Use provider-specific app passwords where required, especially for Gmail. For local testing, Mailtrap is a safer option than sending real email. Never commit a real `.env` file.

## Build and Test

Frontend build:

```powershell
cd frontend
npm run build
```

Backend tests:

```powershell
cd backend
mvn test
```

Backend package:

```powershell
cd backend
mvn package
```

After all three plans are merged, verify the full booking flow by starting both apps, submitting the booking form, confirming the frontend success state, and confirming delivery in the recipient inbox or Mailtrap.
