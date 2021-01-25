/**
 * Get the first item that pass the test
 * by second argument function
 *
 * @param {Array} list
 * @param {Function} f
 * @return {*}
 */
export function find (list, f) {
  return list.filter(f)[0]
}

/**
 * Deep copy the given object considering circular structure.
 * This function caches all nested objects and its copies.
 * If it detects circular structure, use cached copy to avoid infinite loop.
 *
 * @param {*} obj
 * @param {Array<Object>} cache
 * @return {*}
 */
export function deepCopy (obj, cache = []) {
  // just return if obj is immutable value
  if (obj === null || typeof obj !== 'object') {
    return obj
  }

  // if obj is hit, it is in circular structure
  const hit = find(cache, c => c.original === obj)
  if (hit) {
    return hit.copy
  }

  const copy = Array.isArray(obj) ? [] : {}
  // put the copy into cache at first
  // because we want to refer it in recursive deepCopy
  cache.push({
    original: obj,
    copy
  })

  Object.keys(obj).forEach(key => {
    copy[key] = deepCopy(obj[key], cache)
  })

  return copy
}

/**
 * forEach for object
 */
export function forEachValue (obj, fn) {
  Object.keys(obj).forEach(key => fn(obj[key], key))
}

export function isObject (obj) {
  return obj !== null && typeof obj === 'object'
}

export function isPromise (val) {
  return val && typeof val.then === 'function'
}

export function assert (condition, msg) {
  if (!condition) throw new Error(`[vuex] ${msg}`)
}

export function partial (fn, arg) {
  return function () {
    return fn(arg)
  }
}

export function objectFilter (obj, predicate) {
  const result = {}
  let key

  for (key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key) && predicate(obj[key])) {
      result[key] = obj[key]
    }
  }

  return result
}

/**
 * find menu name for route path
 * @param menu
 * @param routePath ex) /manager/list
 * @returns {string}
 */
export function findMenuName (menu, routeName) {
  const menuObj = searchMenu(null, menu, routeName)
  return menuObj != null ? menuObj.title : ''
}

function searchMenu (parent, child, routeName) {
  if (parent !== null && child.to && child.to === routeName) {
    return child
  } else if (child.children) {
    for (let i = 0; i < child.children.length; i++) {
      const result = searchMenu(child, child.children[i], routeName)
      if (result != null) {
        return result
      }
    }
  }
  return null
}

export function dayToKr (day) {
  const days = ['일', '월', '화', '수', '목', '금', '토']
  return days[day]
}

export function setCookie (cname, cvalue) {
  document.cookie = cname + '=' + cvalue
}

export function deleteCookie (cname) {
  document.cookie = cname + '= ; max-age=-1'
}

export function getCookie (name) {
  const value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)')
  return value ? value[2] : null
}

export function calculatePercentage (actual, total, pointLength = 1) {
  const percentage = (actual / total) * 100
  if (isNaN(percentage) || percentage === 0) {
    return 0
  }

  return isInteger(percentage) ? percentage : percentage.toFixed(pointLength)
}

export function calculateAverage (actual, total, pointLength = 1) {
  const average = actual / total
  if (isNaN(average) || average === 0) {
    return 0
  }

  return isInteger(average) ? average : average.toFixed(pointLength)
}

export function isInteger (n) {
  return n % 1 === 0
}

export function isEqualsObject (source, target) {
  try {
    for (const key in target) {
      const targetValue = target[key]
      let sourceValue = source[key]
      if (sourceValue === undefined || sourceValue === null) {
        sourceValue = typeof targetValue === 'object' ? null : ''
      }

      if (targetValue instanceof Array) {
        if (!isEqualsArray(sourceValue, targetValue)) {
          return false
        }
      } else if (typeof targetValue === 'object') {
        if (!isEqualsObject(sourceValue, targetValue)) {
          return false
        }
      } else if (targetValue !== sourceValue) {
        return false
      }
    }

    return true
  } catch (e) {
    // undefined property 때문에 false로 return
    return false
  }
}

export function isEqualsArray (source = [], target = []) {
  return target.filter((v, i) => !isEqualsObject(source[i], v)).length === 0
}

export function createDefaultDisplayStartDateTime () {
  const now = new Date()

  if (now.getHours() >= 13) {
    now.setDate(now.getDate() + 1)
  }

  return `${now.toISOString().substr(0, 10)} 13:00:00`
}

export function defaultIfBlank (str, defaultValue = '-') {
  const emptyRegExp = /^\s+$/

  return !str || emptyRegExp.test(str) ? defaultValue : str
}

export function scrollTop () {
  window.scrollTo(0, 0)
}

export function maskingId (value) {
  if (value === undefined || value === '') {
    return ''
  }
  let masking = ''
  let totalLength = value.length
  let starIndex = 0

  switch (value.length) {
    case 5:
    case 6:
      starIndex = 2
      break
    case 7:
      starIndex = 3
      break
    default:
      if (totalLength < 5) {
        starIndex = value.length
        totalLength += 4
      } else {
        starIndex = 4
        totalLength = totalLength > 8 ? 8 : totalLength
      }
      break
  }
  masking = value.substring(0, starIndex)
  for (let i = starIndex; i < totalLength; i++) {
    masking += '*'
  }
  return masking
}
