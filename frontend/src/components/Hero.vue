<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue';

const menuOpen = ref(false);
const scrolled = ref(false);

const handleScroll = () => {
  scrolled.value = window.scrollY > 60;
};

onMounted(() => window.addEventListener('scroll', handleScroll, { passive: true }));
onBeforeUnmount(() => window.removeEventListener('scroll', handleScroll));
</script>

<template>
  <header class="hero" id="top">
    <div class="section-shell hero__nav" aria-label="Primary navigation">
      <a href="#top" class="hero__brand">C.Y.L.D</a>
      <button
        class="hero__hamburger"
        :aria-label="menuOpen ? 'Close menu' : 'Open menu'"
        @click="menuOpen = !menuOpen"
      >{{ menuOpen ? '✕' : '☰' }}</button>
      <nav :class="['hero__nav-links', { 'is-open': menuOpen }]">
        <a href="#about" @click="menuOpen = false">About</a>
        <a href="#djs" @click="menuOpen = false">DJs</a>
        <a href="#listen" @click="menuOpen = false">Listen</a>
        <a href="#events" @click="menuOpen = false">Events</a>
        <a href="#booking" @click="menuOpen = false">Booking</a>
        <a href="#socials" @click="menuOpen = false">Socials</a>
      </nav>
    </div>

    <div class="section-shell hero__content">
      <div>
        <p class="text-kicker">Bookings & Events</p>
        <h1>C.Y.L.D</h1>
      </div>
      <div class="hero__copy">
        <p class="hero__subtitle">Your event. Our energy.</p>
        <p class="hero__text">
          C.Y.L.D delivers versatile DJ performances tailored to your event. From clubs and bars to
          private parties and festivals, we bring the energy and adapt to the crowd.
        </p>
        <a href="#booking" class="hero__cta">Book Us</a>
      </div>
    </div>

    <a
      href="#about"
      class="hero__scroll-indicator"
      :class="{ 'is-hidden': scrolled }"
      aria-label="Scroll to about"
    >↓</a>
  </header>
</template>

<style scoped>
.hero {
  min-height: 96vh;
  position: relative;
  overflow: hidden;
  isolation: isolate;
}

.hero::before {
  content: '';
  position: absolute;
  inset: 0;
  z-index: -1;
  background:
    linear-gradient(90deg, rgba(7, 6, 5, 0.96) 0%, rgba(7, 6, 5, 0.72) 48%, rgba(7, 6, 5, 0.2) 100%),
    linear-gradient(0deg, var(--bg) 0%, rgba(7, 6, 5, 0.18) 38%),
    url('../assets/remman/dj/1Rem.jpg') center / cover;
  filter: saturate(0.72) contrast(1.14);
}

.hero__nav {
  position: relative;
  z-index: 2;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  padding-top: 1.35rem;
}

.hero__brand {
  font-weight: 900;
  letter-spacing: 0.14em;
}

.hero__hamburger {
  display: none;
  background: none;
  border: 1px solid var(--border);
  border-radius: var(--radius);
  color: var(--text);
  padding: 0.4rem 0.7rem;
  font-size: 1.1rem;
  cursor: pointer;
  line-height: 1;
  transition: border-color 0.2s ease;
  z-index: 3;
}

.hero__hamburger:hover {
  border-color: rgba(255, 248, 239, 0.4);
}

.hero__nav-links {
  display: flex;
  gap: clamp(0.8rem, 2.2vw, 1.8rem);
  color: var(--text-muted);
  font-size: 0.86rem;
  font-weight: 700;
  text-transform: uppercase;
}

.hero__nav-links a {
  transition: color 0.2s ease;
}

.hero__nav-links a:hover,
.hero__nav-links a:focus-visible {
  color: var(--text);
}

.hero__content {
  position: relative;
  min-height: calc(96vh - 72px);
  z-index: 1;
  display: grid;
  grid-template-columns: minmax(0, 1.18fr) minmax(280px, 0.82fr);
  align-items: end;
  gap: clamp(2rem, 7vw, 6rem);
  padding: 7rem 0 5rem;
  animation: fade-up 0.9s ease both;
}

.hero__content > * {
  min-width: 0;
}

.hero h1 {
  margin: 0;
  font-family: Georgia, 'Times New Roman', serif;
  font-size: clamp(4.5rem, 11vw, 12rem);
  line-height: 0.78;
  letter-spacing: 0;
  text-shadow: 0 18px 60px rgba(0, 0, 0, 0.45);
}

.hero__subtitle {
  font-size: clamp(1.25rem, 2.6vw, 2rem);
  line-height: 1.15;
  margin: 0 0 1rem;
}

.hero__text {
  max-width: 48ch;
  color: var(--text-muted);
}

.hero__cta {
  margin-top: 1.7rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  min-height: 48px;
  background: var(--accent);
  color: #fff8ef;
  padding: 0.9rem 1.45rem;
  border: 1px solid rgba(255, 248, 239, 0.16);
  border-radius: var(--radius);
  font-weight: 900;
  letter-spacing: 0.08em;
  text-transform: uppercase;
  transition: transform 0.22s ease, box-shadow 0.22s ease, background 0.22s ease;
}

.hero__cta:hover {
  transform: translateY(-2px);
  background: #b92b16;
  box-shadow: 0 18px 36px rgba(255, 61, 31, 0.28);
}

.hero__cta:focus-visible {
  outline: 3px solid rgba(246, 196, 107, 0.9);
  outline-offset: 3px;
}

.hero__scroll-indicator {
  position: absolute;
  bottom: 2rem;
  left: 50%;
  transform: translateX(-50%);
  z-index: 2;
  font-size: 1.4rem;
  color: var(--text-muted);
  opacity: 1;
  transition: opacity 0.4s ease;
  animation: bob 2s ease-in-out infinite;
}

.hero__scroll-indicator.is-hidden {
  opacity: 0;
  pointer-events: none;
}

@keyframes bob {
  0%, 100% { transform: translateX(-50%) translateY(0); }
  50% { transform: translateX(-50%) translateY(6px); }
}

@keyframes fade-up {
  from {
    opacity: 0;
    transform: translateY(24px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

@media (max-width: 1024px) {
  .hero__content {
    grid-template-columns: 1fr;
    align-items: end;
    min-height: calc(92vh - 72px);
    padding: 6rem 0 4rem;
  }

  .hero__copy {
    order: -1;
  }

  .hero::before {
    background:
      linear-gradient(180deg, rgba(7, 6, 5, 0.6), rgba(7, 6, 5, 0.95) 72%, var(--bg)),
      url('../assets/remman/dj/1Rem.jpg') center / cover;
  }
}

@media (max-width: 620px) {
  .hero__nav {
    align-items: center;
    flex-wrap: wrap;
  }

  .hero__hamburger {
    display: block;
  }

  .hero__nav-links {
    display: none;
    flex-direction: column;
    width: 100%;
    padding: 0.75rem 0;
    gap: 0.9rem;
  }

  .hero__nav-links.is-open {
    display: flex;
  }
}
</style>
