<template>
  <section id="socials">
    <p class="text-kicker">Contact</p>
    <h2 class="section-heading">Connect</h2>
    <div class="social-list">
      <a href="https://instagram.com/christianremman/" rel="noreferrer" aria-label="Instagram">Instagram</a>
      <a href="https://soundcloud.com" rel="noreferrer" aria-label="SoundCloud">SoundCloud</a>
      <a href="mailto:cremman@me.com" aria-label="Email">cremman@me.com</a>
    </div>
  </section>
</template>

<style scoped>
.social-list {
  display: flex;
  flex-wrap: wrap;
  gap: 0.9rem;
}

.social-list a {
  background: rgba(21, 17, 15, 0.86);
  border: 1px solid var(--border);
  padding: 0.85rem 1rem;
  border-radius: var(--radius);
  color: var(--text-muted);
  font-weight: 800;
  transition: border-color 0.25s ease, transform 0.25s ease, color 0.25s ease;
}

.social-list a:hover {
  transform: translateY(-2px);
  border-color: var(--accent-2);
  color: var(--text);
}
</style>
