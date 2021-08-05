class Hand {
  value;

  constructor(value, name) {
    this.value = value;
    this.name = name;
  }

  static Rock() {
    return new Hand(2, 'rock');
  }

  static Paper() {
    return new Hand(1, 'paper');
  }

  static Scissors() {
    return new Hand(3, 'scissors');
  }

  static compare(hand1, hand2) {
    let res = hand2.value - hand1.value;
    if (res > 1) {
      res = -1;
    }

    if (res < -1) {
      res = 1;
    }

    return res;
  }
}

const paper = Hand.Paper();
const scissors = Hand.Scissors();
const rock = Hand.Rock();

console.log(paper);
console.log(scissors);
console.log(rock);

console.log(Hand.compare(paper, scissors));
compareAndPrint(paper, scissors);
console.log(Hand.compare(scissors, scissors));
compareAndPrint(scissors, scissors);
console.log(Hand.compare(rock, scissors));
compareAndPrint(rock, scissors);

console.log('\n');

console.log(Hand.compare(paper, paper));
compareAndPrint(paper, paper);
console.log(Hand.compare(scissors, paper));
compareAndPrint(scissors, paper);
console.log(Hand.compare(rock, paper));
compareAndPrint(rock, paper);

console.log('\n');

console.log(Hand.compare(paper, rock));
compareAndPrint(paper, rock);
console.log(Hand.compare(scissors, rock));
compareAndPrint(scissors, rock);
console.log(Hand.compare(rock, rock));
compareAndPrint(rock, rock);

function compareAndPrint(hand1, hand2) {
  const res = Hand.compare(hand1, hand2);
  let compareMsg = `P1 '${hand1.name}' vs P2 '${hand2.name}' :`;
  let msg;
  if (res == 0) {
    msg = 'draw';
  }

  if (res > 0) {
    msg = 'P1 wins';
  }

  if (res < 0) {
    msg = 'P2 wins';
  }

  console.log(compareMsg, msg);
}
