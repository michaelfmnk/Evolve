const imgs = [
  "/img/1.jpg",
  "/img/2.jpg",
  "/img/3.jpg",
]

function randomInteger(min, max) {
  var rand = min - 0.5 + Math.random() * (max - min + 1)
  rand = Math.round(rand);
  return rand;
}

export const randomImg = () => imgs[randomInteger(0,2)]