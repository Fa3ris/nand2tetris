// push constant 3030
@3030
D=A	// D = 3030
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 3030
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THIS
M=D	// THIS = *SP
// push constant 3040
@3040
D=A	// D = 3040
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 3040
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@THAT
M=D	// THAT = *SP
// push constant 32
@32
D=A	// D = 32
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 32
// pop this 2
@THIS
D=M	// D = THIS
@2
D=D+A	// D = THIS + 2
@R13
M=D	// R13 = THIS + 2
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THIS + 2
M=D	// *(THIS + 2) = *SP
// push constant 46
@46
D=A	// D = 46
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 46
// pop that 6
@THAT
D=M	// D = THAT
@6
D=D+A	// D = THAT + 6
@R13
M=D	// R13 = THAT + 6
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@R13
A=M	// A = THAT + 6
M=D	// *(THAT + 6) = *SP
// push pointer 0
@THIS
D=M	// D = THIS
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// push pointer 1
@THAT
D=M	// D = THAT
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// push this 2
@THIS
D=M	// D = THIS
@2
A=D+A	// A = THIS + 2
D=M	// D = *(THIS + 2)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THIS + 2)
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// push that 6
@THAT
D=M	// D = THAT
@6
A=D+A	// A = THAT + 6
D=M	// D = *(THAT + 6)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(THAT + 6)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
