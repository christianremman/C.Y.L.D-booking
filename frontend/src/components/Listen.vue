<script setup>
import { ref } from 'vue';
import { useScrollReveal } from '../composables/useScrollReveal';

const sectionRef = ref(null);
const { isVisible } = useScrollReveal(sectionRef);

const players = [
  { name: 'REMMAN', url: 'https://soundcloud.com/remman-558448967' },
  { name: 'Disco Higgins', url: 'https://soundcloud.com/user-791088678' },
];

function embedUrl(trackUrl) {
  return `https://w.soundcloud.com/player/?url=${encodeURIComponent(trackUrl)}&color=%23f6c46b&auto_play=false&hide_related=true&show_comments=false&show_user=true&show_reposts=false&visual=false`;
}
</script>

<template>
  <section id="listen" ref="sectionRef" class="reveal" :class="{ 'is-visible': isVisible }">
    <p class="text-kicker">Listen</p>
    <h2 class="section-heading">Hear Us Play</h2>
    <p class="section-text">A taste of what we bring to every room.</p>
    <div class="listen-grid">
      <div v-for="p in players" :key="p.name" class="card listen-card">
        <h3>{{ p.name }}</h3>
        <iframe
          :src="embedUrl(p.url)"
          width="100%"
          height="166"
          allow="autoplay"
          sandbox="allow-scripts allow-same-origin"
          :title="`${p.name} on SoundCloud`"
        ></iframe>
      </div>
    </div>
  </section>
</template>

<style scoped>
.listen-grid {
  display: grid;
  grid-template-columns: repeat(2, minmax(0, 1fr));
  gap: clamp(1rem, 3vw, 1.6rem);
  margin-top: 1.6rem;
}

.listen-card {
  padding: 1.4rem;
  overflow: hidden;
}

.listen-card h3 {
  margin: 0 0 1rem;
  font-size: clamp(1.2rem, 2.2vw, 1.8rem);
  line-height: 1;
}

.listen-card iframe {
  display: block;
  border: none;
  border-radius: calc(var(--radius) / 2);
}

@media (max-width: 760px) {
  .listen-grid {
    grid-template-columns: 1fr;
  }
}
</style>
