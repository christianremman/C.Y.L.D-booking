<script setup>
const events = ['Clubs', 'Festivals', 'Private Events', 'Bars & Lounges', 'Corporate Events'];
</script>

<template>
  <section id="events">
    <h2 class="section-heading">Where We Perform</h2>
    <p class="section-text">We adapt our music selection to the crowd and the atmosphere of the event.</p>
    <div class="event-grid">
      <article v-for="event in events" :key="event" class="card event-card">
        <span class="event-card__icon">◆</span>
        <h3>{{ event }}</h3>
      </article>
    </div>
  </section>
</template>

<style scoped>
.event-grid {
  margin-top: 1.4rem;
  display: grid;
  grid-template-columns: repeat(5, minmax(0, 1fr));
  gap: 0.9rem;
}

.event-card {
  padding: 1.2rem 1rem;
  text-align: center;
}

.event-card__icon {
  color: var(--accent-2);
}

.event-card h3 {
  margin: 0.55rem 0 0;
  font-size: 1rem;
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
