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
// function Class1.set 0
(Class1.set)
@0
D=A	// D = 0
(Class1.set_INIT_LOOP)
@Class1.set_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Class1.set_INIT_LOOP
0;JMP	// loop
(Class1.set_INIT_END)
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
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@Class1.0
M=D	// Class1.0 = *SP
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
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@Class1.1
M=D	// Class1.1 = *SP
// push constant 0
@0
D=A	// D = 0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 0
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
// function Class1.get 0
(Class1.get)
@0
D=A	// D = 0
(Class1.get_INIT_LOOP)
@Class1.get_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Class1.get_INIT_LOOP
0;JMP	// loop
(Class1.get_INIT_END)
// push static 0
@Class1.0
D=M	// D = Class1.0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Class1.0
// push static 1
@Class1.1
D=M	// D = Class1.1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Class1.1
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
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
// function Class2.set 0
(Class2.set)
@0
D=A	// D = 0
(Class2.set_INIT_LOOP)
@Class2.set_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Class2.set_INIT_LOOP
0;JMP	// loop
(Class2.set_INIT_END)
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
// pop pointer 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@Class2.0
M=D	// Class2.0 = *SP
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
// pop pointer 1
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@Class2.1
M=D	// Class2.1 = *SP
// push constant 0
@0
D=A	// D = 0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 0
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
// function Class2.get 0
(Class2.get)
@0
D=A	// D = 0
(Class2.get_INIT_LOOP)
@Class2.get_INIT_END
D;JEQ	// end if pushed all local vars
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=0	// push constant 0
D=D-1
@Class2.get_INIT_LOOP
0;JMP	// loop
(Class2.get_INIT_END)
// push static 0
@Class2.0
D=M	// D = Class2.0
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Class2.0
// push static 1
@Class2.1
D=M	// D = Class2.1
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Class2.1
// sub
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
A=A-1	// A = SP - 1
M=M-D	// *(SP - 1) = *(SP - 1) - D
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
// push constant 6
@6
D=A	// D = 6
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 6
// push constant 8
@8
D=A	// D = 8
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 8
// call Class1.set 2
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
@7
D=D-A	// D = SP - (5 + 2)
@ARG
M=D	// ARG = SP - (5 + 2)
@Class1.set
0;JMP	// goto Class1.set
(Sys.init$ret.0)
// pop temp 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@5	// A = 5 + 0
M=D	// *(5 + 0) = *SP
// push constant 23
@23
D=A	// D = 23
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 23
// push constant 15
@15
D=A	// D = 15
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = 15
// call Class2.set 2
// save Sys.init$ret.1
@Sys.init$ret.1
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.init$ret.1
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
@7
D=D-A	// D = SP - (5 + 2)
@ARG
M=D	// ARG = SP - (5 + 2)
@Class2.set
0;JMP	// goto Class2.set
(Sys.init$ret.1)
// pop temp 0
@SP
AM=M-1	// A = --SP
D=M	// D = *SP
@5	// A = 5 + 0
M=D	// *(5 + 0) = *SP
// call Class1.get 0
// save Sys.init$ret.2
@Sys.init$ret.2
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.init$ret.2
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
@Class1.get
0;JMP	// goto Class1.get
(Sys.init$ret.2)
// call Class2.get 0
// save Sys.init$ret.3
@Sys.init$ret.3
D=A
@SP
AM=M+1	// A = ++SP
A=A-1	// A = SP - 1
M=D	// *(SP - 1) = Sys.init$ret.3
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
@Class2.get
0;JMP	// goto Class2.get
(Sys.init$ret.3)
// label WHILE
(Sys.init$WHILE)
// goto WHILE
@Sys.init$WHILE
0;JMP	// jump to WHILE
