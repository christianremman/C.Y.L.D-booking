import { describe, it, expect } from 'vitest'
import { mount } from '@vue/test-utils'
import StatsStrip from '../StatsStrip.vue'

describe('StatsStrip', () => {
  it('renders three stats', () => {
    const wrapper = mount(StatsStrip)
    expect(wrapper.findAll('.stat')).toHaveLength(3)
  })

  it('shows 100+, 2+, 2 as stat numbers', () => {
    const wrapper = mount(StatsStrip)
    const numbers = wrapper.findAll('.stat__number').map((n) => n.text())
    expect(numbers).toContain('100+')
    expect(numbers).toContain('2+')
    expect(numbers).toContain('2')
  })

  it('shows Events, Years, DJs as labels', () => {
    const wrapper = mount(StatsStrip)
    const labels = wrapper.findAll('.stat__label').map((l) => l.text())
    expect(labels).toContain('Events')
    expect(labels).toContain('Years')
    expect(labels).toContain('DJs')
  })
})
