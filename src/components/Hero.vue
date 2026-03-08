<template>
  <header class="hero" id="top">
    <div class="hero__overlay"></div>
    <div class="section-shell hero__content">
      <p class="hero__eyebrow">Bookings & Events</p>
      <h1>C.Y.L.D</h1>
      <p class="hero__subtitle">DJ Collective for Clubs, Events and Festivals</p>
      <p class="hero__text">
        C.Y.L.D delivers versatile DJ performances tailored to your event. From clubs and bars to
        private parties and festivals, we bring the energy and adapt to the crowd.
      </p>
      <a href="#booking" class="hero__cta">Book Us</a>
    </div>
  </header>
</template>

<style scoped>
.hero {
  min-height: 92vh;
  display: grid;
  align-items: center;
  position: relative;
  overflow: hidden;
}

.hero__overlay {
  position: absolute;
  inset: 0;
  background: radial-gradient(circle at 75% 20%, rgba(0, 210, 255, 0.2), transparent 35%),
    radial-gradient(circle at 20% 80%, rgba(110, 93, 255, 0.22), transparent 40%);
}

.hero__content {
  position: relative;
  z-index: 1;
  animation: fade-up 0.9s ease both;
}

.hero h1 {
  margin: 0;
  font-size: clamp(3rem, 9vw, 6rem);
  letter-spacing: 0.08em;
}

.hero__eyebrow {
  color: var(--accent-2);
  text-transform: uppercase;
  letter-spacing: 0.25em;
  font-size: 0.78rem;
  margin-bottom: 0.9rem;
}

.hero__subtitle {
  font-size: clamp(1.1rem, 2.5vw, 1.9rem);
  margin: 0.35rem 0 1rem;
}

.hero__text {
  max-width: 60ch;
  color: var(--text-muted);
}

.hero__cta {
  margin-top: 1.7rem;
  display: inline-block;
  background: linear-gradient(120deg, var(--accent), var(--accent-2));
  color: #fff;
  padding: 0.95rem 1.8rem;
  border-radius: 999px;
  font-weight: 600;
  letter-spacing: 0.02em;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.hero__cta:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 28px rgba(0, 210, 255, 0.28);
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
</style>
