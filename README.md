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

## Docker

Build and run the backend Docker image:

```powershell
docker build -t cyld-booking ./backend
docker run --env-file .env -p 8080:8080 cyld-booking
```

The Docker image serves the Spring Boot API and any files committed under `backend/src/main/resources/static/`.

## Render Deployment

Render is configured by `render.yaml` at the repo root. Create the service from the Blueprint or make sure the Render service uses:

```text
Runtime: Docker
Dockerfile Path: ./backend/Dockerfile
Docker Context: ./backend
```

Set the required mail environment variables in Render. Do not set `PORT`; Render provides it automatically.

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

### Preventing Free-Tier Spin-Down

Render's free plan spins the service down after ~15 minutes of no inbound traffic (30-60s cold start on the next request). To keep it warm during active hours without paying for a 24/7 plan, an external cron pinger hits the health check on a schedule:

- Service: [cron-job.org](https://cron-job.org) (free)
- Target: `GET https://c-y-l-d-booking-page.onrender.com/api/health`
- Interval: every 10 minutes (under Render's 15-min idle timeout)
- Time restriction: 08:00-21:00, timezone `Australia/Melbourne` (DST-aware)

`/api/health` is unauthenticated and not subject to `BookingRateLimiter`, so frequent pings are safe. The service is expected to cold-start once per day on the first request after 08:00, since it spins down overnight outside the ping window. This is external configuration only — no backend code is involved.

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
- `TURNSTILE_SECRET`
- `FRONTEND_ORIGIN`

Frontend build variables for split deployments:

- `VITE_API_BASE_URL`
- `VITE_TURNSTILE_SITE_KEY`

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
