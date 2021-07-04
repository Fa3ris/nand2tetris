// push constant 10
@10
D=A	// D = 10
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 10
// pop local 0
@LCL
D=M	// D = LCL
@0
D=D+A	// D = LCL + 0
@R13
M=D	// R13 = LCL + 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = LCL + 0
M=D	// *(LCL + 0) = *SP
// push constant 21
@21
D=A	// D = 21
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 21
// push constant 22
@22
D=A	// D = 22
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 22
// pop argument 2
@ARG
D=M	// D = ARG
@2
D=D+A	// D = ARG + 2
@R13
M=D	// R13 = ARG + 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = ARG + 2
M=D	// *(ARG + 2) = *SP
// pop argument 1
@ARG
D=M	// D = ARG
@1
D=D+A	// D = ARG + 1
@R13
M=D	// R13 = ARG + 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = ARG + 1
M=D	// *(ARG + 1) = *SP
// push constant 36
@36
D=A	// D = 36
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 36
// pop this 6
@THIS
D=M	// D = THIS
@6
D=D+A	// D = THIS + 6
@R13
M=D	// R13 = THIS + 6
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THIS + 6
M=D	// *(THIS + 6) = *SP
// push constant 42
@42
D=A	// D = 42
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 42
// push constant 45
@45
D=A	// D = 45
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 45
// pop that 5
@THAT
D=M	// D = THAT
@5
D=D+A	// D = THAT + 5
@R13
M=D	// R13 = THAT + 5
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 5
M=D	// *(THAT + 5) = *SP
// pop that 2
@THAT
D=M	// D = THAT
@2
D=D+A	// D = THAT + 2
@R13
M=D	// R13 = THAT + 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 2
M=D	// *(THAT + 2) = *SP
// push constant 510
@510
D=A	// D = 510
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 510
// pop temp 6
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@11	// A = 5 + 6
M=D	// *(5 + 6) = *SP
// push local 0
@LCL
D=M	// D = LCL
@0
A=D+A	// A = LCL + 0
D=M	// D = *(LCL + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 0)
// push that 5
@THAT
D=M	// D = THAT
@5
A=D+A	// A = THAT + 5
D=M	// D = *(THAT + 5)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THAT + 5)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// push argument 1
@ARG
D=M	// D = ARG
@1
A=D+A	// A = ARG + 1
D=M	// D = *(ARG + 1)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 1)
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// push this 6
@THIS
D=M	// D = THIS
@6
A=D+A	// A = THIS + 6
D=M	// D = *(THIS + 6)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THIS + 6)
// push this 6
@THIS
D=M	// D = THIS
@6
A=D+A	// A = THIS + 6
D=M	// D = *(THIS + 6)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THIS + 6)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// push temp 6
@11	// A = 5 + 6
D=M	// D = *(5 + 6)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(5 + 6)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
