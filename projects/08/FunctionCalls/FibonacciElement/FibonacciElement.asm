// bootstrap
@256
D=A
@SP
M=D	// SP = 256
// call Sys.init 0
// save GLOBAL$ret.0
@GLOBAL$ret.0
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = GLOBAL$ret.0
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@5
D=D-A	// D = SP - (5 + 0)
@ARG
M=D	// ARG = SP - (5 + 0)
@Sys.init
0;JMP	// goto Sys.init
(GLOBAL$ret.0)
// function Main.fibonacci 0
(Main.fibonacci)
@0
D=A	// D = 0
(Main.fibonacci_INIT_LOOP)
@Main.fibonacci_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Main.fibonacci_INIT_LOOP
0;JMP	// loop
(Main.fibonacci_INIT_END)
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
// push constant 2
@2
D=A	// D = 2
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 2
// lt 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
D=M-D	// D = *(SP - 1) - D
M=0	// *(SP - 1) = false, assume it is false
@LT_END_0
D;JGE	// if D >= 0, it is really false, finish
@SP
A=M-1
M=-1	// else set true
(LT_END_0)
// if-goto IF_TRUE
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@Main.fibonacci$IF_TRUE
D;JNE	// if D then jump to IF_TRUE
// goto IF_FALSE
@Main.fibonacci$IF_FALSE
0;JMP	// jump to IF_FALSE
// label IF_TRUE
(Main.fibonacci$IF_TRUE)
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
// return
// save endFrame
@LCL
D=M	// D = LCL
@R13
M=D	// endFrame = LCL
// save return address
@5
A=D-A	// A = LCL - 5
D=M	// D = *(LCL - 5)
@R14
M=D	// retAddr = *(LCL - 5)
// put return value at ARG[0]
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@ARG
A=M	// A = ARG
M=D	// *ARG = *SP
// restore SP
D=A+1	// D = ARG + 1
@SP
M=D	// SP = ARG + 1
// restore THAT
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THAT
M=D	// THAT = *endFrame
// restore THIS
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THIS
M=D	// THIS = *endFrame
// restore ARG
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@ARG
M=D	// ARG = *endFrame
// restore LCL
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@LCL
M=D	// LCL = *endFrame
// goto return address
@R14
A=M	// A = retAddr
0;JMP	//  goto retAddr
// label IF_FALSE
(Main.fibonacci$IF_FALSE)
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
// push constant 2
@2
D=A	// D = 2
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 2
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// call Main.fibonacci 1
// save Main.fibonacci$ret.0
@Main.fibonacci$ret.0
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Main.fibonacci$ret.0
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@6
D=D-A	// D = SP - (5 + 1)
@ARG
M=D	// ARG = SP - (5 + 1)
@Main.fibonacci
0;JMP	// goto Main.fibonacci
(Main.fibonacci$ret.0)
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
// push constant 1
@1
D=A	// D = 1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 1
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
// call Main.fibonacci 1
// save Main.fibonacci$ret.1
@Main.fibonacci$ret.1
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Main.fibonacci$ret.1
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@6
D=D-A	// D = SP - (5 + 1)
@ARG
M=D	// ARG = SP - (5 + 1)
@Main.fibonacci
0;JMP	// goto Main.fibonacci
(Main.fibonacci$ret.1)
// add
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=D+M	// *(SP - 1) = D + [*(SP - 1)]
// return
// save endFrame
@LCL
D=M	// D = LCL
@R13
M=D	// endFrame = LCL
// save return address
@5
A=D-A	// A = LCL - 5
D=M	// D = *(LCL - 5)
@R14
M=D	// retAddr = *(LCL - 5)
// put return value at ARG[0]
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@ARG
A=M	// A = ARG
M=D	// *ARG = *SP
// restore SP
D=A+1	// D = ARG + 1
@SP
M=D	// SP = ARG + 1
// restore THAT
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THAT
M=D	// THAT = *endFrame
// restore THIS
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@THIS
M=D	// THIS = *endFrame
// restore ARG
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@ARG
M=D	// ARG = *endFrame
// restore LCL
@R13
AM=M-1	// A = --endFrame
D=M	// D = *endFrame
@LCL
M=D	// LCL = *endFrame
// goto return address
@R14
A=M	// A = retAddr
0;JMP	//  goto retAddr
// function Sys.init 0
(Sys.init)
@0
D=A	// D = 0
(Sys.init_INIT_LOOP)
@Sys.init_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Sys.init_INIT_LOOP
0;JMP	// loop
(Sys.init_INIT_END)
// push constant 4
@4
D=A	// D = 4
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 4
// call Main.fibonacci 1
// save Sys.init$ret.0
@Sys.init$ret.0
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.init$ret.0
// save LCL
@LCL
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = LCL
// save ARG
@ARG
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = ARG
// save THIS
@THIS
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THIS
// save THAT
@THAT
D=M
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = THAT
@SP
D=M	// D = SP
@LCL
M=D	// LCL = SP
@6
D=D-A	// D = SP - (5 + 1)
@ARG
M=D	// ARG = SP - (5 + 1)
@Main.fibonacci
0;JMP	// goto Main.fibonacci
(Sys.init$ret.0)
// label WHILE
(Sys.init$WHILE)
// goto WHILE
@Sys.init$WHILE
0;JMP	// jump to WHILE
