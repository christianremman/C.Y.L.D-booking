<script setup>
import { reactive, ref } from 'vue';

const initialForm = {
  name: '',
  email: '',
  phone: '',
  eventDate: '',
  eventLocation: '',
  eventType: '',
  budget: '',
  message: '',
};

const form = reactive({ ...initialForm });
const status = ref('idle');
const responseMessage = ref('');

const resetForm = () => {
  Object.assign(form, initialForm);
};

const submitBooking = async () => {
  status.value = 'submitting';
  responseMessage.value = '';

  try {
    const response = await fetch('/api/bookings', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ ...form }),
    });

    const data = await response.json().catch(() => ({}));

    if (!response.ok || data.success === false) {
      throw new Error(data.message || 'Booking request could not be sent.');
    }

    status.value = 'success';
    responseMessage.value = data.message || 'Booking request sent.';
    resetForm();
  } catch (error) {
    status.value = 'error';
    responseMessage.value = error.message || 'Booking request could not be sent.';
  }
};
</script>

<template>
  <section id="booking" class="booking">
    <div class="booking__intro">
      <p class="text-kicker">Booking</p>
      <h2 class="section-heading">Bring C.Y.L.D to your night.</h2>
      <p class="section-text">
        Share your event details and we will get back to you with availability and pricing.
      </p>
    </div>

    <form class="card booking-form" @submit.prevent="submitBooking">
      <label>
        Name
        <input v-model.trim="form.name" type="text" name="name" autocomplete="name" required />
      </label>

      <label>
        Email
        <input v-model.trim="form.email" type="email" name="email" autocomplete="email" required />
      </label>

      <label>
        Phone
        <input v-model.trim="form.phone" type="tel" name="phone" autocomplete="tel" required />
      </label>

      <label>
        Event Date
        <input v-model="form.eventDate" type="date" name="eventDate" required />
      </label>

      <label>
        Event Location
        <input v-model.trim="form.eventLocation" type="text" name="eventLocation" required />
      </label>

      <label>
        Event Type
        <select v-model="form.eventType" name="eventType" required>
          <option value="" disabled>Select event type</option>
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
        <input v-model.trim="form.budget" type="text" name="budget" />
      </label>

      <label class="booking-form__message">
        Message
        <textarea v-model.trim="form.message" name="message" rows="5" required></textarea>
      </label>

      <div v-if="responseMessage" class="booking-form__status" :class="`is-${status}`" role="status">
        {{ responseMessage }}
      </div>

      <button type="submit" :disabled="status === 'submitting'">
        {{ status === 'submitting' ? 'Sending...' : 'Send Booking Request' }}
      </button>
    </form>
  </section>
</template>

<style scoped>
.booking {
  display: grid;
  grid-template-columns: minmax(0, 0.75fr) minmax(320px, 1fr);
  gap: clamp(1.5rem, 5vw, 4rem);
  align-items: start;
}

.booking__intro {
  position: sticky;
  top: 1.5rem;
}

.booking-form {
  padding: clamp(1rem, 3vw, 1.6rem);
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1rem;
}

label {
  display: grid;
  gap: 0.45rem;
  color: var(--text-muted);
  font-size: 0.92rem;
  font-weight: 700;
}

input,
select,
textarea {
  width: 100%;
  border: 1px solid var(--border);
  border-radius: var(--radius);
  background: rgba(7, 6, 5, 0.78);
  color: var(--text);
  padding: 0.82rem 0.88rem;
  transition: border-color 0.2s ease, box-shadow 0.2s ease, background 0.2s ease;
}

input:focus,
select:focus,
textarea:focus {
  outline: none;
  border-color: var(--accent-2);
  background: rgba(7, 6, 5, 0.94);
  box-shadow: 0 0 0 3px rgba(246, 196, 107, 0.18);
}

textarea {
  resize: vertical;
}

.booking-form__message,
.booking-form__status,
button {
  grid-column: 1 / -1;
}

.booking-form__status {
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 0.82rem 0.9rem;
  color: var(--text);
}

.booking-form__status.is-success {
  border-color: rgba(246, 196, 107, 0.45);
  background: rgba(246, 196, 107, 0.1);
}

.booking-form__status.is-error {
  border-color: rgba(255, 61, 31, 0.5);
  background: rgba(255, 61, 31, 0.11);
}

button {
  margin-top: 0.25rem;
  border: 1px solid rgba(255, 248, 239, 0.18);
  border-radius: var(--radius);
  padding: 0.98rem;
  background: var(--accent);
  color: white;
  font-size: 1rem;
  font-weight: 900;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  cursor: pointer;
  transition: transform 0.2s ease, filter 0.2s ease, opacity 0.2s ease;
}

button:hover:not(:disabled) {
  transform: translateY(-1px);
  filter: brightness(1.08);
}

button:focus-visible {
  outline: 3px solid rgba(246, 196, 107, 0.55);
  outline-offset: 3px;
}

button:disabled {
  cursor: wait;
  opacity: 0.68;
}

@media (max-width: 860px) {
  .booking {
    grid-template-columns: 1fr;
  }

  .booking__intro {
    position: static;
  }
}

@media (max-width: 680px) {
  .booking-form {
    grid-template-columns: 1fr;
  }
}
</style>
