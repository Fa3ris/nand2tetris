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
        y1 = in is directed to either a or b
    else
        y2 = in is directed to either c or d

Dmux(y1) on LSB of sel
    if 0 then
        in is directed to a
    else
        in is directed to b

Dmux(y2) on LSB of sel
    if 0 then
        in is directed to c
    else
        in is directed to d
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
        y1 = in is directed to either a, b, c or d
    else
        y2 = in is directed to either e, f, g or h

DMux4(y1) on 2 LSB of sel
    if 00
        in is directed to a
    else if 01
        in is directed to b
    else if 10
        in is directed to c
    else
        in is directed to d

DMux4(y2) on 2 LSB of sel
    if 00
        in is directed to e
    else if 01
        in is directed to f
    else if 10
        in is directed to g
    else
        in is directed to h
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
#### Definition
```
x + ~x = 2^n
x + ~x = (1)00...00 = 1 bit of overflow followed by n zeros

with n = word size = number of bits
```
#### Properties 1: values range
```
let n = word size

we have 2^n values = number of permutations of the bits

process x => ~x
flip all bits (Not)
add 1

max = 2^(n-1) - 1 = 011...111
min = -2^(n-1) = 100...00

0 = 00...00
1 = 00...01
-1 = 111...11

so we have 1 more negative number = the most negative number = MNN
~MNN = 2^n - MNN 
     = 2^n + 2^(n-1) 
     = (1)00...00 + 100...00 
     = 100...000 = MNN

for n = 4
max = 2^3 - 1 = 7 = 0111
min = -2^3 = -8 = 1000

SHAPE

The codes of all positive numbers begin with a 0.

The codes of all negative numbers begin with a 1.
```
#### Properties 2: shape
```
MSB of positive numbers = 0
MSB of negative numbers = 1
```
#### Properties 3: equations
```
~x = 1 + Not(x)

~x = Not(x + 2^n - 1)

~x = Not(x + 11...11) (same as above)
```
```
Proof ~x = 1 + Not(x)

First we prove (2^n - 1) - x = Not(x)
because (2^n - 1) - x = (11...11) - x = Not(x) (flip all bits)

so 
~x = 2^n - x = 1 + [(2^n - 1) - x] = 1 + Not(x)

```

```
Proof ~x = Not(x + 2^n - 1)

let Y = x + 2^n - 1

Not(Y) = ~Y - 1 // because ~Y = 1 + Not(Y) 
       = 2^n - Y - 1 // because ~Y = 2^n = Y
       = -x // replace Y by expression
```

### If p then x else y
compute x and y and let pass chosen value with Mux whose sel input is p


### check is negative

MSB is 1

### check is zero
Or(all bits) == 0




