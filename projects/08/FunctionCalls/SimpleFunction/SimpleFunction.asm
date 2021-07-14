// function SimpleFunction.test 2
@2
D=A	// D = 2
(SimpleFunction.test_INIT_LOOP)
@SimpleFunction.test_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@SimpleFunction.test_INIT_LOOP
0;JMP	// loop
(SimpleFunction.test_INIT_END)
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
// push local 1
@LCL
D=M	// D = LCL
@1
A=D+A	// A = LCL + 1
D=M	// D = *(LCL + 1)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(LCL + 1)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// not
@SP
A=M-1	// A = SP - 1
M=!M	// *(SP - 1) = ![*(SP - 1)]
// push argument 0
@ARG
D=M	// D = ARG
@0
A=D+A	// A = ARG + 0
D=M	// D = *(ARG + 0)
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = *(ARG + 0)
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
// return
@LCL
D=M	// D = LCL
@R13
M=D	// endFrame = LCL
@5
D=D-A	// D = LCL - 5
@R14
M=D	// retAddr = LCL - 5
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@ARG
A=M	// A = ARG
M=D	// *ARG = *SP
D=A+1	// D = ARG + 1
@SP
M=D	// SP = ARG + 1
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THAT
M=D	// THAT = *endFrame
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THIS
M=D	// THIS = *endFrame
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@ARG
M=D	// ARG = *endFrame
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@LCL
M=D	// LCL = *endFrame
@R14
A=M	// A = retAddr
0;JMP	//  goto retAddr
