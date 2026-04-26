<template>
  <section id="booking">
    <h2 class="section-heading">Booking Request</h2>
    <p class="section-text">Share your event details and we’ll get back to you with availability and pricing.</p>

    <form name="booking" method="post" data-netlify="true" netlify class="card booking-form">
      <input type="hidden" name="form-name" value="booking" />

      <label>
        Name
        <input type="text" name="name" required />
      </label>

      <label>
        Email
        <input type="email" name="email" required />
      </label>

      <label>
        Phone
        <input type="tel" name="phone" required />
      </label>

      <label>
        Event Date
        <input type="date" name="event-date" required />
      </label>

      <label>
        Event Location
        <input type="text" name="event-location" required />
      </label>

      <label>
        Event Type
        <select name="event-type" required>
          <option value="" disabled selected>Select event type</option>
          <option>Club</option>
          <option>Private Party</option>
          <option>Festival</option>
          <option>Bar Event</option>
          <option>Corporate Event</option>
          <option>Wedding</option>
          <option>Other</option>
        </select>
      </label>

      <label>
        Budget (optional)
        <input type="text" name="budget" />
      </label>

      <label class="booking-form__message">
        Message
        <textarea name="message" rows="5" required></textarea>
      </label>

      <button type="submit">Send Booking Request</button>
    </form>
  </section>
</template>

<style scoped>
.booking-form {
  margin-top: 1.5rem;
  padding: 1.4rem;
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}

label {
  display: grid;
  gap: 0.45rem;
  color: var(--text-muted);
  font-size: 0.95rem;
}

input,
select,
textarea {
  width: 100%;
  border: 1px solid var(--border);
  border-radius: 10px;
  background: rgba(11, 13, 21, 0.95);
  color: var(--text);
  font: inherit;
  padding: 0.75rem 0.85rem;
}

input:focus,
select:focus,
textarea:focus {
  outline: 2px solid rgba(0, 210, 255, 0.35);
  border-color: var(--accent-2);
}

.booking-form__message,
button {
  grid-column: 1 / -1;
}

button {
  margin-top: 0.4rem;
  border: none;
  border-radius: 12px;
  padding: 0.92rem;
  background: linear-gradient(120deg, var(--accent), var(--accent-2));
  color: white;
  font-size: 1rem;
  font-weight: 700;
  letter-spacing: 0.01em;
  cursor: pointer;
  transition: transform 0.25s ease, filter 0.25s ease;
}

button:hover {
  transform: translateY(-1px);
  filter: brightness(1.06);
}

@media (max-width: 680px) {
  .booking-form {
    grid-template-columns: 1fr;
  }
}
</style>
