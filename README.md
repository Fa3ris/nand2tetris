# Project 1

## Given Gate : **Nand**

1. ## Elementary Logic Gates

## Not
```
Not a = a Nand a
```
## And
```
a AND b = (a Nand b) Nand (a Nand b)
```
## Or
```
a OR b = Not(Not(a) And Not(b))
```
## Xor
```
 a Xor b = (a Nand b) And (a Or b)
```
## Mux
definition: out = a if sel == 0, out = b otherwise
```
out = (Not(sel) And a) Or (sel And b)
```
## Dmux

{a, b} = 
* {in, 0} if sel == 0
* {0, in} if sel == 1

```
a = Not(sel) And in
b = sel And  in
```

2. ## 16-bit variants

## Not16
## And16
## Or16
## Mux16

3. ## Multi-way variants

## Or 8-way
tournament-like
```
| | | | | | | |

 |   |   |   |

   |       |
       |
```
## Mux 4-way 16-bit
```
out = a if sel == 00
      b if sel == 01
      c if sel == 10
      d if sel == 11
```
```
x1 = Mux(a, b) on LSB of sel 
x2 = Mux(c, d) on LSB of sel 
out = Mux(x1, x2) on MSB of sel
```
## Mux 8-way 16-bit
```
out = a if sel == 000
      b if sel == 001
      etc.
      h if sel == 111
```
```
x1 = Mux4(a, b, c, d) on 2 LSB of sel 
x2 = Mux4(e, f, g, h) on 2 LSB of sel 
Mux(x1, x2) on MSB of sel
```
## DMux 4-way
```
{a, b, c, d} = {in, 0, 0, 0} if sel == 00
               {0, in, 0, 0} if sel == 01
               {0, 0, in, 0} if sel == 10
               {0, 0, 0, in} if sel == 11
```
```
DMux(in) on MSB of sel
    if 0 then 
        y1 = a-b
    else
        y2 = c-d
Dmux(y1) on LSB of sel
    if 0 then
        output on a
    else
        output on  b
Dmux(y2) on LSB of sel
    if 0 then
        output on  c
    else
        output on  d
```
## DMux 8-way
```
{a, b, c, d, e, f, g, h} = {in, 0, 0, 0, 0, 0, 0, 0} if sel == 000
                           {0, in, 0, 0, 0, 0, 0, 0} if sel == 001
                           etc.
                           {0, 0, 0, 0, 0, 0, 0, in} if sel == 111
```
```
DMux(in) on MSB of sel
    if 0 then
        y1 = a-b-c-d
    else
        y2 = e-f-g- h
DMux4(y1) on 2 LSB of sel
    if 00
        output on a
    else if 01
        output on b
    else if 10
        output on c
    else
        output on d
DMux4(y2) on 2 LSB of sel
    if 00
        output on e
    else if 01
        output on f
    else if 10
        output on g
    else
        output on h
```
# Project 2

## Half Adder
```
sum = a Xor b // =1 if only one is 1 
carry = a And b // =1 if both are 1
```
## Full Adder
```
sum = LSB of a + b + c
carry = MSB of a + b + c
```
```
HalfAdder(a, b, sum1, carry1)
HalfAdder(sum1, c, sum, carry2)
carry = carry1 Or carry2
```
Possibles values

```
0 = 00 = 0 + 0 + 0

1 = 01 = combo of 1-0-0 // no carry

2 = 10 = combo of 1-1-0 // 1 carry
ex: a=1, b=1, c=0
a+b = 1+1 = 10 => sum1 = 0, carry1 = 1
sum1 + c = 0 + 0 = 0 => sum = 0, carry2 = 0
carry = carry1 or carry2 = 1 or 0 = 1

ex: a=1, b=0, c=1
a+b = 1+0 = 01 => sum1 = 1, carry1 = 0
sum1 + c = 1 + 1 = 10 => sum = 0, carry2 = 1
carry = carry1 or carry2 = 0 or 1 = 1

3 = 11 = 1 + 1 + 1 // 1 carry
a+b = 1+1 = 10 => sum1 = 0, carry1 = 1
sum1 + c = 0 + 1 = 1 => sum = 1, carry2 = 0
carry = carry1 or carry2 = 1 or 0 = 1

```
## Adder 16-bit
* Chain HalfAdder
* Optimization: carry look-ahead
## Inc 16-bit
Adder with constant 1
## ALU
### 2's complement: ~x
**x + ~x = 2^n** by definition

**~x = 1 + Not(x)**

**~x = Not(x + 2^n - 1)**

**~x = Not(x + 11...11)** (same as above)

```
~x = 2's complement of x
~x = 2^n - x, n = word size

We prove
(2^n - 1) - x = (11...11) - x = Not(x) (flip all bits)

so 
~x = 2^n - x = 1 + (2^n - 1) - x = 1 + Not(x)

also
x + ~x = 2^n = (1)00...00 = 1 bit of overflow followed by 0s
```

```
Not(x + 2^n - 1) = ~x

proof
let Y = x + 2^n - 1

Not(Y) = ~Y - 1 // because ~Y = 1 + Not(Y) 
       = 2^n - Y - 1 // because ~Y = 2^n = Y
       = -x // replace Y by expression
```





