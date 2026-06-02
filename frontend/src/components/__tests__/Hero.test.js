import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Hero from '../Hero.vue'

describe('Hero mobile navigation', () => {
  it('nav is hidden by default', () => {
    const wrapper = mount(Hero)
    expect(wrapper.find('.hero__nav-links').classes()).not.toContain('is-open')
  })

  it('hamburger button exists', () => {
    const wrapper = mount(Hero)
    expect(wrapper.find('.hero__hamburger').exists()).toBe(true)
  })

  it('shows ☰ when menu closed', () => {
    const wrapper = mount(Hero)
    expect(wrapper.find('.hero__hamburger').text()).toBe('☰')
  })

  it('shows nav after hamburger click', async () => {
    const wrapper = mount(Hero)
    await wrapper.find('.hero__hamburger').trigger('click')
    expect(wrapper.find('.hero__nav-links').classes()).toContain('is-open')
  })

  it('shows ✕ when menu open', async () => {
    const wrapper = mount(Hero)
    await wrapper.find('.hero__hamburger').trigger('click')
    expect(wrapper.find('.hero__hamburger').text()).toBe('✕')
  })

  it('hides nav again after second click', async () => {
    const wrapper = mount(Hero)
    const btn = wrapper.find('.hero__hamburger')
    await btn.trigger('click')
    await btn.trigger('click')
    expect(wrapper.find('.hero__nav-links').classes()).not.toContain('is-open')
  })
})

describe('Hero scroll indicator', () => {
  it('renders scroll indicator linking to #about', () => {
    const wrapper = mount(Hero)
    const indicator = wrapper.find('.hero__scroll-indicator')
    expect(indicator.exists()).toBe(true)
    expect(indicator.attributes('href')).toBe('#about')
  })
})

describe('Hero copy', () => {
  it('subtitle is "Your event. Our energy."', () => {
    const wrapper = mount(Hero)
    expect(wrapper.find('.hero__subtitle').text()).toBe('Your event. Our energy.')
  })
})
