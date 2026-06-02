import { describe, it, expect, vi, beforeEach, afterEach } from 'vitest'
import { mount, flushPromises } from '@vue/test-utils'
import BookingForm from '../BookingForm.vue'

describe('BookingForm fields', () => {
  it('renders all required fields', () => {
    const wrapper = mount(BookingForm)
    expect(wrapper.find('input[name="name"]').exists()).toBe(true)
    expect(wrapper.find('input[name="email"]').exists()).toBe(true)
    expect(wrapper.find('input[name="phone"]').exists()).toBe(true)
    expect(wrapper.find('input[name="eventDate"]').exists()).toBe(true)
    expect(wrapper.find('input[name="eventLocation"]').exists()).toBe(true)
    expect(wrapper.find('select[name="eventType"]').exists()).toBe(true)
    expect(wrapper.find('textarea[name="message"]').exists()).toBe(true)
  })

  it('submit button is enabled when turnstile is disabled (test env)', () => {
    const wrapper = mount(BookingForm)
    expect(wrapper.find('button[type="submit"]').element.disabled).toBe(false)
  })

  it('turnstile widget not rendered when site key absent', () => {
    const wrapper = mount(BookingForm)
    expect(wrapper.find('.booking-form__turnstile').exists()).toBe(false)
  })
})

describe('BookingForm submission', () => {
  beforeEach(() => {
    global.fetch = vi.fn()
  })

  afterEach(() => {
    vi.clearAllMocks()
    delete global.fetch
  })

  it('shows success message on ok response', async () => {
    global.fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve({ success: true, message: 'Booking request sent.' }),
    })

    const wrapper = mount(BookingForm)
    await wrapper.find('form').trigger('submit')
    await flushPromises()

    expect(wrapper.find('.booking-form__status').classes()).toContain('is-success')
    expect(wrapper.find('.booking-form__status').text()).toContain('Booking request sent.')
  })

  it('shows error message on failed response', async () => {
    global.fetch.mockResolvedValue({
      ok: false,
      status: 500,
      json: () => Promise.resolve({}),
    })

    const wrapper = mount(BookingForm)
    await wrapper.find('form').trigger('submit')
    await flushPromises()

    expect(wrapper.find('.booking-form__status').classes()).toContain('is-error')
  })

  it('shows server validation message on 400', async () => {
    global.fetch.mockResolvedValue({
      ok: false,
      status: 400,
      json: () => Promise.resolve({ message: 'Name is required.' }),
    })

    const wrapper = mount(BookingForm)
    await wrapper.find('form').trigger('submit')
    await flushPromises()

    expect(wrapper.find('.booking-form__status').text()).toContain('Name is required.')
  })

  it('disables submit button while submitting', async () => {
    let resolveRequest
    global.fetch.mockReturnValue(new Promise((resolve) => { resolveRequest = resolve }))

    const wrapper = mount(BookingForm)
    wrapper.find('form').trigger('submit')
    await wrapper.vm.$nextTick()

    expect(wrapper.find('button[type="submit"]').element.disabled).toBe(true)

    resolveRequest({ ok: true, json: () => Promise.resolve({ success: true, message: 'sent' }) })
    await flushPromises()
  })

  it('resets form fields after successful submission', async () => {
    global.fetch.mockResolvedValue({
      ok: true,
      json: () => Promise.resolve({ success: true, message: 'Booking request sent.' }),
    })

    const wrapper = mount(BookingForm)
    await wrapper.find('input[name="name"]').setValue('John Doe')
    await wrapper.find('form').trigger('submit')
    await flushPromises()

    expect(wrapper.find('input[name="name"]').element.value).toBe('')
  })
})
