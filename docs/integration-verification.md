# Integration Verification

Run these checks after Plan 1, Plan 2, and Plan 3 are merged together.

## Local Flow

1. Copy `.env.example` to `.env` and fill in SMTP values.
2. Start the backend on `localhost:8080`.
3. Start the frontend with the Vite dev server.
4. Submit the booking form.
5. Confirm the frontend shows the success state.
6. Confirm the recipient inbox or Mailtrap receives the email.

## Commands

```powershell
cd frontend
npm run build
```

```powershell
cd backend
mvn test
```

```powershell
cd backend
mvn package
```

## Notes

- Do not commit a real `.env` file.
- Use Mailtrap or another sandbox SMTP provider for safe local testing.
- The backend expects `POST /api/bookings` with the shared Plan 1 and Plan 2 field names.
