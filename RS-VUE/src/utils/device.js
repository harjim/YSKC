import enquireJs from 'enquire.js'

export const DEVICE_TYPE = {
  DESKTOP: 'desktop',
  TABLET: 'tablet',
  MINDESKTOP: 'minDesktop',
  MOBILE: 'mobile'
}

export const deviceEnquire = function (callback) {
  const matchDesktop = {
    match: () => {
      callback && callback(DEVICE_TYPE.DESKTOP)
    }
  }

  const matchLablet = {
    match: () => {
      callback && callback(DEVICE_TYPE.TABLET)
    }
  }
  const matchMinDesktop = {
    match: () => {
      callback && callback(DEVICE_TYPE.MINDESKTOP)
    }
  }

  // const matchMobile = {
  //   match: () => {
  //     callback && callback(DEVICE_TYPE.MOBILE)
  //   }
  // }
  // screen and (max-width: 1087.99px)
  enquireJs
    // .register('screen and (max-width: 576px)', matchMobile)
    .register('screen and (min-width: 576px) and (max-width: 1024px)', matchMinDesktop)
    .register('screen and (min-width: 1025px) and (max-width: 1199px)', matchLablet)
    .register('screen and (min-width: 1200px)', matchDesktop)
}
