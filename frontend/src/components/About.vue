<script setup>
import remmanSetImg from '../assets/remman/dj/3Rem.jpg';
import higgaSetImg from '../assets/higga/dj/1Hig.jpeg';
import sharedImg from '../assets/shared/MountainShared.JPG';
</script>

<template>
  <section id="about" class="about">
    <p class="text-kicker">About the collective</p>
    <div class="about__grid">
      <h2 class="section-heading">Built for the room, not a playlist.</h2>
      <div>
        <p class="section-text">
          C.Y.L.D is a DJ collective known for energetic and versatile performances. With roots in
          house, pop and dance music, we tailor every set to the atmosphere of the event. Whether it
          is a club night, festival stage, corporate event or private party, we focus on creating the
          right vibe for the audience.
        </p>
        <div class="about__media" aria-label="C.Y.L.D event photos">
          <img :src="remmanSetImg" alt="DJ REMMAN performing" />
          <img :src="higgaSetImg" alt="DJ HIGGA performing" />
          <img :src="sharedImg" alt="C.Y.L.D travel and event atmosphere" />
        </div>
      </div>
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

.about__media {
  margin-top: 1.5rem;
  display: grid;
  grid-template-columns: 1.15fr 0.85fr;
  grid-template-rows: repeat(2, 150px);
  gap: 0.75rem;
}

.about__media img {
  width: 100%;
  height: 100%;
  display: block;
  object-fit: cover;
  border: 1px solid var(--border);
  border-radius: var(--radius);
  filter: saturate(0.86) contrast(1.08);
}

.about__media img:first-child {
  grid-row: 1 / -1;
}

@media (max-width: 760px) {
  .about__grid {
    grid-template-columns: 1fr;
  }

  .about__media {
    grid-template-rows: repeat(2, 128px);
  }
}
</style>
