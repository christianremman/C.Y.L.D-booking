<template>
  <section id="about" class="about">
    <p class="text-kicker">About the collective</p>
    <div class="about__grid">
      <h2 class="section-heading">Built for the room, not a playlist.</h2>
      <p class="section-text">
        C.Y.L.D is a DJ collective known for energetic and versatile performances. With roots in
        house, pop and dance music, we tailor every set to the atmosphere of the event. Whether it
        is a club night, festival stage, corporate event or private party, we focus on creating the
        right vibe for the audience.
      </p>
    </div>
  </section>
</template>

<style scoped>
.about {
  border-bottom: 1px solid var(--border);
}

.about__grid {
  display: grid;
  grid-template-columns: minmax(0, 0.95fr) minmax(280px, 0.75fr);
  gap: clamp(1.6rem, 6vw, 5rem);
  align-items: start;
}

@media (max-width: 760px) {
  .about__grid {
    grid-template-columns: 1fr;
  }
}
</style>
