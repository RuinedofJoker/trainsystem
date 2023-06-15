function useDebounce(fn, delay) {
  let timer = null

  return function (...args) {
    timer && clearTimeout(timer)

    timer = setTimeout(() => {
      timer = null
      fn.apply(this, args)
    }, delay)
  }
}

export default useDebounce