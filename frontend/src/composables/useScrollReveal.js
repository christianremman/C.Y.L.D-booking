import { ref, onMounted, onUnmounted } from 'vue'

export function useScrollReveal(el) {
  const isVisible = ref(false)
  let observer

  onMounted(() => {
    if (!el?.value) return

    if (window.matchMedia('(prefers-reduced-motion: reduce)').matches) {
      isVisible.value = true
      return
    }

    observer = new IntersectionObserver(
      ([entry]) => {
        if (entry.isIntersecting) {
          isVisible.value = true
          observer.disconnect()
        }
      },
      { threshold: 0.12 },
    )

    observer.observe(el.value)
  })

  onUnmounted(() => observer?.disconnect())

  return { isVisible }
}
