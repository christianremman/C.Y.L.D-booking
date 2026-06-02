import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import DJs from '../DJs.vue'

describe('DJs', () => {
  it('renders two DJ cards', () => {
    const wrapper = mount(DJs)
    expect(wrapper.findAll('.dj-card')).toHaveLength(2)
  })

  it('renders genre tags for each DJ', () => {
    const wrapper = mount(DJs)
    expect(wrapper.findAll('.tag').length).toBeGreaterThan(0)
  })

  it('each dj-card contains at least one tag', () => {
    const wrapper = mount(DJs)
    const cards = wrapper.findAll('.dj-card')
    for (const card of cards) {
      expect(card.findAll('.tag').length).toBeGreaterThan(0)
    }
  })
})
