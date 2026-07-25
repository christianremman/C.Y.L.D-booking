import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import HowItWorks from '../HowItWorks.vue'

describe('HowItWorks', () => {
  it('renders three steps', () => {
    const wrapper = mount(HowItWorks)
    expect(wrapper.findAll('.step')).toHaveLength(3)
  })

  it('step headings are Enquire, Connect, Book', () => {
    const wrapper = mount(HowItWorks)
    const headings = wrapper.findAll('.step h3').map((h) => h.text())
    expect(headings).toEqual(['Enquire', 'Connect', 'Book'])
  })

  it('Connect step mentions 24h response', () => {
    const wrapper = mount(HowItWorks)
    expect(wrapper.text()).toContain('24')
  })
})
