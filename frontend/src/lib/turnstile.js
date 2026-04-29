const TURNSTILE_SCRIPT_ID = 'cloudflare-turnstile-script';
const TURNSTILE_SCRIPT_SRC = 'https://challenges.cloudflare.com/turnstile/v0/api.js?render=explicit';

let turnstileScriptPromise;

export const loadTurnstile = () => {
  if (window.turnstile) {
    return Promise.resolve(window.turnstile);
  }

  if (turnstileScriptPromise) {
    return turnstileScriptPromise;
  }

  turnstileScriptPromise = new Promise((resolve, reject) => {
    const existingScript = document.getElementById(TURNSTILE_SCRIPT_ID);

    if (existingScript) {
      existingScript.addEventListener('load', () => resolve(window.turnstile), { once: true });
      existingScript.addEventListener('error', reject, { once: true });
      return;
    }

    const script = document.createElement('script');
    script.id = TURNSTILE_SCRIPT_ID;
    script.src = TURNSTILE_SCRIPT_SRC;
    script.async = true;
    script.defer = true;
    script.addEventListener('load', () => resolve(window.turnstile), { once: true });
    script.addEventListener('error', () => reject(new Error('Turnstile could not be loaded.')), { once: true });
    document.head.appendChild(script);
  });

  return turnstileScriptPromise;
};

export const renderTurnstileWidget = (container, options) => window.turnstile.render(container, options);

export const resetTurnstileWidget = (widgetId) => {
  if (window.turnstile && widgetId !== null) {
    window.turnstile.reset(widgetId);
  }
};

export const removeTurnstileWidget = (widgetId) => {
  if (window.turnstile && widgetId !== null) {
    window.turnstile.remove(widgetId);
  }
};
