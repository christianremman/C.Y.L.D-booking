<script setup>
const events = ['Clubs', 'Festivals', 'Private Events', 'Bars & Lounges', 'Corporate Events'];
</script>

<template>
  <section id="events">
    <p class="text-kicker">Events</p>
    <h2 class="section-heading">Where We Perform</h2>
    <p class="section-text">We adapt our music selection to the crowd and the atmosphere of the event.</p>
    <div class="event-grid">
      <article v-for="event in events" :key="event" class="card event-card">
        <span class="event-card__icon" aria-hidden="true"></span>
        <h3>{{ event }}</h3>
      </article>
    </div>
  </section>
</template>

<style scoped>
.event-grid {
  margin-top: 1.8rem;
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  gap: 0.9rem;
}

.event-card {
  min-height: 132px;
  padding: 1.2rem 1rem;
  display: grid;
  align-content: end;
  transition: transform 0.22s ease, border-color 0.22s ease;
}

.event-card:hover {
  transform: translateY(-4px);
  border-color: rgba(246, 196, 107, 0.36);
}

.event-card__icon {
  width: 14px;
  height: 14px;
  border: 2px solid var(--accent-2);
  transform: rotate(45deg);
}

.event-card h3 {
  margin: 1.45rem 0 0;
  font-size: 1rem;
  line-height: 1.15;
}

@media (max-width: 960px) {
  .event-grid {
    grid-template-columns: repeat(3, minmax(0, 1fr));
  }
}

@media (max-width: 620px) {
  .event-grid {
    grid-template-columns: repeat(2, minmax(0, 1fr));
  }
}
</style>
