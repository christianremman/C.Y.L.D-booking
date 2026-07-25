<script setup>
import { ref } from 'vue';
import { useScrollReveal } from '../composables/useScrollReveal';
import remmanImg from '../assets/remman/dj/ProRem.jpeg';
import discoHigginsImg from '../assets/disco-higgins/dj/ProDiscoHiggins.JPG';

const sectionRef = ref(null);
const { isVisible } = useScrollReveal(sectionRef);

const djs = [
  {
    name: 'REMMAN',
    bio: 'Experienced club DJ with a passion for house and dance music. Known for reading the crowd and building high-energy sets.',
    genres: ['House', 'Dance', 'Open Format'],
    link: 'https://www.instagram.com/christianremman/',
    linkLabel: 'Instagram',
    image: remmanImg,
  },
  {
    name: 'Disco Higgins',
    bio: 'Versatile open-format DJ blending pop, dance and club edits for unforgettable nights across bars, private events and festivals.',
    genres: ['Pop', 'Dance', 'Club Edits'],
    link: 'https://www.instagram.com/oliver_wh/',
    linkLabel: 'Instagram',
    image: discoHigginsImg,
  },
];
</script>

<template>
  <section id="djs" ref="sectionRef" class="reveal" :class="{ 'is-visible': isVisible }">
    <p class="text-kicker">Residents</p>
    <h2 class="section-heading">Meet the DJs</h2>
    <div class="dj-grid">
      <article v-for="dj in djs" :key="dj.name" class="card dj-card">
        <img :src="dj.image" :alt="dj.name" class="dj-card__image" />
        <div class="dj-card__body">
          <h3>{{ dj.name }}</h3>
          <div class="dj-card__tags">
            <span v-for="genre in dj.genres" :key="genre" class="tag">{{ genre }}</span>
          </div>
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
  gap: clamp(1rem, 3vw, 1.6rem);
  margin-top: 1.6rem;
}

.dj-card {
  overflow: hidden;
  transition: transform 0.28s ease, border-color 0.28s ease, filter 0.28s ease;
}

.dj-card:hover {
  transform: translateY(-6px);
  border-color: rgba(246, 196, 107, 0.5);
  filter: brightness(1.06);
}

.dj-card__image {
  width: 100%;
  height: clamp(280px, 36vw, 430px);
  object-fit: cover;
  display: block;
  border-bottom: 1px solid var(--border);
  filter: grayscale(0.22) saturate(0.86) contrast(1.08);
}

.dj-card__body {
  padding: 1.4rem;
}

.dj-card h3 {
  margin: 0;
  font-size: clamp(1.4rem, 2.6vw, 2.2rem);
  line-height: 1;
}

.dj-card__tags {
  display: flex;
  flex-wrap: wrap;
  gap: 0.4rem;
  margin: 0.75rem 0 0;
}

.tag {
  display: inline-block;
  padding: 0.22rem 0.65rem;
  border: 1px solid rgba(246, 196, 107, 0.35);
  border-radius: 999px;
  color: var(--accent-2);
  font-size: 0.72rem;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  background: rgba(246, 196, 107, 0.07);
}

.dj-card p {
  color: var(--text-muted);
  margin: 0.75rem 0 1rem;
}

.dj-card a {
  color: var(--accent-2);
  font-weight: 800;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  font-size: 0.78rem;
}

@media (max-width: 760px) {
  .dj-grid {
    grid-template-columns: 1fr;
  }
}
</style>
