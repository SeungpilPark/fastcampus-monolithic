export function objectFilter(obj, predicate) {
  const result = {}
  let key

  for (key in obj) {
    if (Object.prototype.hasOwnProperty.call(obj, key) && predicate(obj[key])) {
      result[key] = obj[key]
    }
  }

  return result
}
