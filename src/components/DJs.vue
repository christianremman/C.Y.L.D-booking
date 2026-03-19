<script setup>
const djs = [
  {
    name: 'DJ AERA',
    bio: 'Experienced club DJ with a passion for house and dance music. Known for reading the crowd and building high-energy sets.',
    link: 'https://instagram.com',
    linkLabel: 'Instagram'
  },
  {
    name: 'DJ NOVA',
    bio: 'Versatile open-format DJ blending pop, dance and club edits for unforgettable nights across bars, private events and festivals.',
    link: 'https://soundcloud.com',
    linkLabel: 'SoundCloud'
  }
];
</script>

<template>
  <section id="djs">
    <h2 class="section-heading">Meet the DJs</h2>
    <div class="dj-grid">
      <article v-for="dj in djs" :key="dj.name" class="card dj-card">
        <div class="dj-card__image" :aria-label="`${dj.name} image placeholder`" role="img"></div>
        <div class="dj-card__body">
          <h3>{{ dj.name }}</h3>
          <p>{{ dj.bio }}</p>
          <a :href="dj.link" target="_blank" rel="noreferrer">{{ dj.linkLabel }}</a>
        </div>
      </article>
    </div>
  </section>
</template>

<style scoped>
.dj-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: 1.4rem;
}

.dj-card {
  overflow: hidden;
  transition: transform 0.28s ease, border-color 0.28s ease;
}

.dj-card:hover {
  transform: translateY(-6px);
  border-color: rgba(110, 93, 255, 0.65);
}

.dj-card__image {
  min-height: 210px;
  background: linear-gradient(150deg, rgba(110, 93, 255, 0.35), rgba(0, 210, 255, 0.2));
  border-bottom: 1px solid var(--border);
}

.dj-card__body {
  padding: 1.3rem;
}

.dj-card h3 {
  margin: 0;
}

.dj-card p {
  color: var(--text-muted);
  margin: 0.75rem 0 1rem;
}

.dj-card a {
  color: var(--accent-2);
  font-weight: 600;
}

@media (max-width: 760px) {
  .dj-grid {
    grid-template-columns: 1fr;
  }
}
</style>
