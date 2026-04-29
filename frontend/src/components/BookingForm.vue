<script setup>
import { computed, onBeforeUnmount, onMounted, reactive, ref } from 'vue';

import { buildApiUrl, turnstileSiteKey } from '../lib/config';
import { loadTurnstile, removeTurnstileWidget, renderTurnstileWidget, resetTurnstileWidget } from '../lib/turnstile';

const GENERIC_ERROR_MESSAGE = 'Booking request could not be sent. Please try again later.';

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
const turnstileContainer = ref(null);
const turnstileToken = ref('');
const widgetId = ref(null);
const turnstileEnabled = Boolean(turnstileSiteKey);

const canSubmit = computed(() => {
  if (status.value === 'submitting') {
    return false;
  }

  if (!turnstileEnabled) {
    return true;
  }

  return Boolean(turnstileToken.value);
});

const resetForm = () => {
  Object.assign(form, initialForm);
};

const resetTurnstile = () => {
  turnstileToken.value = '';

  if (widgetId.value !== null) {
    resetTurnstileWidget(widgetId.value);
  }
};

const initializeTurnstile = async () => {
  if (!turnstileEnabled || !turnstileContainer.value) {
    return;
  }

  await loadTurnstile();

  widgetId.value = renderTurnstileWidget(turnstileContainer.value, {
    sitekey: turnstileSiteKey,
    callback: (token) => {
      turnstileToken.value = token;
    },
    'expired-callback': () => {
      turnstileToken.value = '';
    },
    'error-callback': () => {
      turnstileToken.value = '';
    },
  });
};

const submitBooking = async () => {
  status.value = 'submitting';
  responseMessage.value = '';

  try {
    const response = await fetch(buildApiUrl('/api/bookings'), {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        ...form,
        turnstileToken: turnstileToken.value,
      }),
    });

    const data = await response.json().catch(() => ({}));

    if (!response.ok || data.success === false) {
      const message = response.status === 400 && typeof data.message === 'string'
        ? data.message
        : GENERIC_ERROR_MESSAGE;

      throw new Error(message);
    }

    status.value = 'success';
    responseMessage.value = data.message || 'Booking request sent.';
    resetForm();
    resetTurnstile();
  } catch (error) {
    status.value = 'error';
    responseMessage.value = error instanceof Error && error.message
      ? error.message
      : GENERIC_ERROR_MESSAGE;
    resetTurnstile();
  }
};

onMounted(() => {
  initializeTurnstile();
});

onBeforeUnmount(() => {
  if (widgetId.value !== null) {
    removeTurnstileWidget(widgetId.value);
  }
});
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
        <input v-model.trim="form.name" type="text" name="name" autocomplete="name" required maxlength="100" />
      </label>

      <label>
        Email
        <input v-model.trim="form.email" type="email" name="email" autocomplete="email" required maxlength="254" />
      </label>

      <label>
        Phone
        <input v-model.trim="form.phone" type="tel" name="phone" autocomplete="tel" required maxlength="30" />
      </label>

      <label>
        Event Date
        <input v-model="form.eventDate" type="date" name="eventDate" required />
      </label>

      <label>
        Event Location
        <input v-model.trim="form.eventLocation" type="text" name="eventLocation" required maxlength="120" />
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
        <input v-model.trim="form.budget" type="text" name="budget" maxlength="60" />
      </label>

      <label class="booking-form__message">
        Message
        <textarea v-model.trim="form.message" name="message" rows="5" required maxlength="2000"></textarea>
      </label>

      <div v-if="turnstileEnabled" ref="turnstileContainer" class="booking-form__turnstile"></div>

      <div v-if="responseMessage" class="booking-form__status" :class="`is-${status}`" role="status">
        {{ responseMessage }}
      </div>

      <button type="submit" :disabled="!canSubmit">
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
.booking-form__turnstile,
.booking-form__status,
button {
  grid-column: 1 / -1;
}

.booking-form__turnstile {
  min-height: 65px;
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
  border-color: rgba(204, 50, 27, 0.72);
  background: rgba(204, 50, 27, 0.16);
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
  background: #b92b16;
}

button:focus-visible {
  outline: 3px solid rgba(246, 196, 107, 0.9);
  outline-offset: 3px;
}

button:disabled {
  cursor: wait;
  background: #743126;
  color: rgba(255, 248, 239, 0.82);
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
