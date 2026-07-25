<script setup>
import { ref } from 'vue';
import { useScrollReveal } from '../composables/useScrollReveal';
import clubImg from '../assets/remman/dj/2Rem.jpeg';
import festivalImg from '../assets/disco-higgins/dj/2DiscoHiggins.jpeg';
import privateImg from '../assets/PrivateEvent.jpeg';
import barImg from '../assets/BarsAndLounges.JPG';
import corporateImg from '../assets/CorporateEvent.jpeg';

const sectionRef = ref(null);
const { isVisible } = useScrollReveal(sectionRef);

const events = [
  { title: 'Clubs', image: clubImg },
  { title: 'Festivals', image: festivalImg },
  { title: 'Private Events', image: privateImg },
  { title: 'Bars & Lounges', image: barImg, position: 'center top' },
  { title: 'Corporate Events', image: corporateImg },
];
</script>

<template>
  <section id="events" ref="sectionRef" class="reveal" :class="{ 'is-visible': isVisible }">
    <p class="text-kicker">Events</p>
    <h2 class="section-heading">Where We Perform</h2>
    <p class="section-text">We adapt our music selection to the crowd and the atmosphere of the event.</p>
    <div class="event-grid">
      <article
        v-for="event in events"
        :key="event.title"
        class="card event-card"
        :style="{
          backgroundImage: `linear-gradient(180deg, rgba(7, 6, 5, 0.34), rgba(7, 6, 5, 0.94)), url(${event.image})`,
          backgroundPosition: event.position || 'center',
        }"
      >
        <span class="event-card__icon" aria-hidden="true"></span>
        <h3>{{ event.title }}</h3>
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
  min-height: 180px;
  padding: 1.2rem 1rem;
  display: grid;
  align-content: end;
  background-size: cover;
  transition: transform 0.22s ease, border-color 0.22s ease;
  overflow: hidden;
}

.event-card:hover {
  transform: translateY(-4px);
  border-color: rgba(246, 196, 107, 0.36);
}

.event-card__icon {
  width: 14px;
  height: 14px;
  border: 2px solid var(--accent-2);
  background: rgba(7, 6, 5, 0.64);
  transform: rotate(45deg);
}

.event-card h3 {
  margin: 1.45rem 0 0;
  font-size: 1rem;
  line-height: 1.15;
  text-shadow: 0 2px 12px rgba(0, 0, 0, 0.86);
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
