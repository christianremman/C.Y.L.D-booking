<template>
  <header class="hero" id="top">
    <div class="section-shell hero__nav" aria-label="Primary navigation">
      <a href="#top" class="hero__brand">C.Y.L.D</a>
      <nav>
        <a href="#about">About</a>
        <a href="#djs">DJs</a>
        <a href="#events">Events</a>
        <a href="#booking">Booking</a>
      </nav>
    </div>

    <div class="section-shell hero__content">
      <div>
        <p class="text-kicker">Bookings & Events</p>
        <h1>C.Y.L.D</h1>
      </div>
      <div class="hero__copy">
        <p class="hero__subtitle">DJ collective for clubs, events and festivals.</p>
        <p class="hero__text">
          C.Y.L.D delivers versatile DJ performances tailored to your event. From clubs and bars to
          private parties and festivals, we bring the energy and adapt to the crowd.
        </p>
        <a href="#booking" class="hero__cta">Book Us</a>
      </div>
    </div>
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

nav {
  display: flex;
  gap: clamp(0.8rem, 2.2vw, 1.8rem);
  color: var(--text-muted);
  font-size: 0.86rem;
  font-weight: 700;
  text-transform: uppercase;
}

nav a {
  transition: color 0.2s ease;
}

nav a:hover,
nav a:focus-visible {
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

.hero h1 {
  margin: 0;
  font-family: Georgia, 'Times New Roman', serif;
  font-size: clamp(4.5rem, 15vw, 12rem);
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

@media (max-width: 820px) {
  .hero__content {
    grid-template-columns: 1fr;
    align-items: end;
    min-height: calc(92vh - 72px);
    padding: 6rem 0 4rem;
  }

  .hero::before {
    background:
      linear-gradient(180deg, rgba(7, 6, 5, 0.6), rgba(7, 6, 5, 0.95) 72%, var(--bg)),
      url('../assets/remman/dj/1Rem.jpg') center / cover;
  }
}

@media (max-width: 620px) {
  .hero__nav {
    align-items: flex-start;
  }

  nav {
    display: none;
  }
}
</style>
