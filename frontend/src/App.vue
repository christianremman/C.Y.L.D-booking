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
  --bg: #06070d;
  --surface: #11131d;
  --surface-soft: #191c27;
  --text: #f7f8ff;
  --text-muted: #b7bbd3;
  --accent: #6e5dff;
  --accent-2: #00d2ff;
  --border: #272b3d;
  --shadow: 0 12px 40px rgba(2, 4, 15, 0.45);
}

* {
  box-sizing: border-box;
}

html {
  scroll-behavior: smooth;
}

body {
  margin: 0;
  font-family: Inter, system-ui, -apple-system, Segoe UI, Roboto, sans-serif;
  background: radial-gradient(circle at top, #121529 0%, var(--bg) 55%);
  color: var(--text);
  line-height: 1.6;
}

a {
  color: inherit;
  text-decoration: none;
}

main,
.section-shell {
  width: min(1120px, 92vw);
  margin: 0 auto;
}

section {
  padding: 4.8rem 0;
}

.section-heading {
  font-size: clamp(1.8rem, 2.8vw, 2.6rem);
  margin-bottom: 1rem;
  letter-spacing: 0.02em;
}

.section-text {
  color: var(--text-muted);
  max-width: 68ch;
}

.card {
  background: linear-gradient(145deg, rgba(24, 27, 39, 0.9), rgba(14, 16, 24, 0.95));
  border: 1px solid var(--border);
  border-radius: 16px;
  box-shadow: var(--shadow);
}

@media (max-width: 720px) {
  section {
    padding: 4rem 0;
  }
}
</style>
