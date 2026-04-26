# C.Y.L.D Booking Three-Agent Restructure Plan

## Summary

Split the work into three parallel modules with clear ownership:

- **Plan 1:** Project structure, build orchestration, and Spring Boot backend foundation.
- **Plan 2:** Frontend restructure, visual overhaul, and booking form integration.
- **Plan 3:** Email delivery, environment documentation, validation, and end-to-end verification.

Agents must avoid editing each other's owned files unless explicitly noted. Shared API contract: frontend submits `POST /api/bookings` as JSON, backend validates it, sends email, and returns JSON status.

---

## Plan 1: Full-Stack Structure and Backend Foundation

### Goal

Create the new project layout and Spring Boot backend shell without implementing the final mail service logic.

### Owned Scope

- Owns root structure, backend scaffold, backend build config, root docs/build notes.
- Does not own frontend visual redesign.
- Does not own final SMTP documentation content beyond wiring config placeholders.

### Target Structure

```text
C.Y.L.D-booking/
  frontend/
    index.html
    package.json
    package-lock.json
    vite.config.js
    src/
  backend/
    pom.xml
    src/main/java/com/cyld/booking/
      CyldBookingApplication.java
      booking/
        BookingRequest.java
        BookingResponse.java
        BookingController.java
        BookingService.java
    src/main/resources/
      application.yml
    src/test/java/com/cyld/booking/
  .gitignore
  .env.example
  README.md
```

### Implementation Details

- Move existing Vue/Vite files into `frontend/`.
- Create Spring Boot Maven project in `backend/`.
- Use Java 21 and Spring Boot 3.x.
- Add dependencies:
  - `spring-boot-starter-web`
  - `spring-boot-starter-validation`
  - `spring-boot-starter-mail`
  - `spring-boot-starter-test`
- Configure `backend/src/main/resources/application.yml` to read environment values:
  - `MAIL_HOST`
  - `MAIL_PORT`
  - `MAIL_USERNAME`
  - `MAIL_PASSWORD`
  - `MAIL_FROM`
  - `BOOKING_RECIPIENT`
- Add `BookingController` with `POST /api/bookings`.
- Add DTOs matching the frontend contract:

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

- Return success shape:

```json
{
  "success": true,
  "message": "Booking request sent."
}
```

- Return validation/error shape:

```json
{
  "success": false,
  "message": "Readable error message."
}
```

- Implement `BookingService` as an interface/class boundary so Plan 3 can fill in mail behavior cleanly.
- Configure backend static serving for production after frontend build output is copied into Spring Boot static resources.

### Tests

- Backend context loads.
- Controller accepts valid request and delegates to service.
- Controller rejects invalid required fields.
- `mvn test` must pass in `backend/`.

### Merge Contract

- Plan 1 must expose stable backend endpoint `/api/bookings`.
- Plan 1 must not hardcode email credentials or recipient.
- Plan 1 must leave frontend behavior compatible with Plan 2.

---

## Plan 2: Frontend Restructure and Visual Overhaul

### Goal

Move the Vue app into `frontend/`, preserve content, redesign the visual experience, and wire the booking form to the backend API.

### Owned Scope

- Owns all files inside `frontend/`.
- Owns visual design, Vue components, form UX, responsive behavior.
- Does not own backend implementation except matching the agreed API contract.

### Visual Direction

Use **premium nightlife**:

- Dark editorial club aesthetic.
- Strong typography and large confident section headings.
- Atmospheric image treatment using existing assets.
- More intentional spacing and section rhythm.
- High-contrast booking CTA.
- Subtle motion: entrance reveals, hover states, focus states.
- Avoid generic purple-on-white or default Vite styling.
- Keep page fast, readable, and mobile-friendly.

### Frontend Changes

- Move current app files into `frontend/`.
- Preserve existing content sections:
  - Hero
  - About
  - DJs
  - Events
  - Booking form
  - Social/contact links
  - Footer
- Refactor styling so the app feels like one cohesive landing page:
  - Use CSS variables for color, spacing, radius, shadows, and typography.
  - Improve hero layout with stronger CTA placement.
  - Improve DJ/event cards with better image framing and hierarchy.
  - Add responsive layout polish for mobile.
- Remove Netlify form attributes from `BookingForm.vue`.
- Implement Vue form state with fields:
  - `name`
  - `email`
  - `phone`
  - `eventDate`
  - `eventLocation`
  - `eventType`
  - `budget`
  - `message`
- Submit to:

