// import wilcoxon from '@stdlib/stats-wilcoxon'

// const x = [29738, 30331, 29550, 29808, 29941, 28966, 30457, 29547, 27159, 29595, 30076, 29458, 29824, 28328, 28499, 29692, 30130, 30513, 29213, 29840, 30049, 28842, 27528, 29149, 28281, 30554, 29529, 30486, 30112]

// const y = [5788, 5749, 5602, 5540, 5560, 5593, 5592, 5739, 5564, 5731, 5536, 5653, 5485, 5786, 5576, 5611, 6254, 5684, 5625, 5714, 5605, 5550, 5530, 5474, 5480, 5511, 5634, 5689, 5606]

// const z = [5651, 5749, 5602, 5540, 5560, 5593, 5592, 5739, 5564, 5731, 5536, 5653, 5485, 5786, 5576, 5611, 6254, 5684, 5625, 5714, 5605, 5550, 5530, 5474, 5480, 5511, 5634, 5689, 5606]

// const a = [5651, 5340, 5392, 5353, 5471, 5433, 5422, 5452, 5438, 5343, 5407, 5425, 5429, 5514, 5452, 5526, 5542, 5372, 5362, 5469, 5671, 5460, 5395, 5513, 5452, 5376, 5574, 5502, 5352]

// const t1 = [29738, 30331, 29550, 29808]
// const t2 = [5788, 5749, 5602, 5540]

// const out1 = wilcoxon(x, y)
// const out2 = wilcoxon(a, y)

// console.log(out1)

// pValue - prawdopodobienstwo tego ze nasza teza jest fałszywa, gdzie teza jest to ze mediana naszych warotsci ejst 0, i gdzy tak jest to nie ma roznic.

while (true) {
  let i = 1
  var h = 300
  var k = 400
  var step = (2 * Math.PI) / 25
  var r = 300
  for (var theta = 0; theta < 2 * Math.PI; theta += step) {
    var x = h + r * Math.cos(theta)
    var y = k - r * Math.sin(theta) //note 2.
    console.log(`${i} ${x} ${y}`)
    i++
  }

  var step = (2 * Math.PI) / 19
  var r = 226
  for (var theta = 0; theta < 2 * Math.PI; theta += step) {
    var x = h + r * Math.cos(theta)
    var y = k - r * Math.sin(theta) //note 2.
    console.log(`${i} ${x} ${y}`)
    i++
  }
  break
}
