import { ref, onMounted } from 'vue'

export function useScrollReveal(el, options = {}) {
  const isVisible = ref(false)

  onMounted(() => {
    if (!el?.value) return

    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      isVisible.value = true
      return
    }

    const observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          isVisible.value = true
          observer.disconnect()
        }
      },
      { threshold: 0.12, ...options },
    )

    observer.observe(el.value)
  })

  return { isVisible }
}
