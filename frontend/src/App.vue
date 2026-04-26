<script setup>
import Hero from './components/Hero.vue';
import About from './components/About.vue';
import DJs from './components/DJs.vue';
import Events from './components/Events.vue';
import BookingForm from './components/BookingForm.vue';
import Socials from './components/Socials.vue';
import Footer from './components/Footer.vue';
</script>

<template>
  <div class="app">
    <Hero />
    <main>
      <About />
      <DJs />
      <Events />
      <BookingForm />
      <Socials />
    </main>
    <Footer />
  </div>
</template>

<style>
:root {
  --bg: #070605;
  --surface: #15110f;
  --surface-soft: #211814;
  --surface-strong: #2d201b;
  --text: #fff8ef;
  --text-muted: #c4b8aa;
  --accent: #ff3d1f;
  --accent-2: #f6c46b;
  --border: rgba(255, 248, 239, 0.14);
  --radius: 8px;
  --space-section: clamp(4.5rem, 8vw, 7rem);
  --shadow: 0 22px 70px rgba(0, 0, 0, 0.46);
}

* {
  box-sizing: border-box;
}

html {
  scroll-behavior: smooth;
}

body {
  margin: 0;
  font-family: Inter, ui-sans-serif, system-ui, -apple-system, Segoe UI, Roboto, sans-serif;
  background:
    linear-gradient(180deg, rgba(7, 6, 5, 0.74), var(--bg) 680px),
    radial-gradient(circle at 18% 16%, rgba(255, 61, 31, 0.18), transparent 34%),
    radial-gradient(circle at 82% 12%, rgba(246, 196, 107, 0.12), transparent 30%),
    var(--bg);
  color: var(--text);
  line-height: 1.6;
  text-rendering: optimizeLegibility;
}

a {
  color: inherit;
  text-decoration: none;
}

button,
input,
select,
textarea {
  font: inherit;
}

main,
.section-shell {
  width: min(1120px, 92vw);
  margin: 0 auto;
}

section {
  padding: var(--space-section) 0;
}

.section-heading {
  font-family: Georgia, 'Times New Roman', serif;
  font-size: clamp(2.35rem, 5vw, 4.7rem);
  line-height: 0.95;
  margin: 0 0 1rem;
  letter-spacing: 0;
}

.section-text {
  color: var(--text-muted);
  max-width: 68ch;
}

.card {
  background: linear-gradient(145deg, rgba(33, 24, 20, 0.92), rgba(13, 10, 9, 0.96));
  border: 1px solid var(--border);
  border-radius: var(--radius);
  box-shadow: var(--shadow);
}

.text-kicker {
  color: var(--accent-2);
  font-size: 0.75rem;
  font-weight: 800;
  letter-spacing: 0.18em;
  text-transform: uppercase;
}

::selection {
  background: rgba(255, 61, 31, 0.55);
  color: var(--text);
}

@media (max-width: 720px) {
  section {
    padding: 4rem 0;
  }
}

@media (prefers-reduced-motion: reduce) {
  *,
  *::before,
  *::after {
    animation-duration: 0.01ms !important;
    animation-iteration-count: 1 !important;
    scroll-behavior: auto !important;
    transition-duration: 0.01ms !important;
  }
}
</style>
