<template>
  <section id="socials">
    <h2 class="section-heading">Connect</h2>
    <div class="social-list">
      <a href="https://instagram.com" target="_blank" rel="noreferrer" aria-label="Instagram">📸 Instagram</a>
      <a href="https://soundcloud.com" target="_blank" rel="noreferrer" aria-label="SoundCloud">🔊 SoundCloud</a>
      <a href="mailto:bookings@cyldmusic.com" aria-label="Email">✉️ bookings@cyldmusic.com</a>
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
  background: rgba(17, 19, 29, 0.85);
  border: 1px solid var(--border);
  padding: 0.85rem 1.1rem;
  border-radius: 999px;
  transition: border-color 0.25s ease, transform 0.25s ease;
}

.social-list a:hover {
  transform: translateY(-2px);
  border-color: var(--accent-2);
}
</style>