```text
POST /api/bookings
Content-Type: application/json
```

- Use this JSON shape:

```json
{
  "name": "...",
  "email": "...",
  "phone": "...",
  "eventDate": "2026-05-20",
  "eventLocation": "...",
  "eventType": "...",
  "budget": "...",
  "message": "..."
}
```

- Add visible states:
  - Idle
  - Submitting
  - Success
  - Error
- Disable submit button while submitting.
- Keep browser-native validation where useful, but rely on backend validation as source of truth.

### Dev Proxy

Configure `frontend/vite.config.js` so local frontend dev can call backend without CORS issues:

```js
server: {
  proxy: {
    '/api': 'http://localhost:8080'
  }
}
```

### Tests and Checks

- `npm install` inside `frontend/` if needed.
- `npm run build` inside `frontend/`.
- Manual browser check:
  - Desktop layout.
  - Mobile layout.
  - Booking form submit loading state.
  - Success/error display.
- Do not claim exact email delivery works; that belongs to Plan 3.

### Merge Contract

- Frontend must only depend on `/api/bookings`.
- Do not hardcode backend host in production code.
- Keep field names exactly aligned with Plan 1 and Plan 3.

---

## Plan 3: Email Delivery, Environment Docs, and Integration Verification

### Goal

Implement real SMTP email sending, document `.env.example` clearly, and verify the full booking flow.

### Owned Scope

- Owns backend mail service implementation.
- Owns `.env.example`.
- Owns README setup instructions.
- Owns end-to-end verification notes.
- Must coordinate with Plan 1 DTO/controller contract and Plan 2 request shape.

### Email Implementation

- Use Spring Boot Mail via `JavaMailSender`.
- Implement `BookingService` to:
  - Accept validated `BookingRequest`.
  - Format a readable booking inquiry email.
  - Send it to `BOOKING_RECIPIENT`.
  - Use `MAIL_FROM` as sender/from address.
  - Use submitted customer email as reply-to where supported.
- Email subject:

```text
New C.Y.L.D booking request from {name}
```

- Email body should include:
  - Name
  - Email
  - Phone
  - Event date
  - Event location
  - Event type
  - Budget if provided
  - Message
- Never log SMTP passwords.
- On mail failure, return a safe error response without exposing credentials or provider internals.

### `.env.example`

Create a well-documented `.env.example` at repo root:

```env
# SMTP server hostname.
# Examples:
# Gmail: smtp.gmail.com
# Outlook: smtp.office365.com
# Mailtrap: sandbox.smtp.mailtrap.io
MAIL_HOST=smtp.example.com

# SMTP port.
# Common values:
# 587 = STARTTLS, recommended for most providers
# 465 = SSL
MAIL_PORT=587

# SMTP account username.
# For Gmail this is usually your full email address.
MAIL_USERNAME=your-email@example.com

# SMTP account password or app password.
# Do not use your normal Gmail password. Use an app password when required.
MAIL_PASSWORD=your-smtp-password

# Address shown as the sender.
# Usually the same as MAIL_USERNAME or a verified sender address.
MAIL_FROM=booking@example.com

# Address that receives booking requests.
BOOKING_RECIPIENT=recipient@example.com
```

### Git Ignore

Ensure `.gitignore` contains:

```gitignore
.env
backend/.env
frontend/.env
node_modules/
frontend/node_modules/
dist/
frontend/dist/
backend/target/
```

### README Updates

Document:

- New project structure.
- Required Java and Node versions.
- Frontend development:

```powershell
cd frontend
npm install
npm run dev
```

- Backend development:

```powershell
cd backend
mvn spring-boot:run
```

- Frontend build:

```powershell
cd frontend
npm run build
```

- Backend test:

```powershell
cd backend
mvn test
```

- Mail setup:
  - Copy `.env.example` to `.env`.
  - Fill SMTP values.
  - Use app passwords where required.
  - Use Mailtrap for safe local testing if desired.
- Explain that real `.env` must not be committed.

### Integration Verification

After all three plans merge:

- Start backend on `localhost:8080`.
- Start frontend on Vite dev server.
- Submit booking form.
- Confirm frontend shows success.
- Confirm recipient inbox or Mailtrap receives email.
- Run:

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

### Merge Contract

- Must use the same request fields defined by Plan 1 and Plan 2.
- Must not change endpoint path without coordinating both other plans.
- Must not commit real SMTP credentials.
- Must keep documentation practical enough that a new developer can configure mail without reading source code.
