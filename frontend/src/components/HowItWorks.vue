<script setup>
import { ref } from 'vue';
import { useScrollReveal } from '../composables/useScrollReveal';

const sectionRef = ref(null);
const { isVisible } = useScrollReveal(sectionRef);
</script>

<template>
  <section class="how-it-works reveal" ref="sectionRef" :class="{ 'is-visible': isVisible }">
    <p class="text-kicker">Process</p>
    <h2 class="section-heading">How it works</h2>
    <ol class="steps">
      <li class="step">
        <span class="step__number">1</span>
        <h3>Enquire</h3>
        <p>Fill out the booking form with your event details.</p>
      </li>
      <li class="step">
        <span class="step__number">2</span>
        <h3>Connect</h3>
        <p>We'll respond within 24 hours to confirm availability.</p>
      </li>
      <li class="step">
        <span class="step__number">3</span>
        <h3>Book</h3>
        <p>Finalize the details and lock in your date.</p>
      </li>
    </ol>
  </section>
</template>

<style scoped>
.how-it-works {
  border-bottom: 1px solid var(--border);
}

.steps {
  list-style: none;
  margin: 2rem 0 0;
  padding: 0;
  display: grid;
  grid-template-columns: repeat(3, minmax(0, 1fr));
  gap: clamp(1rem, 4vw, 2.5rem);
}

.step {
  background: linear-gradient(145deg, rgba(33, 24, 20, 0.7), rgba(13, 10, 9, 0.85));
  border: 1px solid var(--border);
  border-radius: var(--radius);
  padding: 1.6rem 1.4rem;
}

.step__number {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background: var(--accent);
  color: #fff8ef;
  font-size: 0.85rem;
  font-weight: 900;
  margin-bottom: 1rem;
}

.step h3 {
  margin: 0 0 0.5rem;
  font-size: 1.25rem;
}

.step p {
  color: var(--text-muted);
  margin: 0;
  font-size: 0.92rem;
}

@media (max-width: 720px) {
  .steps {
    grid-template-columns: 1fr;
  }
}
</style>
