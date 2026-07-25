import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import Listen from '../Listen.vue'

describe('Listen', () => {
  it('renders section with id listen', () => {
    const wrapper = mount(Listen)
    expect(wrapper.find('section#listen').exists()).toBe(true)
  })

  it('renders heading Hear Us Play', () => {
    const wrapper = mount(Listen)
    expect(wrapper.find('h2').text()).toBe('Hear Us Play')
  })

  it('renders two player cards', () => {
    const wrapper = mount(Listen)
    expect(wrapper.findAll('.listen-card')).toHaveLength(2)
  })

  it('each card has an iframe with SoundCloud src', () => {
    const wrapper = mount(Listen)
    const iframes = wrapper.findAll('iframe')
    expect(iframes).toHaveLength(2)
    for (const iframe of iframes) {
      expect(iframe.attributes('src')).toContain('w.soundcloud.com/player')
    }
  })

  it('renders DJ names in cards', () => {
    const wrapper = mount(Listen)
    const text = wrapper.text()
    expect(text).toContain('REMMAN')
    expect(text).toContain('Disco Higgins')
  })
})
