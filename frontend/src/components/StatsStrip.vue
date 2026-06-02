<template>
  <div class="stats-strip">
    <div class="stat">
      <span class="stat__number">100+</span>
      <span class="stat__label">Events</span>
    </div>
    <div class="stat">
      <span class="stat__number">2+</span>
      <span class="stat__label">Years</span>
    </div>
    <div class="stat">
      <span class="stat__number">2</span>
      <span class="stat__label">DJs</span>
    </div>
  </div>
</template>

<style scoped>
.stats-strip {
  display: flex;
  gap: clamp(2rem, 8vw, 5rem);
  padding: 2.5rem 0;
  border-top: 1px solid var(--border);
  border-bottom: 1px solid var(--border);
}

.stat {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.stat__number {
  font-family: Georgia, 'Times New Roman', serif;
  font-size: clamp(2.2rem, 5vw, 3.5rem);
  line-height: 1;
  color: var(--text);
}

.stat__label {
  font-size: 0.78rem;
  font-weight: 800;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: var(--text-muted);
}
</style>
