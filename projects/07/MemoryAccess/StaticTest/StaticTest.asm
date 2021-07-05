// push constant 111
@111
D=A	// D = 111
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 111
// push constant 333
@333
D=A	// D = 333
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 333
// push constant 888
@888
D=A	// D = 888
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 888
// pop pointer 8
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@StaticTest.8
M=D	// StaticTest.8 = *SP
// pop pointer 3
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@StaticTest.3
M=D	// StaticTest.3 = *SP
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@StaticTest.1
M=D	// StaticTest.1 = *SP
// push static 3
@StaticTest.3
D=M	// D = StaticTest.3
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = StaticTest.3
// push static 1
@StaticTest.1
D=M	// D = StaticTest.1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = StaticTest.1
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// push static 8
@StaticTest.8
D=M	// D = StaticTest.8
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = StaticTest.8
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
