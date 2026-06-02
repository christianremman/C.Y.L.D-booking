import { describe, it, expect, vi, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import { defineComponent, ref } from 'vue'
import { useScrollReveal } from '../useScrollReveal'

let mockCallback
let mockObserve
let mockDisconnect

beforeEach(() => {
  mockCallback = null
  mockObserve = vi.fn()
  mockDisconnect = vi.fn()

  global.IntersectionObserver = vi.fn((cb) => {
    mockCallback = cb
    return { observe: mockObserve, disconnect: mockDisconnect, unobserve: vi.fn() }
  })

  window.matchMedia = vi.fn().mockImplementation((query) => ({
    matches: false,
    media: query,
    addListener: vi.fn(),
    removeListener: vi.fn(),
  }))
})

function mountWithComposable(elValue = null) {
  const el = ref(elValue)
  let exposed
  mount(defineComponent({
    setup() {
      exposed = useScrollReveal(el)
      return {}
    },
    template: '<div></div>',
  }))
  return exposed
}

describe('useScrollReveal', () => {
  it('isVisible is false initially', () => {
    const { isVisible } = mountWithComposable()
    expect(isVisible.value).toBe(false)
  })

  it('sets isVisible true when element intersects', () => {
    const el = document.createElement('div')
    const { isVisible } = mountWithComposable(el)
    mockCallback([{ isIntersecting: true }])
    expect(isVisible.value).toBe(true)
  })

  it('does not set visible when not intersecting', () => {
    const el = document.createElement('div')
    const { isVisible } = mountWithComposable(el)
    mockCallback([{ isIntersecting: false }])
    expect(isVisible.value).toBe(false)
  })

  it('disconnects observer after becoming visible', () => {
    const el = document.createElement('div')
    mountWithComposable(el)
    mockCallback([{ isIntersecting: true }])
    expect(mockDisconnect).toHaveBeenCalledOnce()
  })

  it('sets isVisible true immediately when prefers-reduced-motion', () => {
    window.matchMedia = vi.fn().mockReturnValue({ matches: true })
    const el = document.createElement('div')
    const { isVisible } = mountWithComposable(el)
    expect(isVisible.value).toBe(true)
    expect(mockObserve).not.toHaveBeenCalled()
  })
})
