import wilcoxon from '@stdlib/stats-wilcoxon'

const x = [8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0, 8937.0]

const y = [8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0]

const z = [8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0, 8160.0]

const a = [7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0, 7730.0]

const out1 = wilcoxon(x, y)
const out2 = wilcoxon(x, z)
const out3 = wilcoxon(x, a)

console.log(out1)
console.log(out2)
console.log(out3)

// pValue - prawdopodobienstwo tego ze nasza teza jest fałszywa, gdzie teza jest to ze mediana naszych warotsci ejst 0, i gdzy tak jest to nie ma roznic.

// while (true) {
//   let i = 1
//   var h = 300
//   var k = 400
//   var step = (2 * Math.PI) / 25
//   var r = 300
//   for (var theta = 0; theta < 2 * Math.PI; theta += step) {
//     var x = h + r * Math.cos(theta)
//     var y = k - r * Math.sin(theta) //note 2.
//     console.log(`${i} ${x} ${y}`)
//     i++
//   }

//   var step = (2 * Math.PI) / 19
//   var r = 226
//   for (var theta = 0; theta < 2 * Math.PI; theta += step) {
//     var x = h + r * Math.cos(theta)
//     var y = k - r * Math.sin(theta) //note 2.
//     console.log(`${i} ${x} ${y}`)
//     i++
//   }
//   break
// }
