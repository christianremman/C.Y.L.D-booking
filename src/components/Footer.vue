<template>
  <footer>
    <div class="section-shell footer-inner">
      <div>
        <strong>C.Y.L.D</strong>
        <p>Bookings & Events</p>
      </div>
      <a href="https://instagram.com/christianremman/" target="_blank" rel="noreferrer">Instagram</a>
    </div>
  </footer>
</template>

<style scoped>
footer {
  border-top: 1px solid var(--border);
  padding: 1.7rem 0 2.4rem;
  margin-top: 1.6rem;
}

.footer-inner {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 1rem;
}

.footer-inner p {
  margin: 0.35rem 0 0;
  color: var(--text-muted);
}

.footer-inner a {
  color: var(--accent-2);
}

@media (max-width: 620px) {
  .footer-inner {
    flex-direction: column;
    align-items: flex-start;
  }
}
</style>
